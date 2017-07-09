package hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

/**
 * This class mainly allows getting a random word out of a given dictionary
 *
 * @author Nathanael Paiement
 */
public class Dictionary {

	final Name mDictionary;
	final protected int wordCount;

	/**
	 * Available dictionaries
	 */
	public enum Name {

		SHORT("short.txt"),
		MEDIUM("medium.txt"),
		LONG("long.txt");

		private String mName;

		Name(String name) {
			mName = name;
		}

		public String getName() {
			return mName;
		}

		public URL getResource() {
			return getClass().getClassLoader().getResource("dictionaries/en_US/" + mName);
		}
	}

	Dictionary(Name dictionary) throws IOException {
		mDictionary = dictionary;
		wordCount = getWordCount();
	}

	public int getWordCount() throws IOException {
		if (wordCount != 0) {
			return wordCount;
		}

		int lineCount = 0;

		// First pass at reading the file to get its number of lines
		try (BufferedReader br = getFileReader()) {

			while (br.readLine() != null) {
				lineCount++;
			}
		}

		return lineCount;
	}

	protected BufferedReader getFileReader() throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(mDictionary.getResource().openStream()));
		return r;
	}

	public String getRandWord() throws IOException {

		Random rand = new Random();
		String line = "";

		// Second pass at reading the file to get the selected line
		try (BufferedReader br = getFileReader()) {

			int selectedLine = rand.nextInt(getWordCount());
			int i = 0;

			while ((line = br.readLine()) != null) {
				if (i == selectedLine) {
					break;
				}
				i++;
			}
		}

		return line;
	}

}
