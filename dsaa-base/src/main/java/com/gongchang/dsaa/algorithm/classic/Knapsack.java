package com.gongchang.dsaa.algorithm.classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 动态规划算法应用实践
 * 
 * 动态规划算法：将大问题转换为小问题进行解决，从而一步步获取最优解，可以使用填表的方式一步步推进
 * 与分治算法的相同之处：都是将一个大问题分解成多个小问题，先解出小问题，再从小问题的解中的得到原问题的解
 * 与分治算法的不同之处：动态规划分解出的子问题不是相互独立的，下一子阶段问题的求解是建立在上一个子阶段的基础上来求解的
 * 
 * 背包问题可以使用动态规划来解决
 * 
 * 背包问题介绍：有一个给定容量的背包和若干具有一定价值和重量的物品，如何选择物品放入背包使得物品的价值最大
 * 背包又分为以下两种：
 * 	完全背包：每种物品可以无限次放入
 * 	01背包：每个物品只能放一次，完全背包可以转换为01背包
 * 
 * @Author	gongchang
 */
public class Knapsack {

	public static void main(String[] args) {
		List<Goods> goodsList = new ArrayList<Goods>(){
			private static final long serialVersionUID = 1L;

			{
				add(new Goods("耳机", 1, 2));
				add(new Goods("天幕",4, 5));
				add(new Goods("电灯", 3, 4));
			}
		};
		int backpackCapacity = 4;
		
		Knapsack knapsack = new Knapsack(goodsList, backpackCapacity);
		knapsack.selectMaxValueGoods();
	}
	
	
	private int backpackCapacity;
	
	private List<Goods> goodsList;
	
	
	public Knapsack(List<Goods> goodsList, int backpackCapacity) {
		super();
		this.backpackCapacity = backpackCapacity;
		this.goodsList = goodsList;
	}
	
	
	public void selectMaxValueGoods(){
		// 初始化
		int[][] maxValue = new int[goodsList.size()+1][backpackCapacity+1];
		int[][] maxValuePath = new int[goodsList.size()+1][backpackCapacity+1];
		for(int i=0; i<maxValue.length; i++){
			maxValue[i][0] = 0;
		}
		for(int i=0; i<maxValue[0].length; i++){
			maxValue[0][i] = 0;
		}
		
		// 填表（动态规划核心部分）
		for(int i=1; i<maxValue.length; i++){
			Goods goods = goodsList.get(i-1);
			for(int j=1; j<maxValue[i].length; j++){
				// 如果商品的重量大于背包的承受重量，则复用上个商品在此容量下的填表结果（因为放不下，所以可以认为当前商品没有）
				if(goods.getWeight()>j){
					maxValue[i][j] = maxValue[i-1][j];
				}
				// 如果商品重量小于背包的承受重量，则试着放入当前商品
				else{
					int newMaxValue = goods.getValue()+maxValue[i-1][j-goods.getWeight()];
					if(maxValue[i-1][j] < newMaxValue){
						maxValue[i][j] = newMaxValue;
						maxValuePath[i][j] = 1;
					}else{
						maxValue[i][j] = maxValue[i-1][j];
					}
				}
			}
		}
		
		// 打印填表结果
		System.out.println("最大价值表如下：");
		for(int i=0; i<maxValue.length; i++){
			System.out.println(Arrays.toString(maxValue[i]));
		}
		System.out.println("最大价值商品路径如下：");
		for(int i=0; i<maxValuePath.length; i++){
			System.out.println(Arrays.toString(maxValuePath[i]));
		}
		
		// 输出挑选结果
		ArrayList<Integer> selectResultList = new ArrayList<Integer>();
		int i = maxValuePath.length-1;
		int j = maxValuePath[0].length-1;
		while(i>0&&j>0){
			if(maxValuePath[i][j]==1){
				// 如果maxValuePath[i][j]==1，说明该商品在此容量的最大价值中被选中
				selectResultList.add(i-1);
				// 计算背包的剩余容量
				j = j-goodsList.get(i-1).getWeight();
			}
			// 商品索引递减，判断上一个商品是否被选中
			i--;
		}
		// 强迫症，就想顺序挑，没别的作用
		Collections.reverse(selectResultList);
		System.out.println("背包能承受的重量是："+backpackCapacity);
		System.out.println("挑选到的商品列表是：");
		int maxValueRes = 0;
		for (Integer index : selectResultList) {
			maxValueRes+=goodsList.get(index).getValue();
			System.out.println(goodsList.get(index));
		}
		System.out.println("挑选到的价值总和是："+maxValueRes);
	}

	
	private static class Goods{
		private String name;
		
		private int weight;
		
		private int value;

		
		public Goods(String name, int weight, int value) {
			super();
			this.name = name;
			this.weight = weight;
			this.value = value;
		}
		

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}


		@Override
		public String toString() {
			return "Goods [name=" + name + ", weight=" + weight + ", value=" + value + "]";
		}
		
	}
}
