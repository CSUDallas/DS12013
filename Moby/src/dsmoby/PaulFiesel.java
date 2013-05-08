package dsmoby;

public class PaulFiesel {	
	private static Moby moby = null;
	
	public static void main(String[] args) {
		moby = new Moby("cmupronRand.txt", 
				"mpos.txt",
				"flist.txt",
				"infl.txt");
		moby.setScowlThreshold(60);

		String sentence =  "I love to play basketball on hot days with the sun up high and my jeans down low.";
		String phoneString = moby.getAllPhones(sentence);
		String newPhoneString = changeAllPhones(phoneString);
		String newSentence = BuildNewSentence(newPhoneString);
		while(newSentence == null){
			newPhoneString = changeAllPhones(phoneString);
			newSentence = BuildNewSentence(newPhoneString);
		}
		//w.BuildNewSentence(___);
		
		//System.out.println(newPhoneString);
		System.out.println(newSentence);
	}

	/*
	 * Takes a long string of phones and changes them
	 * one-by-one into a similar-sounding string of phones.
	 */
	private static String changeAllPhones(String s){
		String returnString = "";
		for(int i = 0; i < s.length(); i += 2){
			// For loop here changing the phones one at a time
			returnString += changeOnePhone(s.substring(i,i+2));
		}
		return returnString;
	}

	private static String BuildNewSentence(String ph){
		String newph = "";
		while(ph.length() > 0){
			String w = moby.findWordWithPhones(ph);
			
			ph = ph.substring(moby.getAllPhones(w).length(), ph.length());
			newph += w + " ";
			
			return newph;
		}
		return null;
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
		if (p.compareTo("S ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "Z ";
			}
			else if (x < .50){
				p = "SH";
			}
			return p;
		}
		if (p.compareTo("B ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "M ";
			}
			else{
				p = "B ";
			}
			return p;
		}
		if (p.compareTo("CH") == 0){
			double x = Math.random();
			if (x < .50){
				p = "CH";
			}
			else{
				p = "SH";
			}
			return p;
		}
		if (p.compareTo("D ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "B ";
			}
			else{
				p = "D ";
			}
			return p;
		}
		if (p.compareTo("DH") == 0){
			double x = Math.random();
			if (x < .50){
				p = "B ";
			}
			else{
				p = "T ";
			}
			return p;
		}
		if (p.compareTo("F ")== 0){
			double x = Math.random();
			if (x < .50){
				p = "P ";
			}
			else{
				p = "F ";
			}
			return p;
		}
		if (p.compareTo("G ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "K ";
			}
			else{
				p = "G";
			}
			return p;
		}
		if (p.compareTo("HH") == 0){
			double x = Math.random();
			if (x < .25){
				p = "SH";
			}
			if (x < .75){
				p = "HH";
			}
			else{
				p = "CH";
			}
			return p;
		}
		if (p.compareTo("K ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "T ";
			}
			else{
				p = "K ";
			}
			return p;
		}
		if (p.compareTo("L ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "L ";
			}
			else{
				p = "Y ";
			}
			return p;
		}
		if (p.compareTo("M ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "M ";
			}
			else{
				p = "N ";
			}
			return p;
		}
		if (p.compareTo("N ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "N ";
			}
			else{
				p = "M ";
			}
			return p;
		}
		if (p.compareTo("P ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "F ";
			}
			else{
				p = "T ";
			}
			return p;
		}
		if (p.compareTo("R ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "P ";
			}
			else{
				p = "M ";
			}

			return p;
		}
		if (p.compareTo("S ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "S";
			}
			else{
				p = "T ";
			}
			return p;
		}
		if (p.compareTo("SH") == 0){
			double x = Math.random();
			if (x < .50){
				p = "S ";
			}
			else{
				p = "TH";
			}
			return p;
		}
		if (p.compareTo("T ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "T ";
			}
			else{
				p = "P ";
			}
			return p;
		}
		if (p.compareTo("TH") == 0){
			double x = Math.random();
			if (x < .50){
				p = "SH";
			}
			else{
				p = "T ";
			}
			return p;
		}
		if (p.compareTo("V ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "F ";
			}
			else{
				p = "W ";
			}
			return p;
		}
		if (p.compareTo("W ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "V ";
			}
			else{
				p = "W ";
			}
			return p;
		}
		if (p.compareTo("Y ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "V ";
			}
			else{
				p = "W ";
			}

			return p;
		}
		if (p.compareTo("Z ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "SH";
			}
			else{
				p = "S ";
			}
			return p;
		}
		return p;
	}
}