package com.gongchang.dsaa.algorithm.classic;

/**
 * 约瑟夫环
 * 
 * 介绍：n个人围成一圈，从第k个人开始数，数到第m个人，第m个人出圈；然后再从下一个人开始数，再次数到第m个人，第m个人出圈
 * 以此类推，直到最后一个人出圈，这样就形成了一个出圈的顺序
 * 
 * @author gongchang
 */
public class JosephRing {

	public static void main(String[] args) {
		JosephRing josephRing = new JosephRing();
		josephRing.buildJosephRing(10);
		josephRing.printJosephRing();
		josephRing.outOfTheRing(3, 2);
	}
	
	private int count;
	
	private Node first;
	
	
	public JosephRing() {
		super();
	}

	
	public void addNode(Node node){
		if(this.first==null){
			this.first = node;
			node.next = this.first;
		}
		
		Node cur = this.first;
		while(cur.next!=first){
			cur = cur.next;
		}
		cur.next = node;
		node.next = first;
	}
	
	public void buildJosephRing(int size){
		for(int i=0; i<size; i++){
			addNode(new Node(i+1));
		}
		
		count = size;
	}
	
	public void printJosephRing(){
		System.out.println(">>>>>>>>>>>>>>>>打印约瑟夫环：");
		Node cur = this.first;
		while(true){
			System.out.println("约瑟夫环编号为："+cur.getId());
			if(cur.next==this.first){
				break;
			}
			cur = cur.next;
		}
	}
	
	public void outOfTheRing(int k, int m){
		System.out.println(">>>>>>>>>>>>>>>>约瑟夫环出环顺序为：");
		
		// 指针移动到起始位置
		Node pre = first;
		// 这里的first已经没有用，置为空即可，方便垃圾回收
		first = null;
		while(k>1){
			pre = pre.next;
			k--;
		}
		
		while(true){
			// 走到这里说明只剩下一个节点，出还后退出循环
			if(pre.next==pre){
				System.out.println("编号："+pre.next.getId()+"出环");
				break;
			}
			
			for(int i=0; i<m-1; i++){
				pre = pre.getNext();
			}
			
			System.out.println("编号："+pre.next.getId()+"出环");
			
			pre.next = pre.next.next;
		}
	}
	
	public Node getFirst() {
		return first;
	}

	public void setFirst(Node first) {
		this.first = first;
	}
	

	private class Node{
		
		private int id;
		
		private Node next;

		
		public Node(int id) {
			super();
			this.id = id;
		}
		

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}
	}
}
