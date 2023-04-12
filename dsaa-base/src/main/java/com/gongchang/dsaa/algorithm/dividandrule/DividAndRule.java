package com.gongchang.dsaa.algorithm.dividandrule;

import java.util.ArrayList;
import java.util.List;

/**
 * 分治算法
 * 
 * 介绍：将一个问题分解成多个小问题，如果小问题可以直接解决则直接解决，如果不能解决则继续分解，直到分解后的问题可以被直接解决
 * 	最后将分解后的各个直接解决的小问题的结果合并得到这个问题的结果
 * 
 * 分治即分而治之，分为分和治两个阶段
 * 分：将复杂问题不断的分解，直到可以直接解决
 * 治：将直接解决的问题不断的合并，最终得到问题的结果
 * 
 * 分治算法是很多高效算法的基础，有很多地方用到，比如：
 *  快速排序，归并排序
 * 	Java线程池中的ForkJoin
 * 	二分查找
 * 	傅里叶变换，快速傅里叶变换
 * 	汉诺塔问题
 * 	大整数乘法问题
 * 	棋盘覆盖问题
 * 	线性时间选择问题
 * 	循环赛日程表问题
 * 	最接近点对问题
 * 
 * @Author	gongchang
 */
public class DividAndRule {

	public static void main(String[] args) {
		List<Integer> arrayList = new ArrayList<Integer>();
		for(int i=0; i<10000; i++){
			arrayList.add(i);
		}
		
		DividAndRule dividAndRule = new DividAndRule();
		Integer maxValue = dividAndRule.recursionDividAndRule(arrayList, 0, arrayList.size()-1);
		System.out.println("递归分治最大值是："+maxValue);
	}
	
	private Integer recursionDividAndRule(List<Integer> list, int leftIndex, int rightIndex){
		//================================
		// 分：将复杂问题分解成可以直接解决的小问题，
		// 在这里的体现是：列表里面有很多数据时候求最大值是复杂问题，当列表里面只有一个数据的时候，最大值就是它自己
		//================================
		
		// 进入if判断说明问题可以直接处理解决，直接解决即可 
		if(leftIndex==rightIndex){
			return list.get(leftIndex);
		}
		// 这里将问题不断的递归拆解，拆解到问题可以直接解决
		int midIndex =leftIndex+(rightIndex-leftIndex)/2;
		Integer leftMaxValue = recursionDividAndRule(list, leftIndex, midIndex);
		Integer rightMaxValue = recursionDividAndRule(list, midIndex+1, rightIndex);
		
		//================================
		// 治：将小问题的处理结果合并
		// 在这里的体现是：leftMaxValue和rightMaxValue分别是两个小问题的处理结果，将这个两个值求一个最大值做合并处理
		//================================
		int maxValue = Math.max(leftIndex, rightIndex);
		
		return maxValue;
	}
	
}
