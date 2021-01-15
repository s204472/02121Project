
public class ScoreModel {
	private int value3BV;
	private int secondsPassed;
	private boolean[][] checkedZeros;
	
	private boolean[][] zerosToCheck;

	public ScoreModel (GameObjects[][] solutionBoard) {
		
		// Calculates the most optimal solution using 3BV
		checkedZeros = new boolean[solutionBoard.length][solutionBoard[0].length];
		zerosToCheck = new boolean[solutionBoard.length][solutionBoard[0].length];
		
		for (int i = 0; i < solutionBoard.length; i++) {
			for (int j = 0; j < solutionBoard[i].length; j++) {
				
				// Checks how many zeros needed to be pressed
				if (solutionBoard[i][j] instanceof Zero && !checkedZeros[i][j]) {
					checkZeroSequence(solutionBoard, i,j);
					value3BV++;
				}
				
				if (solutionBoard[i][j] instanceof Number) {
					if (!canBeDiscoveredByBlank(solutionBoard, i, j)) {
						value3BV++;
					} 
				}
				
			}
		}
		
		System.out.println("Amount of clicks " + value3BV);
	}
	
	// Checks whether the square can be discovered by the blank square sequence.
	// Only used for numbered squares (Not zeros)
	public boolean canBeDiscoveredByBlank(GameObjects[][] solutionBoard, int x, int y) {
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				
				// If any square next to the searched square is a zero the method ends with 'true'
				if ((i != x || j != y) && i >= 0 && i < solutionBoard.length && j >= 0
						&& j < solutionBoard[i].length && solutionBoard[i][j] instanceof Zero) {
					
					return true;
				}
				
			}
		}
		
		
		return false;
	}
	
	public void checkZeroSequence(GameObjects[][] solutionBoard, int x, int y) {		
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				
				// Makes sure always to be in bound of Array
				if ((i != x || j != y) && i >= 0 && i < solutionBoard.length && j >= 0
						&& j < solutionBoard[i].length) {

					if (solutionBoard[i][j] instanceof Zero && !checkedZeros[i][j]) {
						zerosToCheck[i][j] = true;
					}
					
					
					
				}
			}
		}
		checkedZeros[x][y] = true;
		zerosToCheck[x][y] = false;
		
		
		// Makes sure that all the zero squares in the sequence will be checked
		for (int i = 0; i < zerosToCheck.length; i++) {
			for (int j = 0; j < zerosToCheck[i].length; j++) {
				if (zerosToCheck[i][j]) {
					checkZeroSequence(solutionBoard, i, j);
				}
			}
		}
	}
	
	// Timing part of score
	public void incSeconds() {
		secondsPassed++;
	}
	
	public String getTimeElapsed() {
		String minutes = secondsPassed / 60 < 10 ? "0" + (secondsPassed / 60) : "" + (secondsPassed / 60);
		String seconds = secondsPassed % 60 < 10 ? "0" + (secondsPassed % 60) : "" + (secondsPassed % 60);
		
		String timeString = minutes + ":" + seconds;
		
		return timeString;
	}
	
	public String getScore() {
		int endScore = value3BV*6-secondsPassed;
		if (endScore < 0) {
			endScore = 0;
		}
		return "" + endScore;
	}
}