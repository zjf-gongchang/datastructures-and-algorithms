package com.gongchang.dsaa.datastructure.lineartables.linkedlist;

/**
 * 双链表
 * 
 * @author GongChang
 *
 */
public class DoubleLinkedList<T> {

	public static void main(String[] args) {
		DoubleLinkedList<Integer> doubleLinkedList = new DoubleLinkedList<>();
		
		System.out.println("列表中添加0-9的元素");
		for(int i=0; i<10; i++){
			doubleLinkedList.add(i);
		}
		
		System.out.println("查询下标为2的元素："+ doubleLinkedList.get(2).getData());
		
		System.out.println("查询下标为7的元素："+ doubleLinkedList.get(7).getData());
		
		System.out.println("删除下标为2的元素："+ doubleLinkedList.remove(2).getData());
		
		System.out.println("删除下标为7的元素："+ doubleLinkedList.remove(7).getData());
		
		System.out.println("遍历列表：");
		for(int i=0; i<doubleLinkedList.size; i++){
			System.out.print(doubleLinkedList.get(i).getData());
			if(i!=doubleLinkedList.size-1){
				System.out.print(",");
			}
		}
		
	}
	
	
	private Node<T> head;
	
	private Node<T> tail;
	
	private Integer size = 0;
	
	
	public DoubleLinkedList() {
		super();
	}
	

	public void add(T t){
		Node<T> node = new Node<T>(t);
		if(head==null){
			head = node;
			tail = node;
		}else{
			node.pre = tail;
			tail.next = node;
			tail = node;
		}
		size++;
	}
	
	public Node<T> remove(int index){
		if(index>=size){
			throw new IndexOutOfBoundsException("下标越界");
		}
		
		Node<T> result = null;
		if(index<size/2){
			int i = 0;
			Node<T> curNode = head;
			while(i<index-1){
				curNode = curNode.next;
				i++;
			}
			result = curNode.next;
			curNode.next = curNode.next.next;
		}else{
			int i = size-1;
			Node<T> curNode = tail;
			while(i>index){
				curNode = curNode.pre;
				i--;
			}
			result = curNode.pre;
			curNode.pre.pre.next = curNode;
			curNode.pre = curNode.pre.pre;
		}
		size--;
		return result;
	}
	
	public Node<T> get(int index){
		if(index>=size){
			throw new IndexOutOfBoundsException("下标越界");
		}
		
		Node<T> curNode = null;
		if(index<size/2){
			int i = 0;
			curNode = head;
			while(i<index){
				curNode = curNode.next;
				i++;
			}
		}else{
			int i = size-1;
			curNode = tail;
			while(i>index){
				curNode = curNode.pre;
				i--;
			}
		}
		return curNode;
	}
	
	public Integer size(){
		return size;
	}
	
	private static class Node<T>{
		
		private T data;
		
		private Node<T> pre;
		
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

		public Node(Node<T> pre) {
			super();
			this.pre = pre;
		}

		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}
		
	}
}
