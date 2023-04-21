package com.gongchang.dsaa.algorithm.classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 最小生成树（Minimum Cost Spanning Tree 简称MST）：给定一个带权的无向连通图，选取一棵树，连通所有集结点，
 * 使得树上所有边上权的总和最小，那么这棵树就是最小生成树
 * 
 * 最小生成树可以使用普里姆算法和克鲁斯卡尔算法来解决，这两种算法的核心思路有以下三点：
 * 1.N个顶点选取N-1条边
 * 2.每次选取权值最小的边
 * 3.选取的边不能构成回路
 * 
 * 普里姆算法计算最小生成树核心思想：N个顶点有N-1条边，从某一个初始节点开始访问并标记为已访问，循环N-1次，每次从从已访问的节点中
 * 选出权值最小的一条边（避免构成回路），将边的另一端的顶点标记为已访问
 * 
 * 克鲁鲁斯卡尔算法计算最小生成树核心思想：N个顶点有N-1条边，首先将所有的边从图中抽取出来，然后按照权重从小到大排序，每次取出一条边，
 * 如果这条边不构成回路则将这条边选取出来
 * 
 * @Author	gongchang
 */
public class MiniCostSpanningTree {

	public static void main(String[] args) {
		List<Integer> sourceNodeList = MiniCostSpanningTree.getSourceNodeList();
		List<int[]> sourceEdgesList = MiniCostSpanningTree.getEdgesList();
		
		MiniCostSpanningTree primMiniCostSpanningTree = new MiniCostSpanningTree(sourceNodeList.size());
		primMiniCostSpanningTree.buildGraph(sourceNodeList, sourceEdgesList);
		
		primMiniCostSpanningTree.showGrpah();
		
		List<EdgeEntity> primList = primMiniCostSpanningTree.primMiniCostSpanningTree(1);
		System.out.println(">>>>>>普里姆算法计算得到的最小生成树：");
		primMiniCostSpanningTree.printCostSpanningTree(primList);
		
		List<EdgeEntity> kruskalList = primMiniCostSpanningTree.kruskalMiniCostSpanningTree();
		System.out.println(">>>>>>克鲁斯卡尔算法计算得到的最小生成树：");
		primMiniCostSpanningTree.printCostSpanningTree(kruskalList);
		
	}
	
	public void printCostSpanningTree(List<EdgeEntity> treeList){
		List<Node> nodeList = getNodeList();
		for(int i=0; i<treeList.size(); i++){
			EdgeEntity edgeEntity = treeList.get(i);
			System.out.println("找到第"+(i+1)+"条边:"+nodeList.get(edgeEntity.getStartNodeIndex()).getName()+"--->"+nodeList.get(edgeEntity.getEndNodeIndex()).getName()+",长度为："+edgeEntity.getEdgeWeight());
		}
		System.out.println();
	}
	
	public static List<Integer> getSourceNodeList(){
		List<Integer> resultList = new ArrayList<Integer>();
		for(int i=1; i<8; i++){
			resultList.add(i);
		}
		return resultList;
	}
	
	public static List<int[]> getEdgesList(){
		List<int[]> resultList = new ArrayList<int[]>();
		resultList.add(new int[]{1, 2, 60});
		resultList.add(new int[]{1, 3, 50});
		resultList.add(new int[]{2, 4, 55});
		resultList.add(new int[]{3, 5, 65});
		resultList.add(new int[]{5, 6, 30});
		resultList.add(new int[]{4, 6, 35});
		resultList.add(new int[]{3, 7, 45});
		resultList.add(new int[]{4, 7, 45});
		return resultList;
	}
	
	private void buildGraph(List<Integer> nodeList, List<int[]> edgesList) {
		for (int nodeId : nodeList) {
			addNode(new Node(nodeId, nodeId+"-城市"));
		}
		for (int[] edge : edgesList) {
			addEdges(edge[0]-1, edge[1]-1, edge[2]);
		}
	}

	private static final int NO_CONNECTED = -1;
	
	private List<Node> nodeList;
	
	private int[][] edges;
	
	private int edgeSum;

	
	public MiniCostSpanningTree(int nodeNum) {
		nodeList = new ArrayList<Node>(nodeNum);
		edges = new int[nodeNum][nodeNum];
		edgeSum = 0;
		
		for(int i=0; i<edges.length; i++){
			for(int j=0; j<edges[i].length; j++){
				edges[i][j] = NO_CONNECTED;
			}
		}
	}
	
