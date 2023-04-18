package com.gongchang.dsaa.algorithm.classic;

/**
 * 分治算法和递归算法应用实践
 * 
 * 汉诺塔问题介绍：有A,B,C三根柱子，并且有一些圆盘按照从大到小的顺序落在A盘上，现在要求把A盘上的圆盘按照从大到小的顺序挪到C盘上
 * 	要求三根柱子之间一次只能移动一个圆盘，并且小圆盘上不能放大圆盘。
 * 
 * 汉诺塔问题的处理使用到了递归和分治
 * 
 * @Author	gongchang
 * 
 */
public class HannotTower {

	public static void main(String[] args) {
		// 定义A柱上圆盘的数量
		int num = 3;
		
		HannotTower hannotTower = new HannotTower();
		hannotTower.hannotTower(num, 'A', 'B', 'C');
	}
	
	/**
	 * 
	 * @param num A柱上盘子的编号，从上往下盘子的编号依次为1到num
	 * @param A A柱
	 * @param B B柱
	 * @param C C柱
	 */
	private void hannotTower(int num, char A, char B, char C){
		// ============================================
		// 当圆盘只有一个的时候，直接将圆盘从A移动到C
		// ============================================
		if(num==1){
			moveXToY(num, A, C);
			return;
		}
		
		// ============================================
		// 当圆盘数量大于等于2的时候，按照两个圆盘处理，最下面的圆盘作为一个整体的圆盘，上面的所有圆盘作为一个整体的圆盘
		// ============================================
		// A柱上面的所有圆盘从A移动到B
		hannotTower(num-1, A, C, B);
		// A柱最下面的圆盘从A移动到C
		moveXToY(num, A, C);
		// B柱上所有的圆盘移动到C
		hannotTower(num-1, B, A, C);
		
	}
	
	private void moveXToY(int num, char X, char Y){
		System.out.println("将第"+num+"个盘子从"+X+"移动到"+Y);
	}
}
