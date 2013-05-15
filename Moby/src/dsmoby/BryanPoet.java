package dsmoby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BryanPoet {
	static Moby moby;

	public static void main(String[] args) {
		moby = new Moby("cmupronRand.txt", 
				"mpos.txt",
				"flist.txt",
				"infl.txt");
		moby.setScowlThreshold(60);

		String scheme = readPoem("Frost1.txt");
		generatePoemWithScheme(scheme);
	}

	private static String readPoem(String poemFile){
		try { 
			FileReader f = new FileReader(poemFile);
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			while ((line = reader.readLine()) != null) {
				String parts[] = line.split(" ");
				String lastWord = parts[parts.length - 1];
				parts[parts.length - 1] = lastWord;
				String newLine = "";
				for(int i = 0; i < parts.length; i++)
					newLine = newLine + parts[i].replaceAll("\\W", "") + " ";
				System.out.println(newLine);
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		return "";
	}
	
	private static void generatePoemWithScheme(String scheme){
		
		
	}
}
