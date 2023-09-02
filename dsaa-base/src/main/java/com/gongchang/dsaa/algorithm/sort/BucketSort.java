package com.gongchang.dsaa.algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序
 * 
 * 排序类型：稳定排序
 * 
 * 核心思想：
 * 	1.假如有10个桶，那么第一个桶里面的元素小于第二个桶里面的元素
 * 	2.元素能分配到同一个桶说明这些元素都被同一个数除后得到的商是一样的，只是余数不同，在这个例子中除数就是arr.length
 * 
 * 最坏时间复杂度：O(n^2)
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

        // 这里是核心：计算桶的数量
        int bucketCount = (max - min) / arr.length + 1;
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int num : arr) {
        	//这里是核心：分配到某个桶
        	int index = (num - min) / arr.length;
            buckets.get(index).add(num);
        }

        // 针对每个桶排序
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

