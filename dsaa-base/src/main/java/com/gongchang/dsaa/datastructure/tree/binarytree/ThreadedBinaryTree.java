package com.gongchang.dsaa.datastructure.tree.binarytree;

/**
 * 线索二叉树
 * 
 * 有n个节点的二叉树有n+1（计算方式：2n-(n-1)）个空指针域，这些空指针域可以指向某种遍历方式（前序，中序，后序）下的前驱结点或后继节点
 * 左指针为空时指向前驱结点，不为空时指向左子树
 * 右指针为空时指向后继节点，不为空时指向右子数
 * 
 * 
 * @author GongChang
 *
 */
public class ThreadedBinaryTree {
	public static void main(String[] args) {
		ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
		threadedBinaryTree.buildBinaryTree();
		
		threadedBinaryTree.preOrderThreadBinaryTree();
		threadedBinaryTree.preOrderRecListBinaryTree();
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
	
	private Node preThreadedNode;
	
	private Boolean isPostRecListEnd = false;
	
	
	public void preOrderThreadBinaryTree() {
		preOrderThreadBinaryTree(root);
	}
	
	public void midOrderThreadBinaryTree() {
		midOrderThreadBinaryTree(root);
	}
	
	public void postOrderThreadBinaryTree() {
		postOrderThreadBinaryTree(root);
	}
	
	public void preOrderLoopListBinaryTree() {
		preOrderLoopListBinaryTree(root);
	}
	
	public void preOrderRecListBinaryTree() {
		preOrderRecListBinaryTree(root);
	}

	public void midOrderLoopListBinaryTree() {
		midOrderLoopListBinaryTree(root);
	}
	
	public void midOrderRecListBinaryTree() {
		midOrderRecListBinaryTree(root);
	}
	
	public void postOrderLoopListBinaryTree() {
		postOrderLoopListBinaryTree(root);
	}
	
	public void postOrderRecListBinaryTree() {
		postOrderRecListBinaryTree(root);
	}
	
	/**
	 * 前序线索二叉树
	 * 
	 * @param node 需要线索化的树的根节点
	 */
	private void preOrderThreadBinaryTree(Node node) {
		// 结束递归
		if(node==null) {
			return;
		}
		
		// 当前节点处理
		if(node.getLeftNode()==null) {
			node.setLeftNode(preThreadedNode);
			node.setLeftType(1);
		}
		if(preThreadedNode!=null&&preThreadedNode.getRightNode()==null) {
			preThreadedNode.setRightNode(node);
			preThreadedNode.setRightType(1);
		}
		preThreadedNode = node;
		
		// 左递归
		if(node.getLeftType()!=1) {
			preOrderThreadBinaryTree(node.getLeftNode());
		}
		
		// 右递归
		if(node.getRightType()!=1) {
			preOrderThreadBinaryTree(node.getRightNode());
		}
	}
	
	/**
	 * 中序线索二叉树
	 * 
	 * @param node 需要线索化的树的根节点
	 */
	private void midOrderThreadBinaryTree(Node node) {
		// 结束递归
		if(node==null) {
			return;
		}
		
		// 左递归
		if(node.getLeftType()!=1) {
			midOrderThreadBinaryTree(node.getLeftNode());
		}
		
		// 当前节点处理
		if(node.getLeftNode()==null) {
			node.setLeftNode(preThreadedNode);
			node.setLeftType(1);
		}
		if(preThreadedNode!=null&&preThreadedNode.getRightNode()==null) {
			preThreadedNode.setRightNode(node);
			preThreadedNode.setRightType(1);
		}
		preThreadedNode = node;
		
		// 右递归
		if(node.getRightType()!=1) {
			midOrderThreadBinaryTree(node.getRightNode());
		}
	}
	
	/**
	 * 后序线索二叉树
	 * 
	 * @param node 需要线索化的树的根节点
	 */
	private void postOrderThreadBinaryTree(Node node) {
		// 递归退出条件
		if(node==null) {
			return;
		}
		
		// 左递归
		if(node.getLeftType()!=1) {
			postOrderThreadBinaryTree(node.getLeftNode());
		}
		
		// 右递归
		if(node.getRightType()!=1) {
			postOrderThreadBinaryTree(node.getRightNode());
		}
		
		// 当前节点处理
		if(node.getLeftNode()==null) {
			node.setLeftNode(preThreadedNode);
			node.setLeftType(1);
		}
		if(preThreadedNode!=null&&preThreadedNode.getRightNode()==null) {
			preThreadedNode.setRightNode(node);
			preThreadedNode.setRightType(1);
		}
		preThreadedNode = node;
	}
	
	/**
	 * 前序递归遍历二叉树
	 * 
	 * @param node 需要前序遍历的根节点
	 */
	private void preOrderRecListBinaryTree(Node node) {
		if(node==null) {
			return;
		}
		
		Node curNode = node; 
		curNode.printNodeInfo();
		
		while(curNode.getLeftType()!=1) {
			curNode = curNode.getLeftNode();
			curNode.printNodeInfo();
		}
		
		while(curNode.getRightType()==1) {
			curNode = curNode.getRightNode();
			curNode.printNodeInfo();
		}
		
		if(curNode.getLeftType()!=1) {
			preOrderRecListBinaryTree(curNode.getLeftNode());
		}else {
			preOrderRecListBinaryTree(curNode.getRightNode());
		}
		
		
	}
	
	/**
	 * 前序循环遍历二叉树
	 * 
	 * @param node 需要前序遍历的根节点
	 */
	private void preOrderLoopListBinaryTree(Node node) {
		if(node==null) {
			return;
		}
		
		Node curNode = node; 
		while(curNode!=null) {
			
			curNode.printNodeInfo();
			
			while(curNode.getLeftType()!=1) {
				curNode = curNode.getLeftNode();
				curNode.printNodeInfo();
			}
			
			while(curNode.getRightType()==1) {
				curNode = curNode.getRightNode();
				curNode.printNodeInfo();
			}
			
			if(curNode.getLeftType()!=1) {
				curNode=curNode.getLeftNode();
			}else {
				curNode=curNode.getRightNode();
			}
			
		}
		
	}
	
	/**
	 * 中序递归遍历二叉树
	 * 
	 * @param node 需要中序遍历的根节点
	 */
	private void midOrderRecListBinaryTree(Node node) {
		if(node==null) {
			return;
		}
		
		Node curNode = node; 
		while(curNode.getLeftType()!=1) {
			curNode = curNode.getLeftNode();
		}
		curNode.printNodeInfo();
		
		while(curNode.getRightType()==1) {
			curNode = curNode.getRightNode();
			curNode.printNodeInfo();
		}
		
		midOrderRecListBinaryTree(curNode.getRightNode());
	}
	
	/**
	 * 中序循环遍历二叉树
	 * 
	 * @param node 需要中序遍历的根节点
	 */
	private void midOrderLoopListBinaryTree(Node node) {
		if(node==null) {
			return;
		}
		
		Node curNode = node; 
		while(curNode!=null) {
			
			while(curNode.getLeftType()!=1) {
				curNode = curNode.getLeftNode();
			}
			
			curNode.printNodeInfo();
			
			while(curNode.getRightType()==1) {
				curNode = curNode.getRightNode();
				curNode.printNodeInfo();
			}
			
			curNode = curNode.getRightNode();
		}
		
	}
	
	/**
	 * 后序循环遍历二叉树
	 * 
	 * @param node 需要后序遍历的根节点
	 */
	private void postOrderLoopListBinaryTree(Node node) {
		if(node==null) {
			return;
		}
		
		Node curNode = node;
		retry:
		while(curNode!=null) {
			while(curNode.getLeftType()!=1) {
				curNode = curNode.getLeftNode();
			}
			
			if(curNode.getRightType()==0) {
				curNode = curNode.getRightNode();
				continue retry;
			}else {
				curNode.printNodeInfo();
				while(curNode.getRightType()==1) {
					curNode = curNode.getRightNode();
					curNode.printNodeInfo();
				}
			}
			
			if(curNode==root) {
				curNode=null;
			}else if(curNode==curNode.getParentNode().getRightNode()){
				curNode = curNode.getParentNode();
			}else if(curNode==curNode.getParentNode().getLeftNode()&&curNode.getParentNode().getRightNode()==null) {
				curNode = curNode.getParentNode();
			}else if(curNode==curNode.getParentNode().getLeftNode()&&curNode.getParentNode().getRightNode()!=null) {
				curNode=curNode.getParentNode().getRightNode();
			}
		}
	}
	
	/**
	 * 后序递归遍历二叉树
	 * 
	 * @param node 需要后序遍历的根节点
	 */
	private void postOrderRecListBinaryTree(Node node) {
		if(node==null) {
			return;
		}
		
		Node curNode = node;
		while(curNode!=null&&!isPostRecListEnd) {
			while(curNode.getLeftType()!=1) {
				curNode = curNode.getLeftNode();
			}
			
			if (curNode.getRightType() == 0) {
				postOrderRecListBinaryTree(curNode.getRightNode());
			} else {
				curNode.printNodeInfo();
				while (curNode.getRightType() == 1) {
					curNode = curNode.getRightNode();
					curNode.printNodeInfo();
				}
			}
			 
			if(curNode==root) {
				curNode=null;
				isPostRecListEnd=true;
			}else if(curNode==curNode.getParentNode().getRightNode()){
				curNode = curNode.getParentNode();
			}else if(curNode==curNode.getParentNode().getLeftNode()&&curNode.getParentNode().getRightNode()==null) {
				curNode = curNode.getParentNode();
			}else if(curNode==curNode.getParentNode().getLeftNode()&&curNode.getParentNode().getRightNode()!=null) {
				postOrderRecListBinaryTree(curNode.getParentNode().getRightNode());
			}
		}
	}
	
	
	public void setRoot(Node root) {
		this.root = root;
	}


	private class Node{
		private int data;
		private Node parentNode;
		private Node leftNode;
		private Node rightNode;
		/**
		 * 1-前驱节点，0-左子树
		 */
		private int leftType;
		
		/**
		 * 1-后继节点，0-右子树
		 */
		private int rightType;
		
		
		public Node() {
			super();
		}

		public Node(int data) {
			super();
			this.data = data;
		}
		
		
		public void printNodeInfo() {
			System.out.println(this.toString());
		}
		
		
		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}

		public Node getParentNode() {
			return parentNode;
		}

		public void setParentNode(Node parentNode) {
			this.parentNode = parentNode;
		}

		public Node getLeftNode() {
			return leftNode;
		}

		public void setLeftNode(Node leftNode) {
			if(leftNode!=null&&leftNode.getParentNode()==null) {
				leftNode.setParentNode(this);
			}
			this.leftNode = leftNode;
		}

		public Node getRightNode() {
			return rightNode;
		}

		public void setRightNode(Node rightNode) {
			if(rightNode!=null&&rightNode.getParentNode()==null) {
				rightNode.setParentNode(this);
			}
			this.rightNode = rightNode;
		}

		public int getLeftType() {
			return leftType;
		}

		public void setLeftType(int leftType) {
			this.leftType = leftType;
		}

		public int getRightType() {
			return rightType;
		}

		public void setRightType(int rightType) {
			this.rightType = rightType;
		}

		@Override
		public String toString() {
			return "Node [data=" + data + ", leftNode=" + ((leftNode==null)?"null":leftNode.getData()) + ", rightNode=" + ((rightNode==null)?"null":rightNode.getData()) + ", leftType="
					+ leftType + ", rightType=" + rightType + "]";
		}
		
	}
}
