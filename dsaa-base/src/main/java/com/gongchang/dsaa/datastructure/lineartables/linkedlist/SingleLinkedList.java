package com.gongchang.dsaa.datastructure.lineartables.linkedlist;

/**
 * 单链表
 * 
 * @author GongChang
 *
 */
public class SingleLinkedList<T> {

	public static void main(String[] args) {
		SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<>();
		System.out.println("列表中添加0-9的元素");
		for(int i=0; i<10; i++){
			singleLinkedList.add(i);
		}
		
		System.out.println("查询下标为2的元素："+ singleLinkedList.get(2).getData());
		
		System.out.println("删除下标为2的元素："+ singleLinkedList.remove(2).getData());
		
		System.out.println("遍历列表：");
		for(int i=0; i<singleLinkedList.size; i++){
			System.out.print(singleLinkedList.get(i).getData());
			if(i!=singleLinkedList.size-1){
				System.out.print(",");
			}
		}
	}
	
	
	
	private Node<T> head;
	
	private Integer size = 0;
	
	
	public SingleLinkedList() {
		super();
	}
	
	
	public void add(T t){
		Node<T> node = new Node<T>(t);
		if(head==null){
			head = node;
		}else{
			Node<T> tail = head;
			while(tail.next!=null){
				tail = tail.next;
			}
			tail.next = node;
		}
		size++;
	}
	
	public Node<T> remove(int index){
		if(index>=size){
			throw new IndexOutOfBoundsException("下标越界");
		}
		int i = 0;
		Node<T> curNode = head;
		while(i<index-1){
			curNode = curNode.next;
			i++;
		}
		Node<T> result = curNode.next;
		curNode.next = curNode.next.next;
		size--;
		return result;
	}
	
	public Node<T> get(int index){
		if(index>=size){
			throw new IndexOutOfBoundsException("下标越界");
		}
		int i = 0;
		Node<T> curNode = head;
		while(i<index){
			curNode = curNode.next;
			i++;
		}
		return curNode;
	}
	
	public Integer size(){
		return size;
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
