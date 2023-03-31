package com.gongchang.dsaa.algorithm.find;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 插值查找 插值查找跟适合分布比较均匀的数据
 * 
 * 插值查找与二分查找的不同之处在于系数变了，将1/2变成了(findValue-arr[left])/(arr[right]-arr[left])
 * 
 * 二分查找的mid计算方式是：int mid = left + 1/2 * (right-left); 
 * 插值查找的mid计算方式是：int mid = left + (findValue-arr[left])/(arr[right]-arr[left])* (right-left);
 * 
 * @author GongChang
 *
 */
public class InterpolationSearch {

	public static void main(String[] args) {

		Integer[] intArr = { 1, 3, 2, 55, 42, -23, 24, -78 };

		System.out.println("排序前的数组：");
		arrPrint(intArr);
		arrSort(intArr);
		System.out.println("排序后的数组：");
		arrPrint(intArr);

		int findValue = 42;
		interpolationSearch(intArr, findValue, MethodInvokeType.RECURSION);
		interpolationSearch(intArr, findValue, MethodInvokeType.LOOP);

	}

	private enum MethodInvokeType {
		RECURSION,

		LOOP;
	}

	private static void interpolationSearch(Integer[] intArr, Integer findValue, MethodInvokeType methodInvokeType) {
		System.out.println("查找方式为：" + methodInvokeType.name());
		System.out.println("查找的数据是：" + findValue);

		int index = -1;
		switch (methodInvokeType) {
		case RECURSION:
			index = recursionBinarySearch(intArr, 0, intArr.length - 1, findValue);
			break;
		case LOOP:
			index = loopBinarySearch(intArr, 0, intArr.length - 1, findValue);
			break;
		default:
			System.out.println("尚未实现的查找方式");
			return;
		}

		if (index != -1) {
			System.out.println("搜索到的数据对应的下标为：" + index);
		} else {
			System.out.println("没有搜索到相应的数据");
		}
	}

	private static void arrSort(Integer[] intArr) {
		Arrays.sort(intArr, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 > o2) {
					return 1;
				} else if (o1 < o2) {
					return -1;
				} else {
					return 0;
				}
			}
		});
	}

	private static void arrPrint(Integer[] arr) {
		System.out.println("数组打印开始===>");
		int length = arr.length;
		for (int i = 0; i < length; i++) {
			System.out.print(arr[i]);
			if (i < length - 1) {
				System.out.print(",");
			} else {
				System.out.println();
			}
		}
		System.out.println("数组打印完成===<");
		System.out.println();
	}

	private static int recursionBinarySearch(Integer[] arr, int left, int right, int findValue) {
		// 这里的findValue < arr[left] || findValue > arr[right]不能省略，否则会下标越界
		// 因为如果没有这两个判断(findValue - arr[left]) / (arr[right] - arr[left])可能会大于1，导致最终的mid超出数组下标
		if (left > right || findValue < arr[left] || findValue > arr[right]) {
			return -1;
		}

		int mid = left + (findValue - arr[left]) / (arr[right] - arr[left]) * (right - left);
		if (arr[mid] < findValue) {
			return recursionBinarySearch(arr, mid + 1, right, findValue);
		} else if (arr[mid] > findValue) {
			return recursionBinarySearch(arr, left, mid - 1, findValue);
		} else {
			return mid;
		}
	}

	private static int loopBinarySearch(Integer[] arr, int left, int right, int findValue) {
		// 这里的findValue < arr[left] || findValue > arr[right]不能省略，否则会下标越界
		// 因为如果没有这两个判断(findValue - arr[left]) / (arr[right] - arr[left])可能会大于1，导致最终的mid超出数组下标
		if (findValue < arr[left] || findValue > arr[right]) {
			return -1;
		}

		while (left <= right) {
			int mid = left + (findValue - arr[left]) / (arr[right] - arr[left]) * (right - left);
			if (arr[mid] < findValue) {
				left = mid + 1;
			} else if (arr[mid] > findValue) {
				right = mid - 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
}
