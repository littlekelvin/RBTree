package cn.kelvin.oocl.avl;

import java.util.ArrayDeque;
import java.util.Queue;

public class AvlTree {
	AVLNode root;
	
	public AvlTree(AVLNode root) {
		this.root = root;
	}
	
	public AvlTree() {
	}
	
	public void listOrder(AVLNode root){
		if(root==null){
			return;
		}
		Queue<AVLNode> queue = new ArrayDeque<>();
		queue.offer(root);
		AVLNode curr = null;
		while(!queue.isEmpty()){
			curr = queue.poll();
			System.out.print(curr.data+" ");
			if(curr.left != null){
				queue.offer(curr.left);
			}
			if(curr.right != null){
				queue.offer(curr.right);
			}
		}
	}
	
	public void insert(AVLNode root, int data){
		if(data < root.data){
			if(root.left != null){
				insert(root.left, data);
			}else {
				root.left = new AVLNode(data);
				root.left.parent = root;
			}
		}else {
			if(root.right != null){
				insert(root.right, data);
			}else {
				root.right = new AVLNode(data);
				root.right.parent = root;
			}
		}
		
		// ����root�ڵ��ƽ������
		root.balance = calBalance(root);
		
		//�������ߣ�Ӧ������
		if(root.balance >= 2){
			if(root.left.balance < 0){ // ����ߣ�������
				leftRotate(root.left);
			}
			rightRotate(root);
		}else if(root.balance <= -2){	//�������ߣ�Ӧ������
			if(root.right.balance > 0){	// ����ߣ�������
				rightRotate(root.right);
			}
			leftRotate(root);
		}
		
		root.balance = calBalance(root);
		root.depth = calDepth(root);
	}

	//��������
	public void rightRotate(AVLNode p) {
		AVLNode parent = p.parent;	//���ڵ�
		AVLNode pLeftSon = p.left;	// ��ڵ�
		AVLNode pleftGrandRightSon = pLeftSon.right; // �������ڵ���ҽڵ�
		
		pLeftSon.parent = parent;
		if(parent != null){ //�ж���ת�Ľڵ��Ƿ��и��ڵ�
			if(p == parent.left){
				parent.left = pLeftSon;
			}else if(p == parent.right){
				parent.right = pLeftSon;
			}
		}
		
		pLeftSon.right = p;
		p.parent = pLeftSon;
		
		p.left = pleftGrandRightSon;
		if(pleftGrandRightSon != null){
			pleftGrandRightSon.parent = p;
		}
		
		//���¼���ƽ������
		p.balance = calBalance(p);
		p.depth = calDepth(p);
		pLeftSon.balance = calBalance(pLeftSon);
		pLeftSon.depth = calDepth(pLeftSon);
	}

	//��������
	public void leftRotate(AVLNode p) {
		AVLNode parent = p.parent;	// ���ڵ�
		AVLNode pRightSon = p.right; //�Һ���
		AVLNode pRrigtGrandLeftSon = pRightSon.left;//�Һ��ӵ�����
		
		pRightSon.parent = parent;
		if(parent != null){
			if(p == parent.left){
				parent.left = pRightSon;
			}else if(p == parent.right){
				parent.right = pRightSon;
			}
		}
		
		pRightSon.left = p;
		p.parent = pRightSon;
		
		p.right = pRrigtGrandLeftSon;
		if(pRrigtGrandLeftSon != null){
			pRrigtGrandLeftSon.parent = p;
		}
		
		//���¼���ƽ������
		p.balance = calBalance(p);
		p.depth = calDepth(p);
		pRightSon.balance = calBalance(pRightSon);
		pRightSon.depth = calDepth(pRightSon);
	}

	public int calBalance(AVLNode p) {
		int l_depth = 0;
		int r_depth = 0;
		if(p.left!=null){
			l_depth = p.left.depth;
		}
		if(p.right!=null){
			r_depth = p.right.depth;
		}
		return l_depth - r_depth;
	}
	
	public int calDepth(AVLNode p) {
		int depth = 0;
		if(p.left != null){
			depth = p.left.depth;
		}
		if(p.right!=null && depth<p.right.depth){
			depth = p.right.depth;
		}
		depth ++;
		return depth;
	}
}
