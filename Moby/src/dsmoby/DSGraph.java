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
	public DSLinkedList<DSVertex> connectedVertices;
	public boolean isConnected(){
		DSElement<DSVertex> k = vertexList.first; 
		while (k != null){
			k.getItem().visited = false;
			k = k.getNext();
		}
		connectedVertices = new DSLinkedList<DSVertex>();
		DSElement<DSVertex> t = vertexList.first;
		connectedVertices.addFirst(t.getItem());
		t.getItem().visited = true;

		while (connectedVertices.first != null){
			System.out.println(connectedVertices.first.getItem().label);
			DSElement<DSVertex> j = connectedVertices.first.getItem().neighbors.first;
			while (j != null){
				connectedVertices.addLast(j.getItem());
				if (connectedVertices.last.getItem().visited == true){
					connectedVertices.removeLast();
				}
				connectedVertices.last.getItem().visited = true;
				System.out.println("innerloop" + j.getItem().label);
				j = (j.getNext());
			}
			connectedVertices.removeFirst();
			System.out.println("ilooped" + connectedVertices.count);
		}
		DSElement<DSVertex> r = vertexList.first; 
		while (r != null){
			if (r.getItem().visited == true){
				r = r.getNext();
			}
			else return false;
		}
		return true;
	}

	public boolean isBipartite(){
		DSElement<DSVertex> e = vertexList.first;
		DSLinkedList<DSVertex> vertexQueue;
		vertexQueue = new DSLinkedList<DSVertex>();
		for(int i = 0; i < vertexList.count; i++){
			DSVertex v = e.getItem();
			v.color = 0;
			v.visited = false;
			e.getNext();
		}
		e = vertexList.first;
		while(e != null){
			DSVertex v = e.getItem();
			if(v.visited == false){
				v.visited = true;
				vertexQueue.addLast(v);
				DSElement<DSVertex> k = vertexQueue.first;
				DSVertex m = k.getItem();
				DSLinkedList<DSVertex> l = m.neighbors;
				DSElement<DSVertex> n = l.first;
				while(n != null){
					DSVertex q = n.getItem();
					if(v.color == 0 || q.color == 0)
						q.color = 1;
					if(v.color == 1 || q.color == 0)
						q.color = 2;
					else if(v.color == 2 || q.color == 0)
						q.color = 1;
					//System.out.print("Vertex " + q.label + " is colored: " + q.color + "\n");
					vertexQueue.addLast(q);
					n = n.getNext();

				}
				vertexQueue.removeFirst();
				
				while(k != null){
					DSVertex q = k.getItem();
					if(v.color == q.color)
						return false;
					k.getNext();
				}
				e.getNext();
			}
			else
				e.getNext();

	}
	return true;
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