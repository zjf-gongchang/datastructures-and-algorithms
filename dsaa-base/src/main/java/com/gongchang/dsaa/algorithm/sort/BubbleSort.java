package com.gongchang.dsaa.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 
 * 排序类型：内部排序
 * 
 * 核心思想：
 * 	若有n个元素，则进行n-1轮排序，
 * 	每一轮排序中：从数组头部开始比较相邻两个元素的大小，将较大的值往后换，这样每轮排序都会把本轮排序的最大值交换到本轮参与排序数组的最后面
 * 	注意：每轮排序中参与排序的数组大小 = 上轮排序中参与排序的数组大小-1
 * 
 * 时间复杂度O(n2)
 * 
 * @author GongChang
 *
 */
public class BubbleSort {

	public static void main(String[] args) {
		int[] intArr = {1,3,56,-2,-2,-78,332,-2,42};
		
		System.out.println("排序前：");
		System.out.println(Arrays.toString(intArr));
		
		bubbleSort(intArr);
		
		System.out.println("排序后：");
		System.out.println(Arrays.toString(intArr));
		
	}
	
	private static void bubbleSort(int[] intArr) {
		int temp;
		int tail = intArr.length-1;
		for(int i=0; i<=tail-1; i++) {
			Boolean isComplete = true;
			for(int j=0; j<tail-i; j++) {
				if(intArr[j]>intArr[j+1]) {
					temp = intArr[j];
					intArr[j] = intArr[j+1];
					intArr[j+1] = temp;
					
					isComplete = false;
				}
			}
			// 优化排序，如果数组已经是有序的就不用再进入下一轮循环了
			if(isComplete){
				return;
			}
		}
	}
	
}
