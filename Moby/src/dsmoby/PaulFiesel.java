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
			if (x < .33){
				p = "Z ";
			}
			else if (x < .66){
				p = "SH";
			}
			return p;
		}
		if (p.compareTo("B ") == 0){
			double x = Math.random();
			if (x < .33){
				p = "M ";
			}
			else if (x < .66){
				p = "V ";
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
			if (x < .33){
				p = "T ";
			}
			else if (x < .66){
				p = "TH";
			}
			else{
				p = "D ";
			}
			return p;
		}
		if (p.compareTo("DH") == 0){
			double x = Math.random();
			if (x < .33){
				p = "TH";
			}
			else if (x < .66){
				p = "DH";
			}
			else{
				p = "T ";
			}
			return p;
		}
		if (p.compareTo("F ")== 0){
			double x = Math.random();
			if (x < .50){
				p = "V ";
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
			if (x < .33){
				p = "EH";
			}
			if (x < .66){
				p = "HH";
			}
			else{
				p = "EHHH";
			}
			return p;
		}
		if (p.compareTo("K ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "G ";
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
				p = "EHL ";
			}
			return p;
		}
		if (p.compareTo("M ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "M ";
			}
			else{
				p = "B ";
			}
			return p;
		}
		if (p.compareTo("N ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "N ";
			}
			else{
				p = "D ";
			}
			return p;
		}
		if (p.compareTo("P ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "F ";
			}
			else{
				p = "V ";
			}
			return p;
		}
		if (p.compareTo("R ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "R ";
			}
			else{
				p = "EHR ";
			}

			return p;
		}
		if (p.compareTo("S ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "S";
			}
			else{
				p = "Z ";
			}
			return p;
		}
		if (p.compareTo("SH") == 0){
			double x = Math.random();
			if (x < .33){
				p = "S ";
			}
			else if (x < .66){
				p = "SH";
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
				p = "D ";
			}
			return p;
		}
		if (p.compareTo("TH") == 0){
			double x = Math.random();
			if (x < .33){
				p = "DH";
			}
			else if (x < .66){
				p = "TH";
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
				p = "V ";
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
			if (x < .33){
				p = "SH";
			}
			else if (x < .66){
				p = "Z ";
			}
			else{
				p = "S ";
			}
			return p;
		}
		
		// Now the vowels
		if (p.compareTo("AA") == 0){
			double x = Math.random();
			if (x < .33){
				p = "AA";
			}
			else if (x < .66){
				p = "HHAA";
			}
			else{
				p = "AO";
			}
			return p;
		}
		
		if (p.compareTo("AE") == 0){
			double x = Math.random();
			if (x < .33){
				p = "AA";
			}
			else if (x < .66){
				p = "AE";
			}
			else{
				p = "AO";
			}
			return p;
		}
		
		if (p.compareTo("AH") == 0){
			double x = Math.random();
			if (x < .33){
				p = "AH";
			}
			else if (x < .66){
				p = "EH";
			}
			else{
				p = "OW";
			}
			return p;
		}
		
		if (p.compareTo("AO") == 0){
			double x = Math.random();
			if (x < .33){
				p = "AW";
			}
			else if (x < .66){
				p = "AO";
			}
			else{
				p = "OW";
			}
			return p;
		}
		
		if (p.compareTo("AW") == 0){
			double x = Math.random();
			if (x < .33){
				p = "AE";
			}
			else if (x < .66){
				p = "AW";
			}
			else{
				p = "AA";
			}
			return p;
		}
		
		if (p.compareTo("AY") == 0){
			double x = Math.random();
			if (x < .33){
				p = "AY";
			}
			else if (x < .66){
				p = "HHAY";
			}
			else{
				p = "OY";
			}
			return p;
		}
		
		if (p.compareTo("EH") == 0){
			double x = Math.random();
			if (x < .33){
				p = "EH";
			}
			else if (x < .66){
				p = "AE";
			}
			else{
				p = "EY";
			}
			return p;
		}
		
		if (p.compareTo("ER") == 0){
			double x = Math.random();
			if (x < .33){
				p = "ER";
			}
			else if (x < .66){
				p = "HHER";
			}
			else{
				p = "EHER";
			}
			return p;
		}
		
		if (p.compareTo("EY") == 0){
			double x = Math.random();
			if (x < .33){
				p = "EY";
			}
			else if (x < .66){
				p = "AE";
			}
			else{
				p = "EH";
			}
			return p;
		}
		
		if (p.compareTo("IH") == 0){
			double x = Math.random();
			if (x < .33){
				p = "IH";
			}
			else if (x < .66){
				p = "EH";
			}
			else{
				p = "AE";
			}
			return p;
		}
		
		if (p.compareTo("IY") == 0){
			double x = Math.random();
			if (x < .33){
				p = "IY";
			}
			else if (x < .66){
				p = "IH";
			}
			else{
				p = "EH";
			}
			return p;
		}
		
		if (p.compareTo("OW") == 0){
			double x = Math.random();
			if (x < .33){
				p = "OW";
			}
			else if (x < .66){
				p = "AO";
			}
			else{
				p = "OWHH";
			}
			return p;
		}
		
		if (p.compareTo("OY") == 0){
			double x = Math.random();
			if (x < .33){
				p = "OY";
			}
			else if (x < .66){
				p = "OWIY";
			}
			else{
				p = "HHOY";
			}
			return p;
		}
		
		if (p.compareTo("UH") == 0){
			double x = Math.random();
			if (x < .33){
				p = "UH";
			}
			else if (x < .66){
				p = "AH";
			}
			else{
				p = "AO";
			}
			return p;
		}
		
		if (p.compareTo("UW") == 0){
			double x = Math.random();
			if (x < .33){
				p = "UW";
			}
			else if (x < .66){
				p = "EHW ";
			}
			else{
				p = "ER";
			}
			return p;
		}
		
		return p;
	}
}
