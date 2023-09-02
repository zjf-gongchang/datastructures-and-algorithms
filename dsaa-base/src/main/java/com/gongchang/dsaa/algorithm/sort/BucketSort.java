package com.gongchang.dsaa.algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序
 * 
 * 排序类型：
 * 
 * 核心思想：
 * 
 * 时间复杂度：
 * 
 * @author GongChang
 *
 */
public class BucketSort {
	
    public static void bucketSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        int min = arr[0];
        int max = arr[0];
        for (int num : arr) {
            if (num < min) {
                min = num;
            }
            if (num > max) {
                max = num;
            }
        }

        int bucketCount = (max - min) / arr.length + 1;
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int num : arr) {
            int index = (num - min) / arr.length;
            buckets.get(index).add(num);
        }

        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        int index = 0;
        for (List<Integer> bucket : buckets) {
            for (int num : bucket) {
                arr[index++] = num;
            }
        }
    }

    
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("原始数组:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();

        bucketSort(arr);

        System.out.println("排序后的数组:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

