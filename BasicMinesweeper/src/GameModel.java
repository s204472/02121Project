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
    	
    	this.mines = fillMines(mineCount);
    	
    	
    	
    }
	
	private boolean[][] fillMines(int mineCount) {
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
    
	
	
	
	



}
