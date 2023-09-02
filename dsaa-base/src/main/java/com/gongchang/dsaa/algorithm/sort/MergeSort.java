package com.gongchang.dsaa.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序
 * 
 * 排序类型：内部排序
 * 
 * 核心思想：
 * 	分治思想+两路归并
 * 
 * 	什么是两路归并：若有左右两个数组（均有序），分别从里面取出一个值，比较大小，哪个值小就将哪个值排到新数组的前面，然后移动小元素数组的指针
 * 	
 * 	分：归并排序的核心是参与归并的数组必须都是有序的，为了实现这个目的，将原始数组递归拆分成只含有一个元素的数组，只含有一个元素
 * 	治：从递归退出开始回溯，每回溯一层执行一次两路归并，然后将归并后的结果复制到原数组对应区间中
 * 
 * 最坏时间复杂度：O(nlogn)
 * 
 * @author GongChang
 *
 */
public class MergeSort {

	public static void main(String[] args) {
		int[] intArr = {1,3,56,-2,-2,-78,332,-2,42};
		
		System.out.println("排序前：");
		System.out.println(Arrays.toString(intArr));
		
		int[] temp = new int[intArr.length];
		mergeSort(intArr, 0, intArr.length-1, temp);
		
		System.out.println("排序后：");
		System.out.println(Arrays.toString(intArr));
	}
	
	private static void mergeSort(int[] intArr, int left, int right, int[] temp) {
		// 递归退出条件
		if(left==right) {
			return;
		}
		// 分
		int midIndex = left+(right-left)/2;
		mergeSort(intArr, left, midIndex, temp);
		mergeSort(intArr, midIndex+1, right, temp);
		
		// 治
		int tempIndex = 0;
		int leftStart = left;
		int rightStart = midIndex+1;
		while(leftStart<=midIndex&&rightStart<=right) {
			if(intArr[leftStart]<intArr[rightStart]) {
				temp[tempIndex++]=intArr[leftStart++];
			}else {
				temp[tempIndex++]=intArr[rightStart++];
			}
		}
		while(leftStart<=midIndex) {
			temp[tempIndex++]=intArr[leftStart++];
		}
		while(rightStart<=right) {
			temp[tempIndex++]=intArr[rightStart++];
		}
		for(int i=left,j=0; i<=right; i++,j++) {
			intArr[i] = temp[j];
		}
	}
}
