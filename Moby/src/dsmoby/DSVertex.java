package dsmoby;

public class DSVertex implements Comparable<DSVertex>{
	public String label;
	public DSLinkedList<DSVertex> neighbors;
	public boolean visited;
	public int color;
	public int distance;
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
		visited=false;
		distance=0;
	}
	
	/*
	 * 
	 */
	public int compareTo(DSVertex v){
		return label.compareTo(v.label);
	}
}