package MainPackage;

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
    	
    	currentBoard = new GameObjects[n][m];
    	finalBoard = new GameObjects[n][m];
    	
    	mines = fillMines(mineCount);
    	
    	
    }
	
	private GameObjects[][] fillMines(int mineCount) {
		
		
		return mines;
	}
    
    
	
	
	
	



}
