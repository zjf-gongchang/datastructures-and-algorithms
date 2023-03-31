package com.gongchang.dsaa.algorithm.find;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 二分查找
 * 
 * @author GongChang
 *
 */
public class BinarySearch {

	
	public static void main(String[] args) {
		
		Integer[] intArr = {1,3,2,55,42,-23,24,-78};
		
		System.out.println("排序前的数组：");
		arrPrint(intArr);
		arrSort(intArr);
		System.out.println("排序后的数组：");
		arrPrint(intArr);
		
		int findValue = 42;
		binarySearch(intArr, findValue, MethodInvokeType.RECURSION);
		binarySearch(intArr, findValue, MethodInvokeType.LOOP);
		
	}
	
	private enum MethodInvokeType{
		RECURSION,
		
		LOOP;
	}
	
	private static void binarySearch(Integer[] intArr, Integer findValue, MethodInvokeType methodInvokeType) {
		System.out.println("查找方式为："+methodInvokeType.name());
		System.out.println("查找的数据是："+findValue);
		
		int index = -1;
		switch (methodInvokeType) {
		case RECURSION:
			index = recursionBinarySearch(intArr, 0, intArr.length-1, findValue);
			break;
		case LOOP:
			index = loopBinarySearch(intArr, 0, intArr.length-1, findValue);
			break;
		default:
			System.out.println("尚未实现的查找方式");
			return;
		}
		
		
		if(index!=-1) {
			System.out.println("搜索到的数据对应的下标为："+index);
		}else {
			System.out.println("没有搜索到相应的数据");
		}
	}
	
	private static void arrSort(Integer[] intArr) {
		Arrays.sort(intArr, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1>o2) {
					return 1;
				}else if(o1<o2) {
					return -1;
				}else {
					return 0;
				}
			}
		});
	}
	
	private static void arrPrint(Integer[] arr) {
		System.out.println("数组打印开始===>");
		int length = arr.length;
		for (int i=0; i< length; i++) {
			System.out.print(arr[i]);
			if(i<length-1) {
				System.out.print(",");
			}else {
				System.out.println();
			}
		}
		System.out.println("数组打印完成===<");
		System.out.println();
	}
	
	private static int recursionBinarySearch(Integer[] arr, int left, int right, int findValue) {
		// 这里的findValue<arr[left] || findValue>arr[right]可以省略，并且不会有任何的问题
		// 加在这里是为了提高查找效率
		// 注意：插值查找中这个判断是不能省略的，否则会下标越界
		if(left>right || findValue<arr[left] || findValue>arr[right]) {
			return -1;
		}
		
		int mid = left+(right-left)/2;
		if(arr[mid]<findValue) {
			return recursionBinarySearch(arr, mid+1, right, findValue);
		}else if(arr[mid]>findValue) {
			return recursionBinarySearch(arr, left, mid-1, findValue);
		}else {
			return mid;
		}
	}
	
	private static int loopBinarySearch(Integer[] arr, int left, int right, int findValue) {
		// 这里的findValue<arr[left] || findValue>arr[right]可以省略，并且不会有任何的问题
		// 加在这里是为了提高查找效率
		// 注意：插值查找中这个判断是不能省略的，否则会下标越界
		if(findValue<arr[left] || findValue>arr[right]) {
			return -1;
		}
		
		while(left<=right) {
			int mid = left + (right-left)/2;
			if(arr[mid]<findValue) {
				left = mid+1;
			}else if(arr[mid]>findValue) {
				right = mid-1;
			}else {
				return mid;
			}
		}
		return -1;
	}

	
}


