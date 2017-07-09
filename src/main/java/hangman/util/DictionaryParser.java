package hangman.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import hangman.Dictionary;

/**
 * This class parses http://app.aspell.net/create generated dictionaries and
 * inserts the words in a short, medium, or long dictionary depending on its
 * length.
 *
 * @author Nathanael Paiement
 */
class DictionaryParser {

	final static public Path DICTIONARY_DIR = Paths.get("dictionaries");
	final static public Path PARSED_DICTIONARY_DIR = Paths.get("src/main/resources/dictionaries/en_US");

	public static void main(String[] args) {

		// First pass at reading the file to get its number of lines
		try (BufferedReader br = Files.newBufferedReader(DICTIONARY_DIR.resolve("en_US.txt"))) {

			Files.createDirectories(PARSED_DICTIONARY_DIR);

			StandardOpenOption[] options = { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING };
			BufferedWriter s_bw = Files.newBufferedWriter(PARSED_DICTIONARY_DIR.resolve(Dictionary.Name.SHORT.getName()), options);
			BufferedWriter m_bw = Files.newBufferedWriter(PARSED_DICTIONARY_DIR.resolve(Dictionary.Name.MEDIUM.getName()), options);
			BufferedWriter l_bw = Files.newBufferedWriter(PARSED_DICTIONARY_DIR.resolve(Dictionary.Name.LONG.getName()), options);

			String line;
			boolean skip = true;
			while ((line = br.readLine()) != null) {

				// skip the license, short lines and lines containing non-word characters (e.g. apostrophe)
				int length = line.length();
				if (skip || length < 3 || line.matches(".*\\W+.*")) {
					if (line.contains("---")) skip = false;
					continue;
				}

				if (length < 6) {
					// Put shorter words in short dictionary
					s_bw.write(line + '\n');
				} else if (length > 5 && length < 9) {
					// Put medium words in medium dictionary
					m_bw.write(line + '\n');
				} else if (length > 8) {
					// Put longer words in long dictionary
					l_bw.write(line + '\n');
				}
			}

			s_bw.close();
			m_bw.close();
			l_bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
