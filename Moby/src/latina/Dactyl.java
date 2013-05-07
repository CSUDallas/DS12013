/*
 * XXX Run Configurations: encoding must be set to UTF-8
 * Running this file will output lines of dactylic hexameter
 * Parameters are:
 * numLimes - the number of lines to print
 * randomOrder - true yields random word order and grammar, false follows preset order
 * senDetails - true prints out long marks, meter, and iteration info
 *
 * 05/04/2013
 * Joseph Malone
 */

package latina;

import java.util.Calendar;

public class Dactyl {
	//Parameters that you should set
	public static int numLines = 10;
	private static boolean randomOrder = true;
	private static boolean senDetails = true;
	//Some other fields
	private static ServumVerbi sv;
	private static int wordCounter;
	private static Calendar rightNow;
	private static String line;
	private static int iterations;
	private static boolean haveSubj;
	private static Verbum startObj;
	private static Verbum startSubj;

	public static void main(String[] args) {
		System.out.println("You are using Dactyl.java to create lines of Latin that are in dactylic hexameter");
		//Read in files and create server
		sv = new ServumVerbi("DICTLINERAND.GEN", "InflectsMacs.txt", "LatinMacronFile.xml");

		System.out.println();
		System.out.println("Dactylic Hexameter:");
		System.out.println();
		//Get time before starting
		rightNow = Calendar.getInstance();
		int startMin = rightNow.get(Calendar.MINUTE);
		int startSec = rightNow.get(Calendar.SECOND);
		//Write numLines of dactyl hexameter
		for(int i = 0; i<numLines; i++){
			iterations=0;
			wordCounter=0;
			do {
				haveSubj = false;
				startObj = new Verbum();
				startObj.cw.wordcase = "NOM";
				startObj.cw.number = "P";
				startObj.cw.gender = "M";
				startSubj = new Verbum();
				startSubj.cw.number = "S";
				if(randomOrder){
					//This one is random in order and grammar
					line = writeRandomDactylLine();
				} else {
					//This one can be tailored to style you want with grammar, etc.
					line = writeDactylLine();
				}
				iterations++;
				//System.out.println(line.replace("*", ""));
			} while (!meterMatch(getMeter(line)));		
			if(senDetails){
				System.out.println(line.replace("*", "") + "\n" + line + "\n" + getMeter(line) + "\nWords tried: " + wordCounter + ". Lines tried: " + iterations + ". \n");
			}else{
				System.out.println(line.replace("*", "") + ".");
			}
		}
		//Get time after done and print
		rightNow = Calendar.getInstance();
		int endMin = rightNow.get(Calendar.MINUTE);
		int endSec = rightNow.get(Calendar.SECOND);
		int time = (endMin-startMin)*60 + endSec-startSec;
		System.out.println("Time elapsed (sec): " + time);
	}
	/*
	 * Randomly chooses which verbum, N, Adj, Verb, to get next
	 * Adds ending to verbum
	 * Makes sure that there is most likely a subject
	 */
	public static Verbum randomDactylWord(String s, boolean haveSubj, Verbum obj, Verbum subj){
		//Find the word that fits next with the current meter for dactyls
		Verbum w = new Verbum();
		int i = 1;
		while(i<100){
			wordCounter++;
			int rand = (int)(Math.random()*100);
			//System.out.println(rand);
			if(rand<30 && haveSubj==false){
				if(rand<15){
					w = sv.nGetTerminus(sv.getWord("N", 'B'), "NOM", "P");
				} else {
					w = sv.nGetTerminus(sv.getWord("N", 'B'), "NOM", "S");
				}
			} else if(rand<50){
				if(haveSubj==false){
					if(rand<60){
						w = sv.nGetTerminus(sv.getWord("N", 'B'), "NOM", "P");
					} else {
						w = sv.nGetTerminus(sv.getWord("N", 'B'), "NOM", "S");
					}
				} else if(rand<33){
					w = sv.nGetTerminus(sv.getWord("N", 'B'), "DAT", "P");
					startObj = w;
				} else if(rand<36){
					w = sv.nGetTerminus(sv.getWord("N", 'B'), "DAT", "S");
					startObj = w;
				} else if(rand<39){
					w = sv.nGetTerminus(sv.getWord("N", 'B'), "ACC", "P");
					startObj = w;
				} else if(rand<42){
					w = sv.nGetTerminus(sv.getWord("N", 'B'), "ACC", "S");
					startObj = w;
				} else if(rand<45){
					w = sv.nGetTerminus(sv.getWord("N", 'B'), "ACL", "P");
					startObj = w;
				} else {
					w = sv.nGetTerminus(sv.getWord("N", 'B'), "ABL", "S");
					startObj = w;
				} 
			} else if(rand<70){
				w = sv.adjGetTerminus(sv.getWord("ADJ", 'B'), startObj, startObj.cw.wordcase, startObj.cw.number);
			} else if(rand<90){
				if(rand<73){
					w = sv.vGetTerminus(sv.getWord("V", 'B'), 3, startSubj.cw.number, "PRES", "ACTIVE", "IND");
				} else if(rand<76){
					w = sv.vGetTerminus(sv.getWord("V", 'B'), 3, startSubj.cw.number, "FUT", "ACTIVE", "IND");
				} else if(rand<79){
					w = sv.vGetTerminus(sv.getWord("V", 'B'), 3, startSubj.cw.number, "IMPF", "ACTIVE", "IND");
				} else if(rand<82){
					w = sv.vGetTerminus(sv.getWord("V", 'B'), 3, startSubj.cw.number, "PERF", "ACTIVE", "IND");
				} else if(rand<85){
					w = sv.vGetTerminus(sv.getWord("V", 'B'), 3, startSubj.cw.number, "PLUP", "ACTIVE", "IND");
				} else {
					w = sv.vGetTerminus(sv.getWord("V", 'B'), 3, startSubj.cw.number, "FUTP", "ACTIVE", "IND");
				}
			} else {
				w = sv.getWord("INTERJ", 'Z');
				w.cw.item = w.form1;
			}
			//Makes sure w is valid word and checks meter once added to line
			if(w.cw.item!=""){
				if(getMeter(w.cw.item).length()<7){					
					if(dactylMeter(s + w.cw.item)){
						return w;
					}
				}
			}
			i++;
		}
		return w;
	}
	/*
	 * Gets a verbum by pos and adds endings 
	 */
	public static Verbum dactylWord(Verbum v, String pos, String c, String num, int p, String tense, String voice, String mood, String s){
		//Find the word that fits next with the current meter for dactyls
		Verbum w = new Verbum();
		int i = 1;
		while(i<100){
			wordCounter++;
			if(pos.equals("Na")){
				w = sv.nGetTerminus(sv.getName(), c, num);
			} else if(pos.equals("N")){
				w = sv.nGetTerminus(sv.getWord("N", 'B'), c, num);
			} else if(pos.equals("A")){
				w = sv.adjGetTerminus(sv.getWord("ADJ", 'B'), v, v.cw.wordcase, v.cw.number);
			} else if(pos.equals("V")){
				w = sv.vGetTerminus(sv.getWord("V", 'B'), p, v.cw.number, tense, voice, mood);
			}
			if(w.cw.item!=""){
				if(getMeter(w.cw.item).length()<7){					
					if(dactylMeter(s + w.cw.item)){
						return w;
					}
				}
			}
			i++;
		}
		return w;
	}
	/*
	 * To write a line with random word order and grammar
	 */
	public static String writeRandomDactylLine(){
		String Sen = "";
		int wordsInSen = 5;
		for(int i=0; i<wordsInSen; i++){
			Sen = Sen + randomDactylWord(Sen, haveSubj, startObj, startSubj).cw.item + " ";
		}
		Sen = Sen.trim();
		return Sen;
	}
	/*
	 * Predefines the word order and grammar
	 */
	public static String writeDactylLine(){
		Verbum dummy = new Verbum();
		String K = "";
		Verbum subject = dactylWord(dummy, "N", "NOM", "P", 0, "", "", "", K);
		K = K + subject.cw.item + " ";
		Verbum object = dactylWord(dummy, "N", "ACC", "S", 0, "", "", "", K);
		K = K + object.cw.item + " ";
		Verbum adj = dactylWord(object, "A", "ACC", "S", 0, "", "", "", K);
		K = K + adj.cw.item + " ";
		Verbum otherWord = dactylWord(dummy, "N", "ABL", "S", 0, "", "", "", K);
		K = K + otherWord.cw.item + " ";
		Verbum verb= dactylWord(subject, "V", "", subject.cw.number, 3, "FUT", "ACTIVE", "IND", K);
		K = K + verb.cw.item;
		return K;
	}
	/*
	 * The various meter strings for dactylic hexameter
	 * The final sentence must match one of these variations
	 */
	public static boolean meterMatch(String s){
		//Dactylic Hexameter - some possibilities - there are more
		//Foot is either 1 1 or 1 0 0 - ends in 1 0 0 1 1
		if(s.equals(" 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 1 1 0 0 1 0 0 1 0 0 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 1 1 1 1 0 0 1 0 0 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 1 1 1 1 1 1 0 0 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 0 0 1 1 1 1 1 1 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 1 1 0 0 1 1 1 1 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 1 1 1 1 0 0 1 1 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 1 1 0 0 1 1 1 0 0 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 1 1 0 0 1 0 0 1 1 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 0 0 1 1 1 0 0 1 1 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 0 0 1 1 1 0 0 1 0 0 1 0 0 1 1"))
			return true;
		//Foot is either 1 1 or 1 0 0 - ends in 1 0 0 1 0
		//The last syllable is long by position at the end of the line 
		//cf. Virgil
		else if(s.equals(" 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0"))
			return true;
		else if(s.equals(" 1 1 1 0 0 1 0 0 1 0 0 1 0 0 1 0"))
			return true;
		else if(s.equals(" 1 1 1 1 1 0 0 1 0 0 1 0 0 1 0"))
			return true;
		else if(s.equals(" 1 1 1 1 1 1 1 0 0 1 0 0 1 0"))
			return true;
		else if(s.equals(" 1 0 0 1 1 1 1 1 1 1 0 0 1 0"))
			return true;
		else if(s.equals(" 1 1 1 0 0 1 1 1 1 1 0 0 1 0"))
			return true;
		else if(s.equals(" 1 1 1 1 1 0 0 1 1 1 0 0 1 0"))
			return true;
		else if(s.equals(" 1 1 1 0 0 1 1 1 0 0 1 0 0 1 0"))
			return true;
		else if(s.equals(" 1 1 1 0 0 1 0 0 1 1 1 0 0 1 0"))
			return true;
		else if(s.equals(" 1 0 0 1 1 1 0 0 1 1 1 0 0 1 0"))
			return true;
		else if(s.equals(" 1 0 0 1 1 1 0 0 1 0 0 1 0 0 1 0"))
			return true;
		return false;
	}
	/*
	 * Gets the meter of a word and checks against a dactyl foot
	 * This is used when building a sentence - checking as each word is added
	 */
	public static boolean dactylMeter(String s){
		String m = getMeter(s); //Get Meter
		m = m.replace(" ", ""); //Remove spaces
		char[] syllS = m.toCharArray(); //Split into chars
		int[] syll = new int[syllS.length]; 
		boolean f = false;
		for(int i = 0; i<syllS.length; i++){ //Make into ints
			syll[i] = Character.getNumericValue(syllS[i]);
		}
		int i = 0;
		do {
			if(syll.length>2){ //Avoid out of bounds error
				if(i<syll.length-2){
					//foot option one
					if(syll[i] == 1 && syll[i+1] == 0 && syll[i+2] == 0){
						//dactyl
						f= true;
						i=i+3;
					} else if(syll[i] == 1 && syll[i+1] == 1){ //foot option two
						//dactyl
						f= true;
						i=i+2;
					} else {
						//Foot is bad here.
						return false;
					}
				} else {
					//exit
					break;
				}
			} else {
				return false;
			}
		} while(i<syll.length);
		return f;
	}
	/*
	 * Gets the meter of a string based on vowel length and position
	 * 1 is a long syllable, 0 is a short syllable
	 */
	public static String getMeter(String s){
		/*
		 * SAMPLES
		 * System.out.println(getMeter("aarpathium jucunditatem hammoenit quoque"));
		 * //a arp ath i umj uc und it at amm oen itqu oqu e
		 * //0 1   1   0 1   0  1   0  0   1   1   1    1   0
		 * System.out.println(getMeter("arma virumque cano troiae qui primus ab oris"));
		 * System.out.println(getMeter("arma virumque cano tr*iae qui pr*imus ab *or*is"));
		 * //arm av ir umqu ec an otr oi aequ ipr im us ab or is
		 * //1   0  0  1    0  0  1   1  1    1   1  0  0  1  1 
		 */
		boolean finalVowel = false;
		String m = "";
		s = s.replace(" h", "");
		//Remove vowels and such that elide
		s = elisions(s);
		s = s.replace(" ", "");
		//System.out.println(s);
		int i = 0;
		do {
			char c = s.charAt(i);
			if(c=='*'){
				m = m + " 1"; //long by nature
				i++;
			} else if(isVowel(c)){
				if(isVowel(s.charAt(i+1))){
					if(isDipthong(Character.toString(c) + Character.toString(s.charAt(i+1)))){
						m = m + " 1"; //dipthong, long
						i=i+2;
					} else {
						m = m + " 0"; //single vowel + single vowel, short
						i++;
					}			
				} else {
					if(s.length()>2){
						if(isVowel(s.charAt(i+2))){
							m = m + " 0"; //vowel + single consonant, short
							i=i+2;
						} else {
							m = m + " 1"; //vowel + double consonant, long
							i=i+3;
						}
					} else {
						m = m + " 0"; //word is only 2 chars, short
						i=i+3;
					}
				}		
			} else {
				i++; // A third consonant, do nothing
			}
			if(i>s.length()-3 && i<s.length()){ //If on the penultimate or ultimate letter
				c = s.charAt(i);
				if(c=='*'){
					m = m + " 1"; //long by nature
					i++;
				} else if(isVowel(c)){
					if(i!=s.length()-1){ //Not the ultimate letter
						if(isVowel(s.charAt(i+1))){
							if(isDipthong(Character.toString(c) + Character.toString(s.charAt(i+1)))){
								m = m + " 1"; //dipthong so long
								i=i+2;
							} else {
								m = m + " 0"; //single vowel, short
								m = m + " 0"; //final letter is vowel, short
								finalVowel = true;
								i++;
							}			
						} else {
							m = m + " 0"; //vowel + single consonant, short
							i=i+2;
						}		
					} 
				} else {
					i++; //consonant, do nothing
				}
			} 
		} while(i<s.length()-1);
		//Take care of final vowel if it hasn't been already, finalVowel ==false
		if(isVowel(s.charAt(s.length()-1)) && s.charAt(s.length()-1)!='*' && finalVowel != true) {
			m = m + " 0";
		}
		return m;
	}
	/*
	 * Simple method to check if char is vowel
	 */
	public static boolean isVowel(char ch) {
		return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'y' || ch == '*';
	}
	/*
	 * Check if string is dipthong
	 */
	public static boolean isDipthong(String s) {
		return s.equals("ae") || s.equals("au") || s.equals("ei") || s.equals("eu") || s.equals("oe"); // || s.equals("ii");
	}
	//Resolve elisions in the string - which vowels drop out
	public static String elisions(String s){
		String[] vowels = {"a", "e", "i", "o", "u"};
		//vowel + m followed by a word starting with a vowel, the vowel + m drops out, cf. acc s.
		for(String x : vowels){
			s = s.replace("am " + x, x);
			s = s.replace("em " + x, x);
			s = s.replace("im " + x, x);
			s = s.replace("um " + x, x);
		}
		//dipthong followed by a word starting with a vowel, dipthong drops out
		for(String x : vowels){
			if(!x.equals("i")){
				s = s.replace("ae " + x, x);
				s = s.replace("au " + x, x);
				s = s.replace("ei " + x, x);
				s = s.replace("eu " + x, x);
				s = s.replace("oe " + x, x);
			}
		}
		//Make long vowels (*vowel) just an asterisk which is counted as a vowel in isVowel()
		//Words ending in vowel + word beginning with a vowel - drop first vowel 
		for(String x : vowels){
			if(!x.equals("i")){
				s = s.replace("*a " + x, x);
				s = s.replace("*e " + x, x);
				s = s.replace("*i " + x, x);
				s = s.replace("*o " + x, x);
				s = s.replace("*u " + x, x);

				s = s.replace("a " + x, x);
				s = s.replace("e " + x, x);
				s = s.replace("i " + x, x);
				s = s.replace("o " + x, x);
				s = s.replace("u " + x, x);
			}
		}
		//Replace any other long vowels with asterisk 
		for(String x : vowels){
			s = s.replace("*" + x, "*");
		}
		//qu counts as a double consonant, so just change to qq 
		//so as to not have to worry about the u as a vowel
		s = s.replace("qu", "qq");
		return s;
	}
}
