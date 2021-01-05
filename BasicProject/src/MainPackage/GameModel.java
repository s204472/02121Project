package MainPackage;

public class GameModel {
    private boolean[][] mines;
    private GameObjects[][] currentBoard;
    private GameObjects[][] finalBoard;
    
    public int getNeighbours(int x, int y) {
    	int neighbourBombs = 0;
    	for(int i = 0; i < mines.length; i++) {
    		for(int j = 0; j < mines[i].length; j++) {
    			neighbourBombs += (mines[i][j] ? 1 : 0);
    		}
    	}
    }
    
	
	
	
	
	
	



}
