package dsmoby;

import java.util.Random;

/*
 * Grammar for English
 *
 * Really just to demonstrate stacks
 *
 * In our grammar, all terminals and variables
 * are strings, but the variables will be
 * "special" strings, like P, PP, SUB, ...
 */

public class GraceGrammar{
	DSLinkedList<String> stack;
	Random gen;
	private static Moby moby;
	DSBinaryTree<MobyWord> words;
	DSLinkedList<MobyWord> wordsList;
	/*
	 * Constructor
	 */

	public GraceGrammar(){
		stack = new DSLinkedList<String>();
		gen = new Random();
		//printout duplicates. sorry.
		moby = new Moby("cmupronRand.txt", 
				"mpos.txt",
				"flist.txt",
				"infl.txt");
		moby.setScowlThreshold(60);
	}


	/*
	 * method to generate one sentence in the present tense
	 */
	public String makeSentence(){
		stack.addLast("S");
		String sentence = "";
		String pMark = moby.punctuation();

		while(stack.count != 0){
			//stack.printList();		//debugging stack
			String top = stack.removeLast();

			// S --> SUB PRED
			if(top.equals("S")){
				stack.addLast("SUB");
				stack.addLast("PRED");
			}

			// SUB --> NP | NP "and" SUB
			else if(top.equals("SUB")){
				stack.addLast("NP");
			}

			// N --> "I" | "Grace" | "Cheese" | etc...
			else if(top.equals("N")){
				//String n = nounList[gen.nextInt(nounList.length)];
				stack.addLast(moby.getWord("N", " ", "N T ", false));
				//stack.addLast("(noun)");
			}


			// NP --> ART ADJ N | ART N | NP PP
			else if(top.equals("NP")){
				switch(gen.nextInt(3)){
				case 0:
					stack.addLast("ART");
					stack.addLast("ADJ");
					stack.addLast("N");
					break;
				case 1:
					stack.addLast("ART");
					stack.addLast("N");
					break;
				case 2:
					stack.addLast("N");
					stack.addLast("and");
					stack.addLast("N");
					break;
				}
			}

			// ADJ --> "good" | "bad" | "ugly"
			else if(top.equals("ADJ")){
				//String adj = adjectiveList[gen.nextInt(adjectiveList.length)];
				stack.addLast(moby.getWord("A", "010", "", false));
				//stack.addLast("adj");
			}

			// PP --> P ART N
			else if(top.equals("PP")){
				stack.addLast("P");
				stack.addLast("ART");
				stack.addLast("N");
			}

			// P --> "of" | "in" | etc...
			else if(top.equals("P")){
				//String p = prepositionList[gen.nextInt(prepositionList.length)];
				stack.addLast(moby.getWord("P", "01", "", false));
				//stack.addLast("prep");
			}

			// ART --> "a" | "an" | "the"
			else if(top.equals("ART")){
				//String art = articleList[gen.nextInt(articleList.length)];
				switch(gen.nextInt(2)){
				case 0:
					stack.addLast(moby.getWord("D", "01", "", false));
					//stack.addLast("DefArt");
					break;
				case 2:
					stack.addLast(moby.getWord("I", "10", "", false));
					//stack.addLast("InDefArt");
					break;
				}

			}

			// PRED --> VP | V OBJ
			else if(top.equals("PRED")){
				switch(gen.nextInt(2)){
				case 0:
					stack.addLast("VP");
					break;
				case 1:
					stack.addLast("V");
					stack.addLast("OBJ");
					break;
				}
			}

			// VP --> V | V OBJ | ADV VP
			else if(top.equals("VP")){
				switch(gen.nextInt(3)){
				case 0:
					stack.addLast("V");
					break;
				case 1:
					stack.addLast("V");
					stack.addLast("OBJ");
					break;
				case 2:
					stack.addLast("ADV");
					stack.addLast("VP");
					break;
				}
			}

			// OBJ --> NP
			else if(top.equals("OBJ")){
				stack.addLast("NP");
			}

			// V --> "likes" | "eats" | "runs" | etc...
			else if(top.equals("V")){
				//String v = verbList[gen.nextInt(verbList.length)];
				switch(gen.nextInt(9)){
				case 0:
					stack.addLast(moby.getPSVerb("V", "01", "", false));	//Present tense singular
					break;
				case 1:
					stack.addLast(moby.getPPVerb("V", "01", "", false));	//present tense plural
					break;
				case 2:
					stack.addLast(moby.getFVerb("V", "01", "", false));	//future tense
					break;
				case 3:
					stack.addLast(moby.getPastVerb("V", "0101", "", false));	//past tense
					break;
				case 4:
					stack.addLast(moby.getPrPVerb("V", "01", "", false));	//present perfect tense
					break;
				case 5:
					stack.addLast(moby.getPaPVerb("V", "10", "", false));	//past perfect tense
					break;
				case 6:
					stack.addLast(moby.getFuPVerb("V", "01", "", false));	//future perfect tense
					break;
				case 7:
					stack.addLast(moby.getPaPerProVerb("V", "10", "", false));	//past perfect progressive tense
					break;
				case 8:
					stack.addLast(moby.getFuPerProVerb("V", "01", "", false));	//future perfect progressive tense
					break;


				}
			}

			// ADV --> ADV | ADV | ADV | etc...
			else if(top.equals("ADV")){
				//String adv = adverbList[gen.nextInt(adverbList.length)];
				stack.addLast(moby.getWord("v", "10", "", false));
				//stack.addLast("adv");
			}


			// Otherwise we have a terminal, which we prepend to the sentence
			else {
				sentence = " " + top + sentence;
			}
		}
		
		return sentence;
//		return sentence + pMark;
//		return sentence + "!";
//		return sentence + ".";
//		return sentence + "...";
//		return sentence + "?";
//		return sentence + " - ";
	}



}


/*
A NOT-SO-SIMPLE-GRAMMAR
=====================
Variables:
S = "Sentence" = "Start"
SUB = "Subject"
PRED = "Predicate"
NP = "Noun Phrase"
N = "Noun"
PP = "Prepositional Phrase"
P = "Preposition"
PSVerb = present tense, singular subject. .sForm
PPVerb = present tense, plural subject. word
FSVerb = future tense, singular subject. "will" + .sForm
FPVerb = future tense, plural subject. "will" + word
PastVerb = past tense. .pastForm
PrPVerb = present perfect tense. "have" + .pastForm
PaPVerb = past perfect tense. "had" + .pastForm
FuPVerb = future perfect tense. "will have" + .pastForm
present progressive will prob. be hard coded or implemented later
PaPerProVerb = past perfect progressive tense. "had been" + .ingForm
FuPerProVerb = future perfect progressive tense. "will have been" + .ingForm


Rules:
SENT --> SUB PRED
SUB --> NP | NP "and" SUB
N --> "I" | "Grace" | "Cheese" | etc...
NP --> ART ADJ N | ART N | NP PP
ADJ --> "good" | "bad" | "ugly"
PP --> P ART N
P --> "of" | "in" | etc...
ART --> "a" | "an" | "the"
PRED --> VP | V OBJ
VP --> V | V OBJ | ADV VP
OBJ --> NP
V --> "likes" | "eats" | "runs" | etc...
*/
