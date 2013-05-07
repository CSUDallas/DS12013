package latina;

import java.util.ArrayList;
import java.util.Calendar;

/*
 * PART_OF_SPEECH_TYPE
          N,         --  Noun
          ADJ,       --  ADJective
          NUM,       --  NUMeral
          ADV,       --  ADVerb
          V,         --  Verb
          VPAR,      --  Verb PARticiple
          PREP,      --  PREPosition
          CONJ,      --  CONJunction
          INTERJ,    --  INTERJection
          /
 */

/*
 * XXX
 * XXX 
 * XXX
 * XXX 
 * This file contains old methods and functionality that has been 
 * updated in the Dactyl, Decline, and Conjugate classes
 * ALL THINGS HERE ARE OLD VERSIONS
 * and are NOT MEANT TO BE RUN
 * TEST VERSIONS ONLY
 * XXX
 * XXX 
 * XXX
 * XXX 
 */



// * TEST VERSIONS ONLY *
public class LatinPoet {
	private static ServumVerbi sv;
	private static int counter;
	private static Calendar rightNow;
	public static void main(String[] args) {
		sv = new ServumVerbi("DICTLINERAND.GEN",
				"InflectsMacs.txt", "LatinMacronFile.xml");

		System.out.println();
		System.out.println();
		System.out.println("Dactylic Hexameter:");
		rightNow = Calendar.getInstance();
		int startMin = rightNow.get(rightNow.MINUTE);
		int startSec = rightNow.get(rightNow.SECOND);
		writeDactylLine();
		writeDactylLine();
		writeDactylLine();
		writeDactylLine();
		//writeDactylLine();
		rightNow = Calendar.getInstance();
		int endMin = rightNow.get(rightNow.MINUTE);
		int endSec = rightNow.get(rightNow.SECOND);
		int time = (endMin-startMin)*60 + endSec-startSec;
		System.out.println("Time elapsed (sec): " + time);
		//System.out.println("\"Clerihew\":");
		//writeClerihew(false);
		writeLine(false);
		//System.out.println("-------------");
		//writeLine(false);
		///System.out.println("-------------");
		//writeLine(false);
		////System.out.println("-------------");
		//writeLine(false);
		//System.out.println("-------------");
		//writeLine(false);
		//conjugate("ACTIVE", "IND");
		 //decline();
		// XXX I moved "testGetNom()" into the constructor of the sv, since we
		// XXX need it before we read the macron file, also in the constructor.
		//sv.testGetNom();
		//String st = "abcd";
		//st = new StringBuffer(st).insert(2, "C").toString();
		//System.out.println(st);
		//System.out.println(getMeter("arma virumque cano troiae qui pr*imus ab *or*is"));
		//arm av ir umqu ec an otr oi aequ ipr im us ab or is
		//1   0  0  1    0  0  1   1  1    1   1  0  0  1  1 
		//System.out.println(getMeter("arma virumque cano tr*iae qui pr*imus ab *or*is"));
		//arm av ir umqu ec an otr oi aequ ipr im us ab or is
		//1   0  0  1    0  0  1   1  1    1   1  0  0  1  1 
		System.out.println(sv.findWordByNomForm("venio").form1 + " " + sv.findWordByNomForm("venio").form2 + " " + sv.findWordByNomForm("venio").form3 + " " + sv.findWordByNomForm("venio").form4);
		System.out.println(sv.findWordByNomForm("amo").form1 + " " + sv.findWordByNomForm("amo").form2 + " " + sv.findWordByNomForm("amo").form3 + " " + sv.findWordByNomForm("amo").form4);
		System.out.println(sv.findWordByNomForm("laudo").form1 + " " + sv.findWordByNomForm("laudo").form2 + " " + sv.findWordByNomForm("laudo").form3 + " " + sv.findWordByNomForm("laudo").form4);
		
		//sv.testThings();
	}

