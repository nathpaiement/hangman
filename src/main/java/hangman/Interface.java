package hangman;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class takes care of all the console output.
 *
 * @author Nathanael Paiement
 */
class Interface {

	final static Scanner sc = new Scanner(System.in);

	/**
	 * Outputs the start menu
	 */
	static void startMenu() {

		System.out.println("\n\n\n");
		blockText("HANGMAN - guess the word");
		System.out.println("\n\n");

		startMenuDialog();
	}

	/**
	 * Outputs the start menu selections and does the selected action on input
	 */
	static void startMenuDialog() {

		String startOrResume = Hangman.gameSession != null ? "to start a new game/to resume the current game/"
				: "to start a new game/";
		String actionsStr = startOrResume + "to show the scoreboard/";
		actionsStr += "to show the introduction/";
		actionsStr += "to quit";

		String[] actions = actionsStr.split("/");

		int choice = actionsOnInput(actions);
		if (actions.length == 5) {
			choice -= 1;
		}

		switch (choice) {
		case 0:
			Game.resetGame();
			// continue to case 1
		case 1:
			clearConsole();
			Game.startGame();
			break;
		case 2:
			clearConsole();
			scoreTable();
			break;
		case 3:
			clearConsole();
			introduction();
			break;
		case 4:
			sc.close();
			System.out.println("\nThanks for playing!");
			System.exit(0);
		}
	}

