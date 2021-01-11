import java.util.Date;
import java.util.Random;

public class GameModel {
	public boolean gameover =false;
	private int xSize;
	private int ySize;
	private int mineCount;
    private boolean[][] mines;
    private GameObjects[][] currentBoard;
    private GameObjects[][] finalBoard;
    private int clickCount;
    private ScoreModel scoreModel;
    public boolean Flag = false;
     
    //Constructing a GameModel object, with two boards of the given size and mines. 
    public GameModel(int n, int m, int mineCount) {
    	this.xSize = n;
    	this.ySize = m;
    	this.mineCount = mineCount;
    	
    	this.currentBoard = new GameObjects[n][m]; //representing the board displayed to the player
    	this.finalBoard = new GameObjects[n][m]; // representing the solution
    	this.mines = new boolean[n][m];
    	
    	this.mines = genMines(mineCount);
    	this.finalBoard = fillFinalBoard();
    	
    	this.clickCount = 0;
    	
    	this.scoreModel = new ScoreModel(n*m,mineCount);
    	
    }
    
	//Generating the given number of mines in random positions. 
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
	
    //Fills the final board with mines and numbers representing the number of neighbouring mines. 
	private GameObjects[][] fillFinalBoard(){
		GameObjects[][] tempBoard = new GameObjects[xSize][ySize];
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				if (mines[i][j]) {
					tempBoard[i][j] = new Mine();
				} else {
					tempBoard[i][j] = new Number(getNeighbours(i, j));
				}
			}
		}
		return tempBoard;
	}
	
	//Returns the number of neighbouring mines to a given field.
	public int getNeighbours(int x, int y) {
    	int neighbourBombs = 0;
    	for(int i = -1; i < 2; i++) {
    		for(int j = -1; j < 2; j++) {
    			int cx = x + i;
    			int cy = y + j;
				if(cx >= 0 && cx < xSize && cy >= 0 && cy < ySize && !(i == 0 && j == 0)) {
    				neighbourBombs += (mines[x + i][y + j] ? 1 : 0);
    			}
    		}
    	}
    	return neighbourBombs;
	}
	
	public GameObjects[][] getCurrentBoard(){
		return currentBoard;
	}
	//Reaction to the user clicking a specific field and updating the current-board to a new state.  
	public void clickField(int x, int y) {
		// Starts the game timer on users first click.
		if (clickCount == 0) {
	    	scoreModel.startTimer();
		}
		
		currentBoard[x][y] = finalBoard[x][y];
		clickCount++;
	}
	
	public int getXSize() {
		return xSize;
	}
	
	public int getYSize() {
		return ySize;
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
	
	public GameObjects[][] showAll() {
		return finalBoard;
	}
	
	public boolean checkWin() {
		if ((xSize * ySize) - mineCount == clickCount) {
			gameover = true;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkGameover(int x, int y) {
		if (mines[x][y]) {
			gameover = true;
			return true;
		} else {
			return false;
		}
	}
	public boolean getGameOver() {
		return gameover;
	}
	
	public ScoreModel getScoreModel() {
		return scoreModel;
	}
	public void setFlag(int x, int y) {
		currentBoard[x][y] = new Flag();
	}
	
	public void removeFlag(int x, int y) {
		((Flag) currentBoard[x][y]).toggleVisible();;
	}
	
	public boolean checkFlag (int x, int y) {
		if (currentBoard[x][y] instanceof Flag) {
			return true;
		} else {
			return false;
		}
	}
	
	

	
	
}
