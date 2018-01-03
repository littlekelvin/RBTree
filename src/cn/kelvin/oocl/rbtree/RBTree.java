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
	
    //添加节点
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
		
		//由于新插入节点会影响红黑树的结构，需要修正使其满足红黑树的性质
		insertFixUp(node);
	}
	
	//插入节点后修正红黑树
	private void insertFixUp(RBTNode<T> node) {
		RBTNode<T> parent, gparent;
		parent = parentOf(node);
		//若父节点存在，且父节点的颜色是红色
		while((parent = parentOf(node))!=null && isRed(parent)){
			gparent = parentOf(parent);
			if(gparent==null){
				break;
			}
			
			//若父节点是祖父节点的左孩子
			if(parent == gparent.left){
				//叔叔节点
				RBTNode<T> uncle = gparent.right;
				
				// case 1: 叔叔节点是红色
				if(uncle!=null && isRed(uncle)){
					setBlack(uncle);
					setBlack(parent);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					node = gparent;
					continue;
				}
				//case 2: 叔叔是黑色，当前节点是右孩子或者叔叔是null
				else if(parent.right==node){
					leftRotate(parent);
					setBlack(node);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					rightRotate(gparent);
				}
				//case 3: 叔叔是黑色，当前节点是左孩子或者叔叔是null
				else{
					setBlack(parent);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					rightRotate(gparent);
				}
			}else { //父节点是祖父节点的右孩子
				RBTNode<T> uncle = gparent.left;
				// case 4: 叔叔节点是红色
				if(uncle!=null && isRed(uncle)){
					setBlack(uncle);
					setBlack(parent);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					node = gparent;
					continue;
				}
				
				//case 5:叔叔节点是黑色，且当前节点是左孩子或者叔叔是null
				else if(parent.left == node){
					rightRotate(parent);
					setBlack(node);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					leftRotate(gparent);
				}
				else {//case 6:叔叔节点是黑色，且当前节点是右孩子或者叔叔是null
					setBlack(parent);
					if(gparent != this.mRoot){
						setRed(gparent);
					}
					leftRotate(gparent);
				}
			}
			
		}
	}

	//删除节点
	public void remove(T key){
		RBTNode<T> node = search(this.mRoot, key);
		if(null!=node){
			remove(node);
		}
	}
	
	private void remove(RBTNode<T> node) {
		RBTNode<T> child=null,parent=null;
		boolean color=false;
		
		//删除的节点有两个孩子,转化为删除该节点的后继节点
		if((node.left!=null) && (node.right!=null)){
			//记录后继节点
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
				
			//将后继节点数据拷贝到待删除节点
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

	//删除节点后，修正红黑树
	private void removeFixUp(RBTNode<T> node, RBTNode<T> parent) {
		//node的兄弟节点
		RBTNode<T> silNode = null;
		
		//如果node是黑色的话，那么必为NIL节点
		while((node == null || isBlack(node)) && (node != this.mRoot)){
			if (parent.left == node) {
				silNode = parent.right;
				if(isRed(silNode)){		//case 1: 兄弟节点是红色节点，则parent为黑色，silNode的两个儿子都为黑色
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
		
		//如果node不是NIL节点，则必为红色
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
	 * 对红黑树的节点(x)进行左旋转
	 * 左旋示意图(对节点x进行左旋)：
	 *      px                           px
	 *     /                            /
	 *    x                            y                
	 *   / \      --(左旋)-.           / \                #
	 *  lx  y                        x  ry     
	 *     / \                      / \
	 *    ly ry                    lx ly  
	 */
	public void leftRotate(RBTNode<T> p){
		RBTNode<T> parent = p.parent;	// 父节点
		RBTNode<T> pRightSon = p.right; //右孩子
		RBTNode<T> pRrigtGrandLeftSon = pRightSon.left;//右孩子的左孩子
		
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
	 * 对红黑树的节点(y)进行右旋转
	 *
	 * 右旋示意图(对节点y进行右旋)：
	 *            py                             py
	 *           /                              /
	 *          y                              x                  
	 *         /  \      --(右旋)-.            /  \                     #
	 *        x   ry                         lx  y  
	 *       / \                                / \                   #
	 *      lx  rx                             rx  ry
	 * 
	 */
	public void rightRotate(RBTNode<T> p){
		RBTNode<T> parent = p.parent;	//父节点
		RBTNode<T> pLeftSon = p.left;	// 左节点
		RBTNode<T> pleftGrandRightSon = pLeftSon.right; // 左孙，即左节点的右节点
		
		pLeftSon.parent = parent;
		if(parent != null){ //判断旋转的节点是否有父节点
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
	
	//查找节点
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
	 * 层次遍历
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
