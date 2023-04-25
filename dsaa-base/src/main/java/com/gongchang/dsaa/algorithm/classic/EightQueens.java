package com.gongchang.dsaa.algorithm.classic;

import java.util.Arrays;

/**
 * 八皇后
 * 
 * 介绍：在一个8*8的棋盘上放置8个皇后，要求任意两个皇后不在同一行，不在同一列，不在同一斜线上
 * 
 * 一共有92中摆法
 * 
 * @Author	gongchang
 */
public class EightQueens {

	public static void main(String[] args) {
		EightQueens eightQueens = new EightQueens(8);
		eightQueens.placeQueen(0);
		System.out.println(eightQueens.getCount());
	}
	
	
	/**
	 * 用来计数，一共有多少种摆放方式
	 */
	int count;
	
	/**
	 * 棋盘大小
	 */
	int length;
	
	/**
	 * 棋盘
	 */
	int[][] chessboard;
	
	/**
	 * 存储皇后摆放的位置；下标表示行号，下标值表示列号
	 */
	int[] queenPos;
	
	
	public EightQueens(int length) {
		this.length = length;
		this.chessboard = new int[length][length];
		this.queenPos = new int[length];
		
		Arrays.fill(queenPos, Integer.MAX_VALUE);
	}
	

	public void placeQueen(int num){
		// 进入if判断说明现在已经要摆放第length+1个皇后了（因为下标是从0开始的），length个皇后已经摆放完成，退出递归
		if(num == this.length){
			count++;
			return;
		}
		
		
		for(int i=0; i<this.length; i++){
			queenPos[num] = i;
			if(isSatisfyTheRule(num, i)){
				placeQueen(num+1);
			}
		}
	}
	
	/**
	 * 判断是否满足皇后的摆放规则
	 * 
	 * @param row 行值
	 * @param col 列值
	 * @return true-满足，false-不满足
	 */
	private Boolean isSatisfyTheRule(int row, int col){
		for(int i=0; i<row; i++){
			// 每一行放一个皇后，肯定不在同一行，所以不用判断是否在同一行
			// col==queenPos[i]判断是否在同一列
			// Math.abs(row-i)==Math.abs(col-queenPos[i])判断是否在同一斜线
			if(col==queenPos[i] || Math.abs(row-i)==Math.abs(col-queenPos[i])){
				return false;
			}
		}
		return true;
	}

	public int getCount() {
		return count;
	}
	
}
