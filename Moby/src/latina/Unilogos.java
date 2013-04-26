package latina;

public class Unilogos {
	public String item;		// The (up to) four forms
	public String stem;
	public String ending;
	public String gender;		
	public String degree;
	public String wordcase;		// for nouns and adjectives ("case" is a reserved word in Java)
	public String number;
	public String tense;
	public String voice;
	public String mood;
	public int person;	
	public String meter;

	/*
	 * Constructor
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
