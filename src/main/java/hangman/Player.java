package hangman;

import java.util.Arrays;

/**
 * This class manages the currents player. Stores the highscore and the score
 * history
 *
 * @author Nathanael Paiement
 */
class Player {

	protected int highscore = 0;
	protected String scoreHistory = "";
	protected int roundScore = 0;

	Player() {
		getPlayerSettings();
	}

	protected void getPlayerSettings() {
		// TODO: Read player settings form file
	}

	public int getHighscore() {
		return highscore;
	}

	public int[] getScoreHistory() {
		if (scoreHistory.isEmpty()) {
			return new int[0];
		} else {
			String[] sh = scoreHistory.split(" ");
			int[] shInt = new int[sh.length];

			for (int i = 0; i < sh.length; i++) {
				shInt[i] = Integer.parseInt(sh[i]);
			}

			Arrays.sort(shInt);
			return shInt;
		}
	}

	public boolean setRoundScore() {

		scoreHistory += scoreHistory.isEmpty() ? roundScore : " " + roundScore;

		// woot, new highscore!
		if (roundScore > highscore) {
			highscore = roundScore;
			return true;
		}

		return false;
	}

	public int getRoundScore() {
		return roundScore;
	}

	public void addGameScore(int score) {
		roundScore += score;
	}
}
