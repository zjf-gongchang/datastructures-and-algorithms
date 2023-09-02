package com.gongchang.dsaa.algorithm.sort;

/**
 * 计数排序
 * 
 * 排序类型：
 * 
 * 核心思想：
 * 
 * 时间复杂度：O(n + k)
 * 
 * @author GongChang
 *
 */
public class CountingSort {

    public static void countingSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        // 1. 找到数组中的最大值
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }

        // 2. 创建计数数组并初始化
        int[] countArray = new int[max + 1];
        for (int num : arr) {
            countArray[num]++;
        }

        // 3. 根据计数数组重新构建排序后的数组
        int index = 0;
        for (int i = 0; i <= max; i++) {
            while (countArray[i] > 0) {
                arr[index++] = i;
                countArray[i]--;
            }
        }
    }

    // 测试计数排序
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

