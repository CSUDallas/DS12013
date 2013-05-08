package dsmoby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SyllableWorker<E> {
	String newSyllable = "";
	int x = 0;
	
	public SyllableWorker(){
		this("", null);
	}

	public SyllableWorker(String sFile, Moby m){

		if(sFile == "")
			return;
		
		try { 
			FileReader f = new FileReader(sFile);
			BufferedReader reader = new BufferedReader(f);
			String line = null;

			while ((line = reader.readLine()) != null && 
					line.trim().compareTo("--END--") != 0){
				
				//String parts[] = line.split("=");
				//String p1 = parts[0];
				//String p2 = parts[1];
				
				
				StringTokenizer tokenizer = new StringTokenizer(line, "=");
				String word = tokenizer.nextToken();
				//System.out.println(tokenizer.countTokens() + line);
				//String word = tokenizer.nextToken();
				StringTokenizer tokenizer1 = new StringTokenizer(tokenizer.nextToken(), "·");
				int syllables = tokenizer1.countTokens();
				x++;
				newSyllable += word + " " + syllables + "\n";
				int sss = m.getNumSyllables(word);
				if(syllables != sss){
					System.out.println("Mismatch: " + word + " " + syllables + " vs. " + sss);
				}
			}

		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		
		
		
		//while(tokenizer.hasMoreTokens())
		//{
		//	newSyllable += tokenizer.nextToken() + "\n";
		//}
		
	}
	
	public int syllableCountWord(String wrd)
	{
		int numSyl = 1;
		//System.out.println(wrd);
		String word = wrd.toLowerCase();
		
		StringTokenizer tokenizer = new StringTokenizer(newSyllable, "\n");
		
		while(tokenizer.hasMoreTokens())
		{
			StringTokenizer toka = new StringTokenizer(tokenizer.nextToken(), " ");
			if(word.equals(toka.nextToken().toLowerCase()))
			{
				numSyl = Integer.parseInt(toka.nextToken());
				return numSyl; 
			}
		}
		
		return numSyl;
	}
	
	public String syllableCountSyllables(int syl, int dig)
	{
		int numSyl = syl;
		String word = null;
		
		StringTokenizer tokenizer = new StringTokenizer(newSyllable, "\n");
		
		for(int x = 0; x < dig; x++)
		{
			tokenizer.nextToken();
		}
		
		while(tokenizer.hasMoreTokens())
		{
			StringTokenizer toka = new StringTokenizer(tokenizer.nextToken(), " ");
			String ww = toka.nextToken();
			int nn = Integer.parseInt(toka.nextToken());
			
			if(nn == syl)
			{
				word = ww;
				return word;
			}
		}
		
		return word;
	}
	
	public void printSyllable()
	{
		System.out.println(newSyllable);
	}
	
	public int countWords()
	{
		return x;
	}
}

