package hangman.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
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

	public static void main(String[] args) {

		// First pass at reading the file to get its number of lines
		try (BufferedReader br = Files.newBufferedReader(Dictionary.Name.ORIGINAL.getPath())) {

			Files.createDirectories(Dictionary.PARSED_DICTIONARY_DIR);

			StandardOpenOption[] options = { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING };
			BufferedWriter s_bw = Files.newBufferedWriter(Dictionary.Name.SHORT.getPath(), options);
			BufferedWriter m_bw = Files.newBufferedWriter(Dictionary.Name.MEDIUM.getPath(), options);
			BufferedWriter l_bw = Files.newBufferedWriter(Dictionary.Name.LONG.getPath(), options);

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
