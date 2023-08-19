package com.gongchang.dsaa.datastructure.lineartables.array;

import java.util.Arrays;

import javax.xml.transform.Templates;

/**
 * 二维数组
 * 
 * @author GongChang
 *
 */
public class TwoDimArray {

	public static void main(String[] args) {
		
		int[][] tmpIntArr = {
				{1,2,3,4,5,6,7,8,9,10},
				{1,2,3,4,5,6,7},
				{1,2,3,4,5,6,7,8,9},
				{1,2,3},
				{1,2,3,4},
				{1,2,3,4,5,6,7,8,9,10},
				{1},
				{1,2,3,4,5},
				{1,2,3,4,5,6},
				{1,2}
		};
		
		int[][] intArr = new int[tmpIntArr.length][];
		
		for(int i=0; i<tmpIntArr.length; i++){
			int[] eleArr = new int[tmpIntArr[i].length];
			for(int j=0; j<tmpIntArr[i].length; j++){
				eleArr[j] = tmpIntArr[i][j];
			}
			intArr[i] = eleArr;
		}
		
		for(int i=0; i<intArr.length; i++){
			System.out.println(Arrays.toString(intArr[i]));
		}
		
	}
}
