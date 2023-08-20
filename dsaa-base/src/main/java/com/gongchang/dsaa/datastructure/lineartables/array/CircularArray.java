package com.gongchang.dsaa.datastructure.lineartables.array;

import java.util.Arrays;

/**
 * 环形数组
 *     核心在于%的应用
 * 
 * @author GongChang
 *
 */
public class CircularArray {

	public static void main(String[] args) {
		CircularCache circularCache = new CircularCache(5);
		for(int i=0; i<28; i++){
			Boolean addtoCache = circularCache.addtoCache(i);
			if(!addtoCache){
				System.out.println("添加元素"+i+"时候缓冲区已满，淘汰最早的元素");
				System.out.println(circularCache.getFromCache());
				circularCache.addtoCache(i);
			}
		}
		
		System.out.println("环形缓冲区元素：");
		System.out.println(Arrays.toString(circularCache.getAllCache()));
	}
	
	private static class CircularCache{
		
		private Integer size;
		
		private int[] arr; 
		
		private int headIndex;
		
		private int tailIndex;

		
		public CircularCache(Integer size) {
			super();
			this.size = size;
			this.arr = new int[size];
			this.headIndex = 0;
			this.tailIndex = 0;
		}
		
		public Boolean isFull(){
			return (tailIndex+1)%size==headIndex;
		}
		
		public Boolean isEmpty(){
			return tailIndex == headIndex;
		}
		
		public int size(){
			return (tailIndex+size-headIndex)%size;
		}
		
		public synchronized Boolean addtoCache(int element){
			if(isFull()){
				return false;
			}else{
				arr[tailIndex%size] = element;
				tailIndex = (tailIndex+1)%size;
				return true;
			}
		}
		
		public synchronized Integer getFromCache(){
			if(!isEmpty()){
				int resultIndex = headIndex;
				headIndex = (headIndex+1)%size;
				return arr[resultIndex];
			}else{
				return null;
			}
		}
		
		public synchronized int[] getAllCache(){
			int[] result = new int[size()];
			Integer tmp = getFromCache();
			int index = 0;
			while(tmp!=null){
				result[index++] = tmp;
				tmp = getFromCache();
			}
			return result;
		}
		
	}
	
}
