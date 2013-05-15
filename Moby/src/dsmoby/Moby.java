package dsmoby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

public class Moby {
	DSBinaryTree<MobyWord> words;
	DSLinkedList<MobyWord> wordsList;
	private int scowlThreshold;

	public Moby(String mpron, String mpos, String scowl, String agid){

		// First we read the words from the CMU pronounciation list
		System.out.println("** Reading CMU pronunciation file");
		scowlThreshold = 60;	// Default
		try { 
			FileReader f = new FileReader(mpron);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			words = new DSBinaryTree<MobyWord>();
			wordsList = new DSLinkedList<MobyWord>();
			while ((line = reader.readLine()) != null) {
				MobyWord m = new MobyWord();
				m.setPron(line);
				words.insert(m);
				wordsList.addLast(m);
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		System.out.println("Done reading CMU Pronunciation file");
		System.out.println("The height of the words tree is " + words.maxDepth());


		// Now read the parts of speech file
		System.out.println("** Reading moby parts-of-speech file");
		int linesRead = 0;
		int addedWords = 0;
		try { 
			FileReader f = new FileReader(mpos);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if(linesRead % 20000 == 0)
					System.out.print("" + (int)(linesRead * 100 / 232123) + "% ");	
				String[] parts = line.split("\\*");
				String posString = parts[1];
				MobyWord w = findWord(parts[0], words.root);
				if(w != null){
					w.word = parts[0];	// scowl's version of word is nicer
					w.setPosString(posString);
					addedWords++;
				} 

				linesRead++;
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		System.out.println("\nDone. Added " + addedWords + " out of " + linesRead + " lines read");



		// Now we read their scowl values
		System.out.println("** Reading scowl values: ");
		try { 
			FileReader f = new FileReader(scowl);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			linesRead = 0;
			while ((line = reader.readLine()) != null) {
				if(linesRead % 50000 == 0)
					System.out.print("" + (int)(linesRead * 100 / 513447) + "% ");	
				String[] parts = line.split(" ");
				int scowlVal = Integer.parseInt(parts[0]);
				MobyWord w = findWord(parts[1], words.root);
				if(w != null){
					w.word = parts[1];	// scowl's version of word is nicer
					w.scowlValue = scowlVal;
				} 

				linesRead++;
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		System.out.println("Done.");


		// Now we read their agid file for plural forms and conjugations
		System.out.println("** Reading agid file: ");
		try { 
			FileReader f = new FileReader(agid);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			linesRead = 0;
			while ((line = reader.readLine()) != null) {
				if(linesRead % 10000 == 0)
					System.out.print("" + (int)(linesRead * 100 / 112503) + "% ");	
				line = line.replaceAll("[\\?,~<0123456!]", "");	
				//line = line.replaceAll("\\{.*\\}", "");
				String[] parts = line.split(" ");
				String wordbase = parts[0];
				MobyWord w;
				if(parts[1].charAt(0) == 'N'){ // Noun case
					w = findWordPOS(wordbase, words.root, "N");
					if(w == null){
						linesRead++;
						continue;
					}
					int numPlurals = parts.length - 2;
					if(w != null && numPlurals > 0)
						w.pluralForm = parts[2];
					for(int i = 0; i < numPlurals; i++){
						w = findWord(parts[i + 2], words.root);
						if(w != null && w.isNoun && w.isPlural)
							w.singularForm = wordbase;
					}	
				} 
				else if(parts[1].charAt(0) == 'V'){ // Verb case
					w = findWordPOS(wordbase, words.root, "Vti");
					if(w == null){
						linesRead++;
						continue;
					}
					boolean hasParticiple = line.matches(".*\\|.*\\|.*\\|.*");
					int verbPartsStart = line.indexOf(":") + 1;
					String verbLine = line.substring(verbPartsStart);
					String[] verbParts = verbLine.split("\\|");
					int nvp = verbParts.length;
					for(int i = 0; i < verbParts.length; i++){ // Restrict to only most common variant
						String[] verbPartParts = verbParts[i].trim().split(" ");
						verbParts[i] = verbPartParts[0].trim();
					}
					
					// Set this MobyWord
					w.pastForm = verbParts[0];
					if(hasParticiple){
						if(nvp > 1) w.pastParticipleForm = verbParts[1];
						if(nvp > 2) w.ingForm = verbParts[2];
						if(nvp > 3) w.sForm = verbParts[3];
					} else {
						if(nvp > 1) w.ingForm = verbParts[1];
						if(nvp > 2) w.sForm = verbParts[2];
					}

					// Set the verb form's other fields
					for(int i = 0; i < verbParts.length; i++){
						MobyWord w2  = findWord(verbParts[i], words.root);
						if(w2 == null){
							linesRead++;
							continue;
						}
						w2.baseVerbForm = wordbase;
						w2.pastForm = verbParts[0];
						if(i == 0)
							w2.isPastTense = true;
						if(hasParticiple){
							if(i == 1)
								w2.isPastParticiple = true;
							if(i == 2)
								w2.isIngForm = true;
							if(i == 3)
								w2.isSForm = true;
							if(nvp > 1) w2.pastParticipleForm = verbParts[1];
							if(nvp > 2) w2.ingForm = verbParts[2];
							if(nvp > 3) w2.sForm = verbParts[3];
						} else {
							if(i == 1)
								w2.isIngForm = true;
							if(i == 2)
								w2.isSForm = true;
							if(nvp > 1) w2.ingForm = verbParts[1];
							if(nvp > 2) w2.sForm = verbParts[2];
						}
					}
				} 
				else if(parts[1].charAt(0) == 'A'){		// Adjective / Adverb Form
					w = findWordPOS(wordbase, words.root, "Av");
					if(w == null){
						linesRead++;
						continue;
					}
					//System.out.println(line);
					int adPartsStart = line.indexOf(":") + 1;
					String adLine = line.substring(adPartsStart);
					String[] adParts = adLine.split("\\|");
					for(int i = 0; i < adParts.length; i++){ // Restrict to only most common variant
						String[] adPartParts = adParts[i].trim().split(" ");
						adParts[i] = adPartParts[0].trim();
					}
					w.comparativeForm = adParts[0];
					w.superlativeForm = adParts[1];

					// Set the other form's other fields
					MobyWord w2  = findWord(adParts[0], words.root);
					if(w2 != null){
						w2.baseAd = wordbase;
						w2.superlativeForm = adParts[1];
						w2.isComparative = true;
					}
					w2  = findWord(adParts[1], words.root);
					if(w2 != null){
						w2.baseAd = wordbase;
						w2.comparativeForm = adParts[0];
						w2.isSuperlative = true;
					}
						

				}
				linesRead++;
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		System.out.println("Done.");
	}
	
	
	/*
	 * Set a thesaurus for the Moby
	 * Fills the MobyWords with synonyms in the "synonyms" DSLinkedList
	 */
	public void setSynonyms(String synFile){
		System.out.println("** Reading Thesaurus: ");
		try { 
			FileReader f = new FileReader(synFile);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			int linesRead = 0;
			int found = 0;
			int numSynonyms = 0;
			while ((line = reader.readLine()) != null) {
				if(linesRead % 2000 == 0)
					System.out.print("" + (int)(linesRead * 100 / 30260) + "% ");	
				String[] parts = line.split(",");
				MobyWord w = findWord(parts[0], words.root);
				if(w != null){
					found++;
					for(int i = 1; i < parts.length; i++){
						MobyWord s = findWord(parts[i], words.root);
						if(s != null){
							w.synonyms.addLast(s);
							numSynonyms++;
						}
					}
				}
				linesRead++;
			}
			System.out.println("Done.\nThesaurus done. Found " + numSynonyms + " for " + found + " words.");
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}

	}


	public String getStressWord(String stresses){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// From this point, find the first word (weakly)matching our stresses pattern
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, true) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isNoun)
				return w.getItem().word;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "Amoeba";	// Failsafe word
	}


	/*
	 * This method returns a random word satisfying the following parameters:
	 * pos      = part of speech
	 * stresses = profile that the word must match
	 * rhyme    = string of 2k letters describing the last k phones the word must have
	 * exact    = true if the word must be exactly as long as the "stresses" String indicates,
	 *          = false if it may be shorter.
	 */
	public String getWord(String pos, String stresses, String rhyme, boolean exact){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// From this point, find the first word (weakly)matching our stresses pattern
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos(pos) && w.getItem().rhymesWith(rhyme))
				return w.getItem().word;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "Amoeba";	// Failsafe word
	}
	
	
	public String getVerbSimple(String tense){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();
		// From this point, find the first word (weakly)matching our stresses pattern
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().isPos("V"))
				if(tense == "PSVerb")
					return w.getItem().sForm;
				else if(tense == "PPVerb")
					return w.getItem().word;
				else if(tense == "FSVerb")
					return "will " + w.getItem().sForm;
				else if(tense == "FPVerb")
					return "will " + w.getItem().word;
				else if(tense == "PastVerb")
					return w.getItem().pastForm;
				else if(tense == "PrPVerb")
					return "have " + w.getItem().pastForm;
				else if(tense == "PaPVerb")
					return "had " + w.getItem().pastForm;
				else if(tense == "FuPVerb")
					return "will have " + w.getItem().pastForm;
				else if(tense == "PaPerProVerb")
					return "had been " + w.getItem().ingForm;
				else if(tense == "FuPerProVerb")
					return "will have been " + w.getItem().ingForm;
				else 
					return "Amoeba";
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "Amoeba";	// Failsafe word
	}
	
	
	/*
	 * return present tense verb with singular (not "I") subject
	 */
	public String getPSVerb(String pos, String stresses, String rhyme, boolean exact){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// adapt wordfinder above to just find verb
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos("V") && w.getItem().rhymesWith(rhyme))
				return w.getItem().sForm;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "Amoebas";	// Failsafe word

	}
	
	public String getPSVerbSimple(String pos){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// adapt wordfinder above to just find verb
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().isPos("V"))
				return w.getItem().sForm;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "Amoeba";	// Failsafe word

	}

	/*
	 * return present tense verb with plural subject
	 */
	public String getPPVerb(String pos, String stresses, String rhyme, boolean exact){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// adapt wordfinder above to just find verb
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos("V") && w.getItem().rhymesWith(rhyme))
				return w.getItem().word;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "amoeba";	// Failsafe word

	}

