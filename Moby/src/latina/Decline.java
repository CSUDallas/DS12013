/*
 * XXX Run Configurations: encoding must be set to UTF-8
 * Running this file will output the declension of a particular verbum
 * Parameters are:
 * nomFrom - the nominative form of the word to be declined
 *
 * 05/04/2013
 * Joseph Malone
 */

package latina;

public class Decline {
	private static ServumVerbi sv;
	//Parameter to set
	private static String nomForm = "caedes";

	public static void main(String[] args) {
		System.out.println("You are using Decline.java to decline a word");
		sv  = new ServumVerbi("DICTLINERAND.GEN", "InflectsMacs.txt", "LatinMacronFile.xml");

		//Verbum n = sv.getWord("N", 'B'); //->Declines a random word
		Verbum n = sv.findWordByNomForm(nomForm.toLowerCase());
		String d = "\n";
		if(n!=null){		
			d = d + n.macrons + "\n";
			d = d + n.cd + " " + n.variant + " " + n.gender + " " + n.attribs.charAt(3) + "\n";
			d = d + sv.nGetTerminus(n, "NOM", "S").cw.item + "\n";
			d = d + sv.nGetTerminus(n, "GEN", "S").cw.item + "\n";
			d = d + sv.nGetTerminus(n, "DAT", "S").cw.item + "\n";
			d = d + sv.nGetTerminus(n, "ACC", "S").cw.item + "\n";
			d = d + sv.nGetTerminus(n, "ABL", "S").cw.item + "\n";
			d = d + sv.nGetTerminus(n, "VOC", "S").cw.item + "\n";
			d = d + "--------PL-----------" + "\n";
			d = d + sv.nGetTerminus(n, "NOM", "P").cw.item + "\n";
			d = d + sv.nGetTerminus(n, "GEN", "P").cw.item + "\n";
			d = d + sv.nGetTerminus(n, "DAT", "P").cw.item + "\n";
			d = d + sv.nGetTerminus(n, "ACC", "P").cw.item + "\n";
			d = d + sv.nGetTerminus(n, "ABL", "P").cw.item + "\n";
			d = d + sv.nGetTerminus(n, "VOC", "P").cw.item + "\n";
		} else {
			d = "\nNot found";
		}
		System.out.println(d);

	}
}
