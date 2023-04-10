package com.gongchang.dsaa.datastructure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 基于邻接矩阵实现图
 * 
 * @author GongChang
 *
 */
public class GraphBaseAdjacencyMatrix {
	
	public static void main(String[] args) {
		GraphBaseAdjacencyMatrix gbam = new GraphBaseAdjacencyMatrix(10);
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
		
		addEdges(0, 3);
		addEdges(0, 6);
		addEdges(3, 5);
		addEdges(4, 2);
		addEdges(4, 5);
	}

	private List<Node> nodeList;
	
	private int[][] edges;
	
	private int edgeSum;

	
	public GraphBaseAdjacencyMatrix(int nodeNum) {
		nodeList = new ArrayList<Node>(nodeNum);
		edges = new int[nodeNum][nodeNum];
		edgeSum = 0;
	}
	
	public void showGrpah() {
		for (int[] neighborNodeIndex : edges) {
			System.out.println(Arrays.toString(neighborNodeIndex));
		}
	}
	
	public void addEdges(int oneIndex, int secondIndex) {
		edges[oneIndex][secondIndex]=1;
		edges[secondIndex][oneIndex]=1;
		edgeSum++;
	}
	
	public void addNode(Node node) {
		nodeList.add(node);
	}
	
	private int getNextNeighborNodeIndex(int nodeIndex, int startOffset) {
		for(int i= startOffset; i<nodeList.size(); i++) {
			if(edges[nodeIndex][i]==1) {
				return i;
			}
		}
		return -1;
	}
	
	public void dfs() {
		int[] isvisited = new int[nodeList.size()];
		for(int i=0; i<nodeList.size(); i++) {
			if(isvisited[i]!=1) {
				dfs(i, isvisited);
			}
			
		}
	}
	
	private void dfs(int index, int[] isvisited) {
		nodeList.get(index).printNodeInfo();
		isvisited[index]=1;
		// 下面是深度优先的体现，找到当前节点的第一个未访问的邻接节点，递归进去做上面的重复操作（访问和标识）
		int neighborNodeIndex = getNextNeighborNodeIndex(index, 0);
		while(neighborNodeIndex!=-1) {
			if(isvisited[neighborNodeIndex]!=1) {
				dfs(neighborNodeIndex, isvisited);
			}
			neighborNodeIndex=getNextNeighborNodeIndex(index, neighborNodeIndex+1);
		}
	}
	
	public void bfs() {
		int[] isvisited = new int[nodeList.size()];
		for(int i=0; i<nodeList.size(); i++) {
			if(isvisited[i]!=1) {
				bfs(i, isvisited);
			}
		}
	}
	
	private void bfs(int index, int[] isvisited) {
		nodeList.get(index).printNodeInfo();
		isvisited[index]=1;
		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
		queue.add(index);
		// 下面是宽度优先遍历的体现，每次从队列中取出一个节点之后，都会把这个节点的所有邻接节点都访问完，再去从队列中取下一个节点
		int curNodeIndex;
		int neighborNodeIndex;
		while(queue.size()>0) {
			curNodeIndex = queue.poll();
			neighborNodeIndex = getNextNeighborNodeIndex(curNodeIndex, 0);
			while(neighborNodeIndex!=-1) {
				if(isvisited[neighborNodeIndex]!=1) {
					nodeList.get(neighborNodeIndex).printNodeInfo();
					isvisited[neighborNodeIndex]=1;
					queue.add(neighborNodeIndex);
				}
				neighborNodeIndex=getNextNeighborNodeIndex(curNodeIndex, neighborNodeIndex+1);
			}
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
