package dsmoby;

public class Poet {
	private static Moby 			moby;
	
	public static void main(String[] args){
		moby = new Moby("cmupronRand.txt", 
				"mpos.txt",
				"flist.txt",
				"infl.txt");
		moby.setScowlThreshold(60);
		
		//String poem = writeIambicPoemRhyme(5, 14);	// Sonnet
		//String poem = writeDoubleDactyl();
		//System.out.println(poem);
		System.out.println(moby.getAllPhones("He walked the dog very far"));
		//gram = new PoemGrammar();
		//System.out.println(gram.makeSentence());
		moby.printAllWords();
	}
	
	
	public static String writeIambicPoemDumb(int iambs, int lines){
		String poem = "";
		for(int i = 0; i < lines; i++)
			poem = poem + writeIambicLine(iambs) + "\n";
		
		return poem;
	}
	
	
	public static String writeIambicPoemRhyme(int iambs, int lines){
		String poem = "";
		for(int i = 0; i < lines/2; i++)
			poem = poem + writeTwoRhymingIambicLines2(iambs) + "\n";
		
		return poem;
	}
	
	
	public static String writeIambicLine(int iambs){
		String line = "";
		while(iambs > 0){
			int numIambs = 1 + (int)(Math.random() * iambs);
			switch(numIambs){
			case 1:
				if(Math.random() < 0.5)
					line = line + moby.getStressWord("01");
				else
					line = line + moby.getStressWord("1") + " " + 
							moby.getStressWord("1");
				
				break;
			case 2:
				if(Math.random() < 0.33)
					line = line + moby.getStressWord("0101");
				else if(Math.random() < 0.66)
					line = line + moby.getStressWord("01") + " " + 
							moby.getStressWord("01");
				else
					line = line + moby.getStressWord("1") + " " + 
							moby.getStressWord("101");
				break;
			case 3:
				if(Math.random() < 0.1)
					line = line + moby.getStressWord("010101");
				else if(Math.random() < 0.5)
					line = line + moby.getStressWord("010") + " " + 
							moby.getStressWord("101");
				else
					line = line + moby.getStressWord("1") + " " + 
							moby.getStressWord("10") + " " + 
							moby.getStressWord("101");
				break;
			case 4:
				if(Math.random() < 0.1)
					line = line + moby.getStressWord("0101") + " " + 
							moby.getStressWord("0101");
				else if(Math.random() < 0.5)
					line = line + moby.getStressWord("1") + " " + 
							moby.getStressWord("1010") + " " + 
									moby.getStressWord("101");
				else
					line = line + moby.getStressWord("010") + " " + 
							moby.getStressWord("10") + " " + 
							moby.getStressWord("101");
				break;
			case 5:
				line = line + moby.getStressWord("010101") + " " +
						moby.getStressWord("0101");
				break;
			}
			line = line + " ";
			iambs -= numIambs;
		}
		
		return line;
	}
	


	public static String writeTwoRhymingIambicLines(int iambs){
		String line = "";
		line += "My ";
		line += moby.getWord("A", "10", "", true) + " ";
		line += moby.getWord("N", "1", "", true) + " ";
		line += moby.getWord("i", "010", "", true) + " ";
		line += moby.getWord("P", "1", "", true) + " ";
		line += moby.getWord("N", "01", "AOR ", true);
		line += "\nWhile ";
		line += moby.getWord("N", "101", "", true) + " ";
		line += moby.getWord("v", "01", "", true) + " ";
		line += moby.getWord("t", "010", "", true) + " ";
		line += moby.getWord("N", "1", "AOR ", true);
		
		
		return line;
	}
	
	
	public static String writeTwoRhymingIambicLines2(int iambs){
		String line = "";
		line += moby.getWord("N", "01", "", true) + " ";
		line += moby.getWord("D", "1", "", true) + " ";
		line += moby.getWord("N", "10", "", true) + " ";
		line += moby.getWord("v", "1", "", true) + " ";
		line += moby.getWord("Pv", "01", "", true) + " ";
		line += moby.getWord("D", "1", "", true) + " ";
		String lastWord = moby.getWord("N", "1", "", true);
		line += lastWord + "\n";
		
		line += moby.getWord("P", "01", "", true) + " ";
		line += moby.getWord("D", "1", "", true) + " ";
		line += moby.getWord("A", "101", "", true) + " ";
		line += moby.getWord("A", "1", "", true) + " ";
		line += moby.getWord("N", "10", "", true) + " ";
		String rhyme = moby.getRhymePhones(lastWord);
		String x = "";
		for(int i = 0; i < 50000; i++){
			x = moby.getWord("N", "1", rhyme, true);
			if(x.compareTo("amoeba") != 0)
				break;
			x = moby.getWord("N", "1", rhyme, true);
			if(x.compareTo("amoeba") != 0)
				break;
		}
		line += x;
		
		
		return line;
	}
	
	public static String writeDoubleDactyl(){
		String dd = "";
		dd += moby.getWord("!", "10", "", true) + " ";
		dd += moby.getWord("!", "0100", "", true) + "\n";
		for(int i = 0; i < 2; i++)
			dd += writeOneDoubleDactylLine() + "\n";
		dd += moby.getWord("t", "100", "", true) + " ";
		String end1 = moby.getWord("N", "1", "", true);
		dd += end1 +"\n";
		
		for(int i = 0; i < 2; i++)
			dd += writeOneDoubleDactylLine() + "\n";
		dd += moby.getWord("", "100100", "", true) + "\n";
		dd += moby.getWord("v", "100", "", true) + " ";
		String rp = moby.getRhymePhones(end1);
		dd += /*"(" + rp + ")" +*/ moby.getWord("N", "1", rp, true) + "\n";
		
		return dd;
	}
	
	
	public static String writeOneDoubleDactylLine(){
		String dd = "";
		int choice = (int)(Math.random() * 4);
		//dd += "(" + choice + ") ";
		switch(choice){
		case 0:
			dd += moby.getWord("N", "1", "", true) + " ";
			dd += moby.getWord("t", "1", "", true) + " ";
			dd += moby.getWord("N", "1", "", true) + " ";
			dd += moby.getWord("v", "100", "", true);
			break;
			
		case 1:
			dd += moby.getWord("A", "1", "", true) + " ";
			dd += moby.getWord("A", "1", "", true) + " ";
			dd += moby.getWord("A", "1", "", true) + " ";
			dd += moby.getWord("N", "100", "", true);
			break;
			
		case 2:
			dd += moby.getWord("A", "1", "", true) + " ";
			dd += moby.getWord("N", "1", "", true) + " ";
			dd += moby.getWord("v", "1", "", true) + " ";
			dd += moby.getWord("i", "100", "", true);
			break;
			
		case 3:
			dd += moby.getWord("r", "1", "", true) + " ";
			dd += moby.getWord("V", "1", "", true) + " ";
			dd += moby.getWord("I", "1", "", true) + " ";
			dd += moby.getWord("N", "100", "", true);
			break;
		}
		
		return dd;
	}
}
