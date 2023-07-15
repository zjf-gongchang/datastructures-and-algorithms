package com.gongchang.dsaa.algorithm.find;

/**
 * 布隆过滤器
 * 
 * 原理：首先定义一个大小为N的位数组，使其所有位置上的初始值为0；当有一个key要存储的时候，使用多个hash函数，每个hash函数对key进行hash计算，
 * hash计算结果针对数组大小N做模运算，模运算的结果代表位数组中的一个位置，将这个位置的值设置为1；当有一个key需要判断是否存在的时候，也是使用同
 * 样的方式做多次hash计算，然后每次的运算结果做模运算，并判断模运算结果对应的位置是否为1，如果都为1则可能存在（因为可能存在hash碰撞导致误判），
 * 如果都为0则肯定不存在
 *
 * 位数组大小N的计算公式：N=K*|A|/ln2
 * K表示Hash的次数，|A|表示一个集合中要存储的元素
 * 根据这个公式计算出来的位数组大小能够保证最佳的误判率
 *
 * 注：本算法摘录自《HBase原理与实践》
 */
public class BloomFilter {

	private int k;
	private int bitsPerKey;
	private int bitLen;
	private byte[] result;
	
	/**
	 * 
	 * @param k 每个key哈希的次数
	 * @param bitsPerKey 每个key占用的二进制位数
	 */
	public BloomFilter(int k, int bitsPerKey){
		this.k =k;
		this.bitsPerKey = bitsPerKey;
	}
	
	public byte[] generate(byte[][] keys){
		assert keys!=null;
		bitLen = keys.length * bitsPerKey;
		bitLen = ((bitLen + 7) / 8) << 3; // align the bitLen.
		bitLen = bitLen < 64 ? 64 : bitLen;
		result = new byte[bitLen >> 3]; // each byte have 8 bit.
		for( int i=0; i < keys.length; i++){
			assert keys[i] != null;
			int h  = Bytes.hash(keys[i]);
			for(int t = 0; t < k; t++){
				int idx = (h % bitLen+ bitLen) % bitLen;
				result[idx / 8] |= (1 << (idx % 8));
				// 第一次哈希函数之后借助位运算实现后面的多次哈希
				int delta = (h >> 17) | (h << 15);
				h += delta;
			}
		}
		return result;
	}
	
	public boolean contains(byte[] key){
		assert result != null;
		int h = Bytes.hash(key);
		for(int t = 0; t < k; t++){// Hash k times
			int idx = (h % bitLen + bitLen) % bitLen;
			if((result[idx / 8] & (1 << (idx % 8))) == 0){
				return false;
			}
			// 第一次哈希函数之后借助位运算实现后面的多次哈希
			int delta = (h >> 17) | (h << 15);
			h += delta;
		}
		return true;
	}
	
	private static class Bytes{
		public static int hash(byte[] key){
			// 这里是hash函数的实现
			return 0;
		}
	}
	
}