	public static Verbum randDactylWord(Verbum v, String pos, String c, String num, int p, String tense, String voice, String mood, String s){
		//Find the word that fits next with the current meter for dactyls
		Verbum w = new Verbum();
		int i = 1;
		while(i<100){
			counter++;
			int rand = (int)(Math.random()*10);
			
			if(rand<4){
				if(rand<2){
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
			counter++;
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
	
	public static void writeDactylLine(){
		Verbum dummy = new Verbum();
		String K = "";
		Verbum sub2 = randDactylWord(dummy, "N", "NOM", "P", 0, "", "", "", K);
		K = K + sub2.cw.item + " ";
		Verbum ob2 = randDactylWord(dummy, "N", "ACC", "S", 0, "", "", "", K);
		K = K + ob2.cw.item + " ";
		//Verbum adj = randDactylWord(ob2, "A", "ACC", "S", 0, "", "", "", K);
		//K = K + adj.cw.item + " ";
		Verbum adj4 = randDactylWord(ob2, "A", "ACC", "S", 0, "", "", "", K);
		K = K + adj4.cw.item + " ";
		Verbum ob3 = randDactylWord(dummy, "A", "ABL", "S", 0, "", "", "", K);
		K = K + ob3.cw.item + " ";
		Verbum adj3 = randDactylWord(ob3, "A", "ABL", "S", 0, "", "", "", K);
		K = K + adj3.cw.item + " ";
		Verbum v2= randDactylWord(sub2, "V", "", "P", 3, "PERF", "ACTIVE", "IND", K);
		K = K + v2.cw.item;
		if(!meterMatch(getMeter(K))){ //dactylMeter(K)
			if(counter>40000){
				//System.out.println("+40000");
			}
			writeDactylLine();
		} else {
			String K2 = K.replace("*", "") + "\n" + K + "\n" + getMeter(K) + "\n" + counter + "\n";
			System.out.println(K2);
			System.out.println(sub2.cw.number);
			counter=0;
		}
	}
	
	public static void writeLine(boolean extra){
		//System.out.print(test("Na", "NOM", "S", 0, "", "", "").cw.item);
		//System.out.print(test("A", "ACC", "S", 0, "", "", "").cw.item);
		///System.out.print(test("N", "ACC", "S", 0, "", "", "").cw.item);
		//System.out.println(test("V", "", "S", 3, "PRES", "ACTIVE", "IND").cw.item);
		//Verbum sub = test("N", "NOM", "S", 0, "", "", "");
		//Verbum adj2 = test("A", "ACC", "S", 0, "", "", "");
		//Verbum ob = test("N", "ACC", "S", 0, "", "", "");
		//Verbum v = test("V", "", "P", 3, "PRES", "ACTIVE", "IND");
		//sub.cw.meter = getMeter(sub.cw.item);

		//String T = sub.cw.item + " " + adj2.cw.item + " " + ob.cw.item + " " + v.cw.item;


		//String T2 = T + "\n" + getMeter(T) + "\n";
		//T2 = T2 + analyzeMeter(T);
		//System.out.println(T2);
		//System.out.println("//-------------------------------------//");
		//-------------------------------------//
		

		Verbum subject = sv.nGetTerminus(sv.getName(), "NOM", "S");

		Verbum object = sv.nGetTerminus(sv.getWord("N", 'B'), "ACC", "S");
		Verbum adj = sv.adjGetTerminus(sv.getWord("ADJ", 'B'), object, "ACC", "S");
		Verbum verb = sv.vGetTerminus(sv.getWord("V", 'B'), 3, "S", "FUT", "ACTIVE", "IND");
		Verbum adv = sv.getWord("ADV", 'B');

		ArrayList<Verbum> Verbi = new ArrayList<Verbum>();
		Verbi.add(subject);
		Verbi.add(adj);
		Verbi.add(object);
		Verbi.add(verb);
		String output = "";
		String S = "";
		for(Verbum x : Verbi){
			S = S + " " + x.cw.item;	
		}

		output = S + "\n";
		//output = output + getMeter(subject.cw.item) + "\n";
		output = output + getMeter(S) + "\n";
		//output = output + analyzeMeter(S);
		//S = subject.cw.item;
		//S = S + " " + sv.nGetTerminus(adj, "ACC", "S");
		//S = S + " " + adj.cw.item;
		//S = S + " " + object.cw.item;
		//S = S + " " + verb.cw.item;
		//S = S + " " + adv.form1;
		System.out.println(output);
		System.out.println();
		if(extra){
			System.out.println("Meter: " + getMeter(S));
			//System.out.println("\'Meaning\': " + subject.meaning() + " ~ " + adj.meaning() + " ~ " + object.meaning() + " ~ " + verb.meaning() + " ~ " + adv.meaning());
			System.out.println();
		}
	}

	public static void writeClerihew(boolean extra){
		/*
			Sir Christopher Wren
			Said, "I am going to dine with some men.
			If anyone calls
			Say I am designing St. Paul's."
		 */
		/*
		 * Adjective Name
		 * Verb Object
		 * Conj	Noun Verb
		 * Verb Object
		 */
		Verbum adj = sv.getWord("ADJ", 'B');
		Verbum subject = sv.getName();
		Verbum v1 = sv.getWord("V", 'B');
		Verbum object1 = sv.getWord("N", 'B');
		Verbum conj = sv.getWord("CONJ", 'Z');
		Verbum n = sv.getWord("N", 'B');
		Verbum v2 = sv.getWord("V", 'B');
		Verbum conj2 = sv.getWord("CONJ", 'Z');
		Verbum v3 = sv.getWord("V", 'B');
		Verbum object2 = sv.getWord("N", 'B');

		String l1 = sv.adjGetTerminus(adj, subject, "NOM", "S").cw.item;
		l1 += " " + sv.nGetTerminus(subject, "NOM", "S").cw.item;
		l1 += "\n";
		l1 += sv.vGetTerminus(v1, 3, "S", "PRES", "ACTIVE", "IND").cw.item;
		l1 += " " + sv.nGetTerminus(object1, "ACC", "P").cw.item;
		l1 += "\n";
		l1 += conj.form1;
		l1 += " " + sv.nGetTerminus(n, "NOM", "P").cw.item;
		l1 += " " + sv.vGetTerminus(v2, 3, "P", "FUT", "ACTIVE", "IND").cw.item;
		l1 += "\n";
		l1 += conj2.form1;
		l1 += " " + sv.vGetTerminus(v3, 3, "P", "FUT", "ACTIVE", "IND").cw.item;
		l1 += " " + sv.nGetTerminus(object2, "ACC", "P").cw.item;

		System.out.println(l1);
		System.out.println();
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
						//if(s.charAt(i+1)=='q' && s.charAt(i+2)=='u'){
						//	m = m + " 1"; //vowel + double consonant, long
						///	i=i+3;
						//} //else if(s.charAt(i+2)=='q' && s.charAt(i+3)=='u'){
						//	m = m + " 1"; //vowel + consonant + double consonant, long
						//	i=i+4;
						//}
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
					//m = m + " 0"; //final letter is vowel, short
					//For now
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
		return s.equals("ae") || s.equals("au") || s.equals("ei") || s.equals("eu") || s.equals("oe") || s.equals("ui") || s.equals("ii");
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

	public static void conjugate(String voice, String mood){
		Verbum w = sv.getWord("V", 'Z');//sv.findWordByNomForm("venio");
		System.out.println();	
		System.out.println(w.macrons);		
		System.out.println(w.cd + " " + w.variant);		
		System.out.println(sv.vGetTerminus(w, 1, "S", "PRES", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "S", "PRES", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "S", "PRES", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 1, "P", "PRES", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "P", "PRES", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "P", "PRES", voice, mood).cw.item);
		System.out.println("--------IMPF-----------");
		System.out.println(sv.vGetTerminus(w, 1, "S", "IMPF", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "S", "IMPF", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "S", "IMPF", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 1, "P", "IMPF", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "P", "IMPF", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "P", "IMPF", voice, mood).cw.item);
		System.out.println("--------FUT-----------");
		System.out.println(sv.vGetTerminus(w, 1, "S", "FUT", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "S", "FUT", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "S", "FUT", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 1, "P", "FUT", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "P", "FUT", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "P", "FUT", voice, mood).cw.item);
		System.out.println("--------PERF-----------");
		System.out.println(sv.vGetTerminus(w, 1, "S", "PERF", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "S", "PERF", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "S", "PERF", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 1, "P", "PERF", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "P", "PERF", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "P", "PERF", voice, mood).cw.item);
		System.out.println("--------PLUP-----------");
		System.out.println(sv.vGetTerminus(w, 1, "S", "PLUP", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "S", "PLUP", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "S", "PLUP", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 1, "P", "PLUP", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "P", "PLUP", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "P", "PLUP", voice, mood).cw.item);
		System.out.println("--------FUTP-----------");
		System.out.println(sv.vGetTerminus(w, 1, "S", "FUTP", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "S", "FUTP", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "S", "FUTP", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 1, "P", "FUTP", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 2, "P", "FUTP", voice, mood).cw.item);
		System.out.println(sv.vGetTerminus(w, 3, "P", "FUTP", voice, mood).cw.item);
		System.out.println("-------------------");
		System.out.println();
	}
	public static void decline(){
		Verbum n = sv.getWord("N", 'B');
		System.out.println(n.macrons);
		System.out.println(n.cd + " " + n.variant + " " + n.gender + " " + n.attribs.charAt(3));
		System.out.println(sv.nGetTerminus(n, "NOM", "S").cw.item);
		System.out.println(sv.nGetTerminus(n, "GEN", "S").cw.item);
		System.out.println(sv.nGetTerminus(n, "DAT", "S").cw.item);
		System.out.println(sv.nGetTerminus(n, "ACC", "S").cw.item);
		System.out.println(sv.nGetTerminus(n, "ABL", "S").cw.item);
		System.out.println(sv.nGetTerminus(n, "VOC", "S").cw.item);
		System.out.println("--------PL-----------");
		System.out.println(sv.nGetTerminus(n, "NOM", "P").cw.item);
		System.out.println(sv.nGetTerminus(n, "GEN", "P").cw.item);
		System.out.println(sv.nGetTerminus(n, "DAT", "P").cw.item);
		System.out.println(sv.nGetTerminus(n, "ACC", "P").cw.item);
		System.out.println(sv.nGetTerminus(n, "ABL", "P").cw.item);
		System.out.println(sv.nGetTerminus(n, "VOC", "P").cw.item);
	}
}
