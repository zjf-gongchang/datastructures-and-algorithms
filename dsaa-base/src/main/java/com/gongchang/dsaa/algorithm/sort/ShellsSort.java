package com.gongchang.dsaa.algorithm.sort;

import java.util.Arrays;

/**
 * 希尔排序，又叫做缩小增量排序
 * 
 * 排序类型：内部排序
 * 
 * 核心思想：
 * 	解决直接插入排序中如果最小值在后面，移动次数多的问题。
 * 	比如：数组arr = {2,3,4,5,6,7,8,9,1}；
 * 	某一时刻的有序表包含23456789，无序表中是1，这是需要将无序表中的1插入到有序表中，有序表的移动次数会很大；
 * 	如果这个arr数组的数据量非常大，这种移动次数的开销会更加明显。
 * 
 * 时间复杂度：O(n^s)<1s<2
 * 
 * @author GongChang
 *
 */
public class ShellsSort {

	public static void main(String[] args) {
		int[] intArr = {1,3,56,-2,-2,-78,332,-2,42};
		
		System.out.println("排序前：");
		System.out.println(Arrays.toString(intArr));
		
		shellsSort1(intArr);
//		shellsSort2(intArr);
		
		System.out.println("排序后：");
		System.out.println(Arrays.toString(intArr));
	}
	
	/**
	 * 交换法实现希尔排序
	 * 
	 * @param intArr
	 */
	private static void shellsSort1(int[] intArr) {
		int tail = intArr.length-1;
		
		int gap = intArr.length/2;
		while(gap>0) {
			for(int i=gap; i<=tail; i++){
				for(int j=i; j-gap>=0; j-=gap) {
					if(intArr[j]<intArr[j-gap]) {
						// 下面三行代码体现了交换法的核心
						int temp = intArr[j];
						intArr[j] = intArr[j-gap];
						intArr[j-gap] = temp;
					}
				}
			}
			gap = gap/2;
		}
	}
	
	/**
	 * 移动法实现希尔排序
	 * 
	 * @param intArr
	 */
	private static void shellsSort2(int[] intArr) {
		int tail = intArr.length-1;
		
		int gap = intArr.length/2;
		while(gap>0) {
			for(int i=gap; i<=tail; i++){
				int insertValue = intArr[i];
				int insertIndex = i;
				for(int j=i; j-gap>=0; j-=gap) {
					if(intArr[j-gap]>insertValue) {
						intArr[j] = intArr[j-gap];
						insertIndex-=gap;
					}else {
						break;
					}
				}
				if(insertIndex!=i) {
					intArr[insertIndex] = insertValue;
				}
			}
			
			gap = gap/2;
		}
	}
	
}
