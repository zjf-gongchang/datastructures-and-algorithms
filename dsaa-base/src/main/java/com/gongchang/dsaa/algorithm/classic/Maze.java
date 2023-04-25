package com.gongchang.dsaa.algorithm.classic;

import java.util.Arrays;

/**
 * 迷宫问题
 * 
 * @Author	gongchang
 */
public class Maze {

	public static void main(String[] args) {
		Maze maze = new Maze();
		maze.createMaze(10);
		System.out.println("迷宫初始化");
		maze.printMaze();
		
		maze.findTheWay(1,1);
		System.out.println("找到一条路径");
		maze.printMaze();
	}
	
	int length;
	
	int [][] mazeArr;
	
	
	public Maze() {
		super();
	}

	
	private Maze createMaze(int length){
		this.length = length;
		
		// 初始化，0表示可以通过
		mazeArr =new int[length][length];
		
		// 1表示墙
		for(int i=0; i<length; i++){
			mazeArr[0][i] = 1;
			mazeArr[length-1][i] = 1;
			mazeArr[i][0] = 1;
			mazeArr[i][length-1] = 1;
		}
		mazeArr[2][length-2] = 1;
		mazeArr[2][length-3] = 1;
		mazeArr[6][length-2] = 1;
		mazeArr[6][length-3] = 1;
		mazeArr[6][length-4] = 1;
		mazeArr[6][length-5] = 1;
		mazeArr[6][length-6] = 1;
		mazeArr[6][length-7] = 1;
		mazeArr[3][1] = 1;
		mazeArr[3][2] = 1;
		mazeArr[3][3] = 1;
		
		// 6表示迷宫的出口
		mazeArr[length-1][length-2] = 6;
		
		return this;
	}
	
	public void printMaze(){
		if(mazeArr==null){
			System.out.println("请先创建迷宫");
			return;
		}
		
		for(int i=0; i<mazeArr.length; i++){
			System.out.println(Arrays.toString(mazeArr[i]));
		}
	}
	
	public boolean findTheWay(int x, int y){
		// 等于6说明已经找到一条路径，结束递归
		if(mazeArr[x][y]==6){
			return true;
		}
		
		if(mazeArr[x][y]!=0){
			return false;
		}
		// 先认定此路可以通
		mazeArr[x][y] = 2;
		
		// 向下走
		if(findTheWay(x, y+1)){
			return true;
		}
		// 向右走
		if(findTheWay(x+1, y)){
			return true;
		}
		// 向上走
		if(findTheWay(x, y-1)){
			return true;
		}
		// 向左走
		if(findTheWay(x-1, y)){
			return true;
		}
		
		// 这条路走不通，回溯
		mazeArr[x][y] = 0;
		
		return false;
	}
	
}
