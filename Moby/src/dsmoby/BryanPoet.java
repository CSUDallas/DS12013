package dsmoby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BryanPoet {
	static Moby moby;
	static String rhymeScheme;
	static DSLinkedList<String> rhymePhones;
	static char nextLetter;

	public static void main(String[] args) {
		moby = new Moby("cmupronRand.txt", 
				"mpos.txt",
				"flist.txt",
				"infl.txt");
		moby.setScowlThreshold(60);

		nextLetter = 'A';
		String scheme = buildRhymeScheme("Frost1.txt");
		generatePoemWithScheme("Frost1.txt");
	}

	public static String buildRhymeScheme(String poemFile){
		rhymeScheme = "";
		rhymePhones = new DSLinkedList<String>();
		try { 
			FileReader f = new FileReader(poemFile);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			while ((line = reader.readLine()) != null) {
				String parts[] = line.split(" ");
				String lastWord = parts[parts.length - 1].replaceAll("\\W", "");
				parts[parts.length - 1] = lastWord;
				/*
				String newLine = "";
				for(int i = 0; i < parts.length; i++){
					if(i == parts.length - 1)
						newLine = newLine + parts[i].replaceAll("\\W", "") + " ";
					else
						newLine = newLine + parts[i] + " ";		
				}
				 */

				String rhyme = moby.getRhymePhones(lastWord);
				DSElement<String> r = rhymePhones.first;
				int count = 0;
				boolean alreadyFound = false;
				while(r != null){
					if (r.getItem().compareToIgnoreCase(rhyme) != 0){
						if(r.getNext() == null)
							break;
						r = r.getNext();
						count ++;
					}else{
						rhymeScheme += rhymeScheme.charAt(count);
						alreadyFound = true;
						//System.out.println("rs: " + rhymeScheme + ", COUNT: " + count);
						break;
					} 
				}
				if(!alreadyFound){
					rhymeScheme = rhymeScheme + nextLetter;
					nextLetter ++;
					//System.out.println("rs: " + rhymeScheme + ", COUNT: " + count);
				} 
				rhymePhones.addLast(rhyme);
				// Build Poem with New Rhyme Scheme

				System.out.println(rhymeScheme);
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}

		return "";
	}

	public static void generatePoemWithScheme(String poemFile){
		DSLinkedList<String> rhymeList = new DSLinkedList<String>();
		String random = moby.getWord("", "1", "", false);
		//System.out.println(random);
		moby.getRhymePhones(random);
		rhymeList.addLast(random);
		boolean alreadyFound = false;
		int i = 1;
		DSElement<String> r = rhymeList.first;   // Where you are now
		while( i < rhymeScheme.length()){
			r = rhymeList.last;
			alreadyFound = false;
			for(int n = i-1; n >= 0; n --){
				if(rhymeScheme.charAt(i) == rhymeScheme.charAt(n)){
					String match = moby.getWord("", "1", moby.getRhymePhones(r.getItem()), false);
					rhymeList.addLast(match);
					alreadyFound = true;
					break;
				}  // End of for loop
				r = r.getPrevious();
			}
			if(!alreadyFound){
				String newRandom = moby.getWord("", "1", "", false);
				rhymeList.addLast(newRandom);
			}
			i++;
			rhymeList.printList();
		}
		try { 
			FileReader f = new FileReader(poemFile);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			r = rhymeList.first;
			while ((line = reader.readLine()) != null) {
				String parts[] = line.split(" ");
				String lastWord = parts[parts.length - 1].replaceAll("\\W", "");
				parts[parts.length - 1] = lastWord;
				String newPoem = "";
				for(int k = 0; k < parts.length; k++){
					if(k == parts.length - 1){
						newPoem = newPoem + r.getItem();
						break;
					}else{
						newPoem = newPoem + parts[k] + " ";
					}
				} 
				r = r.getNext();

				System.out.println(newPoem);
				
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}

	}
}
