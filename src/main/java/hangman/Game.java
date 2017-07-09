package hangman;

import java.io.IOException;

/**
 * This class manages the current word to guess. Stores the guesses, lives,
 * word, difficulty and the transient score.
 *
 * @author Nathanael Paiement
 */
class Game {

	// Difficulty doesn't change between games won
	protected static int difficulty = -1;
	protected static int multiplier = -1;
	protected static int maxLives = -1;
	protected static Dictionary dic;

	protected int livesLeft = 0;
	protected int lettersLeft = 0;
	protected int score = 0;
	protected String word;
	protected String badGuesses = "";
	protected String goodGuesses = "";

	Game() {
	}

	public static void startGame() {

		Game gs = Hangman.gameSession = new Game();

		if (difficulty == -1) {
			try {
				Interface.chooseDifficulty();
			} catch (IOException e) {
				return;
			}
		}

		gs.livesLeft = maxLives;
		try {
			gs.word = dic.getRandWord().toUpperCase();
		} catch (IOException e) {
			System.err.println("Error: There was a problem getting a random word from the dictionary.");
			return;
		}
		gs.addGoodGuess(gs.word.charAt(0) + "");

		Interface.clearConsole();
		Interface.guessWord();
	}

	public static void resetGame() {
		Hangman.gameSession = null;
		Hangman.player.roundScore = 0;
		difficulty = -1;
	}

	/**
	 * @return the lives
	 */
	int getLives() {
		return livesLeft;
	}

	public String[] getGuesses() {
		String guesses = goodGuesses.isEmpty() ? badGuesses : badGuesses + " " + goodGuesses;
		return guesses.split(" ");
	}

	public String getWord() {
		return word;
	}

	public void addBadGuess(String guess) {
		String guessStr = badGuesses.isEmpty() ? guess : " " + guess;
		badGuesses += guessStr;
	}

	public void addGoodGuess(String guess) {
		String guessStr = goodGuesses.isEmpty() ? guess : " " + guess;
		goodGuesses += guessStr;
	}

	/**
	 * @param lives
	 *            the lives to set
	 */
	void setLives(int lives) {
		this.livesLeft = lives;
	}

	boolean setDifficulty(int difficulty) throws IOException {

		Game.difficulty = difficulty;

		switch (difficulty) {
		case 1:
			dic = new Dictionary(Dictionary.Name.SHORT);
			multiplier = 1;
			maxLives = 8;
			return true;
		case 2:
			dic = new Dictionary(Dictionary.Name.MEDIUM);
			multiplier = 2;
			maxLives = 7;
			return true;
		case 3:
			dic = new Dictionary(Dictionary.Name.LONG);
			multiplier = 3;
			maxLives = 6;
			return true;
		default:
			return false;
		}
	}

	/**
	 * @return the difficulty
	 */
	int getDifficulty() {
		return difficulty;
	}

}
