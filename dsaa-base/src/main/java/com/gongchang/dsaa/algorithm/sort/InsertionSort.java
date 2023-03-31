package com.gongchang.dsaa.algorithm.sort;

import java.util.Arrays;

/**
 * 插入排序
 * 
 * 排序类型：内部排序
 * 
 * 核心思想：
 * 	将数组分成有序和无序两部分，初始状态下：下标0独立作为有序数组的一部分，剩于的部分作为无序数组的一部分
 * 	若有n个元素，则进行n-1轮排序
 * 	每一轮从无序数组的头部取一个元素插入到有序数组中
 * 
 * 时间复杂度：O(n2)
 * 
 * @author GongChang
 *
 */
public class InsertionSort {

	public static void main(String[] args) {
		int[] intArr = {1,3,56,-2,-2,-78,332,-2,42};
		
		System.out.println("排序前：");
		System.out.println(Arrays.toString(intArr));
		
		insertionSort1(intArr);
//		insertionSort2(intArr);
		
		System.out.println("排序后：");
		System.out.println(Arrays.toString(intArr));
	}
	
	/**
	 * 交换法实现插入排序
	 * 
	 * @param intArr 待排序数组
	 */
	private static void insertionSort1(int[] intArr) {
		int tail = intArr.length-1;
		for(int i=1; i<=tail; i++){
			for(int j=i; j-1>=0; j--) {
				if(intArr[j]<intArr[j-1]) {
					// 下面三行代码体现了交换法的核心
					int temp = intArr[j];
					intArr[j] = intArr[j-1];
					intArr[j-1] = temp;
				}
			}
		}
	}
	
	/**
	 * 移动法实现插入排序
	 * 
	 * @param intArr 待排序数组
	 */
	private static void insertionSort2(int[] intArr) {
		int tail = intArr.length-1;
		for(int i=1; i<=tail; i++){
			int insertValue = intArr[i];
			int insertIndex = i;
			for(int j=i; j-1>=0; j--) {
				if(intArr[j-1]>insertValue) {
					intArr[j] = intArr[j-1];
					insertIndex--;
				}else {
					break;
				}
			}
			if(insertIndex!=i) {
				intArr[insertIndex] = insertValue;
			}
		}
	}
	
}
