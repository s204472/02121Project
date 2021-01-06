import java.util.Random;

public class GameModel {
	private int n;
	private int m;
	private int mineCount;
    private boolean[][] mines;
    private GameObjects[][] currentBoard;
    private GameObjects[][] finalBoard;
     
    
    public GameModel(int n, int m, int mineCount) {
    	this.n = n;
    	this.m = m;
    	this.mineCount = mineCount;
    	
    	this.currentBoard = new GameObjects[n][m];
    	this.finalBoard = new GameObjects[n][m];
    	this.mines = new boolean[n][m];
    	
    	this.mines = genMines(mineCount);
    	this.finalBoard = fillFinalBoard();
    }
	
	private boolean[][] genMines(int mineCount) {
		Random rand = new Random();
		int mineIndex = 0;
		while (mineIndex < mineCount) {
			int x = rand.nextInt(n);
			int y = rand.nextInt(m);
			if (!mines[x][y]) {
				mines[x][y] = true;
				mineIndex++;
			}	
		}
		
		return mines;
	}
    
	private GameObjects[][] fillFinalBoard(){
		GameObjects[][] tempBoard = new GameObjects[n][m];
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
	
	
	public String printBoard() {
		String s = "";
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				s += mines[i][j] ? "X\t" : "O\t";
			}
			s += "\n";
		}
		return s;
	}
	
	public String printFinalBoard() {
		String s = "";
		for (int i = 0; i < finalBoard.length; i++) {
			for (int j = 0; j < finalBoard[i].length; j++) {
				s += finalBoard[i][j] + "\t";
			}
			s += "\n\n";
		}
		return s;
	}
	
	
	public int getNeighbours(int x, int y) {
    	int neighbourBombs = 0;
    	for(int i = -1; i < 2; i++) {
    		for(int j = -1; j < 2; j++) {
    			int cx = x + i;
    			int cy = y + j;
				if(cx >= 0 && cx < n && cy >= 0 && cy < m && !(i == 0 && j == 0)) {
    				neighbourBombs += (mines[x + i][y + j] ? 1 : 0);
    			}
    		}
    	}
    	return neighbourBombs;
	}
	
	public GameObjects[][] getCurrentBoard(){
		return currentBoard;
	}
	
	public void clickField(int x, int y) {
		if (mines[x][y]) {
			System.exit(0);
		}
		currentBoard[x][y] = finalBoard[x][y];
	}
	
	public int getN() {
		return n;
	}
	
	public int getM() {
		return m;
	}
}