	/**
	 * Outputs the introduction text
	 */
	static void introduction() {
		try {
			Thread.sleep(1500);
			System.out.println("Hello and welcome to Hangman!\n");
			Thread.sleep(1500);
			System.out.print("Your goal is to try to guess a word");

			Thread.sleep(1000);
			char[] charDelay = { ' ', 'o', 'n', 'e', ' ', 'l', 'e', 't', 't', 'e', 'r', ' ' };
			for (char c : charDelay) {
				System.out.print(c);
				Thread.sleep(200);
			}

			String[] strDelay = { "at ", "a ", "time.\n" };
			for (String s : strDelay) {
				System.out.print(s);
				Thread.sleep(750);
			}
			System.out.println("You can also guess the whole word for bonus points");
			System.out.println("if you're feeling lucky!\n");

			Thread.sleep(1500);
			System.out.print("If your guess is WRONG,");
			Thread.sleep(1000);
			System.out.println(" you lose a life.");
			Thread.sleep(2000);
			System.out.println("You get 6-8 lives at the start of each game");
			System.out.println("(depending on the difficulty).\n");
			Thread.sleep(2500);
			System.out.print("Of course if you run out of lives,");
			Thread.sleep(1000);

			strDelay = new String[] { " it's", " game", " OVER..." };
			for (String s : strDelay) {
				System.out.print(s);
				Thread.sleep(1000);
			}

			System.out.println("\n\nSo good luck!! ;)\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (actionsOnInput("to start a new game!") == 1) {
			clearConsole();
			Game.startGame();
		}
	}

	/**
	 * Outputs the interface for choosing the difficulty at the start of a
	 * round. Also sets the difficulty for the round. A game session must
	 * already be set.
	 */
	static void chooseDifficulty() throws IOException {

		String[] actions = new String[3];
		actions[0] = "for easy mode   - 4-5 letter words, 8 lives";
		actions[1] = "for normal mode - 6-9 letter words, 7 lives, 2x bonus";
		actions[2] = "for hard mode   - 10+ letter words, 6 lives, 3x bonus";

		blockText("NEW GAME");
		System.out.println("\n\nChoose your difficulty for this round:");
		int choice = actionsOnInput(actions);
		clearConsole();

		switch (choice) {
		case 1:
			Hangman.gameSession.setDifficulty(1);
			break;
		case 2:
			Hangman.gameSession.setDifficulty(2);
			break;
		case 3:
			Hangman.gameSession.setDifficulty(3);
			System.out.println("You chose HARD mode, so you only get " + Game.maxLives + " lives...");
			System.out.println("Good luck!");
			Interface.actionsOnInput("start the game!");
			break;
		}
	}

	/**
	 * Outputs the interface when guessing a word. A game session must already
	 * be set.
	 */
	static void guessWord() {
		int roundScore;
		int lives = Hangman.gameSession.getLives();
		String word = Hangman.gameSession.getWord();
		String goodGuesses = Hangman.gameSession.goodGuesses;
		String badGuesses = Hangman.gameSession.badGuesses;
		String[] currentGuesses = Hangman.gameSession.getGuesses();
		char[] splitWord = word.toCharArray();

		String livesLeftStr = lives != 1 ? " lives" : " life";
		String badGuessesStr = badGuesses.isEmpty() ? "" : " | Misses: " + badGuesses;
		blockText(word.length() + " letters | " + lives + "/" + Game.maxLives + livesLeftStr + " left" + badGuessesStr);

		if (lives < 1) {
			clearConsole();

			System.out.println("\tOh nooo, you ran out of lives :(");
			System.out.println("\tBetter luck next time!\n\n");
			blockText("Your word was " + word);

			int lettersGuessed = word.length() - Hangman.gameSession.lettersLeft;
			Hangman.player.addGameScore(lettersGuessed);
			roundScore = Hangman.player.getRoundScore();

			String highscoreStr = Hangman.player.setRoundScore() ? " (NEW HIGHSCORE!)" : "";
			String finalScore = "Final score: " + roundScore + " points" + highscoreStr;
			System.out.println("\n\n\t" + finalScore + "\n");

			Game.resetGame();

			if (actionsOnInput("to return to the main menu") == 1) {
				clearConsole();
				startMenu();
			}
		}

		Arrays.sort(currentGuesses);

		int lettersLeft = 0;
		String output = "";
		for (int i = 0; i < word.length(); i++) {
			String letter = splitWord[i] + "";

			if (goodGuesses.contains(letter)) {
				output += letter + " ";
			} else {
				output += "_ ";
				lettersLeft++;
			}
		}

		Hangman.gameSession.lettersLeft = lettersLeft;

		if (word.equals(output.replace(" ", ""))) {
			clearConsole();
			wonGame();
			return;
		}

		System.out.print("\n\t Make a guess: " + output);

		// Inline lives
		// System.out.print(" - ");
		// for (int i = 1; i <= Game.maxLives; i++) {
		// char heart = i > lives ? 'Ⅹ' : '♥';
		// System.out.print(heart);
		// }

		System.out.println();

		if (sc.hasNext()) {
			final String str = sc.next().toUpperCase();
			clearConsole();

			if (Arrays.binarySearch(currentGuesses, str) >= 0) {
				// Input string has already been guessed, try again
				System.out.println("\t'" + str + "' has already been guessed... Try again:\n");
				guessWord();
			} else if (word.equals(str)) {
				// Perfect whole word guess!
				clearConsole();
				wonGame();
			} else if (str.length() == 1 && word.contains(str)) {
				// Nice, a single letter has been guessed
				System.out.println("\tGood guess!\n");
				Hangman.gameSession.addGoodGuess(str);
				guessWord();
			} else if (str.equals("1337_HAX0R")) {
				System.out.println("Oh I see how it is... CHEATER :P");
				System.out.println("The word is: " + word + "\n\n");
				guessWord();
			} else {
				System.out.println("\tSorry, wrong guess :/\n");
				Hangman.gameSession.addBadGuess(str);
				Hangman.gameSession.setLives(lives - 1);
				guessWord();
			}
		}
	}

	/**
	 * Outputs the interface when the right word has been guessed. Also adds the
	 * score to the player's scoreboard. A game session must be set.
	 */
	static void wonGame() {
		String word = Hangman.gameSession.getWord();
		int wordScore = word.length();

		int lettersLeft = Hangman.gameSession.lettersLeft;
		int feelingLuckyBonus = 2 * (lettersLeft - 1);
		if (feelingLuckyBonus < 1) {
			feelingLuckyBonus = 0;
		}

		float livesLeft = Hangman.gameSession.getLives();
		float maxLives = Game.maxLives;
		int livesBonus = (int) (livesLeft / maxLives * 10);

		int multiplier = Game.multiplier;
		int score = (wordScore + livesBonus + feelingLuckyBonus) * multiplier;

		Hangman.player.addGameScore(score);
		System.out.println("\n\n");
		blockText("YOU WON! The word was indeed: " + word);

		String[] scoreText = blockTextArray("Current score for this round: " + Hangman.player.getRoundScore());
		System.out.println(scoreText[1] + scoreText[2]);

		System.out.format("%35s %-3s%n", "Guessing the word:", wordScore);
		// System.out.println("Bonus for directly guessing\nthe whole word: ");

		if (feelingLuckyBonus > 0) {
			System.out.format("%35s %-3s%n", "Bonus for guessing in one go:", feelingLuckyBonus);
		}

		if (livesBonus > 0) {
			System.out.format("%35s %-3s%n", "Bonus for lives left:", livesBonus);
		}

		System.out.format("%35s %-3s%n", "Multiplier:", multiplier);
		System.out.format("%35s %-3s%n%n", "Total:", score + " points");

		endGameDialog();
	}

	/**
	 * Outputs the player's scoreboard interface.
	 */
	static void scoreTable() {
		int[] sh = Hangman.player.getScoreHistory();

		System.out.println("\n");
		blockText("SCOREBOARD");
		System.out.println("\n");

		if (sh.length < 1) {
			System.out.println("No scores found :(\n");
			System.out.println("TIP:");
			System.out.println("You need to play until you run out of lives for your score to show!\n");
		} else {
			for (int i = sh.length - 1; i >= 0; i--) {
				System.out.println("> " + sh[i] + " points");
			}
		}

		if (actionsOnInput("to return to the main menu") == 1) {
			clearConsole();
			startMenu();
		}
	}

	/**
	 * Outputs the given String surrounded by a border. Use
	 * blockTextArray(String) to customize the output.
	 */
	static void blockText(String str) {
		String[] output = blockTextArray(str);

		for (String line : output) {
			System.out.print(line);
		}
	}

	/**
	 * Returns the given String surrounded by a border as an array consisting of
	 * each line
	 */
	static String[] blockTextArray(String str) {
		String[] output = new String[3];

		// top horizontal line
		String tHr = "";
		for (int i = -4; i < str.length(); i++) {
			tHr += "▀";
		}

		String bHr = tHr.replace('▀', '▄');

		// top part
		output[0] = "\t█" + tHr + "█\n";

		// left side + word + right side
		output[1] = "\t█  " + str + "  █\n";

		// bottom part
		output[2] = "\t█" + bHr + "█\n";

		return output;
	}

	/**
	 * Outputs the interface actions for when the right word couldn't be
	 * guessed.
	 */
	static void endGameDialog() {
		String[] actions = { "to guess the next word!", "to return to the main menu (points are saved)" };

		int choice = actionsOnInput(actions);

		switch (choice) {
		case 1:
			clearConsole();
			Game.startGame();
			break;
		case 2:
			clearConsole();
			startMenu();
			break;
		}
	}

	/**
	 * Outputs a silly message (randomly chosen) telling the user that their
	 * input isn't valid
	 */
	static void badUserInputMsg() {
		String[] messages = { "You don't follow instructions do you? :P", "Do you think this a a joke? :V",
				"I think you need to learn to read and use a keyboard correctly XD",
				"We don't accept this form of payment ;)",
				"Oh, you think you're that smart...\nLooking to crash me with invalid input >:)",
				"Feel free to waste your time not following instructions...\nI'm just a computer, so I don't care.",
				"You think this is a game? Oh wait... it is :P" };
		Random rand = new Random();
		int msgIndex = rand.nextInt(messages.length);

		System.out.println(messages[msgIndex]);
		System.out.println("Here, try again:");
	}

	/**
	 * Shortcut method for outputting a "> press [0] to continue" action
	 */
	static int continueAction() {
		return actionsOnInput("to continue");
	}

	/**
	 * Shortcut method for outputting a "> press [0] to [the action here]"
	 * action
	 */
	static int actionsOnInput(String action) {
		return actionsOnInput(new String[] { action });
	}

	/**
	 * Outputs a series of actions based on the actions given in the array.
	 * Repeats the action dialog on bad user input.
	 *
	 * @return the index of the user chosen action according to its position in
	 *         the given array.
	 */
	static int actionsOnInput(String[] actions) {
		int choice = -1;

		// Output line as wide as first action
		for (int i = -12; i < actions[0].length(); i++) {
			System.out.print("═");
		}

		// Output action
		for (int i = 0; i < actions.length; i++) {
			System.out.print("\n> Press [" + (i + 1) + "] " + actions[i]);
		}

		System.out.println();

		if (sc.hasNext()) {

			try {
				choice = sc.nextInt();
			} catch (InputMismatchException e) {
				sc.next();
			}

			if (choice > 0 && choice <= actions.length) {
				return choice;
			} else {
				clearConsole();
				badUserInputMsg();
				return actionsOnInput(actions);
			}
		}

		return choice;
	}

	/**
	 * Outputs many newlines to "clear" the console
	 */
	static void clearConsole() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}
}
