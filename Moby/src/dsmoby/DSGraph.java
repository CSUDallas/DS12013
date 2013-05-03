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

	public int numberOfEdges(){
		int sum = 0;
		int edges = 0;
		DSElement<DSVertex> vertex = vertexList.first;
		while(vertex != null){
			sum += vertex.getItem().neighbors.count;
			vertex = vertex.getNext();
		}
		edges = sum/2;
		System.out.println(edges);
		return edges;
	}

	
	
	/*
	 * Finds the shortest path between the vertex with label 'start'
	 * and the vertex with label 'end'
	 * Prints the resulting path length
	 */
	public void shortestPath(String start, String end){
		//Set all vertex distances to 0
		DSElement<DSVertex> d = vertexList.first;
		int count = vertexList.size();    // failsafe counter
		while(count > 0){
			d.getItem().distance=-1;
			d = d.getNext();
			count--;
		}
		//create queue
		DSLinkedList<DSVertex> Q = new DSLinkedList<DSVertex>();
		//create DSElement containing vertex of label
		DSElement<DSVertex> e = new DSElement<DSVertex>();		
		e.setItem(vertexWithLabel(start));
		e.getItem().distance=0;
		e.getItem().parent = null;
		//add e to queue
		Q.addLast(e.getItem());
		//Find path from start to end
		while(Q.count!=0){
			DSVertex v = Q.removeFirst();
			DSLinkedList<DSVertex> n = v.neighbors;
			DSElement<DSVertex> f = n.first;
			while(f != null){
				if(f.getItem().distance==-1){
					f.getItem().distance = v.distance+1;
					f.getItem().parent = v;
					Q.addLast(f.getItem());
				}
				f = f.getNext();
			}
			if(v.label.equals(end)) {
				System.out.println();
				System.out.println("Path length from \"" + start + "\" to \"" + v.label + "\" is " + v.distance);
				System.out.println();
				DSVertex w = v;
				while(w != null){
					System.out.print(w.label + "-");
					w = w.parent;
				}
				System.out.println();
				return;
			} 
		}
		System.out.println();
		System.out.println("No path from \"" + start + "\" to \"" + end + "\" found.");
		System.out.println();
		return;
	}
	
	/*add the first point to the list
	 * then add its neighbors that are not in the list into the list
	 * after the list is full, compare # of items. If # of items is equal,
	 *  then list is connected. 
	 */
	
	public boolean isConnected(){
		DSLinkedList<DSVertex> connectedVertices;
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
			//System.out.println(connectedVertices.first.getItem().label);
			DSElement<DSVertex> j = connectedVertices.first.getItem().neighbors.first;
			while (j != null){
				connectedVertices.addLast(j.getItem());
				if (connectedVertices.last.getItem().visited == true){
					connectedVertices.removeLast();
				}
				connectedVertices.last.getItem().visited = true;
				//System.out.println("innerloop" + j.getItem().label);
				j = (j.getNext());
			}
			connectedVertices.removeFirst();
			//System.out.println("ilooped" + connectedVertices.count);
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
	 * Returns true if and only if the graph can be 3-colored
	 */
	String cs = "";
	public boolean isThreeColorable(){
		if(vertexList.count <= 3)	// Small graphs are 3-colorable
			return true;
		// Initialize all colors to -1
		DSElement<DSVertex> v = vertexList.first;
		while(v != null){
			v.getItem().color = -1;
			v = v.getNext();
		}

		v = vertexList.first;
		return isThreeColorableRecursion(v);

	}

	/*
	 * Recursive step for isThreeColorable()
	 * Returns true if the coloring up to but not including vertex v
	 * can be extended to a coloring of the whole graph, false otherwise.
	 *
	 * It is assumed that the colors assigned so far constitute a valid coloring.
	 */
	private boolean isThreeColorableRecursion(DSElement<DSVertex> v){
		if(v == null)	// Whole graph is already colored!
			return true;

		for(int i = 0; i < 3; i++){
			cs = cs + i;
			//System.out.println(cs);
			DSElement<DSVertex> nbr = v.getItem().neighbors.first;
			boolean goodColor = true;
			while(nbr != null){
				if(nbr.getItem().color == i){
					goodColor = false;
					break;
				}
				nbr = nbr.getNext();
			}
			if(goodColor){
				v.getItem().color = i;
				if(isThreeColorableRecursion(v.getNext()))
					return true;
				v.getItem().color = -1;
			}
			cs = cs.substring(0, cs.length() - 1);
		}

		return false;
	}
/*
 * count the number of connected components od the graph
 */
	public int numComponents(){
		DSLinkedList<DSVertex> stack = new DSLinkedList<DSVertex>();
		DSVertex v = vertexList.first.getItem();//XXX will be modified
		while(v != null){
			stack.addLast(v);
			
		}
		
		
		
	}
	
	
	public boolean isBipartite2()
	{
		DSElement<DSVertex> e = vertexList.first;
		//makes sure all colors and visited values are set the default
		for(int i = 0; i < vertexList.count; i++)
		{
			DSVertex v = e.getItem();
			v.color = 0;
			v.visited = false;
			//System.out.println(v.label + " " +  v.color); //debugging code
			e = e.getNext();
		}

		//The element that will be used to travel through the list
		DSElement<DSVertex> start = vertexList.first;

		//The first vertex is hard coded to color as 1 and visited as true
		DSVertex starter = start.getItem();
		starter.color = 1;
		starter.visited = true;
		//colors and checks the graph for two colorableness
		DSLinkedList<DSVertex> loopy = starter.neighbors;
		for(int x = 1; x < vertexList.count; x++)
		{
			//System.out.println("bump"); //debugging code

			//Loops through the neighbors coloring them as needed and making sure the graph is still legal
			DSElement<DSVertex> vertPro = loopy.first;
			for(int z = 0; z < loopy.count; z++)
			{
				DSVertex vert = vertPro.getItem();
				if(vert.visited == false)
				{
					vert.color = 1;
					vert.visited = true;
					if(!(vert.compareNeighborsColors()))
					{
						vert.color = 2;
						//the actual check to see if the graph is legal. If this is false the graph isn't bipartite
						if(!(vert.compareNeighborsColors()))
						{
							//System.out.println(vert.label + " was the location of the failure"); //debugging code
							return false;
						}
					}
					//System.out.println(vert.label + " " + vert.color); //debugging code

				}
				vertPro = vertPro.getNext();	
			}
			//Grabs the next vertex in the list so the process can continue
			start = start.getNext();
			//System.out.println(start.getItem().label); //debugging code
			loopy = start.getItem().neighbors;
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
