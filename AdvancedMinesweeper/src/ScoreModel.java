/*
 * Lavet af
 * 		Kevin Moore s204462
 *
 */
public class ScoreModel {
	private GameObjects[][] finalBoard;
	private boolean[][] checkedTiles;
	private int value3BV;
	
	private int secondsPassed, endScore;
	
	public ScoreModel(GameObjects[][] finalBoard, int mineCount) {
		this.secondsPassed = 0;
		this.finalBoard = finalBoard;
		int totalFields = finalBoard.length * finalBoard[0].length;
		
		checkedTiles = new boolean[finalBoard.length][finalBoard[0].length];
		value3BV = totalFields - mineCount - getZeroNeighbours() + getZeroBlocks();
		
		endScore = value3BV * 10;
	}
	
	// Calculates the amount of zeros sequenced next to each other
	public int getZeroBlocks() { 
		int counter = 0;
		for(int i = 0; i < finalBoard.length; i ++) {
			for (int j = 0; j < finalBoard[i].length; j++) {					
				if (finalBoard[i][j] instanceof Zero && !checkedTiles[i][j]) {
					counter++;
					checkedTiles[i][j] = true;
					
					checkNeighbours(i, j); // Checks so the same block is not counted twice
				}
			}
		}
		return counter;
	}
	
	// Adds all neighbouring blocks of type zero to checkedTiles array
	public void checkNeighbours(int x, int y) {
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i >= 0 && i < finalBoard.length && j >= 0 && j < finalBoard[i].length) {
					if (finalBoard[i][j] instanceof Zero && !checkedTiles[i][j]) {
						checkedTiles[i][j] = true;
						checkNeighbours(i, j);
					}
				}
			}
		}	
	}
	
	// Returns the amount of tiles that have a Zero neighbour
	public int getZeroNeighbours() {
		int zeroNeighbourCounter = 0;
		for(int i = 0; i < finalBoard.length; i ++) {
			for (int j = 0; j < finalBoard[i].length; j++) {
				if (finalBoard[i][j] instanceof Number || finalBoard[i][j] instanceof Zero) {
					
					// Checks every neighbouring tile of target tile if they are of the type Zero
					neighbourCheckingLoop:
					for (int k = i - 1; k <= i + 1; k++) {
						for (int l = j - 1; l <= j + 1; l++) {
							if (k >= 0 && k < finalBoard.length && l >= 0 && l < finalBoard[k].length) {
								if (finalBoard[k][l] instanceof Zero) {
									zeroNeighbourCounter++;
									break neighbourCheckingLoop;
								}
							}
						}
					}
				}
			}
		}
		return zeroNeighbourCounter;
	}
	
	public int get3BV() {
		return value3BV;
	}
	
	// Timing part of score
	public void incSeconds() {
		secondsPassed++;
	}
	public String getTimeElapsed() {
		String minutes = secondsPassed / 60 < 10 ? "0" + (secondsPassed / 60) : "" + (secondsPassed / 60);
		String seconds = secondsPassed % 60 < 10 ? "0" + (secondsPassed % 60) : "" + (secondsPassed % 60);

		String timeString = minutes + ":" + seconds;

		return timeString;
	}
	
	public void decreaseHintScore() { // decreases score in case user presses hint button
		endScore-= 15;
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

		if (value3BV < 5) {
			difficulty += "Beginner";
		} else if (value3BV < 12) {
			difficulty += "Easy";
		} else if (value3BV < 30) {
			difficulty += "Medium";
		} else if (value3BV < 50) {
			difficulty += "Hard";
		} else if (value3BV < 60) {
			difficulty += "Very Hard";
		} else if (value3BV < 100) {
			difficulty += "Intermediate";
		} else if (value3BV < 150) {
			difficulty += "Expert";
		} else {
			difficulty += "Insane";
		}

		return difficulty;
	}

}