package com.gongchang.dsaa.datastructure.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 基于邻接表实现图
 * 
 * @author GongChang
 *
 */
public class GraphBaseAdjacencyTables {
	
	public static void main(String[] args) {
		GraphBaseAdjacencyTables gbam = new GraphBaseAdjacencyTables();
		gbam.buildGraph();
		
		System.out.println("==============================图结构打印");
		gbam.showGrpah();
		System.out.println("==============================图结构打印");
		
		System.out.println("==============================dfs遍历");
		gbam.dfs();
		System.out.println("==============================dfs遍历");
		
		System.out.println("==============================bfs遍历");
		gbam.bfs();
		System.out.println("==============================bfs遍历");
	}
	
	private void buildGraph() {
		Node node1 = new Node(1, "1-name");
		addNode(node1);
		Node node2 = new Node(2, "2-name");
		addNode(node2);
		Node node3 = new Node(3, "3-name");
		addNode(node3);
		Node node4 = new Node(4, "4-name");
		addNode(node4);
		Node node5 = new Node(5, "5-name");
		addNode(node5);
		Node node6 = new Node(6, "6-name");
		addNode(node6);
		Node node7 = new Node(7, "7-name");
		addNode(node7);
		Node node8 = new Node(8, "8-name");
		addNode(node8);
		Node node9 = new Node(9, "9-name");
		addNode(node9);
		Node node10 = new Node(10, "10-name");
		addNode(node10);
		
		addNeighborNode(0, 3);
		addNeighborNode(0, 6);
		addNeighborNode(3, 5);
		addNeighborNode(4, 2);
		addNeighborNode(4, 5);
	}

	private List<Node> nodeList;
	
	private List<Point> pointList;
	
	private int edgeSum;
	
	
	public GraphBaseAdjacencyTables() {
		super();
		this.nodeList = new ArrayList<Node>();
		this.pointList = new ArrayList<Point>();
		this.edgeSum = 0;
	}
	
	private void dfs() {
		Boolean[] isvisited  = new Boolean[nodeList.size()];
		for(int i=0; i<nodeList.size(); i++) {
			isvisited[i] = false;
		}
		for (int i=0; i<nodeList.size(); i++) {
			if(!isvisited[i]) {
				dfs(i, isvisited);
			}
		}
	}
	
	private void dfs(int nodeIndex, Boolean[] isvisited) {
		// 访问
		nodeList.get(nodeIndex).printNodeInfo();
		// 标识
		isvisited[nodeIndex] = true;
		Point nextNeighborNodePoint = pointList.get(nodeIndex).getNext();
		while(nextNeighborNodePoint!=null) {
			if(!isvisited[nextNeighborNodePoint.getIndex()]) {
				dfs(nextNeighborNodePoint.getIndex(), isvisited);
			}
			nextNeighborNodePoint = nextNeighborNodePoint.getNext();
		}
	}

	private void bfs() {
		Boolean[] isvisited  = new Boolean[nodeList.size()];
		for(int i=0; i<nodeList.size(); i++) {
			isvisited[i] = false;
		}
		for (int i=0; i<nodeList.size(); i++) {
			if(!isvisited[i]) {
				bfs(i, isvisited);
			}
		}
	}
	
	private void bfs(int nodeIndex, Boolean[] isvisited) {
		// 访问
		nodeList.get(nodeIndex).printNodeInfo();
		// 标识
		isvisited[nodeIndex] = true;
		// 入队列
		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
		queue.add(nodeIndex);
		
		while(queue.size()>0) {
			int curPointIndex = queue.poll();
			Point curPoint = pointList.get(curPointIndex).getNext();
			while(curPoint!=null) {
				if(!isvisited[curPoint.getIndex()]) {
					nodeList.get(curPoint.getIndex()).printNodeInfo();
					isvisited[curPoint.getIndex()]=true;
					queue.add(curPoint.getIndex());
				}
				curPoint = curPoint.getNext();
			}
		}
		
	}
	
	private int getNextNeighborNodeIndex(Point startPoint) {
		return startPoint.getNext()==null?-1:startPoint.getNext().getIndex();
	}
	
	private void showGrpah(){
		for (Point point : pointList) {
			Point curPoint = point;
			while(curPoint!=null) {
				System.out.print(curPoint.getIndex());
				if(curPoint.getNext()!=null) {
					System.out.print(",");
				}
				curPoint = curPoint.getNext();
			}
			System.out.println();
		}
	}
	
	private void addNode(Node node) {
		nodeList.add(node);
		pointList.add(new Point(nodeList.size()-1));
	}
	
	private void addNeighborNode(int nodeIndex, int neighborNodeIndex) {
		if(nodeIndex==neighborNodeIndex) {
			return;
		}
		
		Point curPoint = pointList.get(nodeIndex);
		while(curPoint.getNext()!=null){
			curPoint = curPoint.getNext();
			if(curPoint.getIndex()==neighborNodeIndex) {
				return;
			}
		}
		curPoint.setNext(new Point(neighborNodeIndex));
		
		curPoint = pointList.get(neighborNodeIndex);
		while(curPoint.getIndex()!=nodeIndex&&curPoint.getNext()!=null){
			curPoint = curPoint.getNext();
		}
		curPoint.setNext(new Point(nodeIndex));
		
		edgeSum++;
	}
	
	private int getEdgeSum() {
		return edgeSum;
	}
	
	private int getNodeSum() {
		return nodeList.size();
	}
	
	private class Point{
		
		private int index;
		
		private Point next;
		
		
		public Point(int index) {
			super();
			this.index = index;
		}
		
		
		public int getIndex() {
			return index;
		}

		public Point getNext() {
			return next;
		}
		public void setNext(Point next) {
			this.next = next;
		}
	}
	
	private class Node{
		
		private int id;
		
		private String name;
		
		
		public Node(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		public void printNodeInfo() {
			System.out.println(this.toString());
		}

		@Override
		public String toString() {
			return "Node [id=" + id + ", name=" + name + "]";
		}
		
	}
}