	/*
	 * return future tense verb
	 */
	public String getFVerb(String pos, String stresses, String rhyme, boolean exact){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// adapt wordfinder above to just find verb
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos("V") && w.getItem().rhymesWith(rhyme))
				return "will " + w.getItem().word;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "will amoeba";	// Failsafe word
	}	

	/*
	 * return past tense verb
	 */
	public String getPastVerb(String pos, String stresses, String rhyme, boolean exact){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// adapt wordfinder above to just find verb
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos("V") && w.getItem().rhymesWith(rhyme))
				return w.getItem().pastForm;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "Amoeba'd";	// Failsafe word

	}

	/*
	 * return present perfect verb
	 */
	public String getPrPVerb(String pos, String stresses, String rhyme, boolean exact){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// adapt wordfinder above to just find verb
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos("V") && w.getItem().rhymesWith(rhyme))
				return "have " + w.getItem().pastForm;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "have amoeba'd";	// Failsafe word

	}

	/*
	 * return past perfect verb
	 */
	public String getPaPVerb(String pos, String stresses, String rhyme, boolean exact){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// adapt wordfinder above to just find verb
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos("V") && w.getItem().rhymesWith(rhyme))
				return "had " + w.getItem().pastForm;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "had amoeba'd";	// Failsafe word

	}

	/*
	 * return future perfect verb
	 */
	public String getFuPVerb(String pos, String stresses, String rhyme, boolean exact){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// adapt wordfinder above to just find verb
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos("V") && w.getItem().rhymesWith(rhyme))
				return "will have " + w.getItem().pastForm;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "will have amoeba'd";	// Failsafe word

	}

	/*
	 * return past perfect progressive verb
	 */
	public String getPaPerProVerb(String pos, String stresses, String rhyme, boolean exact){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// adapt wordfinder above to just find verb
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos("V") && w.getItem().rhymesWith(rhyme))
				return "had been " + w.getItem().ingForm;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "had been amoeba-ing";	// Failsafe word

	}

	/*
	 * return future perfect progressive verb
	 */
	public String getFuPerProVerb(String pos, String stresses, String rhyme, boolean exact){
		// Pick random starting point in the linked list
		int start = (int)(Math.random() * wordsList.size());
		DSElement<MobyWord> w = wordsList.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// adapt wordfinder above to just find verb
		int count = wordsList.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos("V") && w.getItem().rhymesWith(rhyme))
				return "will have been " + w.getItem().ingForm;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		return "will have been amoeba-ing";	// Failsafe word

	}

	
	/*
	 * Searches a binary tree of MobyWords for one containing string s
	 * beginning its search at node "start"
	 */
	private MobyWord findWord(String s, DSElement<MobyWord> start){
		if(start == null)
			return null;

		int cmp = start.getItem().word.compareToIgnoreCase(s);
		if(cmp == 0){
			return start.getItem();
		}
		if(cmp < 0)
			return findWord(s, start.getRight());
		if(cmp > 0)
			return findWord(s, start.getLeft());

		return null;
	}
	
	/*
	 * Reid Hansen's project
	 * Reads in a "book" file, and for each word in the book adds a link to 
	 * that word's MobyWord object a reference to the MobyWord that follows
	 * it in the text. Adds each following word as many times as it follows
	 * it in the text.
	 */
	public void setNextWords(String book){
		System.out.println("** Reading Book File");
		try { 
			FileReader f = new FileReader(book);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			while ((line = reader.readLine()) != null) {
				line = line.replaceAll("[\\?,~<01234567890!_-]", "");	
				String[] parts = line.split(" ");
				for(int i = 0; i < parts.length; i++){
					//System.out.print(parts[i] + "\n");
					MobyWord w = findWord(parts[i], words.root);
					if(w != null && i < parts.length - 1){
						MobyWord l = findWord(parts[i + 1], words.root);
						if(l != null){
							w.nextWords.addLast(l);
						}
					}
				}
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		System.out.println("Done Reading Book File");

	}



	/*
	 * Reid Hansen's project
	 * Uses nextWords to find an appropriate next word.
	 *
	 * Just like getWord except you need to pass the priorWord as well.
	 * Then it selects a random next word from the "nextWords" field, if there are any.
	 */
	public String getSmartWord(String pos, String stresses, String rhyme, boolean exact, String priorWord){
		// Pick random starting point in the linked list
		MobyWord l = findWord(priorWord, words.root);

		int start = (int)(Math.random() * l.nextWords.size());
		DSElement<MobyWord> w = l.nextWords.first;
		for(int i = 0; i < start; i++)
			w = w.getNext();

		// From this point, find the first word (weakly)matching our stresses pattern
		int count = l.nextWords.size();	// failsafe counter
		while(count > 0){
			if(w.getItem().fits(stresses, exact) && 
					w.getItem().scowlValue <= this.scowlThreshold &&
					w.getItem().isPos(pos) && w.getItem().rhymesWith(rhyme))
				return w.getItem().word;
			w = w.getNext();
			if(w == null)
				w = wordsList.first;
			count--;
		}
		// When the getSmartWord Fails it will just look through all words
		return getWord(pos, stresses, rhyme, exact);  
		//return "";	// Failsafe word
	}


	/*
	 * Searches a binary tree of MobyWords for one containing string s
	 * and is part of speech pos, beginning its search at node "start"
	 */
	private MobyWord findWordPOS(String s, DSElement<MobyWord> start, String pos){
		if(start == null)
			return null;

		int cmp = start.getItem().word.compareToIgnoreCase(s);
		if(cmp == 0 && start.getItem().isPos(pos)){
			return start.getItem();
		}
		if(cmp <= 0)
			return findWord(s, start.getRight());
		if(cmp > 0)
			return findWord(s, start.getLeft());

		return null;
	}


	/*
	 * Returns the last phones from a given word, from the vowel on out.
	 */
	public String getRhymePhones(String word){
		MobyWord w = findWord(word, words.root);
		int numPhones = w.phoneString.length() / 2;
		int loc = numPhones - 1;
		String ps = w.phoneString.substring(2*loc, 2*loc+2);
		while(!isVowelPhone(ps) && loc >= 0){
			loc--;
			ps = w.phoneString.substring(2*loc, 2*loc+2);
		}
		if(loc >= 0)
			return w.phoneString.substring(2*loc);	// Return from vowel phone to end
		else
			return getTwoPhones(word);				// Failsafe for vowel-free words
	}

	/*
	 * Returns true if the input 2-character phone string is a vowel phone.
	 */
	public boolean isVowelPhone(String s){
		String vowelPhones = "AA.AE.AH.AO.AW.AY.EH.ER.EY.IH.IY.OW.OY.UH.UW";
		return vowelPhones.contains(s);
	}


	/*
	 * Returns the last phones from a given word, sufficient for rhyming
	 */
	public String getTwoPhones(String word){
		MobyWord w = findWord(word, words.root);
		return w.phoneString.substring(w.phoneString.length() - 4);
	}


	public int getScowlThreshold() {
		return scowlThreshold;
	}


	public void setScowlThreshold(int scowlThreshold) {
		this.scowlThreshold = scowlThreshold;
	}


	// Takes a sentence string (s) and returns a DSLinkedList of phone strings
	// s should have no punctuation
	public String getAllPhones(String s){
		String returnString = "";
		String[] parts = s.split(" ");
		for(String x : parts){
			MobyWord w = findWord(x, words.root);
			returnString = returnString + w.phoneString;
		}
		return returnString;
	}
	
	public void printAllWords(){
		for(MobyWord w : words.sortedArray()){
			if(w.word.compareToIgnoreCase("collect") == 0)
				w.pastForm = w.pastForm;
			w.print();
		}
	}
	
	public void printAllVerbs(){
		for(MobyWord w : words.sortedArray()){
			if(w.word.compareToIgnoreCase("collect") == 0)
				w.pastForm = w.pastForm;
			w.printIfVerb();
		}
	}
	
	public void shiftChoose(){
		Random rand = new Random();
		String[] tenseList = {"PSVerb", "PPVerb", "FSVerb", "FPVerb", "PastVerb", 
							  "PrPVerb", "PaPVerb", "FuPVerb", "PaPerProVerb", "FuPerProVerb"};
		String a = tenseList[rand.nextInt(tenseList.length)];
		String b = tenseList[rand.nextInt(tenseList.length)];
		System.out.println(a);
		System.out.println(b);
		
	}
	
	public String punctuation(){
		Random rand = new Random();
		String[] tenseList = {".", "!", "?", "..."};
		String pMark = tenseList[rand.nextInt(tenseList.length)];
		return pMark;
		
	}
	

	public int getNumSyllables(String s){
		MobyWord w = this.findWord(s, words.root);
		if(w != null)
			return w.stressString.length();
		else
			return -1;
	}
	
  

	public String findWordWithPhones(String ph){
		DSElement<MobyWord> mwe = wordsList.first;
		while (mwe != null){
			String mws = mwe.getItem().phoneString;
			if(mws.length() > ph.length()){
				mwe = mwe.getNext();
				continue;
			}
			String phs = ph.substring(0, mws.length());
			if(mws.compareTo(phs) == 0){
				String w = mwe.getItem().word;
				return w;
			}
			else{
				mwe = mwe.getNext();
				continue;
			}
		}
		return null;
	}
		
	public String punctuation2(){
		Random rand = new Random();
		String[] tenseList = {",", " - ", " ", ":", ";"};
		String pMark = tenseList[rand.nextInt(tenseList.length)];
		return pMark;
		
	}
	
		
	public String meterSelect(){
		Random rand = new Random();
		String[] meterList = {"01", "1", "0101", "101", "10", "010"};
		String randMeter = meterList[rand.nextInt(meterList.length)];
		return randMeter;
	}
	
  
}
		
	
