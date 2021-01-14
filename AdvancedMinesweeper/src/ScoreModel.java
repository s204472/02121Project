
public class ScoreModel {
	private int score3BV;
	private int secondsPassed;
	private int[][] checkedZeros;

	public ScoreModel (GameObjects[][] solutionBoard) {
		// Calculates the optimal solution using 3BV
		int amountOfClicks = 1;
		checkedZeros = new int[solutionBoard.length][solutionBoard[0].length];
		
		for (int i = 0; i < solutionBoard.length; i++) {
			for (int j = 0; j < solutionBoard[i].length; j++) {
				
				if (checkedZeros[i][j] == 0) {
					if (solutionBoard[i][j] instanceof Zero) {
						amountOfClicks++;
						hasBlankNeighbours(solutionBoard, i, j, true);
					}
				}
				
				if (solutionBoard[i][j] instanceof Number) {
					if (!hasBlankNeighbours(solutionBoard, i, j, false)) {
						amountOfClicks++;
					} 
				}
				
			}
		}
		System.out.println("Amount of clicks = " + amountOfClicks);
	}
	
	public boolean hasBlankNeighbours(GameObjects[][] solutionBoard, int x, int y, boolean checkZero) {
		if (x-1 >= 0) {
			if (solutionBoard[x-1][y] instanceof Zero) {
				
				if(checkZero && checkedZeros[x-1][y] == 0) {
					checkedZeros[x-1][y]=1;
					hasBlankNeighbours(solutionBoard,x-1,y,true);
				}
				
				return true;
			}
			if (y-1 >= 0 && solutionBoard[x-1][y-1] instanceof Zero) {
				
				if(checkZero && checkedZeros[x-1][y-1] == 0) {
					checkedZeros[x-1][y-1]=1;
					hasBlankNeighbours(solutionBoard,x-1,y-1,true);
				}
				
				return true;
			}
			if (y+1 < solutionBoard[x-1].length && solutionBoard[x-1][y+1] instanceof Zero ) {
				
				if(checkZero && checkedZeros[x-1][y+1] == 0) {
					checkedZeros[x-1][y+1]=1;
					hasBlankNeighbours(solutionBoard,x-1,y+1,true);
				}
				
				return true;
			}
		}
		
		
		if (x+1 < solutionBoard.length) {
			if (solutionBoard[x+1][y] instanceof Zero) {
				
				if(checkZero && checkedZeros[x+1][y] == 0) {
					checkedZeros[x+1][y]=1;
					hasBlankNeighbours(solutionBoard,x+1,y,true);
				}
				
				return true;
			}
			if (y-1 >= 0 && solutionBoard[x+1][y-1] instanceof Zero) {
				
				if(checkZero && checkedZeros[x+1][y-1] == 0) {
					checkedZeros[x+1][y-1]=1;
					hasBlankNeighbours(solutionBoard,x+1,y-1,true);
				}
				
				return true;
			}
			if (y+1 < solutionBoard[x+1].length && solutionBoard[x+1][y+1] instanceof Zero ) {
				
				if(checkZero && checkedZeros[x+1][y+1] == 0) {
					checkedZeros[x+1][y+1]=1;
					hasBlankNeighbours(solutionBoard,x+1,y+1,true);
				}
				
				return true;
			}
		}
		
		if (y-1 >= 0 && solutionBoard[x][y-1] instanceof Zero) {
			
			if(checkZero && checkedZeros[x][y-1] == 0) {
				checkedZeros[x][y-1]=1;
				hasBlankNeighbours(solutionBoard,x,y-1,true);
			}
			
			return true;
		}
		
		if (y+1 < solutionBoard[x].length && solutionBoard[x][y+1] instanceof Zero ) {
			
			if(checkZero && checkedZeros[x][y+1] == 0) {
				checkedZeros[x][y+1]=1;
				hasBlankNeighbours(solutionBoard,x,y+1,true);
			}
			
			return true;
		}
		
		
		return false;
	}
	/* TIL DEBUGGING MÅ SLETTES
	public boolean hasSpecialBlankNeighbours(GameObjects[][] solutionBoard, int x, int y) {
		if (x-1 >= 0) {
			System.out.println("1");
			if (solutionBoard[x-1][y] instanceof Zero) {
				System.out.println("2");
				return true;
			}
			if (y-1 >= 0 && solutionBoard[x-1][y-1] instanceof Zero) {
				System.out.println("3");
				return true;
			}
			if (y+1 < solutionBoard.length && solutionBoard[x-1][y+1] instanceof Zero ) {
				System.out.println("4");
				return true;
			}
		}
		
		
		if (x+1 < solutionBoard.length) {
			System.out.println("5");
			if (solutionBoard[x+1][y] instanceof Zero) {
				System.out.println("6");
				return true;
			}
			if (y-1 >= 0 && solutionBoard[x+1][y-1] instanceof Zero) {
				System.out.println("7");
				return true;
			}
			if (y+1 < solutionBoard.length && solutionBoard[x+1][y+1] instanceof Zero ) {
				System.out.println("8");
				return true;
			}
		}
		
		if (y-1 >= 0 && solutionBoard[x][y-1] instanceof Zero) {
			System.out.println("9");
			return true;
		}
		
		if (y+1 < solutionBoard.length && solutionBoard[x][y+1] instanceof Zero ) {
			System.out.println("10");
			
			return true;
		}
		
		
		return false;
	}
	*/
	
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
		int endScore = 0 - secondsPassed;
		if (endScore < 0) {
			endScore = 0;
		}
		return "" + endScore;
	}
}