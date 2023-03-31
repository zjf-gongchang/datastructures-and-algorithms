package com.gongchang.dsaa.algorithm.sort;

import java.util.Arrays;


/**
 * 选择排序
 * 
 * 排序类型：内部排序
 * 
 * 核心思想：
 * 	若有n个元素，则进行n-1轮排序
 * 	每一轮排序中将最大的值移动到参与本轮排序的数组的尾部
 * 
 * 时间复杂度O(n2)
 * 
 * @author GongChang
 *
 */
public class SelectionSort {

	public static void main(String[] args) {
		int[] intArr = {1,3,56,-2,-2,-78,332,-2,42};
		
		System.out.println("排序前：");
		System.out.println(Arrays.toString(intArr));
		
		selectionSort1(intArr);
		selectionSort2(intArr);
		
		System.out.println("排序后：");
		System.out.println(Arrays.toString(intArr));
	}
	
	/**
	 * 后移实现选择排序
	 * 
	 * @param intArr 待排序数组
	 */
	private static void selectionSort1(int[] intArr){
		int tail = intArr.length-1;
		for(int i=0; i<=tail-1; i++) {
			int subTail = tail-i;
			int maxIndex = 0;
			for(int j=1; j<=tail-i; j++) {
				if(intArr[j]>intArr[maxIndex]) {
					maxIndex = j;
				}
			}
			// 这里优化性能，如果最大值就是在本轮排序的尾部，则不进行交换，重新开始下一轮循环即可；否则交换位置
			if(maxIndex!=subTail) {
				int temp = intArr[maxIndex];
				intArr[maxIndex] = intArr[subTail];
				intArr[subTail] = temp;
			}
		}
	}
	
	/**
	 * 前移实现选择排序
	 * 
	 * @param intArr 待排序数组
	 */
	private static void selectionSort2(int[] intArr) {
		int tail = intArr.length-1;
		for(int i=0; i<=tail; i++) {
			int minIndex = i;
			for(int j=i+1; j<=tail; j++) {
				if(intArr[j]<intArr[minIndex]) {
					minIndex = j;
				}
			}
			if(minIndex!=i) {
				int temp = intArr[minIndex];
				intArr[minIndex] = intArr[i];
				intArr[i] = temp;
			}
		}
	}
	
}
