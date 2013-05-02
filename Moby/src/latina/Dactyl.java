package latina;

import java.util.ArrayList;
import java.util.Calendar;

public class Dactyl {
	private static ServumVerbi sv;
	private static int wordCounter;
	private static Calendar rightNow;
	public static String line;
	public static int iterations;
	public static int numLines = 1;

	public static void main(String[] args) {
		sv = new ServumVerbi("DICTLINERAND.GEN",
				"InflectsMacs.txt", "LatinMacronFile.xml");

		System.out.println();
		System.out.println("Dactylic Hexameter:");
		System.out.println();
		rightNow = Calendar.getInstance();
		int startMin = rightNow.get(rightNow.MINUTE);
		int startSec = rightNow.get(rightNow.SECOND);

		for(int i = 0; i<numLines; i++){
			iterations=0;
			wordCounter=0;
			do {
				line = writeDactylLine();
				iterations++;
			} while (!meterMatch(getMeter(line)));		
			System.out.println(line.replace("*", "") + "\n" + line + "\n" + getMeter(line) + "\nWords tried: " + wordCounter + ". Lines tried: " + iterations + ". \n");
			//System.out.println(line.replace("*", ""));
		}

		rightNow = Calendar.getInstance();
		int endMin = rightNow.get(rightNow.MINUTE);
		int endSec = rightNow.get(rightNow.SECOND);
		int time = (endMin-startMin)*60 + endSec-startSec;
		System.out.println("Time elapsed (sec): " + time);
		//System.out.println(getMeter("arma virumque cano troiae qui pr*imus ab *or*is"));
		//arm av ir umqu ec an otr oi aequ ipr im us ab or is
		//1   0  0  1    0  0  1   1  1    1   1  0  0  1  1 
		//System.out.println(getMeter("arma virumque cano tr*iae qui pr*imus ab *or*is"));
		//arm av ir umqu ec an otr oi aequ ipr im us ab or is
		//1   0  0  1    0  0  1   1  1    1   1  0  0  1  1 
	}

	public static Verbum randDactylWord(Verbum v, String pos, String c, String num, int p, String tense, String voice, String mood, String s){
		//Find the word that fits next with the current meter for dactyls
		Verbum w = new Verbum();
		int i = 1;
		while(i<100){
			wordCounter++;
			int rand = (int)(Math.random()*10);

			if(rand<4){
				if(rand<1){
					w = sv.nGetTerminus(sv.getWord("N", 'B'), c, "P");
				} else {
					w = sv.nGetTerminus(sv.getWord("N", 'B'), c, "S");
				}
			} else if(4<rand && rand<7){
				w = sv.adjGetTerminus(sv.getWord("ADJ", 'B'), v, v.cw.wordcase, v.cw.number);
			} else {
				w = sv.vGetTerminus(sv.getWord("V", 'B'), p, v.cw.number, tense, voice, mood);
			}
			if(w.cw.item!=""){//!w.cw.item.contains("MALUM")){
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
				w = sv.adjGetTerminus(sv.getWord("ADJ", 'B'), v, c, num);
			} else if(pos.equals("V")){
				w = sv.vGetTerminus(sv.getWord("V", 'B'), p, num, tense, voice, mood);
			}
			if(!w.cw.item.contains("MALUM")){
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
	public static boolean meterMatch(String s){
		//Dactylic Hexameter
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
		else if(s.equals(" 1 1 1 0 0 1 0 0 1 0 0 1 1 1 1"))
			return true;
		else if(s.equals(" 1 1 1 0 0 1 0 0 1 1 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 0 0 1 1 1 0 0 1 1 1 0 0 1 1"))
			return true;
		else if(s.equals(" 1 0 0 1 1 1 0 0 1 0 0 1 0 0 1 1"))
			return true;
		return false;
	}

	public static String writeDactylLine(){
		Verbum dummy = new Verbum();
		String K = "";
		Verbum sub2 = randDactylWord(dummy, "N", "NOM", "P", 0, "", "", "", K);
		K = K + sub2.cw.item + " ";
		Verbum ob2 = randDactylWord(dummy, "N", "ACC", "S", 0, "", "", "", K);
		K = K + ob2.cw.item + " ";
		//Verbum adj = randDactylWord(ob2, "A", "ACC", "S", 0, "", "", "", K);
		//K = K + adj.cw.item + " ";
		//Verbum adj4 = randDactylWord(ob2, "A", "ACC", "S", 0, "", "", "", K);
		//K = K + adj4.cw.item + " ";
		Verbum ob3 = randDactylWord(dummy, "A", "ABL", "S", 0, "", "", "", K);
		K = K + ob3.cw.item + " ";
		Verbum adj3 = randDactylWord(ob3, "A", "ABL", "S", 0, "", "", "", K);
		K = K + adj3.cw.item + " ";
		Verbum v2= randDactylWord(sub2, "V", "", "P", 3, "PERF", "ACTIVE", "IND", K);
		K = K + v2.cw.item;

		return K;
	}

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
					if(syll[i] == 1 && syll[i+1] == 0 && syll[i+2] == 0){
						//dactyl
						f= true;
						i=i+3;
					} else if(syll[i] == 1 && syll[i+1] == 1){
						//dactyl
						f= true;
						i=i+2;
					} else {
						//Foot is bad here.
						return false;
					}
				} else {
					i=i+10;
				}
			} else {
				return false;
			}
		} while(i<syll.length);
		return f;
	}

	public static String getMeter(String s){
		/*
		 * System.out.println(getMeter("aarpathium jucunditatem hammoenit quoque"));
		 * //a arp ath i umj uc und it at amm oen itqu oqu e
		 * //0 1   1   0 1   0  1   0  0   1   1   1    1   0
		 * System.out.println(getMeter("arma virumque cano troiae qui primus ab oris"));
		 * //arm av ir umqu ec an otr oi aequ ipr im us ab or is
		 * //1   0  0  1    0  0  1   1  1    1   1  0  0  1  1 
		 */

		String m = "";
		s = s.replace(" h", "");
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
						m = m + " 0"; //single vowel, short
						i++;
					}			
				} else {
					if(s.length()>3){
						if(isVowel(s.charAt(i+2))){
							m = m + " 0"; //vowel + single consonant, short
							i=i+2;
						} else {
							m = m + " 1"; //vowel + double consonant, long
							i=i+3;
						}
					} else {
						if(s.charAt(i+1)=='q' && s.charAt(i+2)=='u'){
							m = m + " 1"; //vowel + double consonant, long
							i=i+3;
						} else if(isVowel(s.charAt(i+1))){
							m = m + " 0"; //vowel + single consonant, short
							i=i+1;
						} else {
							m = m + " 1"; //vowel + double consonant, long
							i=i+3;
						}
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
		return m;
	}
	public static boolean isVowel(char ch) {
		return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'y' || ch == '*';
	}
	public static boolean isDipthong(String s) {
		return s.equals("ae") || s.equals("au") || s.equals("ei") || s.equals("eu") || s.equals("oe"); // || s.equals("ii");
	}
	public static String elisions(String s){
		String[] vowels = {"a", "e", "i", "o", "u"};
		for(String x : vowels){
			s = s.replace("am " + x, x);
			s = s.replace("em " + x, x);
			s = s.replace("im " + x, x);
			s = s.replace("um " + x, x);
		}
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
		for(String x : vowels){
			s = s.replace("*" + x, "*");
		}
		s = s.replace("qu", "qq");
		return s;
	}
}
