package com.gongchang.dsaa.datastructure.lineartables.array;

import java.util.Arrays;

/**
 * 一维数组
 * 
 * @author GongChang
 *
 */
public class OneDimArray {

	public static void main(String[] args) {
		
		int[] tmpIntArr = {1,2,3,4,5,6,7,8,9,10};
		
		int[] intArr = new int[tmpIntArr.length];
		
		for(int i=0; i<tmpIntArr.length; i++){
			intArr[i] = tmpIntArr[i];
		}
		
		System.out.println(Arrays.toString(intArr));
	}
}
