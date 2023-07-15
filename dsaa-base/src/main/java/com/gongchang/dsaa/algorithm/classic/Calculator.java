package com.gongchang.dsaa.algorithm.classic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 计算器
 * 
 * 介绍：输入一个字符串表达式，解析之后计算表达式的结果并返回
 * 
 * 1.可以使用中缀表达式（操作符在数字的中间，比如2 * 3 + 5）直接解析计算
 * 2.可以将中缀表达式转换为后缀表达式（操作符在数字的后面，比如2 3 * 5 +）后再计算，后缀表达式更适合做计算
 * 
 * 这里使用第二种方式（因为将中缀表达式转换为后缀表达式的过和第一种方式的计算过程基本相同，所以第一种方式不在给出案例）并加上解释器模式给出一个案例
 * 
 * @Author	gongchang
 */
public class Calculator {

	public static void main(String[] args) {
		String expression = "((2+3)*(3+5-1))/5+(2*5-3)";
		Calculator calculator = new Calculator();
		int calResult = calculator.calculate(expression);
		System.out.println("表达式"+expression+"计算结果是："+calResult);
	}
	
	
	private static final char ADD ='+';
	private static final char SUB = '-';
	private static final char MUL = '*';
	private static final char DIV = '/';
	private static final char LEFT_PAR = '(';
	private static final char RIGHT_PAR = ')';
	
	private static final int ADD_SUB = 1;
	private static final int MUL_DIV = 2;
	private static final int LEFT_RIGHT_PAR = 3;
	
	
	/**
	 * 中缀表达式计算
	 * 
	 * @param str 中缀表达式
	 * @return 计算结果
	 */
	public int calculate(String infixStr){
		charLegitimacyCheck(infixStr);
		
		List<String> infixExpressionList = infixStrToList(infixStr);
		
		List<String> postfixExpressionList = infixListToPostfixList(infixExpressionList);
		
		return postfixExpressionCal(postfixExpressionList);
	}
	
	/**
	 * 中缀表达式转后缀表达式
	 * 
	 * @param str 中缀表达式列表
	 * @return 后缀表达式列表
	 */
	public List<String> infixListToPostfixList(List<String> infixStrList){
		List<String> postfixList = new ArrayList<String>();
		
		Stack<String> expressStack = new Stack<>();
		
		for (String str : infixStrList) {
			if(isOperator(str.charAt(0))){
				if(str.charAt(0)==LEFT_PAR){
					expressStack.push(str);
				}else if(str.charAt(0)==RIGHT_PAR){
					while(expressStack.peek().charAt(0)!=LEFT_PAR){
						postfixList.add(expressStack.pop());
					}
					expressStack.pop();
				}else{
					char curOperator = str.charAt(0);
					while(!expressStack.isEmpty() && getPriority(expressStack.peek().charAt(0))>getPriority(curOperator) && LEFT_PAR!=expressStack.peek().charAt(0)){
						postfixList.add(expressStack.pop());
					}
					expressStack.push(str);
				}
			}else{
				postfixList.add(str);
			}
		}
		
		while(!expressStack.isEmpty()){
			postfixList.add(expressStack.pop());
		}
		
		return postfixList;
	}
	
	/**
	 * 后缀表达式计算
	 * 
	 * @param str 后缀表达式列表
	 * @return 计算结果
	 */
	public int postfixExpressionCal(List<String> postfixExpressionList){
		Stack<Expression> expressStack = new Stack<>();
		
		for (String str : postfixExpressionList) {
			if(isOperator(str.charAt(0))){
				// 下面两行代码的顺序不能乱
				Expression expression2 = expressStack.pop();
				Expression expression1 = expressStack.pop();
				expressStack.push(new NoTerminalExpression(expression1, str.charAt(0) ,expression2));
			}else{
				expressStack.push(new TerminalExpression(Integer.parseInt(str)));
			}
		}
		
		return expressStack.pop().interpreter();
	}
	
	public int getPriority(char opt){
		int result = 0;
		
		switch (opt) {
		case ADD:
			result = ADD_SUB;
			break;
		case SUB:
			result = ADD_SUB;
			break;
		case MUL:
			result = MUL_DIV;
			break;
		case DIV:
			result = MUL_DIV;
			break;
		case LEFT_PAR:
			result = LEFT_RIGHT_PAR;
			break;
		case RIGHT_PAR:
			result = LEFT_RIGHT_PAR;
			break;
		}
		
		return result;
	}
	
	/**
	 * 字符合法性检查
	 * 
	 * @param str
	 */
	public void charLegitimacyCheck(String str){
		for(int i=0; i<str.length(); i++){
			char charAt = str.charAt(i);
			if(!isNumber(charAt) && !isOperator(charAt)){
				throw new IllegalArgumentException("表达式第"+(i+1)+"个字符"+charAt+"属于非法字符");
			}
		}
	}
	
	/**
	 * 中缀表达式字符串转换为中缀列表
	 * 
	 * @param infixStr 中缀表达式字符串
	 * @return 中缀表达式列表
	 */
	public List<String> infixStrToList(String infixStr){
		List<String> resultList = new ArrayList<String>();
		char[] charArray = infixStr.toCharArray();
		StringBuilder sb = null;
		char endChar = charArray[0];
		for (char c : charArray) {
			endChar = c;
			
			if(isNumber(c)){
				if(sb==null){
					sb = new StringBuilder();
				}
				sb.append(c);
			}else if(isOperator(c)){
				
				if(sb!=null){
					resultList.add(sb.toString());
					sb = null;
				}
				resultList.add(String.valueOf(c));
			}
		}
		if(!isOperator(endChar)){
			resultList.add(String.valueOf(endChar));
		}
		
		return resultList;
	}
	
	public Boolean isNumber(char c){
		if((int)c>=48 && (int)c<=57){
			return true;
		}
		return false;
	}
	
	public Boolean isOperator(char c){
		if(c==ADD|c==SUB|c==MUL|c==DIV|c==LEFT_PAR|c==RIGHT_PAR){
			return true;
		}
		return false;
	}
	
	private interface Expression{
		
		abstract int interpreter();
		
		public default int unitCal(char opt, Expression left, Expression right){
			int result = 0;
			
			switch (opt) {
			case ADD:
				result = left.interpreter()+right.interpreter();
				break;
			case SUB:
				result = left.interpreter()-right.interpreter();
				break;
			case MUL:
				result = left.interpreter()*right.interpreter();
				break;
			case DIV:
				result = left.interpreter()/right.interpreter();
				break;
			}	
			
			return result;
		}
		
	}
	
	private class NoTerminalExpression implements Expression{
		Expression left;
		char operator;
		Expression right;
		
		public NoTerminalExpression(Expression left, char operator, Expression right) {
			super();
			this.left = left;
			this.operator = operator;
			this.right = right;
		}

		@Override
		public int interpreter() {
			return unitCal(operator, left, right);
		}
	}
	
	private class TerminalExpression implements Expression{

		int number;
		
		public TerminalExpression(int number) {
			this.number = number;
		}

		@Override
		public int interpreter() {
			return number;
		}
		
	}
}
