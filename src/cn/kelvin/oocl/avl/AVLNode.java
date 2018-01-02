package cn.kelvin.oocl.avl;

public class AVLNode {
	public int data;
	public int depth;	// �������
	public int balance;	// ƽ������
	public AVLNode parent;	// ���ڵ�
	public AVLNode left;
	public AVLNode right;
	
	public AVLNode(int data) {
		this.data = data;
		this.depth = 1;
		this.balance = 0;
	}
}
