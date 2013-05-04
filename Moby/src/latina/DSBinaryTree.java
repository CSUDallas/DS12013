package latina;

public class DSBinaryTree<E extends Comparable> {
	public DSElement<E> root;
	public int count;

	//Gets depth of tree
	public int maxDepth(){
		return height(root);
	}
	//Gets height of node
	public int height(DSElement<E> node){	
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
	 * Inserts an object x into our tree,
	 * beginning its insertion at the root
	 */
	public void insert(E x){
		DSElement<E> newguy = new DSElement<E>(x);
		if(root == null)
			root = newguy;
		else if(x.compareTo(root.getItem()) < 0){    // x is less than item
			if(root.getLeft() == null){
				root.setLeft(newguy);
				newguy.setParent(root);
			}
			else
				insert(newguy, root.getLeft());
		}
		else {    // x is greater than item
			if(root.getRight() == null){
				root.setRight(newguy);
				newguy.setParent(root);
			}
			else
				insert(newguy, root.getRight());
		}
	}

	/*
	 * This insertion starts at some particular
	 * node in the tree
	 */
	private void insert(DSElement<E> x, DSElement<E> node){
		if(node == null)
			node = x;
		else if(x.getItem().compareTo(node.getItem()) < 0){    // x is less than item
			if(node.getLeft() == null){
				node.setLeft(x);
				x.setParent(node);
				count++;
			}
			else {
				insert(x, node.getLeft());			
			}
		}

		else {    // x is greater than item
			if(node.getRight() == null){
				node.setRight(x);
				x.setParent(node);
				count++;
			}
			else{
				insert(x, node.getRight());				
			}
		}		
	}
}