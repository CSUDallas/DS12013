package latina;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServumVerbi {
	DSLinkedList<Verbum> words; 
	//DSBinaryTree<Verbum> wordTree; 
	DSLinkedList<Terminus> inflects; 
	int ft = 0, nft = 0;

	public ServumVerbi(String dict, String flec, String macron) {

		// First we read the DICTLINE.GEN file, preferably randomized first
		try { 
			FileReader f = new FileReader(dict);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			words = new DSLinkedList<Verbum>();
			//wordTree = new DSBinaryTree<Verbum>();
			while ((line = reader.readLine()) != null) {
				Verbum v = new Verbum();
				v.setLine(line);
				words.addFirst(v);
				//wordTree.insert(v);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		System.out.println("Done reading " + dict + " file");
		System.out.println("Verbum tree has " + words.count + 
				" words and height ");// + words.maxDepth());
		//words.root.getItem().print();

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
		System.out.println("Done reading " + flec + " file");
		System.out.println("Termini tree has " + inflects.count + 
				" endings and height ");// + inflects.maxDepth());
		//System.out.println(inflects.root.getItem().ending);
		//inflects.root.getItem().print();
		//inflects.first.getNext().getItem().print();

		// Set the nominative form
		testGetNom();
		
		// Now we read the macron file
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
		System.out.println("Done reading " + macron + " file");
		System.out.println("Found " + numFound + ", failed to find: " + numNotFound);
	}
	
	
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
		Verbum v = findWordByNomForm(key);
		if(v != null){
			v.macrons = macronForm;
			return true;
		}
		else{
			//System.out.println("********** failed with " + key + ": " + nft);
			return false;
		}
	}
	
	/*
	 * Searches the words list for a Verbum whose nom field equals nomForm
	 * XXX This would go quicker if we were in a binary tree...
	 */
	private Verbum findWordByNomForm(String nomForm){
		DSElement<Verbum> e = words.first;
		while(e != null){
			if(e.getItem().nom.equals(nomForm))
				return e.getItem();
			else
				e = e.getNext();
		}
		return null;
	}

	public Verbum getWord(){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * words.size());
		DSElement<Verbum> w = words.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();
		return w.getItem();
		// From this point, find the first word (weakly)matching our stresses pattern

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
		int start = (int)(Math.random() * words.size());
		DSElement<Verbum> w = words.first;
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
		int count = words.size();    // failsafe counter
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
				w = words.first;
			count--;
		}
		w.getItem().form1 = w.getItem().form1 + " BAD";
		return w.getItem();    // Failsafe word
	}

	public void testGetNom(){
		DSElement<Verbum> w = words.first;
		int count = words.size();    // failsafe counter
		while(count > 0){
			w.setItem(vGetNom(w.getItem()));
			w = w.getNext();
			if(w == null)
				w = words.first;
			count--;
		}
		
		DSElement<Verbum> x = words.first;
		for(int i = 0; i<words.size(); i++){
			Verbum v = x.getItem();
			//if(v.pos.equals("ADV")){
			System.out.println(v.pos + " " + v.cd + " " + v.variant + " " + v.nom);
			
			//}
			x = x.getNext();
		}
	}

	public Verbum vGetNom(Verbum w){
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
				} else {
					w.nom = w.form1 + "o";
				}
				return w;
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
					return w; //return nom. s. 3rd decl.
				}
				/* END */
				if (end.wordcase != null && end.number != null){
					if(end.cd == cd && (end.variant == variant || end.variant == variant2) && 
							end.wordcase.equals("NOM") && end.number.equals("S") && 
							end.gender.equals(gender)) {
						form = w.form1;
						w.nom = form + end.ending;
						return w;
					}
					else if(end.cd == cd && (end.variant == variant || end.variant == variant2) && 
							end.wordcase.equals("NOM") && end.number.equals("S") &&
							end.gender.equals("X")) {
						form = w.form1;
						w.nom = form + end.ending;
						return w;
					}
					else if(end.cd == cd && (end.variant == variant || end.variant == variant2) && 
							end.wordcase.equals("NOM") && end.number.equals("S") && 
							end.gender.equals("C")) {
						form = w.form1;
						w.nom = form + end.ending;
						return w;
					}
				}
			} else if(w.pos.equals("ADJ") && end.pos.equals("ADJ")) {
				/* Some special cases */
				if(cd == 1 && (variant == 1 || variant == 3 || variant == 5)) {
					w.nom = w.form1 + "us";
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 1 && (variant == 2 || variant == 4)){ //Common lots of cases
					w.nom = w.form1;
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 0 && variant == 0 && w.type.equals("COMP")) {
					w.nom = w.form1 + "or";
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 0 && variant == 0 && w.type.equals("SUPER")) {
					w.nom = w.form1 + "mus";
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 2 && variant == 1) {
					w.nom = w.form1 + "e";
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 2 && variant == 2) {
					w.nom = w.form1 + "a";
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 2 && variant == 3) {
					w.nom = w.form1 + "es";
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 2 && (variant == 6 || variant == 7)) {
					w.nom = w.form1 + "os";
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 2 && variant == 8) {
					w.nom = w.form1 + "on";
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 3 && (variant == 1 || variant == 3 || variant == 6)) {
					w.nom = w.form1;
					return w; //return nom. s. 3rd decl.
				}
				if(cd == 3 && variant == 2) {
					w.nom = w.form1 + "is";
					return w; //return nom. s. 3rd decl.
				}
				/* END */				
			} else if(w.pos.equals("ADV") && end.pos.equals("ADV")) {
				w.nom = w.form1;
				return w; //return nom. s. 3rd decl.
			}
			ending = ending.getNext();
			if(ending == null)
				ending = inflects.first;
			count--;
		}
		w.nom = w.form1;
		return w;
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
					if(form.equals("zzz")){
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
		cw.item = w.form1+"MALUM";
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
						w.cw.ending = ending.getItem().ending;
						return w;
					}
					else if(ending.getItem().cd == cd && (ending.getItem().variant == variant || ending.getItem().variant == variant2) && 
							ending.getItem().wordcase.equals(c) && ending.getItem().number.equals(num) &&
							ending.getItem().gender.equals("X")) {
						w.cw.item = getForm(w, ending.getItem().form) + ending.getItem().ending;
						w.cw.stem = getForm(w, ending.getItem().form);
						w.cw.ending = ending.getItem().ending;
						return w;
					}
					else if(ending.getItem().cd == cd && (ending.getItem().variant == variant || ending.getItem().variant == variant2) && 
							ending.getItem().wordcase.equals(c) && ending.getItem().number.equals(num) && 
							ending.getItem().gender.equals("C")) {
						w.cw.item = getForm(w, ending.getItem().form) + ending.getItem().ending;
						w.cw.stem = getForm(w, ending.getItem().form);
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
		cw.item = w.form1+"MALUM";
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
		cw.item = w.form1+"MALUM";
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
