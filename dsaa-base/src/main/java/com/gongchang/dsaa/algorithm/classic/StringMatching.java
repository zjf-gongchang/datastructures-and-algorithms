package com.gongchang.dsaa.algorithm.classic;

import java.util.Arrays;

/**
 * KMP算法应用实践
 * 
 * KMP算法介绍：KMP算法的核心思想是，维护一个部分匹配表，通过部分匹配表里面的部分匹配值判断字符串指针向后移动的次数
 * 
 * 部分匹配值的计算方式：一个字符串前缀和后缀最长共有元素的长度，比如：ABCDAB
 * 前缀是：A AB ABC ABCD ABCDA     后缀是：BCDAB CDAB DAB AB B   那么ABCAB的部分匹配值就是AB的长度2
 * 
 * 通过部分匹配值移动指针的思想是：字符串的头部和尾部可能会有重复的部分，如果在匹配的过程中发现当前字符不匹配，则根据前一个字符对应字符串的部分匹配值移动
 * ABCDABCABCDABD
 * ABCDABD
 * 如上面两行，查找ABCDABD在ABCDABCABCDABD中的真实下标，前面的都匹配，但是匹配到C的时候C与D不匹配，D的前一个字符是B，对应的子符串截止到B是ABCDAB,
 * ABCDAB的部分匹配值是2，此时ABCDABCABCDABD的下标在0的位置，应该向后移动4位（ABCDAB的长度-ABCDAB的部分匹配值），同时ABCDABD指针位置归0，然后再开始匹配
 * 
 * 
 * KMP算法详细介绍参考：https://www.cnblogs.com/zzuuoo666/p/9028287.html
 * 
 * 字符串匹配可以使用KMP算法来解决，当然也可以使用暴力匹配（效率相当差），这里给出了暴力匹配与KMP匹配的算法案例
 * 
 * @Author	gongchang
 */
public class StringMatching {

	public static void main(String[] args) {
		String str = "我们好孩子都是好孩好孩子都是好孩子";
		String subStr = "好孩子都是好孩子";
//		int index = violentMatching(str, subStr);
		int index = kmpMatching(str, subStr);
		if(index == -1){
			System.out.println("不包含子串");
		}else{
			System.out.println("子串【"+subStr+"】在【"+str+"】中的下标是："+index);
		}
		
	}
	
	/**
	 * 暴力匹配
	 * 
	 * @param str 字符串
	 * @param subStr 子字符串
	 * @return 包含-返回下标，不包含-返回-1
	 */
	public static int violentMatching(String str, String subStr){
		int resultIndex =-1;
		
		int strLength = str.length();
		int subStrLength = subStr.length();
		
		int strIndex = 0;
		int subStrIndex = 0;
		
		while(strIndex<strLength && subStrIndex<subStrLength){
			if(str.charAt(strIndex)==subStr.charAt(subStrIndex)){
				strIndex++;
				subStrIndex++;
			}else{
				strIndex = strIndex-subStrIndex+1;
				subStrIndex = 0;
			}
		}
		
		if(subStrIndex==subStrLength){
			resultIndex = strIndex-subStrIndex;
		}
		
		return resultIndex;
	}
	
	/**
	 * KMP（Knuth-Morris-Pratt）匹配
	 * 
	 * @param str 字符串
	 * @param subStr 子字符串
	 * @return 包含-返回下标，不包含-返回-1
	 */
	public static int kmpMatching(String str, String subStr){
		int[] pmt = getPartMatchTable(subStr);
		System.out.println("subStr的部分匹配表是："+Arrays.toString(pmt));
		
		int strLen = str.length();
		int subStrLen = subStr.length();
		int strIndex = 0;
		int subStrIndex = 0;
		
		while(strIndex<strLen){
			// 这里的实现方式是另一种移动字串的指针，与类注释中讲解的移动方式稍有不同，跳过了前缀相同部分的比较，更高效
			// 下面的while循环是KMP匹配的核心代码
			while(subStrIndex>0 && str.charAt(strIndex)!=subStr.charAt(subStrIndex)){
				subStrIndex=pmt[subStrIndex-1];
			}
			
			if(str.charAt(strIndex)==subStr.charAt(subStrIndex)){
				subStrIndex++;
			}
			
			if(subStrIndex==subStrLen){
				return strIndex-subStrIndex+1;
			}
			
			strIndex++;
		}
		
		return -1;
	}
	
	/**
	 * 获取字符串的部分匹配表
	 * 
	 * @param str 字符串
	 * @return 字符串对应的部分匹配表
	 */
	private static int[] getPartMatchTable(String str){
		int strLen = str.length();
		int[] resultTable = new int[strLen];
		resultTable[0] = 0;
		
		int i = 1;
		int j = 0;
		while(i<strLen){
			
			while(j>0 && str.charAt(i)!=str.charAt(j)){
				j=resultTable[j-1];
			}
			
			if(str.charAt(i)==str.charAt(j)){
				j++;
			}
			
			resultTable[i] = j;
			
			i++;
		}
		
		return resultTable;
	}
	
}
