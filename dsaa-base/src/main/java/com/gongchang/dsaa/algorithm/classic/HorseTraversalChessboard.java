package com.gongchang.dsaa.algorithm.classic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 马踏棋盘算法（又叫骑士周游算法）
 * 
 * 介绍：在一个8x8的棋盘上，将马放入到任意位置，按照马走日的规则，走完棋盘上的所有方格，要求每个方格只能进入一次
 * 
 * 马踏棋盘算法中用到了递归和深度优先遍历
 * 
 * @Author	gongchang
 */
public class HorseTraversalChessboard {

	public static void main(String[] args) {
		HorseTraversalChessboard horseTraversalChessboard = new HorseTraversalChessboard(8, 8);
		horseTraversalChessboard.horseTraversalChessboard(0, 0, 1);
		horseTraversalChessboard.printChessboard();
	}
	

	int X;
	
	int Y;
	
	int[][] chessboard;
	
	boolean[][] visited;
	
	boolean finished;
	
	
	public HorseTraversalChessboard(int x, int y) {
		super();
		this.X = x;
		this.Y = y;
		this.chessboard = new int[x][y];
		this.visited = new boolean[x][y];
		this.finished = false;
	}

	
	/**
	 * 马踏棋盘算法
	 * 
	 * @param x 列坐标
	 * @param y 行坐标
	 * @param step 第几步
	 */
	public void horseTraversalChessboard(int x, int y, int step){
		chessboard[x][y]= step;
		visited[x][y] = true;
		
		// 获取邻接节点
		List<Point> nextPointList = nextPointList(new Point(x, y));
		// 排序，邻接节点数目少的排在前面，邻接节点数目少的节点先访问完有助于减少回溯的次数
		sortPoint(nextPointList);
		while(!nextPointList.isEmpty()){
			Point point = nextPointList.remove(0);
			// 如果未访问过，则深度优先
			if(!visited[point.x][point.y]){
				horseTraversalChessboard(point.x, point.y, step+1);
			}
		}
		
		// 回溯
		if(step<X*Y && !finished){
			chessboard[x][y]= 0;
			visited[x][y] = false;
		}else{
			finished = true;
		}
	}
	
	
	private List<Point> nextPointList(Point curPoint){
		List<Point> resultList = new ArrayList<Point>();
		
		int tempX,tempY;
		
		if((tempX=curPoint.x-1)>=0  && (tempY=curPoint.y+2)<Y){
			resultList.add(new Point(tempX,tempY));
		}
		if((tempX=curPoint.x-1)>=0  && (tempY=curPoint.y-2)>=0){
			resultList.add(new Point(tempX,tempY));
		}
		if((tempX=curPoint.x+1)<X  && (tempY=curPoint.y+2)<Y){
			resultList.add(new Point(tempX,tempY));
		}
		if((tempX=curPoint.x+1)<X  && (tempY=curPoint.y-2)>=0){
			resultList.add(new Point(tempX,tempY));
		}
		if((tempX=curPoint.x-2)>=0  && (tempY=curPoint.y+1)<Y){
			resultList.add(new Point(tempX,tempY));
		}
		if((tempX=curPoint.x-2)>=0  && (tempY=curPoint.y-1)>=0){
			resultList.add(new Point(tempX,tempY));
		}
		if((tempX=curPoint.x+2)<X  && (tempY=curPoint.y+1)<Y){
			resultList.add(new Point(tempX,tempY));
		}
		if((tempX=curPoint.x+2)<X  && (tempY=curPoint.y-1)>=0){
			resultList.add(new Point(tempX,tempY));
		}
		
		return resultList;
	}
	
	private void sortPoint(List<Point> pointList){
		Collections.sort(pointList, new Comparator<Point>() {
			@Override
			public int compare(Point point1, Point point2) {
				int point1NextSize = nextPointList(point1).size();
				int point2NextSize = nextPointList(point2).size();
				if(point1NextSize < point2NextSize){
					return -1;
				}else if(point1NextSize > point2NextSize){
					return 1;
				}else{
					return 0;
				}
			}
		});
	}
	
	private void printChessboard(){
		for(int i=0; i<chessboard.length; i++){
			System.out.println(Arrays.toString(chessboard[i]));
		}
	}
	
}
