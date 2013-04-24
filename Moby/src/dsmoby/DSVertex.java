package dsmoby;

public class DSVertex implements Comparable<DSVertex>{
	String label;
	DSLinkedList<DSVertex> neighbors;

	/*
	 * Constructs an unlabeled vertex
	 */
	public DSVertex(){
		this("");
	}
	
	/*
	 * Constructs a vertex with label l and empty neighbors list.
	 */
	public DSVertex(String l){
		label = l;
		neighbors = new DSLinkedList<DSVertex>();
	}
	
	/*
	 * 
	 */
	public int compareTo(DSVertex v){
		return label.compareTo(v.label);
	}
}