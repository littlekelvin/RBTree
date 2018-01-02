package cn.kelvin.oocl.avl;

import org.junit.Assert;
import org.junit.Test;

public class AvlTest {
	
	@Test
	public void testAvlTree2(){
		AvlTree tree = new AvlTree(new AVLNode(5));
		tree.insert(tree.root, 4);
		tree.insert(tree.root, 10);
		tree.insert(tree.root, 8);
		tree.insert(tree.root, 12);
		tree.insert(tree.root, 6);
		tree.listOrder(tree.root.parent);
	}
	
	@Test
	public void testAvlTree1(){
		AvlTree tree = new AvlTree();
		AVLNode n1 = new AVLNode(5);
		AVLNode n2 = new AVLNode(4);
		AVLNode n3 = new AVLNode(10);
		AVLNode n4 = new AVLNode(8);
		AVLNode n5 = new AVLNode(12);
		AVLNode n6 = new AVLNode(6);
		
		n1.left=n2;
		n1.right = n3;
		n2.parent = n1;
		n3.parent = n1;
		n3.left = n4;
		n3.right = n5;
		n4.parent = n3;
		n5.parent = n3;
		n4.left = n6;
		n6.parent = n4;
		
		n1.depth = 4;
		n2.depth = 1;
		n3.depth = 3;
		n4.depth = 2;
		n5.depth = 1;
		n6.depth = 1;
		
		n1.balance = tree.calBalance(n1);
		n2.balance = tree.calBalance(n2);
		n3.balance = tree.calBalance(n3);
		n4.balance = tree.calBalance(n4);
		n5.balance = tree.calBalance(n5);
		n6.balance = tree.calBalance(n6);
		Assert.assertTrue(n1.balance==-2);
		Assert.assertTrue(n2.balance==0);
		Assert.assertTrue(n3.balance==1);
		
		tree.listOrder(n1);
		
		//直接左旋
//		tree.leftRotate(n1);
//		System.out.println();
//		tree.listOrder(n3);
		
		//先右孩子右旋，然后左旋
		tree.rightRotate(n3);
		tree.leftRotate(n1);
		System.out.println();
		tree.listOrder(n4);
	}
	
	@Test
	public void testRotateMethod(){
		AvlTree tree = new AvlTree();
		AVLNode n1 = new AVLNode(5);
		AVLNode n2 = new AVLNode(3);
		AVLNode n3 = new AVLNode(6);
		AVLNode n4 = new AVLNode(2);
		AVLNode n5 = new AVLNode(4);
		AVLNode n6 = new AVLNode(1);
		
		n1.left = n2;
		n1.right = n3;
		n2.parent = n1;
		n3.parent = n1;
		
		n2.left = n4;
		n2.right = n5;
		n4.parent = n2;
		n5.parent = n2;
		
		n4.left = n6;
		n6.parent = n4;
		
		tree.listOrder(n1);
		tree.rightRotate(n1);
		System.out.println();
		tree.listOrder(n2);
	}
	
	@Test
	public void testRotateMethod2(){
		AvlTree tree = new AvlTree();
		AVLNode n1 = new AVLNode(5);
		AVLNode n2 = new AVLNode(4);
		AVLNode n3 = new AVLNode(10);
		AVLNode n4 = new AVLNode(8);
		AVLNode n5 = new AVLNode(12);
		AVLNode n6 = new AVLNode(13);
		
		n1.left=n2;
		n1.right = n3;
		n2.parent = n1;
		n3.parent = n1;
		
		n3.left = n4;
		n3.right = n5;
		n4.parent = n3;
		n5.parent = n3;
		
		n5.right = n6;
		n6.parent = n5;
		
		tree.listOrder(n1);
		tree.leftRotate(n1);
		System.out.println();
		tree.listOrder(n3);
	}
}
