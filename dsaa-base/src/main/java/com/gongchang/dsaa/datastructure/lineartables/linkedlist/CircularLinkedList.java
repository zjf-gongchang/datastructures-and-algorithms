package com.gongchang.dsaa.datastructure.lineartables.linkedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 环形链表
 *     链表上的所有元素组成一个环状
 * 
 * 应用
 *     约瑟夫环，丢手帕等。约瑟夫环案例见{@link com.gongchang.dsaa.algorithm.classic#JosephRing}
 * 
 * @author GongChang
 *
 */
public class CircularLinkedList<T> {
	
	public static void main(String[] args) {
		CircularLinkedList<Integer> circularLinkedList = new CircularLinkedList<>();
		for(int i=0; i<10; i++){
			circularLinkedList.add(i);
		}
		
		System.out.println("计数从0开始，查询第11个元素"+ circularLinkedList.get(11).getData());
		
		System.out.println("计数从0开始，删除第11个元素"+ circularLinkedList.remove(11).getData());
		
		System.out.println("遍历环形链表");
		Iterator<Node<Integer>> iterator = circularLinkedList.iterator();
		while(iterator.hasNext()){
			System.out.print(iterator.next().getData());
			if(iterator.hasNext()){
				System.out.print(",");
			}else{
				System.out.println();
				System.out.println();
			}
		}
		
		System.out.println("计数从0开始，查询第3个元素"+ circularLinkedList.get(3).getData());
		
		System.out.println("计数从0开始，删除第3个元素"+ circularLinkedList.remove(3).getData());
		
		System.out.println("遍历环形链表");
		Iterator<Node<Integer>> iterator2 = circularLinkedList.iterator();
		while(iterator2.hasNext()){
			System.out.print(iterator2.next().getData());
			if(iterator2.hasNext()){
				System.out.print(",");
			}else{
				System.out.println();
				System.out.println();
			}
		}
		
		List<Integer> intervalRemoveAll = circularLinkedList.intervalRemoveAll(3, 2);
		System.out.println("计数从0开始，从第3个元素开始，每隔2，从环重出一个元素");
		System.out.println(Arrays.toString(intervalRemoveAll.toArray()));
	}
	
	
	private Node<T> head = new Node<T>();
	
	private Integer size = 0;
	
	
	public CircularLinkedList() {
		super();
		// 自成环
		head.next = head;
	}

	
	private Node<T> getNextNode(Node<T> curNode){
		Node<T> result;
		if(curNode.next==head){
			result = curNode.next.next;
		}else{
			result = curNode.next;
		}
		return result;
	}
	
	public void add(T t){
		Node<T> node = new Node<>(t);
		if(head.next==head){
			head.next = node;
			node.next = head;
		}else{
			Node<T> curNode = head;
			while(curNode.next!=head){
				curNode = curNode.next;
			}
			curNode.next = node;
			node.next = head;
		}
		size++;
	}
	
	public Node<T> remove(int index){
		if(size==0){
			return null;
		}
		int i = 0;
		Node<T> curNode = head.next;
		while(i<index-1){
			curNode = getNextNode(curNode);
			i++;
		}
		Node<T> resultNode = getNextNode(curNode);
		if(curNode.next!=head){
			curNode.next = resultNode.next;
		}else{
			head.next = resultNode.next;
		}
		size--;
		return resultNode;
	}
	
	public List<T> intervalRemoveAll(int startIndex, int indexInterval){
		if(size==0){
			return null;
		}
		
		ArrayList<T> resultList = new ArrayList<T>();
		Node<T> startNode = head.next;
		int i = 0;
		while(i<startIndex){
			startNode = getNextNode(startNode);
			i++;
		}
		
		while(true){
			Node<T> curNode = startNode;
			if(head.next==head){
				break;
			}
			
			int j = 0;
			while(j<indexInterval-1){
				curNode = getNextNode(curNode);
				j++;
			}
			
			Node<T> resultNode = getNextNode(curNode);
			if(curNode.next!=head){
				curNode.next = resultNode.next;
			}else{
				head.next = resultNode.next;
			}
			size--;
			resultList.add(resultNode.getData());
			startNode = resultNode.next;
		}
		
		return resultList;
	}
	
	public Node<T> get(int index){
		if(size==0){
			return null;
		}
		int i = 0;
		Node<T> curNode = head.next;
		while(i<index){
			curNode = getNextNode(curNode);
			i++;
		}
		return curNode;
	}
	
	public Iterator<Node<T>> iterator() {
		return new Iterator<Node<T>>() {
			
			private Node<T> curNode = head;

			@Override
			public boolean hasNext() {
				return curNode.next!=head;
			}

			@Override
			public Node<T> next() {
				curNode = curNode.next;
				return curNode;
			}
		};
	}
	

	private static class Node<T>{
		
		private T data;
		
		private Node<T> next;

		
		public Node(T data) {
			super();
			this.data = data;
		}

		public Node() {
			super();
		}


		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}
		
	}

}
