package com.gongchang.dsaa.algorithm.compaction;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 哈夫曼编码：
 *   是一种使用哈夫曼树组织的变长的编码方式，企图用更少的字节数据表示一个字符，是一种无损的压缩方式。
 *   是一种前缀编码，前缀编码意味着任意一个字符的编码都不能是其它任何字符编码的前缀。
 *   
 * 编码方式：
 *   1.定长编码：像ISO-8859-1，Ascii，GBK，GB2312，Unicode都是定长编码，是用一个固定的代码表示唯一的一个字符，
 *             它的值是固定的，所占的空间也是固定的。
 *   2.变长编码：像哈夫曼编码，可以使用一个字符在字符串中出现的次数构建编码或者一个字节在文件中出现的次数，次数越多编码长度越小，
 *             这样可以有效的压缩空间。
 * 
 * 一般通信领域会使用变长编码对数据进行压缩。
 * 
 * @author GongChang
 *
 */
public class HuffmanCode {

	public static void main(String[] args) {
		
//		System.err.println(Byte.parseByte("1111110", 2));
//		System.out.println(Integer.parseInt("11111110", 2));
//		System.out.println((byte)Integer.parseInt("11111110", 2));
//		System.out.println(Integer.toBinaryString(254));
//		System.out.println(Integer.toBinaryString(2));
		
		zip("test.txt", "test.zip");
		
		unzip("test.zip", "test1.txt");
	}
	
