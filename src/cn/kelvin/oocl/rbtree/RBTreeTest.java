package cn.kelvin.oocl.rbtree;

import org.junit.Test;

public class RBTreeTest {
	
	@Test
	public void testInsertWithColor(){
		RBTree<Integer> tree = new RBTree<Integer>(10);
		tree.insert(7);
		tree.insert(12);
		tree.insert(5);
		tree.insert(8);
		tree.insert(11);
		tree.insert(15);
		tree.insert(3);
		tree.insert(13);
		tree.insert(4);	
		tree.insert(12);
		tree.listOrderWithColor(tree.mRoot);
		System.out.println();
		tree.inOrder(tree.mRoot);
	}

	@Test
	public void testRemoveFixUp(){
		RBTree<Integer> tree = new RBTree<Integer>(10);
		tree.insert(7);
		tree.insert(12);
		tree.insert(5);
		tree.insert(8);
		tree.insert(11);
		tree.insert(15);
		tree.insert(3);
		tree.insert(13);
		tree.insert(4);
		tree.insert(12);
		tree.listOrder(tree.mRoot);
		System.out.println();
		tree.listOrderWithColor(tree.mRoot);
		System.out.println();
//		tree.inOrder(tree.mRoot);
		
//		tree.remove(11);
		tree.remove(8);
		tree.listOrder(tree.mRoot);
		System.out.println();
		tree.listOrderWithColor(tree.mRoot);
//		System.out.println();
	}
	
	@Test
	public void testRemove(){
		RBTree<Integer> tree = new RBTree<Integer>(10);
		tree.insert(7);
		tree.insert(12);
		tree.insert(5);
		tree.insert(8);
		tree.insert(11);
		tree.insert(15);
		tree.insert(3);
		tree.insert(13);
		tree.insert(4);
		tree.insert(12);
		tree.listOrder(tree.mRoot);
		System.out.println();
		tree.listOrderWithColor(tree.mRoot);
		System.out.println();
//		tree.inOrder(tree.mRoot);
		
		tree.remove(10);
		tree.listOrder(tree.mRoot);
		System.out.println();
		tree.listOrderWithColor(tree.mRoot);
		System.out.println();
	}
}
