public class ScoreModel {
	private int value3BV;
	private int secondsPassed;
	private GameObjects[][] finalBoard;
	private int endScore;
	private boolean[][] checked;

	public ScoreModel(GameObjects[][] finalBoard, int mineCount) {
		this.secondsPassed = 0;
		this.finalBoard = finalBoard;
		
		checked = new boolean[finalBoard.length][finalBoard[0].length];
		value3BV = getTotalFields() - mineCount - getZeroNeighbours() + getZeroBlocks();
		endScore = value3BV * 10;
		System.out.println(value3BV);
	}
	
	public int getZeroBlocks() {
		int counter = 0;
		for(int i = 0; i < finalBoard.length; i ++) {
			for (int j = 0; j < finalBoard[i].length; j++) {					
				if (finalBoard[i][j] instanceof Zero && !checked[i][j]) {
					counter++;
					checked[i][j] = true;
					
					checkNeighbours(i, j);
				}
			}
		}
		return counter;
	}
	
	public void checkNeighbours(int x, int y) {
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i >= 0 && i < finalBoard.length && j >= 0 && j < finalBoard[i].length) {
					if (finalBoard[i][j] instanceof Zero && !checked[i][j]) {
						checked[i][j] = true;
						checkNeighbours(i, j);
					}
				}
			}
		}	
	}
	
	public int getZeroNeighbours() {
		int counter = 0;
		for(int i = 0; i < finalBoard.length; i ++) {
			for (int j = 0; j < finalBoard[i].length; j++) {
				if (finalBoard[i][j] instanceof Number || finalBoard[i][j] instanceof Zero) {
					
					// Checks every neighbouring tile if they are of the type Zero
					neighbourCheckingLoop:
					for (int k = i - 1; k <= i + 1; k++) {
						for (int l = j - 1; l <= j + 1; l++) {
							if (k >= 0 && k < finalBoard.length && l >= 0 && l < finalBoard[k].length) {
								if (finalBoard[k][l] instanceof Zero) {
									counter++;
									break neighbourCheckingLoop;
								}
							}
						}
					}
				}
			}
		}
		return counter;
	}

	public int getTotalFields() {
		return finalBoard.length * finalBoard[0].length;
	}
	
	// Timing part of score
	public void incSeconds() {
		secondsPassed++;
	}
	
	public void decreaseHintScore() {
		endScore-= 15;
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
		double difficultyRating = ((double) value3BV / this.getTotalFields()) * 100;

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
	
	public int get3BV() {
		return value3BV;
	}

}