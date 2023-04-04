package com.gongchang.dsaa.datastructure.tree.binarytree;

/**
 * 二叉排序树
 * 
 * 数组适合做get和set访问，因为插入删除需要移动数组，相对链表来说开销很大
 * 来拿表适合做插入删除，因为get和set需要移动指针，相对数组来说开销很大
 * 
 * 数组做搜索：
 * 	若是有序数组可以用二分查找，斐波那契查找，差值查找等方式，但是维护有序数组开销很大（比如有新的数据插入）
 * 	若是无序数组则需要遍历，开销很大
 * 链表做搜索：
 * 	开销都大，因为需要遍历链表
 * 
 * 二叉排序树把get,set,插入，删除，搜索的时间复杂度控制在了log(n),即提高了搜索性能，而且使得get,set,插入，删除的性能也不差
 * 
 * @author gongchang
 *
 */
public class BinarySortTree {

	public static void main(String[] args) {
		BinarySortTree binarySortTree = new BinarySortTree();
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
		}
		
		public void delNode(int delValue){
			Node targetNode = getNode(delValue);
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
