package dsmoby;

public class RickyPoet {
	private static Moby moby;
	
	public static void main(String[] args) {
		moby = new Moby("cmupronRand.txt", 
				"mpos.txt",
				"flist.txt",
				"infl.txt");
		moby.setScowlThreshold(60);

		writePoem();
	}
	
	private static void writePoem(){
		System.out.println("Roses are red");
	}

}
