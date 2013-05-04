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
		for(int i = 0; i < s.length(); i += 2){
			// For loop here changing the phones one at a time
			returnString += changeOnePhone(s.substring(i,i+2));
		}
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
				p = "P ";
			}
			else{
				p = "D ";
			}
			return p;
		}
		if (p.compareTo("CH") == 0){
			double x = Math.random();
			if (x < .50){
				p = "TH";
			}
			else{
				p = "SH";
			}
			return p;
		}
		if (p.compareTo("D ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "P ";
			}
			else{
				p = "K ";
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
				p = "D ";
			}
			return p;
		}
		if (p.compareTo("G ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "K ";
			}
			else{
				p = "CH";
			}
			return p;
		}
		if (p.compareTo("HH") == 0){
			double x = Math.random();
			if (x < .50){
				p = "SH";
			}
			else{
				p = "CH";
			}
			return p;
		}
		if (p.compareTo("K ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "G ";
			}
			else{
				p = "N ";
			}
			return p;
		}
		if (p.compareTo("L ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "P ";
			}
			else{
				p = "Y ";
			}
			return p;
		}
		if (p.compareTo("M ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "S ";
			}
			else{
				p = "N ";
			}
			return p;
		}
		if (p.compareTo("N ") == 0){
			double x = Math.random();
			if (x < .50){
				p = "S ";
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
				p = "SH";
			}
			else{
				p = "Z ";
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
				p = "K ";
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
				p = "M ";
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