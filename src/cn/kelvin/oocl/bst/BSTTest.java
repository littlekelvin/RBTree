package cn.kelvin.oocl.bst;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BSTTest {
	BinarySearchTree<Integer> tree;
	IntegerComparator ic;
	
	@Before
	public void init() throws Exception{
		Integer[] datas = {8,4,3,7,12,9,8,10};
		ic = new IntegerComparator();
		tree = new BinarySearchTree<>(datas, ic);
	}
	
	@Test
	public void testWithInteger() throws Exception{
		tree.inOrder(tree.root);
		System.out.println();
		tree.preOrder(tree.root);
		System.out.println();
		tree.postOrder(tree.root);
		System.out.println();
		tree.listOrder(tree.root);
	}
	
	@Test
	public void testContains(){
		Assert.assertTrue(tree.contains(10, ic));
		Assert.assertTrue(tree.contains(7, ic));
		Assert.assertFalse(tree.contains(11, ic));
		Assert.assertFalse(tree.contains(13, ic));
	}
}
