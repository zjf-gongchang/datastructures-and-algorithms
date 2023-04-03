package com.gongchang.dsaa.datastructure.tree.binarytree;

import java.util.Arrays;

/**
 * 二叉树是可以使用数组来表示的，一颗二叉树按照从上到下，从左到右的顺序标识在数组中的索引，那么满足如下规则：
 * 父节点的索引为i时，左子节点的索引是：2*i+1，右子节点的索引是：2*i+2
 * 注：索引下标从0开始计数
 * 
 * 堆是一颗完全二叉树
 * 
 * 堆构建逻辑：从最后一个非叶子节点开始，自右向左，自下而上，最后一个非叶子节点的下标是：intArr.length/2-1
 * 
 * 大顶堆：父节点的值大于等于子节点的值
 * 小顶堆：父节点的值小于等于子节点的值
 * 
 * 
 * @author GongChang
 *
 */
public class Heap {

	public static void main(String[] args) {
		int[] intArr = {1,5,2,7,8};
		System.out.println("=======================原数组");
		System.out.println(Arrays.toString(intArr));
		
		Heap bigOrSmallTopHeap = new Heap();
		
		bigOrSmallTopHeap.buildBigTopHeap(intArr);
		System.out.println("=======================大顶堆");
		System.out.println(Arrays.toString(intArr));
		
		bigOrSmallTopHeap.buildSmallTopHeap(intArr);
		System.out.println("=======================小顶堆");
		System.out.println(Arrays.toString(intArr));
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
