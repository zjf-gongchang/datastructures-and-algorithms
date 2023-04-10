package com.gongchang.dsaa.algorithm.recursion;

/**
 * 递归
 * 
 * 递归就是在方法中调用方法，能用栈来解决的问题使用递归会是代码变得简洁
 * 使用的递归的地方有很多，比如：快速排序，希尔排序，归并排序，二分查找，打印问题，阶乘问题，汉诺塔问题，八皇后等
 * 递归必须向着递归退出的条件逼近，否则迟早会出现内存溢出
 * 
 * @author zhangjifu
 *
 */
public class Recursion {

	public static void main(String[] args) {
		Recursion recursion = new Recursion();
		recursion.recursion(10);
	}
	
	public void recursion(int value){
		// 递归退出条件
		if(value<=0){
			System.out.println("==============");
			return;
		}
		
		value--;
		System.out.println(value);
		
		recursion(value);
		
		System.out.println(value);
	}
}
