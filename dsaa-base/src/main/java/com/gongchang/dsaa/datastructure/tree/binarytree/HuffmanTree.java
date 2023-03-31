package com.gongchang.dsaa.datastructure.tree.binarytree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 哈夫曼树（又叫赫夫曼树，霍夫曼树，最优二叉树）
 * 
 * 1.哈夫曼树：给定n个权值作为n个叶子节点，构造一颗二叉树，使该树的带权路径长度达到最小，这样的树是哈夫曼树
 * 2.节点的路径：从根节点到达某个子孙节点的通路就是某个子孙节点的路径
 * 3.节点的路径长度：假设两个节点之间的路径长度为1，从根节点到叶子节点走过的路径长度之和就是节点的路径长度
 * 4.节点的权：给某个节点赋予一个某种含义的值，这个值就是该节点的权
 * 5.节点的带权路径长度：节点的权值*节点路径长度
 * 6.树的带权路径长度：所有叶子节点的带权路径长度的和，记作WPL（weighted path length）
 * 
 * 由上述1和6中的描述可知：最优哈夫曼树中权值越大的节点距离根节点越近，即哈夫曼树是WPL最小的数
 * 
 * 根据不同的排序方式，最后生成的哈夫曼树可能是不一样的
 * （因为参与排序的节点中可能存在大小相同的节点，根据排序方法的不同两个节点的位置可能不一样导致最后的树也不一样），
 * 但是WPL是一样的，都是最小值。
 * 
 * 哈夫曼树可用于在通讯领域中压缩数据
 * 
 * @author GongChang
 *
 */
public class HuffmanTree {

	public static void main(String[] args) {
		int[] intArr = {1,3,2,7};
		HuffmanTree huffmanTree = new HuffmanTree();
		huffmanTree.buildHuffmanTree(intArr);
		huffmanTree.preOrderTraversal();
	}
	
	private Node root;
	
	
	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public void preOrderTraversal() {
		if(root==null) {
			System.out.println("二叉树为空，不能进行前序遍历");
		}else {
			root.preOrderTraversal();
		}
	}
	
	private void buildHuffmanTree(int[] intArr) {
		List<Node> nodeList = new ArrayList<Node>();
		for (int i : intArr) {
			nodeList.add(new Node(i));
		}
		
		while(nodeList.size()>1) {
			Collections.sort(nodeList);
			
			Node leftNode = nodeList.get(0);
			Node rightNode = nodeList.get(1);
			Node parentNode = new Node(leftNode.getData()+rightNode.getData());
			parentNode.setLeftNode(leftNode);
			parentNode.setRightNode(rightNode);
			
			nodeList.remove(0);
			nodeList.remove(0);
			nodeList.add(parentNode);
		}
		
		setRoot(nodeList.get(0));
	}
	
	private class Node implements Comparable<Node>{

		private int data;
		
		private Node leftNode;
		
		private Node rightNode;
		
		
		public Node(int data) {
			super();
			this.data = data;
		}

		
		private void preOrderTraversal() {
			this.printNodeInfo();
			
			if(leftNode!=null) {
				leftNode.preOrderTraversal();
			}
			
			if(rightNode!=null) {
				rightNode.preOrderTraversal();
			}
		}
		
		private void printNodeInfo() {
			System.out.println(this.getData());
		}
		
		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}

		public Node getLeftNode() {
			return leftNode;
		}

		public void setLeftNode(Node leftNode) {
			this.leftNode = leftNode;
		}

		public Node getRightNode() {
			return rightNode;
		}

		public void setRightNode(Node rightNode) {
			this.rightNode = rightNode;
		}


		@Override
		public int compareTo(Node node) {
			return this.data-node.data;
		}
		
	}
}
