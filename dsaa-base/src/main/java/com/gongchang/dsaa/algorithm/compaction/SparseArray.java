package com.gongchang.dsaa.algorithm.compaction;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * 稀疏数组
 *     旨在存储有效数据
 * 
 * @author GongChang
 *
 */
public class SparseArray {

	public static void main(String[] args) {
		
		int[][] originalArr = buileOriginalArr();
		
		zip(originalArr, "sparse.zip");
		
		unzip("sparse.zip");
		
	}
	
	public static Boolean zip(int[][] originalArr, String targetName){
		String targetFileStr = System.getProperty("user.dir")+"/"+targetName;
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(targetFileStr));){
			int[][] zipArr = encode(originalArr);
			
			System.out.println("压缩后的数组");
			printTwodimArr(zipArr);
			
			oos.writeObject(zipArr);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private static int[][] encode(int[][] originalArr){
		int xLength = originalArr.length;
		int yLength = originalArr[0].length;
		int eleCount = 0;
		for (int[] xArr : originalArr) {
			for (int ele : xArr) {
				if(ele!=0){
					eleCount++;
				}
			}
		}
		
		int[][] sparseArr = new int[eleCount+1][3];
		sparseArr[0][0] = xLength;
		sparseArr[0][1] = yLength;
		sparseArr[0][2] = eleCount;
		int index=1;
		for(int i=0; i<xLength; i++){
			for(int j=0; j<yLength; j++){
				if(originalArr[i][j]!=0){
					sparseArr[index][0] = i;
					sparseArr[index][1] = j;
					sparseArr[index][2] = originalArr[i][j];
					index++;
				}
			}
		}
		
		return sparseArr;
	}
	
	public static Boolean unzip(String sourceName){
		String sourceFileStr = System.getProperty("user.dir")+"/"+sourceName;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sourceFileStr))){
			int[][] sparseArr = (int[][]) ois.readObject();
			
			int [][] originalArr = decode(sparseArr);
			
			System.out.println("解压后的数组");
			printTwodimArr(originalArr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private static int[][] decode(int[][] sparseArr){
		int xLength = sparseArr[0][0];
		int yLength = sparseArr[0][1];
//		int eleCount = sparseArr[0][2];
		
		int[][] originArr = new int[xLength][yLength];
		for (int i=1; i<sparseArr.length; i++) {
			originArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
		}
		
		return originArr;
	}
	
	private static int[][] buileOriginalArr(){
		int[][] originalArr = {
				{0,1,1,0,0,0,0,0},
				{0,0,2,0,0,2,0,0},
				{0,0,1,1,2,0,0,0},
				{0,0,1,2,0,0,0,0},
				{0,0,1,2,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0}
		};
		
		System.out.println("压缩前的数组");
		printTwodimArr(originalArr);
		
		return originalArr;
	}
	
	private static void printTwodimArr(int[][] twoDimArr){
		for (int[] xArr : twoDimArr) {
			System.out.println(Arrays.toString(xArr));
		}
	}
	
}
