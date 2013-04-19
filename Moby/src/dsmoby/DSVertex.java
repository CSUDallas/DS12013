package dsmoby;

public class DSVertex implements Comparable<DSVertex>{
	String label;
	DSLinkedList<DSVertex> neighbors;

	
	
	public int compareTo(DSVertex v){
		return label.compareTo(v.label);
	}
}