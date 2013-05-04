package dsmoby;

public class DSLinkedList<E extends Comparable<E>> {
	public DSElement<E> first;
	public DSElement<E> last;
	public int count;

	public DSLinkedList(){
		first = null;
		last = null;
		count = 0;
	}

	public void addLast(E item){
		DSElement<E> newItem = new DSElement<E>();
		newItem.setItem(item);
		newItem.setNext(null);
		newItem.setPrevious(last);
		if(last != null)
			last.setNext(newItem);
		else
			first = newItem;
		last = newItem;
		count++;
	}

	public void addFirst(E item){
		DSElement<E> newItem = new DSElement<E>();
		newItem.setItem(item);
		newItem.setNext(first);
		newItem.setPrevious(null);
		if(first != null)
			first.setPrevious(newItem);
		else
			last = newItem;
		first = newItem;
		count++;
	}

	public E removeFirst(){
		if(count == 0)
			return null;
		
		E firstItem = first.getItem();
		first = first.getNext();
		if(first != null)
			first.setPrevious(null);
		else
			last = null;
		count --;
		return firstItem;
	}

	public E removeLast(){
		if(count == 0)
			return null;
		
		E lastItem = last.getItem();
		last = last.getPrevious();
		if(last != null)
			last.setNext(null);
		else
			first = null;
		count --;
		return lastItem;
	}

	public int size(){
		return count;
	}
	

public boolean conTains (E X){
    DSElement<E> e = first;
    if (e == null)
    	return false;
	while (e != null){
    if (e != X)
		e = e.getNext();
	if (e == X)
		return true;
	}
	return false; 
}
	

	public void bubbleSort(){
		for(int i = 0; i < count; i++){
			DSElement<E> e = first;
			while(e != null){
				if(e.getNext() == null)
					break;
				if(e.getItem().compareTo(e.getNext().getItem()) > 0){
					DSElement<E> f = e.getNext();
					if(e.getPrevious() != null)
						e.getPrevious().setNext(f);
					else
						first = f;
					if(f.getNext() != null)
						f.getNext().setPrevious(e);
					else
						last = e;
					e.setNext(f.getNext());
					f.setPrevious(e.getPrevious());
					e.setPrevious(f);
					f.setNext(e);
				} else {
					e = e.getNext();
				}
			}
		}
	}
	
	
	public void printList(){
		DSElement<E> e = first;
		while(e != null){
			System.out.print(e.getItem().toString() + " ");
			e = e.getNext();
		}
		System.out.println("");
	}
}
