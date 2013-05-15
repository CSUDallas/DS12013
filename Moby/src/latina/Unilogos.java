//Final uploaded 5/7/13
package latina;
/*
 * Unilogos holds a computer form of a Verbum
 * it also holds the stem and ending for the particular instance of the Verbum
 */
public class Unilogos {
	public String item;		// complete word
	public String stem;
	public String ending;
	public String gender;		
	public String degree;
	public String wordcase;	// for nouns and adjectives ("case" is a reserved word in Java)
	public String number;
	public String tense;
	public String voice;
	public String mood;
	public int person;	
	public String meter;	// longs and shorts for syllables in word

	/*
	 * Constructor - initialize all fields
	 */
	public Unilogos(){
		item = "";
		stem = "";
		ending = "";
		gender = "";
		degree   = "";
		wordcase    = "";
		gender     = "";
		degree     = "";
		number   = "";
		tense    = "";
		voice = "";
		mood       = "";
		person       = 0;
		meter = "";
	}
}
