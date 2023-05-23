import java.util.ArrayList;
/**
 *	Binary Tree of Comparable values.
 *	The tree only has unique values. It does not add duplicate values.
 *	
 *	@author	Darren Lin
 *	@since	5 - 18 - 2023
 * 	Case 1 Leaf node, just null the pointer
 * 	Case 2 only a left pointer change the pointer to the child 
 * 	Case 3
 */
public class BinaryTree<E extends Comparable<E>> {

	private TreeNode<E> root;		// the root of the tree
	
	private final int PRINT_SPACES = 3;	// print spaces between tree levels
										// used by printTree()
	
	/**	constructor for BinaryTree */
	public BinaryTree() 
	{
		root = new TreeNode<E>(null,null,null); 
	}
	
	/**	Field accessors and modifiers */
	
	/**	Add a node to the tree iteratively
	 *	@param value		the value to put into the tree
	 */
	public void add(E value) 
	{
		if(root.getValue() == null)
		{
			root = new TreeNode<E>(value);
			return;
		}
		boolean found = false;
		TreeNode<E> node = root;
		while(!found)
		{
			if(value.compareTo(node.getValue()) > 0)//bigger
			{
				TreeNode<E> temp = node.getRight();
				if(temp != null)
					node = temp;
				else
				{
					node.setRight(new TreeNode<E>(value, null, null));
					found = true;
				}
			}
			else
			{
				TreeNode<E> temp = node.getLeft();
				if(temp != null)
					node = temp;
				else
				{
					node.setLeft(new TreeNode<E>(value, null, null));
					found = true;
				}
			}
		}
	}
	
	/**	Field accessors and modifiers */
	
	/**	Add a node to the tree
	 *	@param value		the value to put into the tree
	 */
	public void addRecursive(E value) //recursively
	{
		if(root.getValue() == null)
		{
			root = new TreeNode<E>(value);
			return;
		}
		TreeNode<E> node = root;
		addRecursiveHelper(value,root); 
	}
	/** This is the method that is called recursively
	 */
	public void addRecursiveHelper(E value, TreeNode<E> node)
	{
		if(value.compareTo(node.getValue()) > 0)//bigger
		{
			TreeNode<E> temp = node.getRight();
			if(temp != null)
				addRecursiveHelper(value, temp);
			else
				node.setRight(new TreeNode<E>(value));
		}
		else //smaller
		{
			TreeNode<E> temp = node.getLeft();
			if(temp != null)
				addRecursiveHelper(value, temp);
			else
				node.setLeft(new TreeNode<E>(value));
		}
	}
	
	/**
	 *	Print Binary Tree Inorder
	 */
	public void printInorder() {	printInOrder(root);	}
	
	/**
	 * Recursively called inorder method
	 * @param node The node to process 
	 */
	public void printInOrder(TreeNode<E> node)
	{
		if(node != null)
		{
			printInOrder(node.getLeft());
			System.out.println(node.getValue());
			printInOrder(node.getRight());
		}
	}
	
	/**
	 * Adds inorder values to an arrayList
	 * @param arr 	The arraylist to add to
	 * @param node The node to process
	 */
	public void inOrder(ArrayList<E> arr, TreeNode<E> node)
	{
		if(node != null)
		{
			inOrder(arr,node.getLeft());
			arr.add(node.getValue());
			inOrder(arr,node.getRight());
		}
	}
	
	/**
	 *	Print Binary Tree Preorder
	 */
	public void printPreorder() {	printPreorder(root); }
	/**
	 * Recursively called preorder method 
	 * @param node The node to process
	 */
	public void printPreorder(TreeNode<E> node)
	{
		if(node != null)
		{
			System.out.println(node.getValue());
			printInOrder(node.getLeft());
			printInOrder(node.getRight());
		}
	}
	
	/**
	 *	Print Binary Tree Postorder
	 */
	public void printPostorder() {	printPostorder(root);	}
	
	public void printPostorder(TreeNode<E> node)
	{
		if(node != null)
		{
			printInOrder(node.getLeft());
			printInOrder(node.getRight());
			System.out.println(node.getValue());
		}
	}
		
	/**	Return a balanced version of this binary tree
	 *	@return		the balanced tree
	 */
	public BinaryTree<E> makeBalancedTree() {
		BinaryTree<E> balancedTree = new BinaryTree<E>();
		ArrayList<E> arr= new ArrayList<E>();
		inOrder(arr,root);
		makeBalancedTree(arr,balancedTree);
		return balancedTree;
	}
	
