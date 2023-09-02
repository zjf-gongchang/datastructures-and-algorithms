package com.gongchang.dsaa.algorithm.sort;

import java.util.Arrays;

/**
 * 基数排序
 * 
 * 排序类型：内部排序
 * 
 * 核心思想：
 * 	取数组中最大值的长度（maxLength）作为数组中所有数的长度，位数不够的前面补0；
 * 	从个位开始遍历，直到高位结束,一共遍历maxLength次；
 * 	每次遍历中：
 * 		循环整个数组，取出每个元素对应位置上的值对桶的个数取余，得到桶的索引，然后将元素放到桶里
 * 		数组循环完成之后再将桶里的元素放回原数组
 * 
 * 最坏时间复杂度；O(n*k)，k为桶的个数
 * 
 * @author GongChang
 *
 */
public class RadixSort {
	
	public static void main(String[] args) {
		int[] intArr = {1,3,56,2,2,78,332,2,42};
		
		System.out.println("排序前：");
		System.out.println(Arrays.toString(intArr));
		
		int[] temp = new int[intArr.length];
		radixSort(intArr);
		
		System.out.println("排序后：");
		System.out.println(Arrays.toString(intArr));
	}
	
	private static void radixSort(int[] intArr) {
		// 找到数组中最大的值
		int max = intArr[0];
		for(int i=1; i<intArr.length; i++) {
			if(intArr[i]>max) {
				max=intArr[i];
			}
		}
		// 创建桶
		int[][] bucket = new int[10][intArr.length];
		int[] bucketEleCount = new int[10];
		// 获取最大值的长度
		int maxLength=(max+"").length();
		// 排序操作
		for(int i=0,m=1; i<maxLength; i++,m*=10){
			// 分桶
			for(int j=0; j<intArr.length; j++) {
				int bucketIndex = intArr[j]/m%10;
				bucket[bucketIndex][bucketEleCount[bucketIndex]++] = intArr[j];
			}
			// 复制到原数组
			int intArrIndex=0;
			for(int j=0; j<bucket.length; j++){
				if(bucketEleCount[j]>0) {
					for(int k=0; k<bucketEleCount[j]; k++) {
						intArr[intArrIndex++]=bucket[j][k];
					}
				}
				bucketEleCount[j]=0;
			}
		}
		
	}
	
}
