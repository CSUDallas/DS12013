/*
—–-—▒▒▒▒▒▒▒▒▒▒
—–-▒███████████▒
—▒████▒▒▒▒▒▒▒███▒
-▒████▒▒▒▒▒▒▒▒▒███▒……………….▒▒▒▒▒▒
-▒███▒▒▒▒▒███▒▒▒███▒…………..▒██████▒
-▒███▒▒▒▒██████▒▒███▒……….▒██▒▒▒▒██▒
—▒███▒▒▒███████▒▒██▒…….▒███▒▒█▒▒██▒
—–▒███▒▒████████▒██▒…▒███▒▒███▒▒██▒
——–▒██▒▒██████████▒▒███▒▒████▒▒██▒
———▒██▒▒██████████████▒████▒▒██▒
———-▒██▒▒█████████████████▒▒██▒
————▒██▒▒██████████████▒▒██▒
————–▒██▒▒████████████▒▒██▒
—————-▒██▒▒██████████▒▒██▒
—————–▒██▒▒████████▒▒██▒
——————-▒██▒▒██████▒▒██▒
———————▒██▒▒████▒▒██▒
———————-▒██▒▒███▒▒█▒
————————▒██▒▒█▒▒█▒
————————-▒██▒▒▒█▒
—————————▒██▒█▒
—————————♥♥♥♥♥♥
—————————-♥♥♥♥♥
——————————♥♥♥
—————————-—♥♥
———————————♥
 */
package dsmoby;

import java.util.*;

//look i added something very special, more special than you can imagine
public class Poet {
	private static Moby moby;
	private static GraceGrammar grammar;

	// This is a wonderful comment!

	public static void main(String[] args){
		//comment out next 5 lines when using GraceGrammar to avoid duplicate printouts
		moby = new Moby("cmupronRand.txt", 
				"mpos.txt",
				"flist.txt",
				"infl.txt");
		moby.setScowlThreshold(60);

		//grammar = new GraceGrammar();
		//System.out.println(grammar.makeSentence());
		moby.setNextWords("JaneAusten.txt");
		moby.setNextWords("shakespeare.txt");
		String poem = writeIambicPoemRhyme(5, 14);	// Sonnet
		//String poem = writeDoubleDactyl();
		System.out.println(poem);
		//System.out.println(moby.getAllPhones("He walked the dog very far"));
		//gram = new PoemGrammar();
		//System.out.println(gram.makeSentence());
		//moby.printAllWords();
		//moby.printAllVerbs();
		//moby.shiftChoose();
		//String freeVerse = writeFreeVerseVerbs(3);
		//System.out.println(freeVerse);

		//System.out.println(writeHaiku("Eternal bliss sponge" + "\n" + "life without anybody" + "\n" + "sometimes we are here"));

	}// hello


