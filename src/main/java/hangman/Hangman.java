package hangman;

/**
 * Hangman - guess the word one letter at a time.
 * https://en.wikipedia.org/wiki/Hangman_(game)
 *
 * @author Nathanael Paiement
 */
public final class Hangman {

	static Game gameSession;
	final static Player player = new Player();

	public static void main(String[] args) {

		// Start the game!
		Interface.startMenu();
	}
}
