package dsmoby;

public class PaulFiesel {	
	private static Moby moby = null;
	
	public static void main(String[] args) {
		moby = new Moby("cmupronRand.txt", 
				"mpos.txt",
				"flist.txt",
				"infl.txt");
		moby.setScowlThreshold(60);

		String sentence = "He walked the dog very far";
		String phoneString = moby.getAllPhones(sentence);
		String newPhoneString = changeAllPhones(phoneString);
		String newSentence = makeNewSentence(newPhoneString);
		System.out.println(newSentence);
	}
	
	/*
	 * Takes a long string of phones and changes them
	 * one-by-one into a similar-sounding string of phones.
	 */
	private static String changeAllPhones(String s){
		String returnString = "";
		
		// For loop here changing the phones one at a time
		String newPhone = "";
		returnString = returnString + newPhone;
		
		
		return returnString;
	}
	
	/*
	 * Takes as input a long string of phones
	 * Produces a sentence using those phones.
	 */
	private static String makeNewSentence(String s){
		return "";
	}
	
	/*
	 * Takes a 2-character phone string as input and
	 * returns a similar-sounding 2-character phone string.
	 */
	private static String changeOnePhone(String p){
		if (p.compareTo("S )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "S ";
			}
			else if (x < .66){
				p = "SH";
			}
			else{
				p = "B ";
			}
			return p;
		}
		if (p.compareTo("B )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "T ";
			}
			else if (x < .66){
				p = "P ";
			}
			else{
				p = "Z ";
			}
		}
		if (p.compareTo("CH)") == 0){
			double x = Math.random();
			if (x < .33){
				p = "N ";
			}
			else if (x < .66){
				p = "SH";
			}
			else{
				p = "M ";
			}
		}
		if (p.compareTo("D )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "B ";
			}
			else if (x < .66){
				p = "T ";
			}
			else{
				p = "G ";
			}
		}
		if (p.compareTo("DH)") == 0){
			double x = Math.random();
			if (x < .33){
				p = "R ";
			}
			else if (x < .66){
				p = "N ";
			}
			else{
				p = "K ";
			}
		}
		if (p.compareTo("F )")== 0){
			double x = Math.random();
			if (x < .33){
				p = "B ";
			}
			else if (x < .66){
				p = "D ";
			}
			else{
				p = "CH";
			}
		}
		if (p.compareTo("G )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "M ";
			}
			else if (x < .66){
				p = "Z ";
			}
			else{
				p = "DH";
			}
		}
		if (p.compareTo("HH)") == 0){
			double x = Math.random();
			if (x < .33){
				p = "R ";
			}
			else if (x < .66){
				p = "W ";
			}
			else{
				p = "K ";
			}
		}
		if (p.compareTo("K )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "HH";
			}
			else if (x < .66){
				p = "W ";
			}
			else{
				p = "Y ";
			}
		}
		if (p.compareTo("L )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "P ";
			}
			else if (x < .66){
				p = "SH";
			}
			else{
				p = "DH";
			}
		}
		if (p.compareTo("M )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "S ";
			}
			else if (x < .66){
				p = "N ";
			}
			else{
				p = "R ";
			}
		}
		if (p.compareTo("N )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "W ";
			}
			else if (x < .66){
				p = "L ";
			}
			else{
				p = "D ";
			}
		}
		if (p.compareTo("P )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "G ";
			}
			else if (x < .66){
				p = "L ";
			}
			else{
				p = "P ";
			}
		}
		if (p.compareTo("R )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "D ";
			}
			else if (x < .66){
				p = "F ";
			}
			else{
				p = "V ";
			}
		}
		if (p.compareTo("S )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "G ";
			}
			else if (x < .66){
				p = "V ";
			}
			else{
				p = "TH";
			}
		}
		if (p.compareTo("SH)") == 0){
			double x = Math.random();
			if (x < .33){
				p = "DH";
			}
			else if (x < .66){
				p = "B ";
			}
			else{
				p = "M ";
			}
		}
		if (p.compareTo("T )") == 0){
			double x = Math.random();
			if (x < .33){
				p = "K ";
			}
			else if (x < .66){
				p = "M ";
			}
			else{
				p = "F ";
			}
		}
		if (p.compareTo("TH)") == 0){
			double x = Math.random();
			if (x < .33){
				p = "SH";
			}
			else if (x < .66){
				p = "L ";
			}
			else{
				p = "V ";
			}
		}
		if (p.compareTo("V ") == 0){
			double x = Math.random();
			if (x < .33){
				p = "Z ";
			}
			else if (x < .66){
				p = "F ";
			}
			else{
				p = "D ";
			}
		}
		if (p.compareTo("W ") == 0){
			double x = Math.random();
			if (x < .33){
				p = "HH";
			}
			else if (x < .66){
				p = "N ";
			}
			else{
				p = "K ";
			}
		}
		if (p.compareTo("Y ") == 0){
			double x = Math.random();
			if (x < .33){
				p = "V ";
			}
			else if (x < .66){
				p = "W ";
			}
			else{
				p = "L ";
			}
		}
		if (p.compareTo("Z ") == 0){
			double x = Math.random();
			if (x < .33){
				p = "K ";
			}
			else if (x < .66){
				p = "N ";
			}
			else{
				p = "S ";
			}
		}
		return "";	// XXX this is fake --- delete when all "returns" are in there.
	}
}