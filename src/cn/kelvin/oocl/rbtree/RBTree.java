package cn.kelvin.oocl.rbtree;

import java.util.ArrayDeque;
import java.util.Queue;

public class RBTree<T extends Comparable<T>> {
	RBTNode<T> mRoot;
	private static final boolean RED   = false;
    private static final boolean BLACK = true;
    
    public RBTree() {
	}
    
    public RBTree(T rootKey) {
    	this.mRoot = new RBTNode<T>(BLACK, rootKey);
   	}
	
    //��ӽڵ�
	public void insert(T key){
		if(null==key){
			return;
		}
		this.insert(new RBTNode<T>(BLACK, key));
	}
	
	private void insert(RBTNode<T> node){
		if(this.mRoot==null){
			this.mRoot = node;
			return;
		}
		RBTNode<T> curr = this.mRoot;
		int rs = 0;
		while(true){
			rs = node.key.compareTo(curr.key);
			if(rs < 0){
				if(curr.left != null){
					curr = curr.left;
				}else {
					curr.left = node;
					node.parent = curr;
					break;
				}
			}else {
				if(curr.right != null){
					curr = curr.right;
				}else {
					curr.right = node;
					node.parent = curr;
					break;
				}
			}
		}
		node.color = RED;
		
		//�����²���ڵ��Ӱ�������Ľṹ����Ҫ����ʹ����������������
		insertFixUp(node);
	}
	
	//����ڵ�����������
	private void insertFixUp(RBTNode<T> node) {
		RBTNode<T> parent, gparent;
		parent = parentOf(node);
		//�����ڵ���ڣ��Ҹ��ڵ����ɫ�Ǻ�ɫ
		while((parent = parentOf(node))!=null && isRed(parent)){
			gparent = parentOf(parent);
			if(gparent==null){
				break;
			}
			
			//�����ڵ����游�ڵ������
			if(parent == gparent.left){
				//����ڵ�
				RBTNode<T> uncle = gparent.right;
				
				// case 1: ����ڵ��Ǻ�ɫ
				if(uncle!=null && isRed(uncle)){
					setBlack(uncle);
					setBlack(parent);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					node = gparent;
					continue;
				}
				//case 2: �����Ǻ�ɫ����ǰ�ڵ����Һ��ӻ���������null
				else if(parent.right==node){
					leftRotate(parent);
					setBlack(node);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					rightRotate(gparent);
				}
				//case 3: �����Ǻ�ɫ����ǰ�ڵ������ӻ���������null
				else{
					setBlack(parent);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					rightRotate(gparent);
				}
			}else { //���ڵ����游�ڵ���Һ���
				RBTNode<T> uncle = gparent.left;
				// case 4: ����ڵ��Ǻ�ɫ
				if(uncle!=null && isRed(uncle)){
					setBlack(uncle);
					setBlack(parent);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					node = gparent;
					continue;
				}
				
				//case 5:����ڵ��Ǻ�ɫ���ҵ�ǰ�ڵ������ӻ���������null
				else if(parent.left == node){
					rightRotate(parent);
					setBlack(node);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					leftRotate(gparent);
				}
				else {//case 6:����ڵ��Ǻ�ɫ���ҵ�ǰ�ڵ����Һ��ӻ���������null
					setBlack(parent);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					leftRotate(gparent);
				}
			}
			
		}
	}

	//ɾ���ڵ�
	public void remove(T key){
		RBTNode<T> node = search(this.mRoot, key);
		if(null!=node){
			remove(node);
		}
	}
	
	private void remove(RBTNode<T> node) {
		RBTNode<T> child=null,parent=null;
		boolean color=false;
		
		//ɾ���Ľڵ�����������,ת��Ϊɾ���ýڵ�ĺ�̽ڵ�
		if((node.left!=null) && (node.right!=null)){
			//��¼��̽ڵ�
			RBTNode<T> replace = node.right;
			while(replace.left!=null)
				replace = replace.left;
			
			child = replace.right;
			parent = replace.parent;
			if(parent==node){
				node.right = child;
				if(child!=null){
					child.parent = node;
				}
			}else {
				parent.left = child;
				if(child!=null){
					child.parent = parent;
				}
			}
				
			//����̽ڵ����ݿ�������ɾ���ڵ�
			node.key = replace.key;	
			color = replace.color;
			if(color==BLACK){
				removeFixUp(child,parent);
			}
			replace = null;
			return;
		}
		
		if(node.left!=null){
			child = node.left;
		}else {
			child = node.right;
		}
		parent = node.parent;
		
		if(parent!=null){
			if(parent.left==node){
				parent.left = child;
			}else {
				parent.right = child;
			}
		}else {
			this.mRoot = child;
		}
		
		if(child!=null){
			child.parent = parent;
		}
		if(node.color==BLACK){
			removeFixUp(child,parent);
		}
		node = null;
	}

	//ɾ���ڵ�����������
	private void removeFixUp(RBTNode<T> node, RBTNode<T> parent) {
		//node���ֵܽڵ�
		RBTNode<T> silNode = null;
		
		//���node�Ǻ�ɫ�Ļ�����ô��ΪNIL�ڵ�
		while((node == null || isBlack(node)) && (node != this.mRoot)){
			if (parent.left == node) {
				silNode = parent.right;
				if(isRed(silNode)){		//case 1: �ֵܽڵ��Ǻ�ɫ�ڵ㣬��parentΪ��ɫ��silNode���������Ӷ�Ϊ��ɫ
					setBlack(silNode);
					setRed(parent );
					leftRotate(parent);
					silNode = parent.right;
				}
				
				if((node.left==null || isBlack(node.left) && (node.right==null || isBlack(node.right)))){
					setRed(silNode);
					node = parent;
					parent = parentOf(node);
					continue;
				}else {
					
				}
			}else {
				
			}
		}
		
		//���node����NIL�ڵ㣬���Ϊ��ɫ
		if(node != null)
			setBlack(node);
	}

