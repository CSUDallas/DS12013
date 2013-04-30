package dsmoby;

public class GraphTester {

	public static void main(String[] args) {
		DSGraph g= new DSGraph("house.graph");
		g.printGraph();
		g.isConnected();
		if(g.isBipartite()){
			System.out.print("yay.\n");
		}
			else 	
				System.out.print("nay\n");
	}
	
	
}
