import java.util.Random;

/*
 * Made by:
 * 		General logic: Magnus Siegumfeldt, s204472 and Anders Reher, s194587		
 * */

public class GameModel {
	public boolean gameover =false;
	private int xSize;
	private int ySize;
	private int mineCount;
    private boolean[][] mines;
    private GameObjects[][] currentBoard;
    private GameObjects[][] finalBoard;
    private int clickCount;
     
    //Constructing a GameModel object. Mines and boards are generated and values are initialized
    public GameModel(int n, int m, int mineCount) {
    	this.xSize = n;
    	this.ySize = m;
    	this.mineCount = mineCount;
    	
    	this.currentBoard = new GameObjects[n][m];     // Representing the board displayed to the player
    	this.finalBoard = new GameObjects[n][m];       // Representing the solution
    	this.mines = new boolean[n][m];
    	
    	this.mines = genMines(mineCount);
    	this.finalBoard = fillFinalBoard();
    	
    	this.clickCount = 0;
    }
    
	// Generating a given number of mines in random positions. 
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
	
    // Fills the final board with mines and numbers representing the number of neighbouring mines. 
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
	
	// Returns the number of neighbouring mines to a given field.
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
	
	// Called when a field at (x, y) is clicked. Values in currentBoard is updated.  
	public void clickField(int x, int y) {
		currentBoard[x][y] = finalBoard[x][y];
		clickCount++;
	}
	
	public int getXSize() {
		return xSize;
	}
	
	public int getYSize() {
		return ySize;
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
	
	

	
	
}
