//Final uploaded 5/7/13
package latina;
/*
 * Verbum holds a Latin word from the big dictionary file.
 * It contains lots of information about it, such as the pp parts, forms, gender,
 * declension, conjugation, macron form, nom, etc.
 */
public class Verbum implements Comparable<Verbum> {
	public String form1;		// The (up to) four forms
	public String form2;
	public String form3;
	public String form4;
	public String pos;			// part of speech
	public int cd;				// conjugation and declension
	public int variant;	
	public int value;			// for numbers
	public String gender;		
	public String degree;		// for adjectives and adverbs
	public String mystery;		// reserved for future understanding
	public String attribs;		// holds 5 characters - frequency, etc.
	public String definition;
	public String type;			// for nouns and pronouns and verbs (DEP)
	public Unilogos cw; 		// Unilogos - one instance of complete word - holds computed word and ending	
	public String nom;			// computed nominative case
	public String macrons;		// form with macrons
	public String macForm;		// 'denominatized' macrons form
	public int nomFormLengh;
	
	/*
	 * Constructor
	 */
	public Verbum(){
		form1 = "";
		form2 = "";
		form3 = "";
		form4 = "";
		pos   = "";
		cd    = variant = value = -1;
		gender     = "";
		degree     = "";
		mystery   = "";
		attribs    = "";
		definition = "";
		type       = "";
		cw = new Unilogos();
		nom = "";
		macrons = "";
		macForm = "";
		nomFormLengh = -1;
	}
	
	/*
	 * After this object has been created, this method
	 * can be used to set field values from a line of 
	 * text from the file DICTLINE.GEN / DICTLINERAND.GEN
	 * Returns 0 on success, -1 on some error
	 */
	public int setLine(String s){
		form1 = s.substring(0 , 19).trim();
		form2 = s.substring(19, 38).trim();
		form3 = s.substring(38, 57).trim();
		form4 = s.substring(57, 76).trim();
		pos   = s.substring(76, 83).trim();
		
		// Now do different things for different p'sos
		// Note: Nothing extra for CONJ or INTERJ
		if(pos.compareTo("ADJ") == 0){	// adjectives			
			cd      = Integer.parseInt(s.substring(83, 84));
			variant = Integer.parseInt(s.substring(85, 86));
			degree  = s.substring(87, 100).trim();
		} else if(pos.compareTo("ADV") == 0){
			degree = s.substring(83,100).trim();
		} else if(pos.compareTo("N") == 0){
			//System.out.println("in N: s:83-84 = " + s.substring(83, 84));
			try{	// Sometimes this line has a blank there
				cd      = Integer.parseInt(s.substring(83, 84));
			} catch(NumberFormatException x){
				cd      = -1;
			}
			try{	// Sometimes this line has a blank there
				variant = Integer.parseInt(s.substring(85, 86));
			} catch(NumberFormatException x){
				variant = -1;
			}
			gender  = s.substring(87, 88).trim();
			mystery = s.substring(89, 90).trim();
		} else if(pos.compareTo("NUM") == 0){
			cd      = Integer.parseInt(s.substring(83, 84));
			variant = Integer.parseInt(s.substring(85, 86));
			type    = s.substring(87, 95).trim();
			value   = Integer.parseInt(s.substring(95, 100).trim());
		} else if(pos.compareTo("PREP") == 0){
			type = s.substring(83,100).trim();
		} else if(pos.compareTo("PRON") == 0){
			cd      = Integer.parseInt(s.substring(83, 84));
			variant = Integer.parseInt(s.substring(85, 86));
			type    = s.substring(87, 100).trim();
		} else if(pos.compareTo("V") == 0){
			cd      = Integer.parseInt(s.substring(83, 84));
			variant = Integer.parseInt(s.substring(85, 86));
			type    = s.substring(87, 100).trim();
		} else {
			return -1;
		}
		
		// And these last ones are the same for all p'sos
		attribs = s.substring(100, 109).replace(" ", "");
		definition = s.substring(110).trim().replace(";", "");
		//print();
		return 0;
	}
	
	// So that we can be Comparable
	public int compareTo(Verbum w){
		return form1.compareToIgnoreCase(w.form1);
	}
	
	// Print this Verbum's data
	public void print(){
		System.out.println("Forms:" + form1 + ", " + form2 + ", " + form3 + ", " + form4);
		System.out.println("POS: " + pos);
		System.out.println("cd, variant,value: " + cd + ", " + variant + ", " + value);
		System.out.println("gender: " + gender + ", degree: " + degree + 
				", mystery: " + mystery);
		System.out.println("Attribs: " + attribs + ", type: " + type +
				", Definition: " + definition);
		System.out.println("--------------------------------------------");
	}
	// Return a truncated definition of the Verbum
	public String meaning(){
		int c = definition.indexOf(",");
		int s = definition.indexOf(";");
		int i;
		if(s==-1)
			i = c;
		else if(s>c)
			i = c;
		else
			i = s;
		if(i==-1 && definition.length()>25)
			return definition.substring(0, 25);	
		if(i==-1)
			return definition;
		
		if(s>25 || c>25)
			i = 25;
		return definition.substring(0, i);	
	}
}