	/**
	 * 克鲁斯卡尔算法计算最小生成树
	 * @return 
	 */
	public ArrayList<EdgeEntity> kruskalMiniCostSpanningTree(){
		// 获取边的列表
		List<EdgeEntity> edgeEntityList = getEdgeEntityList();
		
		// 从小到大排序
		sortEdgeEntityList(edgeEntityList);
		
		// 创建一个数组，记录每一个节点的后继节点索引
		int[] endArr = new int[edgeEntityList.size()];
		
		// 创建一个列表，记录获取道的最下生成树
		ArrayList<EdgeEntity> resultList = new ArrayList<EdgeEntity>();
		
		for (EdgeEntity edgeEntity : edgeEntityList) {
			int startNodeIndex = edgeEntity.getStartNodeIndex();
			int endNodeIndex = edgeEntity.getEndNodeIndex();
			
			int startNodeEnd = getNodeEnd(endArr, startNodeIndex);
			int endNodeEnd = getNodeEnd(endArr, endNodeIndex);
			
			if(startNodeEnd!=endNodeEnd){
				endArr[startNodeEnd] = endNodeEnd;
				resultList.add(edgeEntity);
			}
		}
		
		return resultList;
		
	}
	
	private int getNodeEnd(int[] endArr, int startIndex){
		int i =startIndex;
		while(endArr[i]!=0){
			i = endArr[i];
		}
		return i;
	}
	
	private void sortEdgeEntityList(List<EdgeEntity> edgeEntityList){
		for(int i=0; i<edgeEntityList.size()-1; i++){
			for(int j=0; j<edgeEntityList.size()-1; j++){
				if(edgeEntityList.get(j).getEdgeWeight()>edgeEntityList.get(j+1).getEdgeWeight()){
					EdgeEntity temp = edgeEntityList.get(j);
					edgeEntityList.set(j, edgeEntityList.get(j+1));
					edgeEntityList.set(j+1, temp);
				}
			}
		}
	}
	
	private List<EdgeEntity> getEdgeEntityList(){
		List<EdgeEntity> resultList = new ArrayList<EdgeEntity>();
		for(int i=0; i<edges.length; i++){
			for(int j=i+1; j<edges[i].length; j++){
				if(edges[i][j]!=NO_CONNECTED){
					resultList.add(new EdgeEntity(i, j, edges[i][j]));
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 普里姆算法计算最小生成树
	 * 
	 * @param startNodeIndex 起始节点索引
	 * @return 
	 */
	public ArrayList<EdgeEntity> primMiniCostSpanningTree(int startNodeIndex){
		ArrayList<EdgeEntity> resultList = new ArrayList<EdgeEntity>();
		
		int[] visited = new int[nodeList.size()];
		
		// 初始节点标识为访问
		visited[startNodeIndex] = 1;
		
		int first = -1;
		int end = -1;
		int minEdge = Integer.MAX_VALUE;
		// N个顶点选出N-1条边
		for(int i=1; i<nodeList.size(); i++){
			// 从已访问的节点中选未访问的节点，避免构成回路
			for(int m=0; m<nodeList.size(); m++){
				if(visited[m]==0){
					continue;
				}
				for(int n=0; n<nodeList.size(); n++){
					if(visited[n]==0 && edges[m][n]!=-1 && edges[m][n] < minEdge){
						minEdge = edges[m][n];
						first = m;
					    end = n;
					}
				}
			}
			
			visited[end] = 1;
			resultList.add(new EdgeEntity(first, end, minEdge));
			
			minEdge = Integer.MAX_VALUE;
		}
		
		return resultList;
	}
	
	public void showGrpah() {
		System.out.println("构造的无向连通网是：");
		for (int[] edge : edges) {
			System.out.println(Arrays.toString(edge));
		}
		System.out.println();
	}
	
	public void addEdges(int oneIndex, int secondIndex, int weight) {
		edges[oneIndex][secondIndex]=weight;
		edges[secondIndex][oneIndex]=weight;
		edgeSum++;
	}
	
	public void addNode(Node node) {
		nodeList.add(node);
	}
	
	
	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public int[][] getEdges() {
		return edges;
	}

	public void setEdges(int[][] edges) {
		this.edges = edges;
	}

	public int getEdgeSum() {
		return edgeSum;
	}

	public void setEdgeSum(int edgeSum) {
		this.edgeSum = edgeSum;
	}


	private class EdgeEntity{
		
		private int startNodeIndex;
		
		private int endNodeIndex;
		
		private int edgeWeight;

		
		public EdgeEntity(int startNodeIndex, int endNodeIndex, int edgeWeight) {
			super();
			this.startNodeIndex = startNodeIndex;
			this.endNodeIndex = endNodeIndex;
			this.edgeWeight = edgeWeight;
		}
		

		public int getStartNodeIndex() {
			return startNodeIndex;
		}

		public void setStartNodeIndex(int startNodeIndex) {
			this.startNodeIndex = startNodeIndex;
		}

		public int getEndNodeIndex() {
			return endNodeIndex;
		}

		public void setEndNodeIndex(int endNodeIndex) {
			this.endNodeIndex = endNodeIndex;
		}

		public int getEdgeWeight() {
			return edgeWeight;
		}

		public void setEdgeWeight(int edgeWeight) {
			this.edgeWeight = edgeWeight;
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
		
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		
		@Override
		public String toString() {
			return "Node [id=" + id + ", name=" + name + "]";
		}
		
	}
}
