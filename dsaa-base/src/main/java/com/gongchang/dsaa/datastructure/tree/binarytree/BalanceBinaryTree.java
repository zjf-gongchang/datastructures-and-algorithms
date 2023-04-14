package com.gongchang.dsaa.datastructure.tree.binarytree;

/**
 * 平衡二叉树（又叫平衡二叉搜素树）
 * 
 * 介绍：平衡二叉树中左右子树最大高度差不会超过1，并且树中的任意一个子数都是一棵平衡二叉树
 * 
 * 二叉排序数在极端的情况下可能会退化成单链表，查询性能极其差；
 * 就算没有退化成单链表，二叉排序数在高度很大的情况下也会影响查询性能
 * 
 * 平衡二叉树通过某种方式降低树的高度，解决了二叉排序树因为高度引起的查询性能问题
 * 
 * 平衡二叉树的实现方式：红黑树，AVL树，替罪羊树，Treap，伸展树等
 * 
 * 此案例的实现位置是在执行addNode的时候，添加完节点立即判断是否需要平衡
 * 
 * @author gongchang
 *
 */
public class BalanceBinaryTree {

	public static void main(String[] args) {
		BalanceBinaryTree binarySortTree = new BalanceBinaryTree();
		int[] intArr = {1,7,4,3,5,6};
		binarySortTree.buildBinaryTree(intArr);
		
		System.out.println("中序遍历节点：");
		binarySortTree.midOrderTraversal();
		System.out.println();
		
		System.out.println("更新节点7的值为7-new_name");
		binarySortTree.setNode(new Node(7, "7-new_name"));
		System.out.println();
		
		Node node = binarySortTree.getNode(7);
		System.out.println("更新后的节点是："+node);
		System.out.println();
		
		/*System.out.println("中序遍历节点：");
		binarySortTree.midOrderTraversal();
		System.out.println();
		
		System.out.println("删除节点：5");
		binarySortTree.delNode(5);
		System.out.println();*/
		
		System.out.println("中序遍历节点：");
		binarySortTree.midOrderTraversal();
		System.out.println();
		
		System.out.println("删除节点：4");
		binarySortTree.delNode(4);
		System.out.println();
		
		System.out.println("中序遍历节点：");
		binarySortTree.midOrderTraversal();
		System.out.println();
	}
	
	public void buildBinaryTree(int[] intArr){
		for (int i : intArr) {
			addNode(new Node(i, i+"_name"));
		}
	}
	
	private Node root;
	
	
	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public void midOrderTraversal(){
		if(root==null){
			printEmptyTreeInfo();
		}else{
			root.midOrderTraversal();
		}
	}
	
	public Node delRightMinTree(Node node){
		Node curNode = node;
		while(curNode.getLeftNode()!=null){
			curNode = curNode.getLeftNode();
		}
		delNode(curNode.getData());
		return curNode;
	}
	
	public Node getParentNode(int findValue){
		if(root==null){
			return null;
		}else{
			return root.getParentNode(findValue);
		}
	}
	
	
	public void addNode(Node node){
		if(root==null){
			root=node;
		}else{
			root.addNode(node);
		}
	}
	
	public void delNode(int delValue){
		if(root==null){
			printEmptyTreeInfo();
		}else{
			Node targetNode = root.getNode(delValue);
			if(targetNode==null){
				return;
			}
			if(root.getLeftNode()==null&&root.getRightNode()==null){
				root=null;
				return;
			}
			Node parentNode = getParentNode(delValue);
			if(targetNode.getLeftNode()==null&& targetNode.getRightNode()==null){
				if(parentNode.getLeftNode()!=null&&parentNode.getLeftNode().getData()==delValue){
					parentNode.setLeftNode(null);
				}else if(parentNode.getRightNode()!=null&&parentNode.getRightNode().getData()==delValue){
					parentNode.setRightNode(null);
				}
			}else if(targetNode.getLeftNode()!=null&& targetNode.getRightNode()!=null){
				Node rightMinTree = delRightMinTree(targetNode.getRightNode());
				targetNode.setData(rightMinTree.getData());
				targetNode.setName(rightMinTree.getName());
			}else{
				if(targetNode.getLeftNode()!=null){
					if(parentNode!=null){
						if(parentNode.getLeftNode().getData()==delValue){
							parentNode.setLeftNode(targetNode.getLeftNode());
						}else{
							parentNode.setRightNode(targetNode.getLeftNode());
						}
					}else{
						root = targetNode.getLeftNode();
					}
				}else{
					if(parentNode!=null){
						if(parentNode.getLeftNode().getData()==delValue){
							parentNode.setLeftNode(targetNode.getRightNode());
						}else{
							parentNode.setRightNode(targetNode.getRightNode());
						}
					}else{
						root = targetNode.getRightNode();
					}
				}
			}
		}
	}
	