	private void setRed(RBTNode<T> node) {
		if(node != null)
			node.color = RED;
	}

	private void setBlack(RBTNode<T> node) {
		if(node != null)
			node.color = BLACK;
	}

	private boolean isRed(RBTNode<T> node) {
		return ((node!=null) && (node.color==RED)) ? true : false;
	}
	
	private boolean isBlack(RBTNode<T> node) {
		return ((node!=null) && (node.color==BLACK)) ? true : false;
	}

	private RBTNode<T> parentOf(RBTNode<T> node) {
		return (node!=null) ? node.parent : null;
	}

	/* 
	 * �Ժ�����Ľڵ�(x)��������ת
	 * ����ʾ��ͼ(�Խڵ�x��������)��
	 *      px                           px
	 *     /                            /
	 *    x                            y                
	 *   / \      --(����)-.           / \                #
	 *  lx  y                        x  ry     
	 *     / \                      / \
	 *    ly ry                    lx ly  
	 */
	public void leftRotate(RBTNode<T> p){
		RBTNode<T> parent = p.parent;	// ���ڵ�
		RBTNode<T> pRightSon = p.right; //�Һ���
		RBTNode<T> pRrigtGrandLeftSon = pRightSon.left;//�Һ��ӵ�����
		
		pRightSon.parent = parent;
		if(parent != null){
			if(p == parent.left){
				parent.left = pRightSon;
			}else if(p == parent.right){
				parent.right = pRightSon;
			}
		}else {
			this.mRoot = pRightSon;
		}
		
		pRightSon.left = p;
		p.parent = pRightSon;
		
		p.right = pRrigtGrandLeftSon;
		if(pRrigtGrandLeftSon != null){
			pRrigtGrandLeftSon.parent = p;
		}
	}
	
	/* 
	 * �Ժ�����Ľڵ�(y)��������ת
	 *
	 * ����ʾ��ͼ(�Խڵ�y��������)��
	 *            py                             py
	 *           /                              /
	 *          y                              x                  
	 *         /  \      --(����)-.            /  \                     #
	 *        x   ry                         lx  y  
	 *       / \                                / \                   #
	 *      lx  rx                             rx  ry
	 * 
	 */
	public void rightRotate(RBTNode<T> p){
		RBTNode<T> parent = p.parent;	//���ڵ�
		RBTNode<T> pLeftSon = p.left;	// ��ڵ�
		RBTNode<T> pleftGrandRightSon = pLeftSon.right; // �������ڵ���ҽڵ�
		
		pLeftSon.parent = parent;
		if(parent != null){ //�ж���ת�Ľڵ��Ƿ��и��ڵ�
			if(p == parent.left){
				parent.left = pLeftSon;
			}else if(p == parent.right){
				parent.right = pLeftSon;
			}
		}else {
			this.mRoot = pLeftSon;
		}
		
		pLeftSon.right = p;
		p.parent = pLeftSon;
		
		p.left = pleftGrandRightSon;
		if(pleftGrandRightSon != null){
			pleftGrandRightSon.parent = p;
		}
	}
	
	//���ҽڵ�
	public RBTNode<T> search(RBTNode<T> root,T key){
		if(null==root || null==key){
			return null;
		}
		int cmp = key.compareTo(root.key);
		if(cmp<0)
			return search(root.left, key);
		else if(cmp>0)
			return search(root.right, key);
		else 
			return root;
	}
	
	public void inOrder(RBTNode<T> root){
		if(root!=null){
			inOrder(root.left);
			System.out.print(root.key.toString()+ " ");
			inOrder(root.right);
		}
	}
	
	public void preOrder(RBTNode<T> root){
		if(root!=null){
			System.out.print(root.key.toString()+ " ");
			preOrder(root.left);
			preOrder(root.right);
		}
	}
	
	public void postOrder(RBTNode<T> root){
		if(root!=null){
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.key.toString()+ " ");
		}
	}
	
	/**
	 * ��α���
	 * @param root
	 */
	public void listOrder(RBTNode<T> root){
		if(root==null){
			return;
		}
		Queue<RBTNode<T>> queue = new ArrayDeque<>();
		queue.offer(root);
		RBTNode<T> curr = null;
		while(!queue.isEmpty()){
			curr = queue.poll();
			System.out.print(curr.key.toString()+" ");
			if(curr.left != null){
				queue.offer(curr.left);
			}
			if(curr.right != null){
				queue.offer(curr.right);
			}
		}
	}
	public void listOrderWithColor(RBTNode<T> root){
		if(root==null){
			return;
		}
		Queue<RBTNode<T>> queue = new ArrayDeque<>();
		queue.offer(root);
		RBTNode<T> curr = null;
		while(!queue.isEmpty()){
			curr = queue.poll();
			System.out.print(curr.key.toString()+"->"+(curr.color ? "B":"R")+" ");
			if(curr.left != null){
				queue.offer(curr.left);
			}
			if(curr.right != null){
				queue.offer(curr.right);
			}
		}
	}
	
	public class RBTNode<T extends Comparable<T>> {
		boolean color;
		T key;
		RBTNode<T> left;
		RBTNode<T> right;
		RBTNode<T> parent;
		
		public RBTNode(boolean color, T key) {
			this.color = color;
			this.key = key;
		}

		public RBTNode(boolean color, T key, RBTNode<T> left, RBTNode<T> right,
				RBTNode<T> parent) {
			super();
			this.color = color;
			this.key = key;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
		
	}
}
