package com.gongchang.dsaa.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序
 * 
 * 排序类型：内部排序
 * 
 * 核心思想：
 * 	将一个数组通过一个中间位置的值midValue分成左右两部分，做多次交换
 * 	每次交换是从左边部分的数组找到一个大于等于midValue的值，从右边部分的数组找到一个小于等于midValue的值，将找到的两个值交换
 * 	这样多次交换之后，midValue左边的都是大于等于它的值，midValue右边的都是小于等于它的值
 * 
 * 	然后将左右两部分数组分别作为新的数组继续执行上面的操作，知道数组不可再分
 * 
 * 最坏时间复杂度：O(n2)
 * 
 * @author GongChang
 *
 */
public class QuickSort {

	public static void main(String[] args) {
		int[] intArr = {1,3,56,-2,-2,-78,332,-2,42};
		
		System.out.println("排序前：");
		System.out.println(Arrays.toString(intArr));
		
		quickSort1(intArr, 0, intArr.length-1);
		
		System.out.println("排序后：");
		System.out.println(Arrays.toString(intArr));
	}
	
	private static void quickSort1(int[] intArr, int left, int right) {
		if(left==right) {
			return;
		}
		
		int midValue = intArr[left+(right-left)/2];
		int start = left;
		int end = right;
		while(start<end) {
			while(intArr[start]<midValue) {
				start++;
			}
			while(intArr[end]>midValue) {
				end--;
			}
			
			if(start==end) {
				break;
			}
			
			if(intArr[start]!=intArr[end]) {
				int temp = intArr[end];
				intArr[end] = intArr[start];
				intArr[start] = temp;
			}
			
			if(intArr[start]==midValue) {
				end--;
			}
			if(intArr[end]==midValue) {
				start++;
			}
		}
		
		if(left<end) {
			quickSort1(intArr, left, end-1);
		}
		if(right>start) {
			quickSort1(intArr, start+1, right);
		}
	}
	
}
