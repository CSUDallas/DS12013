package latina;

public class DSLinkedList<E extends Comparable> {
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
   
    public void printList(){
        DSElement<E> e = first;
        while(e != null){
            System.out.print(e.getItem().toString() + " ");
            e = e.getNext();
        }
        System.out.println("");
    }
    
    public boolean contains(E X){
        boolean found = false;
        if(count == 0) {
            found = false;
        } else {
            DSElement<E> node = first;
            while(node.getNext()!=null){
                if (X.compareTo(node.getItem())==0){
                    found = true;
                    break;
                }
                node = node.getNext();
            }
        }
        return found;
    }
}
