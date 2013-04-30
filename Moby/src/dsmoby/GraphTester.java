package dsmoby;

public class GraphTester {

	public static void main(String[] args) {
		DSGraph g= new DSGraph("word4.graph");
		//g.printGraph();
		//g.isConnected();
		if(g.isConnected()){
			System.out.print("yay.\n");
		}
		else 	
			System.out.print("nay\n");
		
		g.shortestPath("math", "peas");
	}
}
