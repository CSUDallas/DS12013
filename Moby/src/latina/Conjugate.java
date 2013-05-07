/*
 * XXX Run Configurations: encoding must be set to UTF-8
 * Running this file will output the conjugation of a particular verbum
 * Parameters are:
 * firstPPWithEnd - the 1st s. pres act ind of the verb to be conjugated
 * voice - ACTIVE or PASSIVE
 * mood - IND or SUB
 * 
 * 05/04/2013
 * Joseph Malone
 */

package latina;

public class Conjugate {
	private static ServumVerbi sv;
	//Parameters to set
	private static String firstPPWithEnd = "video";
	private static String voice = "ACTIVE";
	private static String mood = "IND";

	public static void main(String[] args) {
		System.out.println("You are using Conjugate.java to conjugate a word");
		sv  = new ServumVerbi("DICTLINERAND.GEN", "InflectsMacs.txt", "LatinMacronFile.xml");

		//Verbum n = sv.getWord("V", 'B'); //->Declines a random word
		Verbum v = sv.findWordByNomForm(firstPPWithEnd.toLowerCase());
		String c = "\n";
		if(v!=null){
			c = c + v.macrons + "\n";		
			c = c + v.cd + " " + v.variant + "\n";		
			c = c + sv.vGetTerminus(v, 1, "S", "PRES", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "S", "PRES", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "S", "PRES", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 1, "P", "PRES", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "P", "PRES", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "P", "PRES", voice, mood).cw.item + "\n";
			c = c + "--------IMPF-----------" + "\n";
			c = c + sv.vGetTerminus(v, 1, "S", "IMPF", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "S", "IMPF", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "S", "IMPF", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 1, "P", "IMPF", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "P", "IMPF", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "P", "IMPF", voice, mood).cw.item + "\n";
			c = c + "--------FUT-----------" + "\n";
			c = c + sv.vGetTerminus(v, 1, "S", "FUT", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "S", "FUT", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "S", "FUT", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 1, "P", "FUT", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "P", "FUT", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "P", "FUT", voice, mood).cw.item + "\n";
			c = c + "--------PERF-----------" + "\n";
			c = c + sv.vGetTerminus(v, 1, "S", "PERF", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "S", "PERF", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "S", "PERF", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 1, "P", "PERF", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "P", "PERF", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "P", "PERF", voice, mood).cw.item + "\n";
			c = c + "--------PLUP-----------" + "\n";
			c = c + sv.vGetTerminus(v, 1, "S", "PLUP", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "S", "PLUP", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "S", "PLUP", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 1, "P", "PLUP", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "P", "PLUP", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "P", "PLUP", voice, mood).cw.item + "\n";
			c = c + "--------FUTP-----------" + "\n";
			c = c + sv.vGetTerminus(v, 1, "S", "FUTP", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "S", "FUTP", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "S", "FUTP", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 1, "P", "FUTP", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 2, "P", "FUTP", voice, mood).cw.item + "\n";
			c = c + sv.vGetTerminus(v, 3, "P", "FUTP", voice, mood).cw.item + "\n";
			c = c + "-------------------" + "\n";	
		} else {
			c = "Not found";
		}
		System.out.println(c);
	}
}
