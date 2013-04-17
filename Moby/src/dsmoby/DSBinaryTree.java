package dsmoby;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DSBinaryTree<E extends Comparable> {
	public DSElement<E> root;
	public int count;
	
	
	/*
	 * Inserts an object x into our tree,
	 * beginning its insertion at the root
	 */
	public void insert(E x){
		DSElement<E> newguy = new DSElement<E>(x);
		if(root == null)
			root = newguy;
		else if(x.compareTo(root.getItem()) < 0){	// x is less than item
			if(root.getLeft() == null){
				root.setLeft(newguy);
				newguy.setParent(root);
			}
			else
				insert(newguy, root.getLeft());
		}
				
		else {	// x is greater than or equal to item
			if(root.getRight() == null){
				root.setRight(newguy);
				newguy.setParent(root);
			}
			else
				insert(newguy, root.getRight());
		}
		count++;
	}
	
	
	
	/*
	 * This insertion starts at some particular
	 * node in the tree
	 */
	private void insert(DSElement<E> x, DSElement<E> node){
		if(node == null)
			node = x;
		else if(x.getItem().compareTo(node.getItem()) < 0){	// x is less than item
			if(node.getLeft() == null){
				node.setLeft(x);
				x.setParent(node);
			}
			else
				insert(x, node.getLeft());
		}
				
		else {	// x is greater than item
			if(node.getRight() == null){
				node.setRight(x);
				x.setParent(node);
			}
			else
				insert(x, node.getRight());
		}
		
	}
	
	
	/*
	 * Returns the maximum depth of the whole tree
	 */
	public int maxDepth(){
		return height(root);
	}
	
	
	/*
	 * Returns the height of the given node
	 */
	private int height(DSElement<E> node){
		int leftHeight, rightHeight;
		
		if(node.getLeft() == null)
			leftHeight = 0;
		else
			leftHeight = height(node.getLeft());

		
		if(node.getRight() == null)
			rightHeight = 0;
		else
			rightHeight = height(node.getRight());
		
		return 1 + Math.max(leftHeight, rightHeight);
		
	}
	
	
	/*
	 * Returns an array with the elements of the tree
	 * in sorted order
	 */
	public ArrayList<E> sortedArray(){
		return sortedArray(root);
	}
	
	
	/*
	 * Recursive step.
	 * 
	 * Returns an array with the elements of the tree
	 * in sorted order, starting from a particular node
	 */
	private ArrayList<E> sortedArray(DSElement<E> node){
		ArrayList<E> returnArray = new ArrayList<E>();
		
		if(node == null)
			return returnArray;
		
		returnArray.addAll(sortedArray(node.getLeft()));
		returnArray.add(node.getItem());
		returnArray.addAll(sortedArray(node.getRight()));
		
		
		return returnArray;
		
	}
	
	
}
