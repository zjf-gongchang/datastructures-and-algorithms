package com.gongchang.dsaa.algorithm.sort;

/**
 * 计数排序
 * 
 * 排序类型：稳定排序
 * 
 * 核心思想：
 * 	1.以数组中的最大值作为新数组的大小
 * 	2.遍历老数组，在新数组中以老数组中的元素值作为下标记录元素出现的次数
 * 
 * 最坏时间复杂度：O(n + k) n是数的规模，k是桶的个数
 * 
 * @author GongChang
 *
 */
public class CountingSort {

    public static void countingSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }

        // 这里是核心，以原始数据作为下标，记录下每个数出现的次数
        int[] countArray = new int[max + 1];
        for (int num : arr) {
            countArray[num]++;
        }

        int index = 0;
        for (int i = 0; i <= max; i++) {
            while (countArray[i] > 0) {
                arr[index++] = i;
                countArray[i]--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 2, 2, 8, 3, 3, 1};

        System.out.println("原始数组:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();

        countingSort(arr);

        System.out.println("排序后的数组:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

