/*
 * XXX Run Configurations: encoding must be set to UTF-8
 * 
 * This is a fun file. It produces a "Clerihew"... sort of
 * 
 * 05/04/2013
 * Joseph Malone
 */

package latina;

public class Clerihew {
	private static ServumVerbi sv;

	public static void main(String[] args) {
		System.out.println("You are using Decline.java to decline a word");
		sv  = new ServumVerbi("DICTLINERAND.GEN", "InflectsMacs.txt", "LatinMacronFile.xml");
		writeClerihew(false);
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

		String l1 = "\n" + sv.adjGetTerminus(adj, subject, "NOM", "S").cw.item;
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

		System.out.println(l1.replace("*", ""));
		System.out.println();
	}
}
