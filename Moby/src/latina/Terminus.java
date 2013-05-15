//Final uploaded 5/7/13
package latina;
/*
 * Terminus holds an ending from the inflects file
 * It contains lots of information about it, such as the form to use, gender,
 * declension, conjugation, tense, voice, etc.
 */
public class Terminus implements Comparable<Terminus> {
	public String pos;			// part of speech
	public int cd;				// conjugation or declension
	public int variant;			// variant inside conjugation or declension
	public String wordcase;		// for nouns and adjectives ("case" is a reserved word in Java)
	public String number;		// number of ending
	public String gender;		
	public String degree;
	public int form;			// Verbum form number the ending takes
	public String ending;
	public String age;
	public String freq;
	public String tense;
	public String voice;
	public String mood;
	public String person;
	public String type;			// for numbers
	public int hash;			// for inserting into binary trees

	public Terminus(){
		// Initialize ints and Strings here.
		pos = "";
		cd = -2;			//-1 is used
		variant = -2;		//-1 is used
		wordcase = "";
		number = "";
		gender = "";		
		degree = "";
		form = -1;	
		ending = "";
		age = "";
		freq = "";
		tense = "";
		voice = "";
		mood = "";
		person = "";
		type = "";		// for numbers
		hash = (int)(Math.random() * 10000);
	}
	/*
	 * After this object has been created, this method
	 * can be used to set field values from a line of 
	 * text from the file INFLECTS.LAT / InflectsMacs.txt
	 * Returns 0 on success, -1 on some error
	 */
	public int setLine(String l){
		int offset;		// -1 if there is no ending, 0 if there is
		String[] a = l.split(" +");
		pos = a[0];
		if(pos.compareTo("ADJ") == 0){
			cd = Integer.parseInt(a[1]);
			variant = Integer.parseInt(a[2]);
			wordcase = a[3];
			number = a[4];
			gender = a[5];
			degree = a[6];
			form = Integer.parseInt(a[7]);
			if(a[8].compareTo("0") == 0){
				offset = -1;
				ending = "";
			} else {
				offset = 0;
				ending = a[9];
			}
			age = a[10 + offset];
			freq = a[11 + offset];
		} else if(pos.compareTo("ADV") == 0){
			degree = a[1];
			form = Integer.parseInt(a[2]);
			age = a[4];
			freq = a[5];
		} else if(pos.compareTo("CONJ") == 0 || pos.compareTo("INTERJ") == 0){
			form = Integer.parseInt(a[1]);
			age = a[3];
			freq = a[4];
		} else if(pos.compareTo("N") == 0 || pos.compareTo("PRON") == 0){
			if(a[1].compareTo("9") == 0) // strange non-declined pronoun case
				return -1;
			cd = Integer.parseInt(a[1]);
			variant = Integer.parseInt(a[2]);
			wordcase = a[3];
			number = a[4];
			gender = a[5];
			form = Integer.parseInt(a[6]);
			if(a[7].compareTo("0") == 0){
				offset = -1;
				ending = "";
			} else {
				offset = 0;
				ending = a[8];
			}
			age = a[9 + offset];
			freq = a[10 + offset];
		} else if(pos.compareTo("NUM") == 0){
			cd = Integer.parseInt(a[1]);
			variant = Integer.parseInt(a[2]);
			wordcase = a[3];
			number = a[4];
			gender = a[5];
			type = a[6];
			form = Integer.parseInt(a[7]);
			if(a[8].compareTo("0") == 0){
				offset = -1;
				ending = "";
			} else {
				offset = 0;
				ending = a[9];
			}
			age = a[10 + offset];
			freq = a[11 + offset];
		}  else if(pos.compareTo("PREP") == 0){
			wordcase = a[1];
			form = Integer.parseInt(a[2]);
			age = a[4];
			freq = a[5];
		}  else if(pos.compareTo("V") == 0){
			cd = Integer.parseInt(a[1]);
			variant = Integer.parseInt(a[2]);
			tense = a[3];
			voice = a[4];
			mood = a[5];
			person = a[6];
			number = a[7];
			form = Integer.parseInt(a[8]);
			if(a[9].compareTo("0") == 0){
				offset = -1;
				ending = "";
			} else {
				offset = 0;
				ending = a[10];
			}
			age = a[11 + offset];
			freq = a[12 + offset];
		} else if(pos.compareTo("VPAR") == 0){
			cd = Integer.parseInt(a[1]);
			variant = Integer.parseInt(a[2]);
			wordcase = a[3];
			number = a[4];
			gender = a[5];
			tense = a[6];
			form = Integer.parseInt(a[9]);
			if(a[10].compareTo("0") == 0){
				offset = -1;
				ending = "";
			} else {
				offset = 0;
				ending = a[11];
			}
			age = a[12 + offset];
			freq = a[13 + offset];
			voice = a[7];
		} else {
			return -1;
		}
		//print();
		return 0;
	}
	// So that we can be Comparable
	public int compareTo(Terminus w){
		return hash - w.hash;
	}
	// Print all info on particular ending
	public void print(){
		System.out.println("POS:" + pos + ", cd = " + cd + ", variant = " + variant);
		System.out.println("wordcase = " + wordcase + ", number = " + number + ", gender = " + gender);
		System.out.println("degree = " + degree + ", form = " + form + ", ending = " + ending);
		System.out.println("age = " + age + ", freq = " + freq);
		System.out.println("tense = " + tense + ", voice = " + voice + ", mood = " + mood +
				", person = " + person + ", type = " + type);
		System.out.println("-------------------------");
	}
}
