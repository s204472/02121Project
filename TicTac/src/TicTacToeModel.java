
public class TicTacToeModel {

	private int[][] grid;
	private int size = 3;
	private int currentPlayer;
	
	
	public TicTacToeModel() {
		grid = new int[size][size];
		currentPlayer = 1;
		
	}
	public boolean makeMove(int x, int y) {
		if (grid[x][y] == 0) {
			grid[x][y] = currentPlayer;
			return true;
		}
		return false;
		
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				s += grid[j][i];
			}
			s += "\n";
		}
		return s;
	}
	
	public int getValue(int x, int y) {
		return grid[x][y];
	}
	public void changePlayer() {
		currentPlayer = currentPlayer == 1 ? 2 : 1;
	}
	public int getPlayer() {
		return currentPlayer;
	}
	
}
