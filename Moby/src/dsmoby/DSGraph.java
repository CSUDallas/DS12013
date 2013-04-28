package dsmoby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class DSGraph {
	DSLinkedList<DSVertex> vertexList;

	/*
	 * Constructor for an empty graph
	 */
	public DSGraph(){
		this("");
	}

	
	/*
	 * Constructor
	 * Reads a graph from graphFile in format:
	 * 
	 * vertexlabel1 vertexlabel2
	 * --END--
	 * 
	 * Where each line represents an edge of the graph.
	 */
	public DSGraph(String graphFile){
		vertexList = new DSLinkedList<DSVertex>();
		if(graphFile == "")
			return;
		try { 
			FileReader f = new FileReader(graphFile);
			BufferedReader reader = new BufferedReader(f);
			String line = null;

			while ((line = reader.readLine()) != null && 
					line.trim().compareTo("--END--") != 0){
				String parts[] = line.split(" ");
				String vertex1Name = parts[0];
				String vertex2Name = parts[1];
				
				DSVertex v1 = this.vertexWithLabel(vertex1Name);
				if(v1 == null){
					v1 = new DSVertex(vertex1Name);
					vertexList.addLast(v1);
				} 
				DSVertex v2 = this.vertexWithLabel(vertex2Name);
				if(v2 == null){
					v2 = new DSVertex(vertex2Name);
					vertexList.addLast(v2);
				} 
				
				v1.neighbors.addLast(v2);
				v2.neighbors.addLast(v1);
			}

		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
	}

	/*
	 * Searches the list of vertices for a vertex with the
	 * given label. Returns the DSVertex object, or null if
	 * there is no vertex with that label
	 */
	private DSVertex vertexWithLabel(String label){
		DSElement<DSVertex> e = vertexList.first;
		while(e != null){
			if(e.getItem().label.compareTo(label) == 0)
				return e.getItem();
			e = e.getNext();
		}
		return null;
	}
	
	public boolean numberOfEdges(){
		return false;
	}
	
	public boolean shortestPath(){
		return false;
	}
	/*add the first point to the list
	 * then add its neighbors that are not in the list into the list
	 * after the list is full, compare # of items. If # of items is equal,
	 *  then list is connected. 
	 */
	public DSLinkedList<DSVertex> ISConnected;
	public DSLinkedList<DSVertex> J;
	public boolean isConnected(String label){
		ISConnected = new DSLinkedList<DSVertex>();
		DSElement<DSVertex> t = vertexList.first;
		t.addFirst();
		DSElement<DSVertex> f = ISConnected.first;
		mark first item in queue as in queue
		J = new DSLinkedList<DSVertex>();
		J.first = vertexList.first.neighbors;
		
		while(f !=null){
			// add items that f is pointing too
			if neighbors is not in the queue
			add it to the queue
			mark these items as in
			pop first off queue
			
			after loops check iff all items are marked
			if all marked connected
			if not all marked not connected
		}
		return false;
	}
	
	public boolean isBipartite(){
		return false;
	}

	/*
	 * Prints the graph as a list of   vertex: neighbor1 neighbor2 neighbor3 ...
	 * one vertex per line, all neighbors of that vertex on its line.
	 */
	public void printGraph(){
		DSElement<DSVertex> e = vertexList.first;
		while(e != null){
			DSVertex v = e.getItem();
			System.out.print(v.label + ": ");
			printNeighbors(v);
			System.out.println("");
			e = e.getNext();
		}
	}
	
	/*
	 * Prints the neighbors of input vertex v, separated by spaces, no newline.
	 */
	private void printNeighbors(DSVertex v){
		DSLinkedList<DSVertex> n = v.neighbors;
		DSElement<DSVertex> e = n.first;
		while(e != null){
			DSVertex w = e.getItem();
			System.out.print(w.label + " ");
			e = e.getNext();
		}
	}
}