package cn.kelvin.oocl.bst;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Queue;

public class BinarySearchTree<T> {
	public Node<T> root;
	
	public BinarySearchTree() {
		
	}
	
	public BinarySearchTree(T[] datas,Comparator<T> comparator) throws Exception {
		buildBinaryTree(datas,comparator);
	}
	
	private void buildBinaryTree(T[] datas,Comparator<T> comparator) throws Exception {
		for(T t:datas){
			insert(t, comparator);
		}
	}
	
	public void inOrder(Node<T> root){
		if(root!=null){
			inOrder(root.left);
			System.out.print(root.data.toString()+ " ");
			inOrder(root.right);
		}
	}
	
	public void preOrder(Node<T> root){
		if(root!=null){
			System.out.print(root.data.toString()+ " ");
			preOrder(root.left);
			preOrder(root.right);
		}
	}
	
	public void postOrder(Node<T> root){
		if(root!=null){
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.data.toString()+ " ");
		}
	}
	
	/**
	 * ²ã´Î±éÀú
	 * @param root
	 */
	public void listOrder(Node<T> root){
		if(root==null){
			return;
		}
		Queue<Node<T>> queue = new ArrayDeque<>();
		queue.offer(root);
		Node<T> curr = null;
		while(!queue.isEmpty()){
			curr = queue.poll();
			System.out.print(curr.data.toString()+" ");
			if(curr.left != null){
				queue.offer(curr.left);
			}
			if(curr.right != null){
				queue.offer(curr.right);
			}
		}
	}
	
	public boolean contains(T t, Comparator<T> comparator){
		if(this.root == null){
			return false;
		}
		Node<T> curr = root;
		while(curr != null){
			if(comparator.compare(t, curr.data) < 0){
				curr = curr.left;
			}else if(comparator.compare(t, curr.data) > 0){
				curr = curr.right;
			}else {
				return true;
			}
		}
		return false;
	}

	public boolean insert(T t,Comparator<T> comparator) throws Exception{
		if(null ==t){
			throw new Exception("insert data can not be null");
		}
		if(null == comparator){
			throw new Exception("comparator can not be null");
		}
		if(root == null){
			root = new Node<>(t);
			return true;
		}
		Node<T> currNode = root;
		while(true){
			if(comparator.compare(t, currNode.data) < 0){
				if(currNode.left != null){
					currNode = currNode.left;
				}else {
					currNode.left = new Node<>(t);
					return true;
				}
			}else {
				if(currNode.right != null){
					currNode = currNode.right;
				}else {
					currNode.right = new Node<>(t);
					return true;
				}
			}
		}
	}
	
	public class Node<E> {
		E data;
		Node<E> left;
		Node<E> right;
		
		public Node(E t) {
			this.data = t;
		}
	}
}

