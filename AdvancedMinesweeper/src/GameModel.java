import java.util.Random;

public class GameModel {
	private int width, height, mineCount;
	private int lastX, lastY, displayedFields;
	private boolean[][] mines;
	private boolean gameOver;
	
	private GameObjects[][] currentBoard, finalBoard;
	private ScoreModel scoreModel;
	

	// Constructing a GameModel object, with two boards of the given size and mines.
	public GameModel(int width, int height, int mineCount) {
		this.width = width;
		this.height = height;
		this.mineCount = mineCount;

		this.currentBoard = new GameObjects[width][height]; // representing the board displayed to the player

		this.mines = genMines(width, height, mineCount);
		this.finalBoard = fillFinalBoard(width, height, mines);

		this.gameOver = false;
		this.displayedFields = 0;

		this.scoreModel = new ScoreModel(finalBoard);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	public int getMines() {
		return mineCount;
	}

	public int getDisplayedFields() {
		return displayedFields;
	}

	public GameObjects[][] getCurrentBoard() {
		return currentBoard;
	}

	public GameObjects[][] getFinalBoard() {
		return finalBoard;
	}

	public boolean getGameOver() {
		return gameOver;
	}

	public ScoreModel getScoreModel() {
		return scoreModel;
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

	// Generating the given number of mines in random positions.
	private boolean[][] genMines(int x, int y, int mineCount) {
		Random rand = new Random();
		
		boolean[][] mines = new boolean[x][y];
		
		int mineIndex = 0;
		while (mineIndex < mineCount) {
			int i = rand.nextInt(width);
			int j = rand.nextInt(height);
			if (!mines[i][j]) {
				mines[i][j] = true;
				mineIndex++;
			}
		}
		return mines;
	}

	// Fills the final board with mines and numbers representing the number of
	// neighbouring mines.
	private GameObjects[][] fillFinalBoard(int width, int height, boolean[][] mines) {
		GameObjects[][] finalBoard = new GameObjects[width][height];
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				if (mines[i][j]) {
					finalBoard[i][j] = new Mine();
				} else {
					finalBoard[i][j] = getNeighbours(i, j) == 0 ? new Zero() : new Number(getNeighbours(i, j));
				}
			}
		}
		return finalBoard;
	}
	

	// Returns the number of neighbouring mines to a given field.
	public int getNeighbours(int x, int y) {
		int neighbourBombs = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i >= 0 && i < width && j >= 0 && j < height && !(i == x && j == y)) {
					neighbourBombs += (mines[i][j] ? 1 : 0);
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
		lastX = x;
		lastY = y;
		
		if ((finalBoard[x][y] instanceof Zero || finalBoard[x][y] instanceof Number) && !finalBoard[x][y].getVisited() ) {	
			finalBoard[x][y].setVisited();
			displayedFields++;
		}
//		System.out.println(displayedFields);
		lastX = x;
		lastY = y;
	}



	public void remakeBoard(int x, int y) {
		mines = genMines(width, height, mineCount);
		finalBoard = fillFinalBoard(width, height, mines);
		gameOver = false;
		displayedFields = 0;
		scoreModel = new ScoreModel(finalBoard);
		
		clickField(x, y);
	}

	public boolean checkWin() {
		if ((width * height) - mineCount == displayedFields) {
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
		
	public int[] findHint() {
		int[] fieldToClick = new int[2];
		for (int i = lastX - 1; i <= lastX + 1; i++) {
			for (int j = lastY - 1; j <= lastY + 1; j++) {
				if ((i != lastX || j != lastY) && i >= 0 
						&& i < currentBoard.length && j >= 0 && j < currentBoard[i].length) {
				
					if(currentBoard[i][j] == null && (finalBoard[i][j] instanceof Number || finalBoard[i][j] instanceof Zero)) {
						fieldToClick[0] = i;
						fieldToClick[1] = j;
					}
				}
			}
		}
		if (fieldToClick[0] == 0 && fieldToClick[1] == 0) {
			for (int i = 0; i < currentBoard.length; i++) {
				for (int j = 0; j < currentBoard[i].length; j++) {
					if ((finalBoard[i][j] instanceof Number	|| finalBoard[i][j] instanceof Zero) && currentBoard[i][j] == null) {
						fieldToClick[0] = i;
						fieldToClick[1] = j;
						return fieldToClick;
					}
				}
			}
		}
		return fieldToClick;
	}
}
