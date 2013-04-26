package latina;
/*
 * Wrapper class for elements
 *
 * Notice that we've used getter and setter
 * methods to access the next and previous
 * references
 */

public class DSElement<E> {
	private E            item;
	private DSElement<E> next;
	private DSElement<E> previous;
	private DSElement<E> left;
	private DSElement<E> right;
	private DSElement<E> parent;

	/*
	 * This constructor builds a new DSElement object
	 * using "x" as the DSElement's "item"
	 *
	 * Part of a constructor's job is to initialize *all*
	 * fields of the object
	 */
	public DSElement(E x){
		this.item = x;
		this.previous = null;
		this.next = null;
		this.left = null;
		this.right = null;
		this.parent = null;
	}


	/*
	 * Default constructor, sets "item" to null
	 *
	 * Notice how slickly this constructor makes use of
	 * the previous constructor, which has more parameters.
	 * (typical design)
	 *
	 * In Java, a constructor may call exactly one other
	 * constructor, but it must be in the first line.
	 */
	public DSElement(){
		this(null);
	}


	// getter and setter for next
	public DSElement<E> getNext(){
		return next;
	}

	public void setNext(DSElement<E> e){
		next = e;
	}

	// getter and setter for previous
	public DSElement<E> getPrevious(){
		return previous;
	}

	public void setPrevious(DSElement<E> e){
		previous = e;
	}

	// getter and setter for previous
	public E getItem(){
		return item;
	}

	public void setItem(E e){
		item = e;
	}

	public DSElement<E> getLeft() {
		return left;
	}

	public void setLeft(DSElement<E> left) {
		this.left = left;
	}

	public DSElement<E> getRight() {
		return right;
	}

	public void setRight(DSElement<E> right) {
		this.right = right;
	}

	public DSElement<E> getParent() {
		return parent;
	}

	public void setParent(DSElement<E> parent) {
		this.parent = parent;
	}
}