	//writes a specified number of sentences containing conjugated verbs
	//"free verse" is poetry that has neither rhyme nor meter
	public static String writeFreeVerseVerbs(int lines){
		String freeVerse = "";
		for(int i = 0; i < lines; i++)
			freeVerse = freeVerse + grammar.makeSentence() + "\n";
		return freeVerse;

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
			poem = poem + writeTwoRhymingIambicLinesFreely(iambs) + "\n";

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


	public static String writeTwoRhymingIambicLinesSmart(int iambs){
		String line = "";
		String w1 = moby.getWord("", "01", "", true);
		line += w1 + " ";
		String w2 = moby.getSmartWord("", "01", "", true, w1);
		line += w2 + " ";
		String w3 =  moby.getSmartWord("", "1", "", true, w2);
		line += w3 + " ";
		String w4 = moby.getSmartWord("", "10", "", true, w3);
		line += w4 + " ";
		String w5 = moby.getSmartWord("", "1", "", true, w4);
		line += w5 + " ";
		String w6 = moby.getSmartWord("", "1", "", true, w5);
		line += "the " + w6 + "\n";
		String w7 = moby.getSmartWord("", "0101", "", true, w6);
		line += w7 + " ";
		String w8 = moby.getSmartWord("", "01", "", true, w7);
		line += w8 + " ";
		String w9 = moby.getSmartWord("", "010", "", true, w8);
		line += w9 + " ";
		String rhyme = moby.getRhymePhones(w6);
		String w10 = moby.getSmartWord("", "1", rhyme, true, w9);
		line += w10 + "\n";


		return line;
	}
	
	
	/*
	 * Write a totally uncontrolled bit of iambic pentameter 
	 * rhyming couplet using only the "nextWord" information
	 */
	public static String writeTwoRhymingIambicLinesFreely(int iambs){
		String line = "";
		
		// First line
		String meter = "0101010101";
		String word = moby.getWord("", meter, "", false);
		line = word + " ";
		meter = meter.substring(moby.getNumSyllables(word));
		while(meter.length() > 0){
			word = moby.getSmartWord("", meter, "", false, word);
			line = line + word + " ";
			meter = meter.substring(moby.getNumSyllables(word));
		}
		line = line + "\n";
		String rhyme = moby.getRhymePhones(word);
		
		// Second line
		meter = "0101010101";
		while(meter.length() > 0){
			word = moby.getSmartWord("", meter, "", false, word);
			String prevWord = word;
			String prevMeter = meter;
			meter = meter.substring(moby.getNumSyllables(word));
			if(0 == meter.length()){	// last word must rhyme
				word = moby.getSmartWord("", prevMeter, rhyme, true, prevWord);
			}
			line = line + word + " ";
		}
		
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


	public static String writeHaiku(String haiku)
	{
		String Haiku = haiku;
		String haikus = "";
		StringTokenizer tokenizer = new StringTokenizer(Haiku, "\n");


		String ln1 = tokenizer.nextToken();
		String ln2 = tokenizer.nextToken();
		String ln3 = tokenizer.nextToken();

		StringTokenizer tok1 = new StringTokenizer(ln1, " ");
		StringTokenizer tok2 = new StringTokenizer(ln2, " ");
		StringTokenizer tok3 = new StringTokenizer(ln3, " ");

		//String[] lin1 = new String[tok1.countTokens()];
		//String[] lin2 = new String[tok2.countTokens()];
		//String[] lin3 = new String[tok3.countTokens()];

		/*
		int ls1 = tok1.countTokens();
		int ls2 = tok2.countTokens();
		int ls3 = tok3.countTokens();
		 */

		SyllableWorker j = new SyllableWorker("SyllablesP1.txt"); 

		while(tok1.hasMoreTokens())
		{
			String l = tok1.nextToken();
			int a = l.length();	
			//System.out.println(l.contains("'s"));
			if(l.contains("'s"))
			{
				int b = 0;
				int c = a - 2;
				l = l.substring(b,c);
			}
			else if((l.charAt(a-1) == 's') && !(l.charAt(a-2) == 'e'))
			{
				int b = 0;
				int c = a - 1;
				l = l.substring(b,c);
			}
			Random rand = new Random();
			int end = rand.nextInt(40000);
			haikus += j.syllableCountSyllables(j.syllableCountWord(l), end) + " ";

			//int x = 0;
			//String z = tok1.nextToken();
			//System.out.println(z);
			//lin1[x] = z;
			//System.out.println(lin1[x]);
			//x++;
		}
		haikus += "\n";
		while(tok2.hasMoreTokens())
		{
			String l = tok2.nextToken();
			int a = l.length();	
			//System.out.println(l.contains("'s"));
			if(l.contains("'s"))
			{
				int b = 0;
				int c = a - 2;
				l = l.substring(b,c);
			}
			else if((l.charAt(a-1) == 's') && !(l.charAt(a-2) == 'e'))
			{
				int b = 0;
				int c = a - 1;
				l = l.substring(b,c);
			}
			//System.out.println("2 + " + l);
			Random rand = new Random();
			int end = rand.nextInt(40000);
			haikus += j.syllableCountSyllables(j.syllableCountWord(l), end) + " ";

			//int y = 0;
			//lin2[y] = tok2.nextToken();
			//y++;
		}
		haikus += "\n";
		while(tok3.hasMoreTokens())
		{
			String l = tok3.nextToken();
			int a = l.length();			
			if(l.contains("'s"))
			{
				int b = 0;
				int c = a - 2;
				l = l.substring(b,c);
			}
			else if((l.charAt(a-1) == 's') && !(l.charAt(a-2) == 'e'))
			{
				int b = 0;
				int c = a - 1;
				l = l.substring(b,c);
			}
			//System.out.println("3 + " + l);
			Random rand = new Random();
			int end = rand.nextInt(40000);
			haikus += j.syllableCountSyllables(j.syllableCountWord(l), end) + " ";

			//int z = 0;
			//lin3[z] = tok3.nextToken();
			//z++;
		}
		haikus += "\n";
		//System.out.println(lin1[0]);

		/*
		for(int x = 0; x < ls1; x++)
		{
			String wd = ln1W[x];
			//System.out.println(wd);

		}
		haiku += "\n";
		for(int x = 0; x < ls2; x++)
		{
			String wd = ln2W[x];

			haiku += j.syllableCountSyllables(j.syllableCountWord(wd)) + " ";
		}
		haiku += "\n";
		for(int x = 0; x < ls3; x++)
		{
			String wd = ln3W[x];

			haiku += j.syllableCountSyllables(j.syllableCountWord(wd)) + " ";
		}
		haiku += "\n";

		//return haikuFinished = constructWriteHaiku(lin1,ls1,lin2,ls2,lin3,ls3);
		 */

		return haikus;
	}


}



