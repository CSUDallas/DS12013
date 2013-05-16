package dsmoby;

import java.util.regex.Pattern;

public class MobyWord implements Comparable<MobyWord> {
	public String		word;		// The word or word phrase
	public String		singularForm;
	public String 		pluralForm;
	public String		baseVerbForm;
	public String		pastForm;
	public String		pastParticipleForm;
	public String 		ingForm;
	public String		sForm;
	public String		baseAd;
	public String 		comparativeForm;
	public String		superlativeForm;

	public int			numStrings;	// How many in the phrase?

	public String		phoneString;// from CMU pronunciation dictionary
	public String 		stressString;// 2=secondary stress, 1=primary, 0=none
	public int 		scowlValue;	// From the scowl dictionary / word lists
	private String		posString;	// all parts of speech

	public boolean 	isProper;	// Is it a proper noun?
	public boolean		isNoun;
	public boolean 	isPlural;
	public boolean 	isNounPhrase;
	public boolean 	isVerb;		// usually a participle
	public boolean 	isVerbT;	// transitive verb
	public boolean 	isVerbI;	// Intransitive verb
	public boolean 	isPastTense;
	public boolean 	isPastParticiple;
	public boolean 	isIngForm;
	public boolean 	isSForm;
	public boolean 	isAdjective;
	public boolean 	isComparative;
	public boolean 	isSuperlative;
	public boolean		isAdverb;
	public boolean		isConjunction;
	public boolean		isPreposition;
	public boolean		isInterjection;
	public boolean		isPronoun;
	public boolean		isDefArticle;
	public boolean		isIndefArticle;
	public boolean		isNominative;

	public DSLinkedList<MobyWord> 	synonyms;
	public DSLinkedList<MobyWord> nextWords;	// Reid Hansen's project

	public MobyWord(){
		word = "";
		singularForm = "";
		pluralForm = "";
		baseVerbForm = "";
		pastForm = "";
		pastParticipleForm = "";
		ingForm = "";
		sForm = "";
		baseAd = "";
		comparativeForm = "";
		superlativeForm = "";
		numStrings = 0;
		scowlValue = 100;
		phoneString = "";
		stressString = "";
		posString = "";
		isProper = false;
		isNoun = false;
		isPlural = false;
		isNounPhrase = false;
		isVerb = false;
		isVerbT = false;
		isVerbI = false;
		isPastTense = false;
		isPastParticiple = false;
		isIngForm = false;
		isSForm = false;
		isAdjective = false;
		isComparative = false;
		isSuperlative = false;
		isAdverb = false;
		isConjunction = false;
		isPreposition = false;
		isInterjection = false;
		isPronoun = false;
		isDefArticle = false;
		isIndefArticle = false;
		isNominative = false;
		synonyms = new DSLinkedList<MobyWord>();
		nextWords = new DSLinkedList<MobyWord>();
	}


	/*
	 * Input: String such as:   heaven-sent 'h/E/v/@/n_s/E/nt
	 * Sets the word, numStrings and phones.
	 * 
	 */
	public void setPron(String inString){
		// Split by spaces
		String[] parts = inString.split(" ");

		// First we get the word
		word = parts[0];
		word = word.replaceAll("\\([012 ]\\)", "");

		// clean up and set the phones
		for(int i = 1; i < parts.length; i++){
			String p = parts[i];
			if(p.endsWith("0"))
				stressString += "0";
			if(p.endsWith("1"))
				stressString += "1";
			if(p.endsWith("2"))
				stressString += "2";
			p = p.replaceAll("[012]", "");
			phoneString = phoneString + p;
			if(p.length() == 1)
				phoneString += " ";
		}
		//System.out.println(word + "--" + phoneString + "--" + stressString);

	}

	public void setPosString(String p){
		posString = new String(p);
		isProper 		= false;//XXX Needs to be done
		isNoun 			= p.contains("N");
		isPlural 		= p.contains("P");
		isNounPhrase 	= p.contains("h");
		isVerb 			= p.contains("V") || p.contains("t") || p.contains("i");
		isVerbT 		= p.contains("t");
		isVerbI 		= p.contains("i");
		isAdjective 	= p.contains("A");
		isAdverb 		= p.contains("v");
		isConjunction 	= p.contains("C");
		isPreposition 	= p.contains("P");
		isInterjection 	= p.contains("!");
		isPronoun 		= p.contains("r");
		isDefArticle 	= p.contains("D");
		isIndefArticle 	= p.contains("I");
		isNominative 	= p.contains("o");
	}
	


	public int compareTo(MobyWord w){
		return word.compareToIgnoreCase(w.word);
	}

	/*
	 * Returns true if this word is no longer than the stresses
	 * string, and if the stresses string dominates this string's
	 * stresses.
	 */
	public boolean fits(String stresses, boolean exact){
		if(stresses.equals(""))
			return true;
		if(stresses.length() < stressString.length() ||
				(exact && stresses.length() != stressString.length()))
			return false;

		for(int i = 0; i < stressString.length(); i++){
			char me = stressString.charAt(i);
			char it = stresses.charAt(i);
			if((it == '0' && me == '1') || (it == '0' && me == '2'))// ||
				//(it == '2' && me == '1'))
				return false;
		}
		return true;

	}