	public static Boolean zip(String sourceFileName, String targetFileName){
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir")+"/"+targetFileName))) {
			// 读取源文件
			Path sourcepPath = Paths.get(System.getProperty("user.dir"), sourceFileName);
			byte[] sourceByteArr = Files.readAllBytes(sourcepPath);
			
			// 获取节点列表
			List<Node> nodeList = getNodeList(sourceByteArr);
			
			// 创建哈夫曼树，返回根节点
			Node rootNode = createHFMTree(nodeList);
			rootNode.preOrderTraversal();
			
			// 获取哈夫曼编码表格
			Map<Byte, String> codeTable = getCodeTable(rootNode);
			
			// 编码
			byte[] targetByteArr = encode(sourceByteArr, codeTable);
			
			// 写入目标文件
			oos.writeObject(targetByteArr);
			oos.writeObject(codeTable);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static Boolean unzip(String sourceFileName, String targetFileName){
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(System.getProperty("user.dir")+"/"+sourceFileName))){
			// 读压缩文件文件
			byte[] sourceByteArr = (byte[]) ois.readObject();
			@SuppressWarnings("unchecked")
			Map<Byte, String> codeTable = (Map<Byte, String>) ois.readObject();
			
			// 解码
			byte[] targetByteArr = decode(sourceByteArr, codeTable);
			
			// 写入目标文件
			Path targetPath = Paths.get(System.getProperty("user.dir"), targetFileName);
			if(Files.exists(targetPath)){
				Files.delete(targetPath);
			}
			Files.write(targetPath, targetByteArr, StandardOpenOption.CREATE);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static List<Node> getNodeList(byte[] sourceByteArr){
		Map<Byte, Integer> countMap = new HashMap<Byte, Integer>();
		for (byte b : sourceByteArr) {
			Integer bCount = countMap.get(b);
			if(bCount==null){
				countMap.put(b, 1);
			}else{
				countMap.put(b, bCount+1);
			}
		}
		
		List<Node> nodeList = countMap.entrySet().stream()
				.map(new Function<Entry<Byte, Integer>, Node>() {
					@Override
					public Node apply(Entry<Byte, Integer> entry) {
						return new Node(entry.getKey(), entry.getValue());
					}
					
				}).collect(Collectors.toList());
		
		return nodeList;
	}
	
	private static Node createHFMTree(List<Node> nodeList){
		while(nodeList.size()>1) {
			Collections.sort(nodeList);
			
			Node leftNode = nodeList.get(0);
			Node rightNode = nodeList.get(1);
			Node parentNode = new Node(null, leftNode.getWeight()+rightNode.getWeight());
			parentNode.setLeftNode(leftNode);
			parentNode.setRightNode(rightNode);
			
			nodeList.remove(0);
			nodeList.remove(0);
			nodeList.add(parentNode);
		}
		
		return nodeList.get(0);
	}
	
	private static Map<Byte, String> getCodeTable(Node rootNode){
		if(rootNode==null){
			return Collections.emptyMap();
		}else{
			Map<Byte, String> codeTable = new HashMap<Byte, String>();
			
			StringBuilder sb = new StringBuilder();
			getCodeTable(rootNode.getLeftNode(), '0', sb, codeTable);
			getCodeTable(rootNode.getRightNode(), '1', sb, codeTable);
			
			return codeTable;
		}
	}
	
	private static void getCodeTable(Node node, char pathValue, StringBuilder sb, Map<Byte, String> codeTable){
		if(node==null){
			return;
		}
		
		StringBuilder newsb = new StringBuilder(sb).append(pathValue);
		if(node.getData()==null){
			getCodeTable(node.getLeftNode(), '0', newsb, codeTable);
			getCodeTable(node.getRightNode(), '1', newsb, codeTable);
		}else{
			codeTable.put(node.getData(), newsb.toString());
		}
	}
	
	private static byte[] encode(byte[] sourceByteArr, Map<Byte, String> codeTable){
		StringBuilder sb = new StringBuilder();
		for (byte b : sourceByteArr) {
			sb.append(codeTable.get(b));
		}
		
		int byteLength = sb.length();
		int targetByteLength = byteLength%8==0 ? byteLength/8 : byteLength/8+1;
		byte[] target = new byte[targetByteLength];
		
		int index = 0;
		for(int i=0; i<byteLength; i+=8,index++){
			String oneByteStr;
			if(i+8>byteLength){
				oneByteStr = sb.substring(i);
			}else{
				oneByteStr = sb.substring(i,i+8);
			}
			target[index] = (byte)Integer.parseInt(oneByteStr, 2);
		}
		
		return target;
	}
	
	private static byte[] decode(byte[] sourceByteArr, Map<Byte, String> codeTable){
		// 获取被编码的二进制字符串
		StringBuilder encodedBitStr = getEncodedBitStr(sourceByteArr);
		// 调换码表的key和value
		Map<String, Byte> turnAboutCodeTabale = turnAboutCodeTabale(codeTable);
		// 将被编码的二进制字符串根据对调后的码表解析成编码前的字节数据
		byte[] resultByteArr = getOriginalByteArr(encodedBitStr, turnAboutCodeTabale);
		return resultByteArr;
	}
	
	private static StringBuilder getEncodedBitStr(byte[] encodedByteArr){
		StringBuilder sb = new StringBuilder();
		
		int ebaLength = encodedByteArr.length;
		for (int i=0; i<ebaLength; i++) {
			// i<ebaLength-1 || ebaLength==1 表示不是最后一个字节或者只有一个字节的情况才会为true，这时才会补全二进制位数
			String btbs = byteToBitString(encodedByteArr[i], i<ebaLength-1 || ebaLength==1);
			sb.append(btbs);
		}
		
		return sb;
	}
	
	private static String byteToBitString(byte b, Boolean isAlign){
		int tmp = b;
		
		String resultStr;
		if(isAlign){
			// 256的二进制字符串是：1 00000000
			// 这里做或运算是为了将正数的位数补足到8位置
			tmp |= 256;
			resultStr = Integer.toBinaryString(tmp);
			resultStr = resultStr.substring(resultStr.length()-8);
		}else{
			resultStr = Integer.toBinaryString(tmp);
			
		}
		
		return resultStr;
	}
	
	private static Map<String, Byte> turnAboutCodeTabale(Map<Byte, String> codeTable){
		Map<String, Byte> resultMap = codeTable.entrySet().stream().collect(Collectors
				.toMap(new Function<Entry<Byte, String>, String>() {
					@Override
					public String apply(Entry<Byte, String> entry) {
						return entry.getValue();
					}
				}, new Function<Entry<Byte, String>, Byte>() {
					@Override
					public Byte apply(Entry<Byte, String> entry) {
						return entry.getKey();
					}
				}));
		return resultMap;
	}
	
	private static byte[] getOriginalByteArr(StringBuilder encodedBitStr, Map<String, Byte> turnAboutCodeTabale) {
		List<Byte> resultByteList = new ArrayList<Byte>();
		int i=0;
		while(i<encodedBitStr.length()){
			int skipLength = 1;
			Byte codeByte = null;
			
			while(true){
				String code = encodedBitStr.substring(i, i+skipLength);
				codeByte = turnAboutCodeTabale.get(code);
				if(codeByte==null){
					skipLength++;
				}else{
					break;
				}
			}
			
			resultByteList.add(codeByte);
			i+=skipLength;
		}
		
		byte[] resulrByteArr = new byte[resultByteList.size()];
		for (int j=0; j<resultByteList.size(); j++) {
			resulrByteArr[j] = resultByteList.get(j);
		}
		
		return resulrByteArr;
	}
	
	private static class Node implements Comparable<Node>{

		private Byte data;
		
		private Node leftNode;
		
		private Node rightNode;
		
		private int weight;
		
		
		public Node(Byte data, int weight) {
			super();
			this.data = data;
			this.weight = weight;
		}
		

		private void preOrderTraversal() {
			this.printDataInfo();
			
			if(leftNode!=null) {
				leftNode.preOrderTraversal();
			}
			
			if(rightNode!=null) {
				rightNode.preOrderTraversal();
			}
		}
		
		@Override
		public int compareTo(Node node) {
			return this.weight-node.weight;
		}
		
		private void printDataInfo(){
			if(this.data!=null){
				System.out.println("节点数据："+this.getData());
			}
		}
		
		
		public Byte getData() {
			return data;
		}

		public void setData(Byte data) {
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

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}
		
	}
	
}
