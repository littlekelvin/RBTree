package cn.kelvin.oocl.avl;

public class AVLNode {
	public int data;
	public int depth;	// 树的深度
	public int balance;	// 平衡因子
	public AVLNode parent;	// 父节点
	public AVLNode left;
	public AVLNode right;
	
	public AVLNode(int data) {
		this.data = data;
		this.depth = 1;
		this.balance = 0;
	}
}
