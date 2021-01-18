import java.util.Random;

public class GameModel {
	public boolean gameOver;
	
	private int xSize, ySize, mineCount;
	
	private boolean[][] mines;
	private int displayedFields;
	
	private GameObjects[][] currentBoard, finalBoard;
	private ScoreModel scoreModel;
	private int lastX, lastY;

	// Constructing a GameModel object, with two boards of the given size and mines.
	public GameModel(int n, int m, int mineCount) {
		this.xSize = n;
		this.ySize = m;
		this.mineCount = mineCount;

		this.currentBoard = new GameObjects[n][m]; // representing the board displayed to the player
		this.finalBoard = new GameObjects[n][m]; // representing the solution
		this.mines = new boolean[n][m];

		this.mines = genMines(mineCount);
		this.finalBoard = fillFinalBoard();

		this.gameOver = false;
		this.displayedFields = 0;

		this.scoreModel = new ScoreModel(finalBoard);
	}

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}
	public int getMines() {
		return mineCount;
	}

	public int getDisplayedFields() {
		return displayedFields;
	}

	public void setXSize(int xSize) {
		this.xSize = xSize;
	}

	public void setYSize(int ySize) {
		this.ySize = ySize;
	}

	public void setMines(int mines) {
		this.mineCount = mines;
	}

	public GameObjects[][] getCurrentBoard() {
		return currentBoard;
	}

	public GameObjects[][] getFinalBoard() {
		return finalBoard;
	}

	public boolean getGameover() {
		return gameOver;
	}

	public ScoreModel getScoreModel() {
		return scoreModel;
	}

	// Generating the given number of mines in random positions.
	private boolean[][] genMines(int mineCount) {
		Random rand = new Random();
		int mineIndex = 0;
		while (mineIndex < mineCount) {
			int x = rand.nextInt(xSize);
			int y = rand.nextInt(ySize);
			if (!mines[x][y]) {
				mines[x][y] = true;
				mineIndex++;
			}
		}
		return mines;
	}

	// Fills the final board with mines and numbers representing the number of
	// neighbouring mines.
	private GameObjects[][] fillFinalBoard() {
		GameObjects[][] tempBoard = new GameObjects[xSize][ySize];
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				if (mines[i][j]) {
					tempBoard[i][j] = new Mine();
				} else {
					tempBoard[i][j] = getNeighbours(i, j) == 0 ? new Zero() : new Number(getNeighbours(i, j));
				}
			}
		}
		return tempBoard;
	}
	

	// Returns the number of neighbouring mines to a given field.
	public int getNeighbours(int x, int y) {
		int neighbourBombs = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int cx = x + i;
				int cy = y + j;
				if (cx >= 0 && cx < xSize && cy >= 0 && cy < ySize && !(i == 0 && j == 0)) {
					neighbourBombs += (mines[x + i][y + j] ? 1 : 0);
				}
			}
		}
		return neighbourBombs;
	}

	// Reaction to the user clicking a specific field and updating the current-board
	// to a new state.
	public void clickField(int x, int y) {
		// Makes you unable to lose on first move
		if (displayedFields == 0) {
			if (mines[x][y]) { 
				remakeBoard(x, y);
			}
		}
		currentBoard[x][y] = finalBoard[x][y];
		if (finalBoard[x][y] instanceof Number) {
			if (((Number) finalBoard[x][y]).getNumVisible()) {
				displayedFields++;
				((Number) finalBoard[x][y]).toggleNumVisible();
			}
		} else if (finalBoard[x][y] instanceof Zero) {
			if (((Zero) finalBoard[x][y]).getZeroVisible()) {
				displayedFields++;
				((Zero) finalBoard[x][y]).toggleZeroVisible();
			}
		}
		lastX = x;
		lastY = y;
	}



	public void remakeBoard(int x, int y) {
		currentBoard = new GameObjects[xSize][ySize]; // representing the board displayed to the player
		finalBoard = new GameObjects[xSize][ySize]; // representing the solution
		this.mines = new boolean[xSize][ySize];

		this.mines = genMines(mineCount);
		this.finalBoard = fillFinalBoard();

		this.gameOver = false;
		this.displayedFields = 0;

		this.scoreModel = new ScoreModel(finalBoard);

		clickField(x, y);
	}

	public boolean checkWin() {
		if ((xSize * ySize) - mineCount == displayedFields) {
			gameOver = true;
			return true;
		} else {
			return false;
		}
	}

	public boolean checkGameover(int x, int y) {
		if (mines[x][y]) {
			gameOver = true;
			return true;
		} else {
			return false;
		}
	}

	public void setFlag(int x, int y) {
		currentBoard[x][y] = new Flag();
	}

	public void removeFlag(int x, int y) {
		currentBoard[x][y] = null;
	}

	public boolean checkFlag(int x, int y) {
		if (currentBoard[x][y] instanceof Flag) {
			return true;
		} else {
			return false;
		}
	}
	
	public int[] findHint() {
		int[] fieldToClick = new int[2];
		for (int i = lastX - 1; i <= lastX + 1; i++) {
			for (int j = lastY - 1; j <= lastY + 1; j++) {
				if ((i != lastX || j != lastY) && i >= 0 && i < currentBoard.length && j >= 0 && j < currentBoard[i].length) {
					if ((finalBoard[i][j] instanceof Number || finalBoard[i][j] instanceof Zero)
							&& currentBoard[i][j] == null) {
						fieldToClick[0] = i;
						fieldToClick[1] = j;
					}
				}
			}
		}
		if (fieldToClick[0] == 0 && fieldToClick[1] == 0) {
			for (int i = 0; i < currentBoard.length; i++) {
				for (int j = 0; j < currentBoard[i].length; j++) {
					if ((finalBoard[i][j] instanceof Number && currentBoard[i][j] == null) ||	(finalBoard[i][j] instanceof Zero && currentBoard[i][j] == null) ) {
						fieldToClick[0] = i;
						fieldToClick[1] = j;
					}
				}
			}
		}
		return fieldToClick;
	}
}
