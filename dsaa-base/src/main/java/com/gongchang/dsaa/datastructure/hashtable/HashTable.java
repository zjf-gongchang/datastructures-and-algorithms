package com.gongchang.dsaa.datastructure.hashtable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 哈希表
 *     哈希表是用哈希算法得到一个值，用这个值对哈希表的大小做模运算后得到元素存储的下标，然后将元素存储到这个下标或者与这个下标相关的位置上
 *     
 * 哈希冲突
 *     不同的值做哈希运算之后得到一个哈希值，这个哈希值对表大小做模运算可能会导致得到相同的下标，即哈希冲突
 *     这将会导致相同的位置上有多个元素存储，如何解决这种冲突可以参考下面的解决方式。
 *     
 * 解决哈希冲突的方式：
 *     1.线性探测法：哈希冲突之后，从冲突的位置开始向前探测一个空的位置作为元素的存储位置
 *     2.再哈希法：哈希冲突之后，重新做哈希运算直到找到一个空的位置
 *     3.链地址法：哈希冲突之后，将冲突的元素链接在已经存在的元素的后面，具体实现可以是链表、红黑树等
 *     4.公共溢出区法：哈希冲突之后，将冲突的元素存储在一个公共区域
 * 
 * @author GongChang
 *
 */
public class HashTable<K,V> {
	
	private Object[] elements;
	
	private int size;
	

	public HashTable() {
		super();
		this.size = 10;
		this.elements = new Object[10];
	}
	
	public HashTable(int size) {
		super();
		this.size = size;
		this.elements = new Object[size];
	}
	
	public void put(K key, V value){
		Node<K, V> node = new Node<>(key, value);
		int index = key.hashCode()%size;
		Object object = elements[index];
		if(object==null){
			elements[index] = node;
		}else{
			Node head = (Node)object;
			head.put(node);
		}
	}
	
	public V get(K key){
		int index = key.hashCode()%size;
		Object object = elements[index];
		if(object==null){
			return null;
		}else{
			Node head = (Node)object;
			if(head.key.hashCode()!=key.hashCode()){
    			return (V) head.get(key);
    		}else{
    			if(!head.key.equals(key)){
    				return (V) head.get(key);
    			}else{
    				return (V) head.value;
    			}
    		}
		}
	}
	
	public static class Node<K,V>{
		
		private K key;
		private V value;
		private Node next;
		
		
		public Node(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

	    private void put(Node node){
	    	if(next!=null){
	    		next.put(node);
	    	}else{
	    		next = node;
	    	}
	    }
	    
	    private V get(K key){
	    	if(next==null){
	    		return null;
	    	}else{
	    		if(this.key.hashCode()!=key.hashCode()){
	    			return (V) next.get(key);
	    		}else{
	    			if(!this.key.equals(key)){
	    				return (V) next.get(key);
	    			}else{
	    				return this.value;
	    			}
	    		}
	    	}
	    }
		
		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}
		
	}
	
	

	public static void main(String[] args) {
		HashTable<String, List<String>> hashTable = new HashTable<>();
		
		hashTable.put("hhh", new ArrayList<String>(){
			{
				add("hhh666");
				add("hhh777");
			}
		});
		hashTable.put("jjj", new ArrayList<String>(){
			{
				add("jjj666");
				add("jjj777");
			}
		});
		hashTable.put("kkk", new ArrayList<String>(){
			{
				add("kkk666");
				add("kkk777");
			}
		});
		
		System.out.println(hashTable.get("hhh"));
		System.out.println(hashTable.get("jjj"));
		System.out.println(hashTable.get("kkk"));
	}
	
}
