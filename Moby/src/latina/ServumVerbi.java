package latina;
/*
 * ServumVerbi is a server of Verba - it reads three files, a dictionary, inflects (endings), and a macron dictionary
 * This class also contains all methods to get Verba and Termini
 * Macrons are also processed here
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServumVerbi {
	public DSLinkedList<Verbum> tempWords; 
	public DSBinaryTree<Verbum> wordTree; 
	public DSLinkedList<Verbum> words;
	public DSLinkedList<Terminus> inflects; 
	int ft = 0, nft = 0;
	private Calendar rightNow;

	public ServumVerbi(String dict, String flec, String macron) {
		//Starts a counter to get time elapsed for reading files
		rightNow = Calendar.getInstance();
		int startMin = rightNow.get(rightNow.MINUTE);
		int startSec = rightNow.get(rightNow.SECOND);
		//Initialize words DSLinkedList
		words = new DSLinkedList<Verbum>();

		// First we read the DICTLINE.GEN / DICTLINERAND.GEN file, preferably randomized first
		try { 
			FileReader f = new FileReader(dict);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			tempWords = new DSLinkedList<Verbum>();
			wordTree = new DSBinaryTree<Verbum>();
			while ((line = reader.readLine()) != null) {
				Verbum v = new Verbum();
				v.setLine(line);
				tempWords.addFirst(v);
				wordTree.insert(v);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		System.out.println("+ Done reading " + dict + " file");
		System.out.println("   Verbum tree has " + wordTree.count + 
				" words and height " + wordTree.maxDepth());

		// Now we read the INFLECTS.LAT file
		try { 
			FileReader f = new FileReader(flec);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			inflects = new DSLinkedList<Terminus>();
			while ((line = reader.readLine()) != null) {
				Terminus v = new Terminus();
				v.setLine(line.trim());
				inflects.addLast(v);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		System.out.println("+ Done reading " + flec + " file");
		System.out.println("   Termini tree has " + inflects.count + " endings"); // and height " + inflects.maxDepth()); - if it were a tree

		// Set the nominative form of all Verba
		setNoms();
		System.out.println("+ Verba nominatized");

		// Now we read the macron file LatinMacronFile.xml
		int numFound = 0, numNotFound = 0;
		try { 
			FileReader f = new FileReader(macron);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			String bothPattern = "entry.*key=\"([a-zA-Z]*).*<orth.*>\\p{P}*(\\p{L}+).*</orth>";
			Pattern bp = Pattern.compile(bothPattern);
			String keyMatch = "", orthMatch = "";
			String workingLine = "";
			while ((line = reader.readLine()) != null) {
				line = line.replace(" - ", "-");
				line = line.replace("-", "");
				workingLine = workingLine + line.trim();
				Matcher m = bp.matcher(workingLine);
				if(m.find()){
					keyMatch  = m.group(1);
					orthMatch = m.group(2);
					boolean foundNomForm = processMacrons(keyMatch, orthMatch);
					if(foundNomForm)
						numFound++;	
					else
						numNotFound++;
					workingLine = "";
				}
			}
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		System.out.println("+ Done reading " + macron + " file");
		System.out.println("   Found " + numFound + ", failed to find " + numNotFound);

		// Delete the nominative ending from the macrons string
		// Put macrons ("*") in forms
		doMacrons();
		System.out.println("+ Verba macronized");
		System.out.println("++ Total Verba available: " + words.count);

		//Get current time and calculate the time elapsed for reading files
		rightNow = Calendar.getInstance();
		int endMin = rightNow.get(rightNow.MINUTE);
		int endSec = rightNow.get(rightNow.SECOND);
		int time = (endMin-startMin)*60 + endSec-startSec;
		System.out.println("Time elapsed (sec): " + time);
	}

	//-----Methods to handle macrons-----//
	//NOTE: A macron is represented by an astrisk
	/*
	 * Forms the asterisked version of the orth string.
	 * Searches for a Verbum whose nominative form equals the key.
	 * If it finds it, sets its macrons field equal to the asterisked string.
	 */
	private boolean processMacrons(String key, String orth){
		if(key.length() != orth.length()){	// This should never happen!
			System.out.println("Different length! =" + key + "=, =" + orth + "=");
			return false;
		}
		String macronForm = "";
		for(int i = 0; i < key.length(); i++){
			if(key.charAt(i) != orth.charAt(i))
				macronForm = macronForm + "*" + key.charAt(i);
			else
				macronForm = macronForm + key.charAt(i);
		}
		//System.out.println(key + ", " + orth + ", " + macronForm);
		Verbum v = findWordByNomForm(key, wordTree.root); //Found 8367, failed to find: 9214 - Sec. 8
		// --- Use below with Linked List
		//Verbum v = findWordByNomForm(key); //Found 13620, failed to find: 3961 - SEc. 20
		if(v != null){
			v.macrons = macronForm;
			words.addLast(v);
			return true;
		}
		else{
			//System.out.println("********** failed with " + key + ": " + nft);
			return false;
		}
	}
	//Method to print several outputs for debugging
	public void testMacronProcess(){
		DSElement<Verbum> e = words.first;
		for(int i = 0; i<1000; i++){
			Verbum v = e.getItem();
			System.out.println(v.nom + " " + v.macrons + " " + v.form1);
			e = e.getNext();
		}
	}
	/*
	 * XXX Deprecated method, use findWordByNomForm(String nomForm, DSElement<Verbum> start)
	 * Searches the words list for a Verbum whose nom field equals nomForm
	 * Uses the linked list for searching
	 * XXX This would go quicker if we were in a binary tree... but we seem to lose some
	 */
	public Verbum findWordByNomForm(String nomForm){
		DSElement<Verbum> e = tempWords.first;
		while(e != null){
			if(e.getItem().nom.equals(nomForm))
				return e.getItem();
			else
				e = e.getNext();
		}
		return null;
	}
	/*
	 * Searches the words list for a Verbum whose nom field equals nomForm
	 * Uses the binary tree for searching
	 */
	private Verbum findWordByNomForm(String nomForm, DSElement<Verbum> start){
		if(start == null)
			return null;		
		int cmp = start.getItem().nom.compareToIgnoreCase(nomForm);
		if(cmp == 0){
			return start.getItem();
		}
		if(cmp < 0)
			return findWordByNomForm(nomForm, start.getRight());
		if(cmp > 0)
			return findWordByNomForm(nomForm, start.getLeft());
		return null;
	}
	/*
	 * Runs delNomFromMac(Verbum w) on each Verbum
	 * Then adds macrons to form1, form2, form3, form4
	 */
	public void doMacrons(){
		DSElement<Verbum> w = words.first;
		int count = words.size();    // failsafe counter
		while(count > 0){
			//Make sure word hasn't been macroned before
			if(w.getItem().macForm.isEmpty()){
				//delete nom endings
				delNomFromMac(w.getItem());
				//insert macrons into forms
				macsToForms(w.getItem());
				//System.out.println(w.getItem().macForm);
				//Insert special macrons into verbs
				if(w.getItem().pos.equals("V")){
					specialMacs(w.getItem());
					//System.out.println(w.getItem().form3 + " " + w.getItem().form4);
				}
			}
			w = w.getNext();
			if(w == null)
				w = words.first;
			count--;
		}
	}
	/*
	 * Deletes the nominative endings from the macron strings and sets to macForm field
	 */
	public void delNomFromMac(Verbum w){
		w.macForm = w.macrons.substring(0, w.macrons.length()-w.nomFormLengh);
		if(w.macForm.charAt(w.macForm.length()-1)=='*'){
			w.macForm = w.macForm.substring(0, w.macForm.length()-2);
		}
		return;
	}
	/*
	 * Inserts macrons into the form1, form2, form3, form4 based on macrons in macForm
	 */
	public void macsToForms(Verbum w){
		char[] letters = w.macForm.toCharArray();
		ArrayList<Integer> indecies = new ArrayList<Integer>();
		int c = 0;
		for(int i = 0; i<letters.length; i++){ //Make into ints
			if(letters[i]=='*'){
				indecies.add(i);
				c++;
			}
		}
		c = 0;
		for(int s : indecies){
			w.form1 = new StringBuffer(w.form1).insert(s, "*").toString();
			if(w.form2.length()>=findLargest(indecies)){
				w.form2 = new StringBuffer(w.form2).insert(s, "*").toString();
			}
			if(w.form3.length()>=findLargest(indecies)){
				w.form3 = new StringBuffer(w.form3).insert(s, "*").toString();
			}
			if(w.form4.length()>=findLargest(indecies)){
				w.form4 = new StringBuffer(w.form4).insert(s, "*").toString();
			}
			c++;
		}	
	}
	/*
	 * Inserts macrons into special verbs which follow a pattern
	 * E.g. 1 conj verbs have long a in 3rd and 4th pp
	 */
	public void specialMacs(Verbum w){
		int conj = w.cd;
		int variant = w.variant;
		int i = 0;
		int index = 0;
		
		if(conj==3 && countSyllables(w.form2)<2 && countSyllables(w.form3)<2){		
			do {
				char c = w.form3.charAt(i);
				if(isVowel(c)){
					index = i;
				}
				i++;
			} while(i<w.form3.length()-1);
			w.form3 = new StringBuffer(w.form3).insert(index, "*").toString();
		}
		if (conj==1 && variant==1){
			w.form3 = new StringBuffer(w.form3).insert(w.form3.length()-2, "*").toString();
			w.form4 = new StringBuffer(w.form4).insert(w.form4.length()-2, "*").toString();
		}
	}
	//Tests if char is vowel
	public static boolean isVowel(char ch) {
		return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'y';
	}
	//Calculates the syllable count
	public int countSyllables(String s){
		s = s.replace("qu", "q");
		int i = 0;
		int count = 0;
		do {
			char c = s.charAt(i);
			if(isVowel(c)){
				count++;
			}
			i++;
		} while(i<s.length()-1);
		return count;		
	}
	//-----END Macron Methods-----//
	
	public Verbum getWord(){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * tempWords.size());
		DSElement<Verbum> w = tempWords.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();
		return w.getItem();
	}

	public Verbum getWord(String pos, char freqLevel){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * words.size());
		DSElement<Verbum> w = words.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();
		char freq;    
		//char freqLevel = 'B'; //Anything ABOVE this char will be used
		/* Frequency:
		 * A   full column or more, more than 50 citations - very frequent
		 * B   half column, more than 20 citations - frequent
		 * C   more then 5 citations - common
		 * D   4-5 citations - lesser
		 * E   2-3 citations - uncommon
		 * F   only 1 citation - very rare
		 */
		int count = words.size()*100;    // failsafe counter
		while(count > 0){
			if(w.getItem().attribs.length()>3)
				freq = w.getItem().attribs.charAt(3);
			else 
				freq = 'E'; // If missing feq data, just put very low - i.e. normally not called
			//Names are not allowed to be retrieved here
			//if(w.getItem().pos.equals(pos) && Character.compare(freq, freqLevel) < 0 && Character.isLowerCase(w.getItem().form1.charAt(0)))
			if(w.getItem().pos.equals(pos) && freq < freqLevel && Character.isLowerCase(w.getItem().form1.charAt(0)))
				return w.getItem();
			w = w.getNext();
			if(w == null)
				w = words.first;
			count--;
		}
		w.getItem().form1 = w.getItem().form1 + " BAD";
		return w.getItem();    // Failsafe word
	}

	public Verbum getName(){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * tempWords.size());
		DSElement<Verbum> w = tempWords.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();
		char freq;        
		char freqLevel = 'C'; //Anything ABOVE this char will be used
		/* Frequency:
		 * A   full column or more, more than 50 citations - very frequent
		 * B   half column, more than 20 citations - frequent
		 * C   more then 5 citations - common
		 * D   4-5 citations - lesser
		 * E   2-3 citations - uncommon
		 * F   only 1 citation - very rare
		 */
		// From this point, find the first word (weakly)matching our stresses pattern
		int count = tempWords.size();    // failsafe counter
		while(count > 0){
			if(w.getItem().attribs.length()>3)
				freq = w.getItem().attribs.charAt(3);
			else 
				freq = 'E';
			if(w.getItem().pos.equals("N") && freq < freqLevel &&
					Character.isUpperCase(w.getItem().form1.charAt(0))) //Check is first letter is capitalized
				return w.getItem();
			w = w.getNext();
			if(w == null)
				w = tempWords.first;
			count--;
		}
		w.getItem().form1 = w.getItem().form1 + " BAD";
		return w.getItem();    // Failsafe word
	}

	public void setNoms(){
		DSElement<Verbum> w = tempWords.first;
		int count = tempWords.size();    // failsafe counter
		while(count > 0){
			GetNom(w.getItem());
			//System.out.println(w.getItem().nom);
			w = w.getNext();
			if(w == null)
				w = tempWords.first;
			count--;
		}

		/*DSElement<Verbum> x = words.first;
		for(int i = 0; i<words.size(); i++){
			Verbum v = x.getItem();
			//if(v.pos.equals("ADV")){
			System.out.println(v.pos + " " + v.cd + " " + v.variant + " " + v.nom);

			//}
			x = x.getNext();
		}*/
	}

	public void GetNom(Verbum w){
		DSElement<Terminus> ending = inflects.first;
		int variant = w.variant;
		int variant2 = -2;
		int cd = w.cd;
		String gender = w.gender;

		int start = (int)(Math.random() * inflects.size());
		for(int i = 0; i < start; i++)
			ending = ending.getNext();
		String form ="";
		int count = inflects.size();    // failsafe counter
		while(count > 0){
			Terminus end = ending.getItem();
			if(w.pos.equals("V")) { //VERB
				//if(end.cd == cd && end.variant == variant && Integer.parseInt(end.person) == 1 && end.number.equals("S") && end.tense.equals("PRES") && 
				//		end.voice.equals("ACTIVE") && end.mood.equals("IND")) {
				//	form = w.form1;
				//	w.nom = form + end.ending;
				//	return w;					
				//}
				if(w.type.equals("DEP")){
					w.nom = w.form1 + "or";
					w.nomFormLengh = 3;
				} else {
					w.nom = w.form1 + "o";
					w.nomFormLengh = 2;
				}
				return;
			} else if(w.pos.equals("N") && end.pos.equals("N")) { //NOUN
				/* Some special cases */
				if(cd == 3 && variant == 1){ //Common lots of cases
					variant = 0;
					variant2 = 1;
				}
				if(cd == 2 && variant == 1){ //Common lots of cases
					variant = 0;
					variant2 = 1;
				}
				if(cd == 3) {
					w.nom = w.form1;
					w.nomFormLengh = 0;
					return; //return nom. s. 3rd decl.
				}
				/* END */
				if (end.wordcase != null && end.number != null){
					if(end.cd == cd && (end.variant == variant || end.variant == variant2) && 
							end.wordcase.equals("NOM") && end.number.equals("S") && 
							end.gender.equals(gender)) {
						form = w.form1;
						w.nom = form + end.ending;
						w.nomFormLengh = end.ending.length()+1;
						return;
					}
					else if(end.cd == cd && (end.variant == variant || end.variant == variant2) && 
							end.wordcase.equals("NOM") && end.number.equals("S") &&
							end.gender.equals("X")) {
						form = w.form1;
						w.nom = form + end.ending;
						w.nomFormLengh = end.ending.length()+1;
						return;
					}
					else if(end.cd == cd && (end.variant == variant || end.variant == variant2) && 
							end.wordcase.equals("NOM") && end.number.equals("S") && 
							end.gender.equals("C")) {
						form = w.form1;
						w.nom = form + end.ending;
						w.nomFormLengh = end.ending.length()+1;
						return;
					}
				}
			} else if(w.pos.equals("ADJ") && end.pos.equals("ADJ")) {
				/* Some special cases */
				if(cd == 1 && (variant == 1 || variant == 3 || variant == 5)) {
					w.nom = w.form1 + "us";
					w.nomFormLengh = 3;
					return; //return nom. s. 3rd decl.
				}
				if(cd == 1 && (variant == 2 || variant == 4)){ //Common lots of cases
					w.nom = w.form1;
					w.nomFormLengh = 0;
					return; //return nom. s. 3rd decl.
				}
				if(cd == 0 && variant == 0 && w.type.equals("COMP")) {
					w.nom = w.form1 + "or";
					w.nomFormLengh = 3;
					return; //return nom. s. 3rd decl.
				}
				if(cd == 0 && variant == 0 && w.type.equals("SUPER")) {
					w.nom = w.form1 + "mus";
					return; //return nom. s. 3rd decl.
				}
				if(cd == 2 && variant == 1) {
					w.nom = w.form1 + "e";
					w.nomFormLengh = 2;
					return; //return nom. s. 3rd decl.
				}
				if(cd == 2 && variant == 2) {
					w.nom = w.form1 + "a";
					w.nomFormLengh = 2;
					return; //return nom. s. 3rd decl.
				}
				if(cd == 2 && variant == 3) {
					w.nom = w.form1 + "es";
					w.nomFormLengh = 3;
					return; //return nom. s. 3rd decl.
				}
				if(cd == 2 && (variant == 6 || variant == 7)) {
					w.nom = w.form1 + "os";
					w.nomFormLengh = 3;
					return; //return nom. s. 3rd decl.
				}
				if(cd == 2 && variant == 8) {
					w.nom = w.form1 + "on";
					w.nomFormLengh = 3;
					return; //return nom. s. 3rd decl.
				}
				if(cd == 3 && (variant == 1 || variant == 3 || variant == 6)) {
					w.nom = w.form1;
					w.nomFormLengh = 0;
					return; //return nom. s. 3rd decl.
				}
				if(cd == 3 && variant == 2) {
					w.nom = w.form1 + "is";
					w.nomFormLengh = 3;
					return; //return nom. s. 3rd decl.
				}
				/* END */				
			} else if(w.pos.equals("ADV") && end.pos.equals("ADV")) {
				w.nom = w.form1;
				w.nomFormLengh = 0;
				return; //return nom. s. 3rd decl.
			}
			ending = ending.getNext();
			if(ending == null)
				ending = inflects.first;
			count--;
		}
		w.nom = w.form1;
		w.nomFormLengh = 0;
		return;
	}

	public static int findLargest(ArrayList<Integer> numbers){  
		int largest = numbers.get(0);  
		for(int s : numbers){  
			if(s > largest){  
				largest = s;  
			}  
		}  
		return largest;
	}


	public Verbum vGetTerminus(Verbum w, int p, String num, String tense, String voice, String mood){
		int cd = w.cd;
		int variant = w.variant;
		int variant2 = -2;
		DSElement<Terminus> ending = inflects.first;
		Unilogos cw  = w.cw;
		cw.person = p;
		cw.tense = tense;
		cw.number = num;
		cw.voice = voice;
		cw.mood = mood;

		int start = (int)(Math.random() * inflects.size());
		for(int i = 0; i < start; i++)
			ending = ending.getNext();
		String form ="";
		int count = inflects.size();    // failsafe counter
		while(count > 0){
			if(ending.getItem().pos.equals("V"))
			{
				/* Some special cases */
				if(cd == 3 && variant == 1) { //Common lots of cases
					variant = 0;
					variant2 = 1;
				}
				if(tense.equals("PERF") || tense.equals("PLUP") || tense.equals("FUTP")) { // All verbs appear to have same endings for these
					cd = 0;
					variant = 0;
				}
				/* END */
				if(ending.getItem().cd == cd && (ending.getItem().variant == variant || ending.getItem().variant == variant2) && 
						Integer.parseInt(ending.getItem().person) == p && 
						ending.getItem().number.equals(num) && 
						ending.getItem().tense.equals(tense) && 
						ending.getItem().voice.equals(voice) && 
						ending.getItem().mood.equals(mood)) {
					form = getForm(w, ending.getItem().form);
					if(form.replace("*", "").equals("zzz")){
						cw.item = "none";
						return w;
					} else {
						cw.item = form + ending.getItem().ending;
						cw.stem = form;
						cw.ending = ending.getItem().ending;
						return w;
					}
				}
			}
			ending = ending.getNext();
			if(ending == null)
				ending = inflects.first;
			count--;
		}
		cw.item = "";//w.form1+"MALUM";
		return w;
	}

	public Verbum nGetTerminus(Verbum w, String c, String num){
		int cd = w.cd;
		int variant = w.variant;
		int variant2 = -2; //Normally not used - special cases only
		String gender = w.gender;
		DSElement<Terminus> ending = inflects.first;
		Unilogos cw  = w.cw;
		//cw.gender = gender;
		//cw.wordcase = c;
		//cw.number = num;

		int start = (int)(Math.random() * inflects.size());
		for(int i = 0; i < start; i++)
			ending = ending.getNext();

		int count = inflects.size();    // failsafe counter
		while(count > 0){
			if(ending.getItem().pos.equals("N"))
			{
				/* Some special cases */
				if(cd == 3 && variant == 1){ //Common lots of cases
					variant = 0;
					variant2 = 1;
				}
				if(cd == 2 && variant == 1){ //Common lots of cases
					variant = 0;
					variant2 = 1;
				}
				if(c.equals("DAT")) //Common datives
					variant = 0;
				if(c.equals("ABL") && (cd == 2 || cd ==4)) //Common ablatives
					variant = 0;
				if(cd == 3 && c.equals("NOM") && num.equals("S")) {
					cw.item = w.form1;
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 3 && c.equals("GEN")) {
					cw.item = w.form1 + "is";
					cw.stem = w.form1;
					cw.ending = "is";
					return w; //return nom. s. 3rd decl.
				}
				/* END */
				if (ending.getItem().wordcase != null && ending.getItem().number != null){
					if(ending.getItem().cd == cd && (ending.getItem().variant == variant || ending.getItem().variant == variant2) && 
							ending.getItem().wordcase.equals(c) && ending.getItem().number.equals(num) && 
							ending.getItem().gender.equals(gender)) {
						w.cw.item = getForm(w, ending.getItem().form) + ending.getItem().ending;
						w.cw.stem = getForm(w, ending.getItem().form);
						w.cw.number = num;
						w.cw.ending = ending.getItem().ending;
						return w;
					}
					else if(ending.getItem().cd == cd && (ending.getItem().variant == variant || ending.getItem().variant == variant2) && 
							ending.getItem().wordcase.equals(c) && ending.getItem().number.equals(num) &&
							ending.getItem().gender.equals("X")) {
						w.cw.item = getForm(w, ending.getItem().form) + ending.getItem().ending;
						w.cw.stem = getForm(w, ending.getItem().form);
						w.cw.number = num;
						w.cw.ending = ending.getItem().ending;
						return w;
					}
					else if(ending.getItem().cd == cd && (ending.getItem().variant == variant || ending.getItem().variant == variant2) && 
							ending.getItem().wordcase.equals(c) && ending.getItem().number.equals(num) && 
							ending.getItem().gender.equals("C")) {
						w.cw.item = getForm(w, ending.getItem().form) + ending.getItem().ending;
						w.cw.stem = getForm(w, ending.getItem().form);
						w.cw.number = num;
						w.cw.ending = ending.getItem().ending;
						return w;
					}
				}
			}
			ending = ending.getNext();
			if(ending == null)
				ending = inflects.first;
			count--;
		}
		cw.item = "";//w.form1+"MALUM";
		w.cw.number = num;
		return w;
	}

	public Verbum adjGetTerminus(Verbum w, Verbum m, String c, String num){
		int cd = w.cd;
		int variant = w.variant;
		int variant2 = -2; //Normally not used - special cases only
		String gender = m.gender;
		DSElement<Terminus> ending = inflects.first;
		Unilogos cw  = w.cw;
		cw.gender = gender;
		cw.wordcase = c;
		cw.number = num;

		int start = (int)(Math.random() * inflects.size());
		for(int i = 0; i < start; i++)
			ending = ending.getNext();

		int count = inflects.size();    // failsafe counter
		while(count > 0){
			if(ending.getItem().pos.equals("ADJ"))
			{
				/* Some special cases */
				if(cd == 3 && variant == 1){ //Common lots of cases
					variant = 0;
					variant2 = 1;
				}
				if(cd == 2 && variant == 1){ //Common lots of cases
					variant = 0;
					variant2 = 1;
				}
				if(c.equals("DAT")) //Common datives
					variant = 0;
				if(c.equals("ABL") && (cd == 2 || cd ==4)) //Common ablatives
					variant = 0;
				if(cd == 3 && c.equals("NOM")) {
					cw.item = w.form1;
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 3 && c.equals("GEN")) {
					cw.item = w.form1 + "is";
					cw.stem = w.form1;
					cw.ending = "is";
					return w; //return nom. s. 3rd decl.
				}
				/* END */
				if (ending.getItem().wordcase != null && ending.getItem().number != null){
					if(ending.getItem().cd == cd && (ending.getItem().variant == variant || ending.getItem().variant == variant2) && 
							ending.getItem().wordcase.equals(c) && ending.getItem().number.equals(num) && 
							ending.getItem().gender.equals(gender)){
						cw.item = getForm(w, ending.getItem().form) + ending.getItem().ending;
						cw.stem = getForm(w, ending.getItem().form);
						cw.ending = ending.getItem().ending;
						return w;
					}
					else if(ending.getItem().cd == cd && (ending.getItem().variant == variant || ending.getItem().variant == variant2) && 
							ending.getItem().wordcase.equals(c) && ending.getItem().number.equals(num) &&
							ending.getItem().gender.equals("X")){
						cw.item = getForm(w, ending.getItem().form) + ending.getItem().ending;
						cw.stem = getForm(w, ending.getItem().form);
						cw.ending = ending.getItem().ending;
						return w;
					}
					else if(ending.getItem().cd == cd && (ending.getItem().variant == variant || ending.getItem().variant == variant2) && 
							ending.getItem().wordcase.equals(c) && ending.getItem().number.equals(num) && 
							ending.getItem().gender.equals("C")){
						cw.item = getForm(w, ending.getItem().form) + ending.getItem().ending;
						cw.stem = getForm(w, ending.getItem().form);
						cw.ending = ending.getItem().ending;
						return w;
					}
				}
			}
			ending = ending.getNext();
			if(ending == null)
				ending = inflects.first;
			count--;
		}
		cw.item = "";//w.form1+"MALUM";
		return w;
	}

	public String getForm(Verbum w, int num){
		if(num == 1)
			return w.form1;
		else if(num == 2)
			return w.form2;
		else if(num == 3)
			return w.form3;

		return w.form4;		
	}

}
