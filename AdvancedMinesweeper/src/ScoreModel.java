
public class ScoreModel {
	private int value3BV;
	private int secondsPassed;
	private boolean[][] checkedZeros, zerosToCheck;
	private GameObjects[][] finalBoard;
	private int endScore;

	public ScoreModel(GameObjects[][] finalBoard) {
		this.secondsPassed = 0;
		this.finalBoard = finalBoard;

		// Calculates the most optimal solution using 3BV
		checkedZeros = new boolean[this.finalBoard.length][this.finalBoard[0].length];
		zerosToCheck = new boolean[this.finalBoard.length][this.finalBoard[0].length];

		for (int i = 0; i < this.finalBoard.length; i++) {
			for (int j = 0; j < this.finalBoard[i].length; j++) {

				if (this.finalBoard[i][j] instanceof Zero && !checkedZeros[i][j]) { // Checks how many zeros needs to be
																					// pressed
					checkZeroSequence(i, j);
					value3BV++;
				} else if (this.finalBoard[i][j] instanceof Number) { // Checks how many numbers needs to be pressed
					if (!canBeDiscoveredByBlank(i, j)) {
						value3BV++;
					}
				}
			}
		}
		this.endScore = value3BV * 10;
	}
	
	// Timing part of score
	public void incSeconds() {
		secondsPassed++;
	}

	// Checks whether the numbered square has any blank squares next to it
	public boolean canBeDiscoveredByBlank(int x, int y) {
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {

				if ((i != x || j != y) && i >= 0 && i < finalBoard.length && j >= 0 && j < finalBoard[i].length
						&& finalBoard[i][j] instanceof Zero) {
					return true;
				}
			}
		}
		return false;
	}

	public void checkZeroSequence(int x, int y) {		
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				
				// Makes sure always to be in bound of Array
				if ((i != x || j != y) && i >= 0 && i < finalBoard.length && j >= 0
						&& j < finalBoard[i].length) {

					if (finalBoard[i][j] instanceof Zero && !checkedZeros[i][j]) {
						zerosToCheck[i][j] = true;
					}
				}
			}
		}
		checkedZeros[x][y] = true;
		zerosToCheck[x][y] = false;
		
		// Makes sure that all the zero squares in the same sequence won't be counted again
				// By checking them all without adding to the 3BV score
				for (int i = 0; i < zerosToCheck.length; i++) {
					for (int j = 0; j < zerosToCheck[i].length; j++) {
						if (zerosToCheck[i][j]) {
							checkZeroSequence(i, j);
						}
					}
				}
			}


	public String getTimeElapsed() {
		String minutes = secondsPassed / 60 < 10 ? "0" + (secondsPassed / 60) : "" + (secondsPassed / 60);
		String seconds = secondsPassed % 60 < 10 ? "0" + (secondsPassed % 60) : "" + (secondsPassed % 60);

		String timeString = minutes + ":" + seconds;

		return timeString;
	}

	public int getScore() {
		if (secondsPassed % 5 == 0) {
			endScore -= 2;
			if (endScore < 0) {
				endScore = 0;
			}
		}
		return endScore;
	}

	public String calculateDifficulty() {
		String difficulty = "";
		int totalSquares = finalBoard.length * finalBoard[0].length;
		double difficultyRating = ((double) value3BV / totalSquares) * 100;

		if (difficultyRating < 5) {
			difficulty += "Very Easy";
		} else if (difficultyRating < 12) {
			difficulty += "Easy";
		} else if (difficultyRating < 20) {
			difficulty += "Medium";
		} else if (difficultyRating < 30) {
			difficulty += "Hard";
		} else if (difficultyRating < 50) {
			difficulty += "Very Hard";
		} else {
			difficulty += "Insane";
		}

		return difficulty;
	}

}