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
	/*
	 * Constructor
	 */
	
	public GraceGrammar(){
		stack = new DSLinkedList<String>();
		gen = new Random();
	}

	
	/*
	 * method to generate one random sentence
	 */
	public String makeSentence(){
		stack.addLast("S");
		String sentence = "";

		while(stack.count != 0){
			String top = stack.removeLast();

			// S   --> SUB PRED
			if(top.equals("S")){
				stack.addLast("SUB");
				stack.addLast("PRED");
			}

			// SUB --> NP | NP "and" SUB
			else if(top.equals("SUB")){
				switch(gen.nextInt(2)){
				case 0:
					stack.addLast("NP");
					break;
				case 1:
					stack.addLast("NP");
					stack.addLast("and");
					stack.addLast("SUB");
					break;
				}
			}

			// N   --> "I" | "Grace" | "Cheese" | etc...
			else if(top.equals("N")){
				//String n = nounList[gen.nextInt(nounList.length)];
				stack.addLast("noun");
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
					stack.addLast("NP");
					stack.addLast("PP");
					break;
				}
			}

			// ADJ --> "good" | "bad" | "ugly"
			else if(top.equals("ADJ")){
				//String adj = adjectiveList[gen.nextInt(adjectiveList.length)];
				stack.addLast("adjective");
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
				stack.addLast("preposition");
			}

			// ART --> "a" | "an" | "the"
			else if(top.equals("ART")){
				//String art = articleList[gen.nextInt(articleList.length)];
				stack.addLast("article");
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
				stack.addLast("verb");
			}

			// ADV --> ADV | ADV | ADV | etc...
			else if(top.equals("ADV")){
				//String adv = adverbList[gen.nextInt(adverbList.length)];
				stack.addLast("adverb");
			}


			// Otherwise we have a terminal, which we prepend to the sentence
			else {
				sentence = top + " " + sentence;
			}
		}
		return sentence;
	}    
}
/*
  A VERY SIMPLE GRAMMAR
  =====================
  Variables:
  S = "Sentence" = "Start"
  SUB = "Subject"
  PRED = "Predicate"
  NP = "Noun Phrase"
  N = "Noun"
  PP = "Prepositional Phrase"
  P = "Preposition"

  Rules:
  SENT   --> SUB PRED
  SUB --> NP | NP "and" SUB
  N   --> "I" | "Grace" | "Cheese" | etc...
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