	public void setNode(Node node){
		if(root==null){
			printEmptyTreeInfo();
		}else{
			root.setNode(node);
		}
	}
	
	public Node getNode(int findValue){
		if(root==null){
			printEmptyTreeInfo();
			return null;
		}else{
			return root.getNode(findValue);
		}
	}
	
	private void printEmptyTreeInfo(){
		System.out.println("这是一颗空树");
	}
	
	private static class Node{
		private int data;
		
		private String name;
		
		private Node leftNode;
		
		private Node rightNode;

		
		public Node(int data, String name) {
			super();
			this.data = data;
			this.name = name;
		}
		
		public int getHeight(){
			return Math.max(this.leftNode==null?0:this.leftNode.getHeight(), this.rightNode==null?0:this.rightNode.getHeight())+1;
		}
		
		public int getLeftNodeHeight(){
			return leftNode==null?0:leftNode.getHeight();
		}
		
		public int getRightNodeHeight(){
			return rightNode==null?0:rightNode.getHeight();
		}
		
		public void rotation(){
			// 左旋转
			if(this.getRightNodeHeight()-this.getLeftNodeHeight()>1){
				// 右子树右旋转
				if(rightNode!=null&&rightNode.getLeftNodeHeight()>rightNode.getRightNodeHeight()){
					rightNode.rightRotation();
				}
				leftRotation();
				
				return;
			}
			// 右旋转
			if(getLeftNodeHeight()-getRightNodeHeight()>1){
				// 左子树左旋转
				if(leftNode!=null&&leftNode.getRightNodeHeight()>leftNode.getLeftNodeHeight()){
					leftNode.leftRotation();
				}
				rightRotation();
			}
		}
		
		private void leftRotation(){
			Node newNode = new Node(data, name);
			newNode.setLeftNode(leftNode);
			newNode.setRightNode(rightNode.getLeftNode());
			
			setData(rightNode.getData());
			setName(rightNode.getName());
			setLeftNode(newNode);
			setRightNode(rightNode.getRightNode());
		}
		
		private void rightRotation(){
			Node newNode = new Node(data, name);
			newNode.setRightNode(rightNode);
			newNode.setLeftNode(leftNode.getRightNode());
			
			setData(leftNode.getData());
			setName(leftNode.getName());
			setRightNode(newNode);
			setLeftNode(leftNode.getLeftNode());
		}
		
		public void midOrderTraversal(){
			if(leftNode!=null){
				leftNode.midOrderTraversal();
			}
			
			printNodeInfo();
			
			if(rightNode!=null){
				rightNode.midOrderTraversal();
			}
		}
		
		public Node getParentNode(int findValue){
			if(leftNode!=null&&leftNode.getData()==findValue||rightNode!=null&&rightNode.getData()==findValue){
				return this;
			}else{
				if(findValue<this.getData()&&leftNode!=null){
					return leftNode.getParentNode(findValue);
				}else if(findValue>this.getData()&&rightNode!=null){
					return rightNode.getParentNode(findValue);
				}else{
					return null;
				}
			}
		}
		
		public void addNode(Node node){
			if(node.getData()<this.getData()){
				if(leftNode!=null){
					leftNode.addNode(node);
				}else{
					leftNode = node;
				}
			}else {
				if(rightNode!=null){
					rightNode.addNode(node);
				}else{
					rightNode = node;
				}
			}
			
			// 旋转
			rotation();
		}
		
		public void setNode(Node node){
			if(node.getData()<this.getData()){
				if(leftNode!=null){
					leftNode.setNode(node);
				}
			}else if(node.getData()>this.getData()){
				if(rightNode!=null){
					rightNode.setNode(node);
				}
			}else{
				this.setName(node.getName());
			}
		}
		
		public Node getNode(int findValue){
			if(findValue<this.getData()){
				if(leftNode!=null){
					return leftNode.getNode(findValue);
				}else{
					return null;
				}
			}else if(findValue>this.getData()){
				if(rightNode!=null){
					return rightNode.getNode(findValue);
				}else{
					return null;
				}
			}else{
				return this;
			}
		}
		
		public void printNodeInfo(){
			System.out.println(this.toString());
		}

		
		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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
		public String toString() {
			return "Node [data=" + data + ", name=" + name + ", leftNode=" + (leftNode!=null?leftNode.getData():null) + ", rightNode=" + (rightNode!=null?rightNode.getData():null)
					+ "]";
		}
		
	}
}