	/*
	 * Returns true if the part of speech pos is either
	 *   - the empty string
	 *   - or contained in this MobyWord's posString
	 *   
	 *   pos can contain many chars, in which case we return true if any
	 *   of the chars is in the word's pos string.
	 */
	public boolean isPos(String pos){
		if(pos.equals(""))
			return true;
		for(int i = 0; i < pos.length(); i++)
			if(posString.contains(pos.substring(i, i+1)))
				return true;
		return false;
	}


	/*
	 * Returns true if the input sequence of phones matches
	 * exactly the end of this MobyWord's sequence of phones
	 */
	public boolean rhymesWith(String phones){
		if(phones.equals(""))
			return true;

		if(phoneString.endsWith(phones))
			return true;
		else
			return false;
	}

	public void print(){
		System.out.println("word = " + word);
		System.out.println("singularForm = " + singularForm);
		System.out.println("pluralForm = " + pluralForm);
		System.out.println("baseVerbForm = " + baseVerbForm);
		System.out.println("pastForm = " + pastForm);
		System.out.println("pastParticipleForm = " + pastParticipleForm);
		System.out.println("ingForm = " + ingForm);
		System.out.println("sForm = " + sForm);
		System.out.println("baseAd = " + baseAd);
		System.out.println("comparativeForm = " + comparativeForm);
		System.out.println("superlativeForm = " + superlativeForm);
		System.out.println("numStrings = " + numStrings);
		System.out.println("scowlValue = " + scowlValue);
		System.out.println("phoneString = " + phoneString);
		System.out.println("stressString = " + stressString);
		System.out.println("posString = " + posString);
		System.out.println("isProper = " + isProper);
		System.out.println("isNoun = " + isNoun);
		System.out.println("isPlural = " + isPlural);
		System.out.println("isNounPhrase = " + isNounPhrase);
		System.out.println("isVerb = " + isVerb);
		System.out.println("isVerbT = " + isVerbT);
		System.out.println("isVerbI = " + isVerbI);
		System.out.println("isPastTense = " + isPastTense);
		System.out.println("isPastParticiple = " + isPastParticiple);
		System.out.println("isIngForm = " + isIngForm);
		System.out.println("isSForm = " + isSForm);
		System.out.println("isAdjective = " + isAdjective);
		System.out.println("isComparative = " + isComparative);
		System.out.println("isSuperlative = " + isSuperlative);
		System.out.println("isAdverb = " + isAdverb);
		System.out.println("isConjunction = " + isConjunction);
		System.out.println("isPreposition = " + isPreposition);
		System.out.println("isInterjection = " + isInterjection);
		System.out.println("isPronoun = " + isPronoun);
		System.out.println("isDefArticle = " + isDefArticle);
		System.out.println("isIndefArticle = " + isIndefArticle);
		System.out.println("isNominative = " + isNominative);
		System.out.println("");
	}
	
	public void printIfVerb(){
		if(!this.isVerb)
			return;
		System.out.println("word = " + word);
		System.out.println("singularForm = " + singularForm);
		System.out.println("pluralForm = " + pluralForm);
		System.out.println("baseVerbForm = " + baseVerbForm);
		System.out.println("pastForm = " + pastForm);
		System.out.println("pastParticipleForm = " + pastParticipleForm);
		System.out.println("ingForm = " + ingForm);
		System.out.println("sForm = " + sForm);
		System.out.println("baseAd = " + baseAd);
		System.out.println("comparativeForm = " + comparativeForm);
		System.out.println("superlativeForm = " + superlativeForm);
		System.out.println("numStrings = " + numStrings);
		System.out.println("scowlValue = " + scowlValue);
		System.out.println("phoneString = " + phoneString);
		System.out.println("stressString = " + stressString);
		System.out.println("posString = " + posString);
		System.out.println("isProper = " + isProper);
		System.out.println("isNoun = " + isNoun);
		System.out.println("isPlural = " + isPlural);
		System.out.println("isNounPhrase = " + isNounPhrase);
		System.out.println("isVerb = " + isVerb);
		System.out.println("isVerbT = " + isVerbT);
		System.out.println("isVerbI = " + isVerbI);
		System.out.println("isPastTense = " + isPastTense);
		System.out.println("isPastParticiple = " + isPastParticiple);
		System.out.println("isIngForm = " + isIngForm);
		System.out.println("isSForm = " + isSForm);
		System.out.println("isAdjective = " + isAdjective);
		System.out.println("isComparative = " + isComparative);
		System.out.println("isSuperlative = " + isSuperlative);
		System.out.println("isAdverb = " + isAdverb);
		System.out.println("isConjunction = " + isConjunction);
		System.out.println("isPreposition = " + isPreposition);
		System.out.println("isInterjection = " + isInterjection);
		System.out.println("isPronoun = " + isPronoun);
		System.out.println("isDefArticle = " + isDefArticle);
		System.out.println("isIndefArticle = " + isIndefArticle);
		System.out.println("isNominative = " + isNominative);
		System.out.println("");
	}


}
