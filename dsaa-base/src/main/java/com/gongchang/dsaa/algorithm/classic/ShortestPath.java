package com.gongchang.dsaa.algorithm.classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 最短路径：一张无向连通网中，找出从一个顶点出发，到其它顶点的最短路径，即：从起始顶点到结束顶点所走过的路线的权重之和最小的路径：
 * 
 * 最短路径可以使用迪杰斯特拉算法和佛洛伊德算法计算得到
 * 
 * 迪杰斯特拉算法核心思想：从一个节点开始以宽度优先的方式向外扩展，扩展的过程中记录访问信息、前驱顶点、最小距离，直到扩展到终点为止
 * 
 * 佛洛伊德算法核心思想：以任意顶点作为出发顶点，计算从任意顶点到另一任意顶点的最短距；3层暴力循环，详细逻辑看方法floyd()
 * 
 * 佛洛伊德算法与佛洛伊德算法比较：
 * 1.佛洛伊德算法计算出来的是从某一个指定的顶点到其它所有顶点的最短路径
 * 2.佛洛伊德算法计算出来的是从每一个顶点到其它顶点的最短路径
 * 
 * @Author	gongchang
 */
public class ShortestPath {

	public static void main(String[] args) {
		List<Integer> sourceNodeList = ShortestPath.getSourceNodeList();
		List<int[]> sourceEdgesList = ShortestPath.getEdgesList();
		
		ShortestPath shortestPath = new ShortestPath(sourceNodeList.size());
		shortestPath.buildGraph(sourceNodeList, sourceEdgesList);
		
		shortestPath.showGrpah();
		
		int startIndex = 0;
		int endIndex = 5;
		
		System.out.println("迪杰斯特拉算法==================================");
		SinglePathInfo dijkstra = shortestPath.dijkstra(startIndex);
		List<Node> shortestPathList = dijkstra.getShortestPathList(endIndex);
		int shortestPathValue = dijkstra.getShortestPathValue(endIndex);
		shortestPath.printPathInfo(startIndex, endIndex, shortestPathList, shortestPathValue);
		
		System.out.println("佛洛伊德算法==================================");
		MultiPathInfo floyd = shortestPath.floyd();
		List<Node> shortestPathList2 = floyd.getShortestPathList(startIndex, endIndex);
		int shortestPathValue2 = floyd.getShortestPathValue(startIndex, endIndex);
		shortestPath.printPathInfo(startIndex, endIndex, shortestPathList2, shortestPathValue2);
		
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

	
	public ShortestPath(int nodeNum) {
		nodeList = new ArrayList<Node>(nodeNum);
		edges = new int[nodeNum][nodeNum];
		edgeSum = 0;
		
		for(int i=0; i<edges.length; i++){
			for(int j=0; j<edges[i].length; j++){
				if(i==j){
					edges[i][j] = 0;
				}else{
					edges[i][j] = NO_CONNECTED;
				}
			}
		}
	}
	
	public void printPathInfo(int startIndex, int endIndex, List<Node> shortestPathList, int shortestPathValue){
		System.out.println("从："+nodeList.get(startIndex).getName()+"到："+nodeList.get(endIndex).getName()+"的最短路径是："+shortestPathValue+"，经过的路线是：");
		for (Node node : shortestPathList) {
			System.out.println(node.getName());
		}
	}
	
	/**
	 * 佛洛伊德算法计算最短路径
	 * 
	 * @return 
	 */
	public MultiPathInfo floyd(){
		MultiPathInfo multiPathInfo = new MultiPathInfo(edges);
		
		// 以顶点m作为中间顶点
		for(int m=0; m<multiPathInfo.getNodeNum(); m++){
			// 从顶点i出发
			for(int i=0; i<multiPathInfo.getNodeNum(); i++){
				if(edges[i][m]==NO_CONNECTED){
					continue;
				}
				// 到达j顶点
				for(int j=0; j<multiPathInfo.getNodeNum(); j++){
					if(edges[m][j]==NO_CONNECTED){
						continue;
					}
					if(m==6&&i==2&&j==3){
						System.out.println(i);
					}
					// 计算最短路径和前驱顶点
					int tempMinDis = multiPathInfo.getMinDis(i, m)+multiPathInfo.getMinDis(m, j);
					if(tempMinDis<multiPathInfo.getMinDis(i, j) || edges[i][j]==NO_CONNECTED){
						multiPathInfo.setMinDis(i, j, tempMinDis);
						multiPathInfo.setPreIndex(i, j, multiPathInfo.getPreIndex(m, j));
					}
				}
			}
		}
		
		return multiPathInfo;
	}
	
	/**
	 * 迪杰斯特拉算法计算最短路径
	 * 
	 * @param startIndex 起始下标
	 * @return 
	 */
	public SinglePathInfo dijkstra(int startIndex){
		int nodeNum = getNodeNum();
		SinglePathInfo nodeInfo = new SinglePathInfo(nodeNum, startIndex);
		dijkstraConputerUtil(nodeInfo, startIndex);
		for(int i=1; i<nodeNum; i++){
			int nextIndex = nodeInfo.getNextNodeIndex();
			dijkstraConputerUtil(nodeInfo, nextIndex);
		}
		return nodeInfo;
	}
	
	private void dijkstraConputerUtil(SinglePathInfo nodeInfo, int index){
		int length = 0;
		for(int i=0; i<edges[index].length; i++){
			if(edges[index][i]==NO_CONNECTED){
				continue;
			}
			length = nodeInfo.getDisValue(index)+edges[index][i];
			if(!nodeInfo.getIsVisited(i) && length<nodeInfo.getDisValue(i)){
				nodeInfo.setPreIndex(i, index);
				nodeInfo.setDisValue(i, length);
			}
		}
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
	
	
	public int getNodeNum(){
		return nodeList.size();
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

	private class MultiPathInfo{
		int nodeNum;
		
		// 一维下标值是顶点的下标，每个下标对应的值表示出发顶点；
		// 二维下标值是顶点的下标，每个下标对应的值表示前驱顶点的下标
		int[][] preArr;
		// 一维下标值是顶点的下标，每个下标对应的值表示出发顶点；
		// 二维下标值是顶点的下标，每个下标对应的值表示从出发顶点到该下标对应顶点的距离
		int[][] minDisArr;
		
		
		public MultiPathInfo(int[][] edges) {
			this.nodeNum = edges.length;
			
			this.preArr = new int[nodeNum][nodeNum];
			for(int i=0; i<nodeNum; i++){
				Arrays.fill(preArr[i], i);
			}
			
			this.minDisArr = new int[nodeNum][nodeNum];
			for(int i=0; i<nodeNum; i++){
				for(int j=0; j<nodeNum; j++){
					minDisArr[i][j] = edges[i][j];
				}
			}
		}
		
		
		public List<Node> getShortestPathList(int startIndex, int endIndex){
			List<Integer> indexList = new ArrayList<Integer>();
			int i = endIndex;
			int[] pathArr = this.preArr[startIndex];
			while(true){
				indexList.add(i);
				if(i==startIndex){
					break;
				}
				i = pathArr[i];
			}
			
			List<Node> resultList = new ArrayList<Node>();
			for(int j=indexList.size()-1; j>=0; j--){
				resultList.add(nodeList.get(indexList.get(j)));
			}
			return resultList;
		}
		
		public int getShortestPathValue(int startIndex, int endIndex){
			return minDisArr[startIndex][endIndex];
		}
		
		
		public int getNodeNum() {
			return nodeNum;
		}

		public void setNodeNum(int nodeNum) {
			this.nodeNum = nodeNum;
		}

		public int getPreIndex(int startIndex, int endIndex){
			return preArr[startIndex][endIndex];
		}
		
		public void setPreIndex(int startIndex, int endIndex, int preIndex){
			preArr[startIndex][endIndex] = preIndex;
		}
		
		public int getMinDis(int startIndex, int endIndex){
			return minDisArr[startIndex][endIndex];
		}
		
		public void setMinDis(int startIndex, int endIndex, int minDis){
			minDisArr[startIndex][endIndex] = minDis;
		}
		
	}	
	
	private class SinglePathInfo{
		// 出发顶点的索引下标
		int startIndex;
		
		// 下标值是顶点的下标，每个下标对应的值表示该顶点是否被访问过
		int[] visitedArr;
		// 下标值是顶点的下标，每个下标对应的值表示前驱顶点的下标
		int[] preArr;
		// 下标值是顶点的下标，每个下标对应的值表示从出发顶点到该下标对应顶点的距离
		int[] minDisArr;
		
		
		public SinglePathInfo(int length, int startIndex) {
			super();
			this.startIndex = startIndex;
			this.visitedArr = new int[length];
			this.preArr = new int[length];
			this.minDisArr = new int[length];
			
			initPathInfo();
		}
		
		private void initPathInfo(){
			// 是否访问默认初始化为：未访问（0），数组初始化，默认就是0，不用设置
			// 前驱节点索引默认初始化为：无(0)，数组初始化，默认就是0，不用设置
			Arrays.fill(preArr, startIndex);
			// 最小距离默认初始化为：int最大值
			Arrays.fill(minDisArr, Integer.MAX_VALUE);
			
			// 设置起始节点已访问
			this.visitedArr[startIndex] = 1;
			// 设置起始顶点的前驱顶点为：无（0），数组初始化，默认就是0，不用设置
			// 设置到起始顶点的访问距离为：0
			this.minDisArr[startIndex] = 0;
		}
		
		public List<Node> getShortestPathList(int endIndex){
			List<Integer> indexList = new ArrayList<Integer>();
			int i = endIndex;
			while(true){
				indexList.add(i);
				if(i==startIndex){
					break;
				}
				i = preArr[i];
			}
			
			List<Node> resultList = new ArrayList<Node>();
			for(int j=indexList.size()-1; j>=0; j--){
				resultList.add(nodeList.get(indexList.get(j)));
			}
			return resultList;
		}
		
		public int getShortestPathValue(int endIndex){
			return minDisArr[endIndex];
		}
		
		public int getNextNodeIndex(){
			int min = Integer.MAX_VALUE;
			int index = 0;
			for(int i=0; i<visitedArr.length; i++){
				if(visitedArr[i]==0 && minDisArr[i]<min){
					min = minDisArr[i];
					index = i;
				}
			}
			
			setIsVisited(index);
			
			return index;
		}
		
		public void setIsVisited(int index){
			visitedArr[index]=1;
		}
		
		public Boolean getIsVisited(int index){
			return visitedArr[index]==1;
		}
		
		public int getPreIndex(int curIndex){
			return preArr[curIndex];
		}
		
		public void setPreIndex(int curIndex, int preIndex){
			preArr[curIndex] = preIndex;
		}
		
		public int getDisValue(int index){
			return minDisArr[index];
		}
		
		public void setDisValue(int index, int dis){
			minDisArr[index] = dis;
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