	public void makeBalancedTree(ArrayList<E> arr,BinaryTree<E> balancedTree)
	{
		E midValue = arr.get(arr.size()/2);
		balancedTree.add(midValue);
		ArrayList<E> left = new ArrayList<E>();
		ArrayList<E> right = new ArrayList<E>();
		for(int i = 0; i < arr.size()/2; i++)
			left.add(arr.get(i));
		if(left.size() > 1)
			makeBalancedTree(left,balancedTree);
		else if(left.size() == 1)
			balancedTree.add(left.get(0));
		for(int i = left.size(); i < arr.size(); i++)
			right.add(arr.get(i));
		if(right.size() > 1)
			makeBalancedTree(right,balancedTree);
		else if(right.size() == 1)
			balancedTree.add(right.get(0));
	}
	
	/**
	 *	Remove value from Binary Tree
	 *	@param value		the value to remove from the tree
	 *	Precondition: value exists in the tree
	 */
	public void remove(E value) {
		remove(root, value);
	}
	/**
	 *	Remove value from Binary Tree
	 *	@param node			the root of the subtree
	 *	@param value		the value to remove from the subtree
	 *	@return				TreeNode that connects to parent
	 */
	public TreeNode<E> remove(TreeNode<E> node, E value) {
		TreeNode<E> left = node.getLeft();
		TreeNode<E> right = node.getRight();
		if(left != null && left.getValue().compareTo(value) == 0)
		{
			System.out.println("left");
			if(left.getRight() == null && left.getLeft() == null)//leaf node
			{
				node.setLeft(null);
				System.out.println("remove left");
				return left;
			}
			else if(left.getRight() == null)//only left child
			{
				node.setLeft(left.getLeft());
				return left;
			}
			else
			{
				TreeNode<E> lr = left.getRight();
				while(lr.getLeft() != null)
					lr = lr.getLeft();
				E temp = lr.getValue();
				remove(left,lr.getValue());
				left = new TreeNode<E>(temp,left.getLeft(),left.getRight());
			}
				
		}
		if(right != null && right.getValue().compareTo(value) == 0)
		{
			System.out.println("right");
			if(right.getRight() == null && right.getLeft() == null)//leaf node
			{
				node.setRight(null);
				System.out.println("remove right");
				return right;
			}
			else if(right.getRight() == null)//only left child
			{
				node.setLeft(left.getLeft());
				return right;
			}
			else
			{
				TreeNode<E> rr = right.getRight();
				while(rr.getLeft() != null)
					rr = rr.getLeft();
				E temp = rr.getValue();
				remove(right,rr.getValue());
				right = new TreeNode<E>(temp,right.getLeft(),right.getRight());
			}
		}
		if(left != null && node.getValue().compareTo(value) > 0)
			remove(left,value);
		else if(right != null)
			remove(right,value);
		
		return null;
	}
	

	/*******************************************************************************/	
	/********************************* Utilities ***********************************/	
	/*******************************************************************************/	
	/**
	 *	Print binary tree
	 *	@param root		root node of binary tree
	 *
	 *	Prints in vertical order, top of output is right-side of tree,
	 *			bottom is left side of tree,
	 *			left side of output is root, right side is deepest leaf
	 *	Example Integer tree:
	 *			  11
	 *			/	 \
	 *		  /		   \
	 *		5			20
	 *				  /	  \
	 *				14	   32
	 *
	 *	would be output as:
	 *
	 *				 32
	 *			20
	 *				 14
	 *		11
	 *			5
	 ***********************************************************************/
	public void printTree() {
		printLevel(root, 0);
	}
	
	/**
	 *	Recursive node printing method
	 *	Prints reverse order: right subtree, node, left subtree
	 *	Prints the node spaced to the right by level number
	 *	@param node		root of subtree
	 *	@param level	level down from root (root level = 0)
	 */
	private void printLevel(TreeNode<E> node, int level) {
		if (node == null) return;
		// print right subtree
		printLevel(node.getRight(), level + 1);
		// print node: print spaces for level, then print value in node
		for (int a = 0; a < PRINT_SPACES * level; a++) System.out.print(" ");
		System.out.println(node.getValue());
		// print left subtree
		printLevel(node.getLeft(), level + 1);
	}
	
	
}
