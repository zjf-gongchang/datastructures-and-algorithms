package com.gongchang.dsaa.algorithm.sort;

import java.util.Arrays;

/**
 * 堆排序
 * 
 * 排序类型：内部排序
 * 
 * 核心思想：
 * 	循环n次，每次构建一个堆，然后将堆顶的元素和尾部未参与排序的元素交换
 * 	从大到小排序就构建大顶堆，每次将堆顶的元素往后移动
 * 	从小到大排序就构建小顶堆，每次将堆顶的元素往后移动
 * 
 * 最坏时间复杂度：O(nlogn)
 * 
 * @author GongChang
 *
 */
public class HeapSort {

	public static void main(String[] args) {
		int[] intArr = {1,5,2,7,8};
		System.out.println("=======================原数组");
		System.out.println(Arrays.toString(intArr));
		
		HeapSort heapSort = new HeapSort();
		
		heapSort.heapSort(intArr, true);
		System.out.println("=======================从大到小排序");
		System.out.println(Arrays.toString(intArr));
		
		heapSort.heapSort(intArr, false);
		System.out.println("=======================从小到大排序");
		System.out.println(Arrays.toString(intArr));
	}
	
	/**
	 * 堆排序方法
	 * 
	 * @param intArr 待排序数组
	 * @param bool true-从大到小， false-从小到大
	 */
	private void heapSort(int[] intArr, Boolean bool) {
		if(bool) {
			buildBigTopHeap(intArr);
			for(int i=intArr.length-1; i>0; i--) {
				int temp = intArr[i];
				intArr[i] = intArr[0];
				intArr[0] = temp;
				buildBigTopHeap(intArr, 0, i);
			}
		}else {
			buildSmallTopHeap(intArr);
			for(int i=intArr.length-1; i>0; i--) {
				int temp = intArr[i];
				intArr[i] = intArr[0];
				intArr[0] = temp;
				buildSmallTopHeap(intArr, 0, i);
			}
		}
	}
	
	private void buildBigTopHeap(int[] intArr) {
		int intArrLength = intArr.length;
		for(int i=intArrLength/2-1; i>=0; i--) {
			buildBigTopHeap(intArr, i, intArrLength);
		}
	}
	
	private void buildBigTopHeap(int[] intArr, int startIndex, int arrLength) {
		int temp = intArr[startIndex];
		for(int m=2*startIndex+1; m<arrLength; m=2*m+1) {
			// 比较左右节点的大小，选出较大的
			if(m+1<arrLength&&intArr[m]<intArr[m+1]) {
				m=m+1;
			}
			// 与进入该方法时最顶端的值比较，若大则往上移，若小于等于则说明找到了顶端值的真实位置，跳出循环
			if(temp<intArr[m]) {
				intArr[startIndex] = intArr[m];
				startIndex = m;
			}else {
				break;
			}
		}
		// 将顶端的值复制到正确的位置上
		intArr[startIndex] = temp;
	}
	
	private void buildSmallTopHeap(int[] intArr) {
		int intArrLength = intArr.length;
		for(int i=intArrLength/2-1; i>=0; i--) {
			buildSmallTopHeap(intArr, i, intArrLength);
		}
	}
	
	private void buildSmallTopHeap(int[] intArr, int startIndex, int arrLength) {
		int temp = intArr[startIndex];
		for(int m=2*startIndex+1; m<arrLength; m=2*m+1) {
			// 比较左右节点的大小，选出较小的
			if(m+1<arrLength&&intArr[m]>intArr[m+1]) {
				m=m+1;
			}
			// 与进入该方法时最顶端的值比较，若小则往上移，若大于等于则说明找到了顶端值的真实位置，跳出循环
			if(temp>intArr[m]) {
				intArr[startIndex] = intArr[m];
				startIndex = m;
			}else {
				break;
			}
		}
		// 将顶端的值复制到正确的位置上
		intArr[startIndex] = temp;
	}
	
}
