package dsmoby;
public class RickyPoet {
	private static Moby moby;
	
	public static void main(String[] args) {
		moby = new Moby("cmupronRand.txt", 
				"mpos.txt",
				"flist.txt",
				"infl.txt");
		moby.setScowlThreshold(30);
		moby.setSynonyms("mobythes.aur");
		writeThesauruspoem();
	}
	static String wrd1 = "";
	static String wrd2 = "";
	static String wrd3 = "";
	static String wrd4 = "";
	static String dd = "";
	public static void writeThesauruspoem(){
		//String syn1 = "";
		//String syn2 ="";
		
		writeThesauruspoemline();
		
		writeThesauruspoemline();
		
		writeThesauruspoemline();
		
		writeThesauruspoemline();
		System.out.println(dd);
		return;
	}
		
		public static void writeThesauruspoemline(){
			int choice = (int)(Math.random() * 4);
			//String dd = "";
			//dd += "(" + choice + ") ";
			
		
			switch(choice){
			case 0:
				dd += "\nThis ";
				wrd1 = moby.getWord("N", "", "", true) + " ";
				dd += wrd1;
				wrd2 = moby.getWord("v", "", "", true) + " ";
				dd += wrd2;
				wrd3 = moby.getWord("V", "", "", true) + " ";
				dd += wrd3;
				wrd4 = moby.getWord("N", "", "", true);
				dd += wrd4 + ".";
				break;
				
				
			case 1:
				dd+= "\nIt was this ";
				wrd1 = moby.getWord("A", "", "", true) + " ";
				dd += wrd1;
				wrd2 = moby.getWord("A", "", "", true) + " ";
				dd += wrd2;
				wrd3 = moby.getWord("N", "", "", true);
				dd += wrd3;
				dd += " that I ";
				wrd4 =  moby.getWord("V", "", "", true);	
				dd += wrd4 + ".";
				break;
				
			case 2:
				dd+= "\nForever ";
				wrd1 = moby.getWord("D", "", "", true) + " ";
				dd += wrd1;
				wrd2 = moby.getWord("V", "", "", true) + " ";
				dd += wrd2;
				wrd3 = moby.getWord("i", "", "", true) + " ";
				dd += wrd3;
				wrd4 = moby.getWord("N", "", "", true);
				dd += wrd4 + ".";
				break;
				
			case 3:
				dd += "\nNever again can the ";
				wrd1 = moby.getWord("N", "", "", true) + " ";
				dd += wrd1;
				wrd2 = moby.getWord("V", "", "", true) + " ";
				dd += wrd2;
				wrd3 = moby.getWord("D", "", "", true) + " ";
				dd += wrd3;
				wrd4 = moby.getWord("N", "", "", true);
				dd += wrd4 + ".";
				break;
			}
				if (choice == 0){
					dd+= "\nIndeed that ";
					dd += moby.getSynonym(wrd1, 30);
					dd += " really ";
					dd += moby.getSynonym(wrd3, 30);
					dd += " that ";
					dd += wrd4;
					dd+= ".\n";
					dd+= "Indeed that ";
					dd += moby.getSynonym(wrd1, 30);
					dd += " really ";
					dd += moby.getSynonym(wrd3, 30);
					dd += " that ";
					dd += wrd4;
					dd+= ".\n";
					dd+= "Indeed that ";
					dd += moby.getSynonym(wrd1, 30);
					dd += " really ";
					dd += moby.getSynonym(wrd3, 30);
					dd += " that ";
					dd += wrd4;
					dd+= ".\n";
					}
	
	if (choice == 1){
		dd+= "\n";
		dd+=  "I ";
		dd += moby.getSynonym(wrd4, 30);
		dd+= " ";
		dd += "it because it was ";
		dd += moby.getSynonym(wrd1, 30);
		dd+= " and ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= ".\n";
		//dd+= "\n";
		dd+=  "I ";
		dd += moby.getSynonym(wrd4, 30);
		dd+= " ";
		dd += "it because it was ";
		dd += moby.getSynonym(wrd1, 30);
		dd+= " and ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= ".\n";
		//dd+= "\n";
		dd+=  "I ";
		dd += moby.getSynonym(wrd4, 30);
		dd+= " ";
		dd += "it because it was ";
		dd += moby.getSynonym(wrd1, 30);
		dd+= " and ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= ".\n";
		}
	
	if (choice == 2){
		dd+= "\n";
		dd += "I ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= " ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= " ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= " ";
		dd += moby.getSynonym(wrd4, 30);
		dd+= ".\n";
		//dd+= "\n";
		dd += "I ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= " ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= " ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= " ";
		dd += moby.getSynonym(wrd4, 30);
		dd+= ".\n";
		//dd+= "\n";
		dd += "I ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= " ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= " ";
		dd += moby.getSynonym(wrd2, 30);
		dd+= " ";
		dd += moby.getSynonym(wrd4, 30);
		dd+= ".\n";
		}
	if (choice == 3){
		dd+= "\nFor the ";
		dd += moby.getSynonym(wrd4, 30);
		dd+= " ";
		dd += moby.getWord("V", "1", "", true);
		dd+= " ";
		dd += moby.getSynonym(wrd1, 30);
		dd+= " ";
		dd += moby.getWord("v", "1", "", true);
		dd+= ".\n";
		dd+= "For the ";
		dd += moby.getSynonym(wrd4, 30);
		dd+= " ";
		dd += moby.getWord("V", "1", "", true);
		dd+= " ";
		dd += moby.getSynonym(wrd1, 30);
		dd+= " ";
		dd += moby.getWord("v", "1", "", true);
		dd+= ".\n";

		}
				//System.out.println(dd);
			return;
		}
		}

	
