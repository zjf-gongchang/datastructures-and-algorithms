package com.gongchang.dsaa.datastructure.tree.binarytree;


/**
 * 二叉树
 * 
 * 二叉树是含有两个子节点的树
 * 节点，根节点，父节点，子节点，叶子节点，路径，权，层，子树，高度（最大层数），森林，阶
 * 
 * 满二叉树：所有叶子节点在最后一层，节点数满足2^n-1(n为层数)
 * 完全二叉树：叶子节点在最后一层和倒数第二层，倒数第一层左边连续，倒数第二层右边连续
 * 其他二叉树在单独的类中介绍
 * 
 * 二叉树也可以使用数组存储，如果i是父节点的下标，则2i+1是左子节点，2i+2是右子节点
 * 
 * @author GongChang
 *
 */
public class BinaryTree {

	public static void main(String[] args) {
		BinaryTree binaryTree = new BinaryTree();
		binaryTree.buildBinaryTree();
		
		System.out.println("前序遍历");
		binaryTree.preOrderTraversal();
		
		System.out.println("中序遍历");
		binaryTree.midOrderTraversal();
		
		System.out.println("后序遍历");
		binaryTree.postOrderTraversal();
		
		System.out.println("前序查找");
		System.out.println(binaryTree.preOrderSearch(3));;
		System.out.println("中序查找");
		System.out.println(binaryTree.midOrderSearch(3));;
		System.out.println("后序查找");
		System.out.println(binaryTree.postOrderSearch(1));;
		
	}
	
	public void buildBinaryTree() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		Node node7 = new Node(7);
		
		node1.setLeftNode(node2);
		node1.setRightNode(node3);
		
		node2.setLeftNode(node4);
		node2.setRightNode(node5);
		
		node4.setRightNode(node6);
		
		node5.setLeftNode(node7);
		
		setRoot(node1);
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
	
	public void midOrderTraversal() {
		if(root==null) {
			System.out.println("二叉树为空，不能进行中序遍历");
		}else {
			root.midOrderTraversal();
		}
	}
	
	public void postOrderTraversal() {
		if(root==null) {
			System.out.println("二叉树为空，不能进行后序遍历");
		}else {
			root.postOrderTraversal();
		}
	}
	
	public Node preOrderSearch(int findValue){
		if(root==null) {
			System.out.println("二叉树为空，不能进行前序查找");
			return null;
		}else {
			return root.preOrderSearch(findValue);
		}
			
	}
	
	public Node midOrderSearch(int findValue){
		if(root==null) {
			System.out.println("二叉树为空，不能进行中序查找");
			return null;
		}else {
			return root.midOrderSearch(findValue);
		}
			
	}
	
	public Node postOrderSearch(int findValue){
		if(root==null) {
			System.out.println("二叉树为空，不能进行后序查找");
			return null;
		}else {
			return root.postOrderSearch(findValue);
		}
			
	}


	private class Node{
		
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
		
		private void midOrderTraversal() {
			if(leftNode!=null) {
				leftNode.midOrderTraversal();
			}
			
			this.printNodeInfo();
			
			if(rightNode!=null) {
				rightNode.midOrderTraversal();
			}
		}
		
		private void postOrderTraversal() {
			if(leftNode!=null) {
				leftNode.postOrderTraversal();
			}
			
			if(rightNode!=null) {
				rightNode.postOrderTraversal();
			}
			
			this.printNodeInfo();
		}
		
		private Node preOrderSearch(int findValue) {
			if(this.getData()==findValue) {
				return this;
			}
			
			Node result = null;
			if(leftNode!=null) {
				result = leftNode.preOrderSearch(findValue);
				if(result!=null) {
					return result;
				}
			}
			
			if(rightNode!=null) {
				result = rightNode.preOrderSearch(findValue);
			}
			return result;
		}
		
		private Node midOrderSearch(int findValue) {
			Node result = null;
			if(leftNode!=null) {
				result = leftNode.midOrderSearch(findValue);
				if(result!=null) {
					return result;
				}
			}
			
			if(this.getData()==findValue) {
				return this;
			}
			
			if(rightNode!=null) {
				result = rightNode.midOrderSearch(findValue);
			}
			return result;
		}
		
		private Node postOrderSearch(int findValue) {
			Node result = null;
			if(leftNode!=null) {
				result = leftNode.postOrderSearch(findValue);
				if(result!=null) {
					return result;
				}
			}
			
			if(rightNode!=null) {
				result = rightNode.postOrderSearch(findValue);
				if(result!=null) {
					return result;
				}
			}
			
			if(this.getData()==findValue) {
				return this;
			}
			return null;
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
		public String toString() {
			return "Node [data=" + data + ", leftNode=" + leftNode + ", rightNode=" + rightNode + "]";
		}
		
	}
	
}
