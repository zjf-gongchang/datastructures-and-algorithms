package com.gongchang.dsaa.algorithm.find;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 斐波那契查找
 * 
 * @author GongChang
 *
 */
public class FibonacciSearch {

	public static void main(String[] args) {
		Integer[] searchArr = {3,6,1,18,7,12,5,2,19,11};
		int orgSearchArrMaxIndex = searchArr.length-1;
		
		System.out.println("排序前的数组：");
		arrPrint(searchArr);
		arrSort(searchArr);
		System.out.println("排序后的数组：");
		arrPrint(searchArr);
		
		Integer[] fibArr = buildFibonaccArr();
		System.out.println("构造的斐波那契数组：");
		arrPrint(fibArr);
		
		Integer scaelSizeKvalue = checkArrScaelSizeKvalue(fibArr, searchArr);
		searchArr = arrScael(searchArr, fibArr[scaelSizeKvalue]);
		System.out.println("扩容后的数组：");
		arrPrint(searchArr);
		
		Integer findValue = 12;
		fibSearch(fibArr, scaelSizeKvalue, searchArr, 0, searchArr.length-1, findValue, orgSearchArrMaxIndex, MethodInvokeType.RECURSION);
		fibSearch(fibArr, scaelSizeKvalue, searchArr, 0, searchArr.length-1, findValue, orgSearchArrMaxIndex, MethodInvokeType.LOOP);
		
	}
	
	
	private static void fibSearch(Integer[] fibArr, Integer k, Integer[] searchArr, int left, int right, Integer findValue, int orgSearchArrMaxIndex, MethodInvokeType methodInvokeType) {
		System.out.println("查找方式为：" + methodInvokeType.name());
		System.out.println("查找的数据是：" + findValue);

		int index = -1;
		switch (methodInvokeType) {
		case RECURSION:
			index = fibRecursionSearch(fibArr, k, searchArr, 0, searchArr.length-1, findValue, orgSearchArrMaxIndex);
			break;
		case LOOP:
			index = fibLoopSearch(fibArr, k, searchArr, 0, searchArr.length-1, findValue, orgSearchArrMaxIndex);
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
	
	private enum MethodInvokeType {
		RECURSION,

		LOOP;
	}
	
	private static int fibRecursionSearch(Integer[] fibArr, Integer k, Integer[] searchArr, int left, int right, Integer findValue, int orgSearchArrMaxIndex) {
		if (left > right || findValue < searchArr[left] || findValue > searchArr[right]) {
			return -1;
		}
		
		int mid = left + fibArr[k-1]-1;
		if (searchArr[mid] < findValue) {
			left = mid + 1;
			k-=2;
			return fibRecursionSearch(fibArr, k, searchArr, left, right, findValue, orgSearchArrMaxIndex);
		} else if (searchArr[mid] > findValue) {
			right = mid - 1;
			k-=1;
			return fibRecursionSearch(fibArr, k, searchArr, right, right, findValue, orgSearchArrMaxIndex);
		} else {
			if(mid>orgSearchArrMaxIndex) {
				return orgSearchArrMaxIndex;
			}else {
				return mid;
			}
		}
	}
		
	private static int fibLoopSearch(Integer[] fibArr, Integer k, Integer[] searchArr, int left, int right, Integer findValue, int orgSearchArrMaxIndex) {
		if (findValue < searchArr[left] || findValue > searchArr[right]) {
			return -1;
		}
		
		while (left <= right) {
			int mid = left + fibArr[k-1]-1;
			if (searchArr[mid] < findValue) {
				left = mid + 1;
				k-=2;
			} else if (searchArr[mid] > findValue) {
				right = mid - 1;
				k-=1;
			} else {
				if(mid>orgSearchArrMaxIndex) {
					return orgSearchArrMaxIndex;
				}else {
					return mid;
				}
			}
		}
		return -1;
	}
	
	private static Integer checkArrScaelSizeKvalue(Integer[] fibArr, Integer[] searchArr) {
		int intArrMaxIndex = searchArr.length-1;
		int k = 0;
		while(fibArr[k]-1 < intArrMaxIndex) {
			k++;
		}
		return k;
	}
	
	private static Integer[] arrScael(Integer[] searchArr, Integer scaelSize) {
		int intArrMaxIndex = searchArr.length-1;
		
		searchArr = Arrays.copyOf(searchArr, scaelSize);
		
		for(int i=intArrMaxIndex; i<scaelSize; i++) {
			searchArr[i] = searchArr[intArrMaxIndex];
		}
		
		return searchArr;
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
	
	private static Integer[] buildFibonaccArr() {
		Integer[] intarr = new Integer[10];
		intarr[0] = 1;
		intarr[1] = 1;
		for(int i=2; i<intarr.length; i++) {
			intarr[i] = intarr[i-1] + intarr[i-2];
		}
		return intarr;
	}
	
}
