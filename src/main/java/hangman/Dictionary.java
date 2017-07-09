package hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * This class mainly allows getting a random word out of a given dictionary
 *
 * @author Nathanael Paiement
 */
public class Dictionary {

	final static public Path DICTIONARY_DIR = Paths.get("src/main/resources/dictionaries");
	final static public Path PARSED_DICTIONARY_DIR = DICTIONARY_DIR.resolve("parsed/en_US");

	final Name mDictionary;
	final protected int wordCount;


	/**
	 * Available dictionaries
	 */
	public enum Name {

		ORIGINAL(DICTIONARY_DIR.resolve("en_US.txt")),
		SHORT(PARSED_DICTIONARY_DIR.resolve("short.txt")),
		MEDIUM(PARSED_DICTIONARY_DIR.resolve("medium.txt")),
		LONG(PARSED_DICTIONARY_DIR.resolve("long.txt"));

		private Path dicPath;

		Name(Path path) {
			dicPath = path;
		}

		public Path getPath() {
			return dicPath;
		}
	}

	Dictionary(Name dictionary) throws IOException {
		mDictionary = dictionary;
		if (!Files.isRegularFile(mDictionary.getPath())) {
			throw new IOException("The given dictionary is not available");
		}
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

		BufferedReader r = null;
		String path = System.getProperty("java.class.path");

		if (!path.equals("Hangman.jar")) {
			r = Files.newBufferedReader(mDictionary.getPath());
		} else {
			r = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(mDictionary.getPath().toString())));
		}

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
