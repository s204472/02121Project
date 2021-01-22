// This class represents a Number in the board stored in gameModel. It has a value property which corresponds to neighbouring mines.

public class Number extends GameObjects {
	private int val;
	
	public Number(int val) {
		this.val = val;
	}
	
	public String toString() {
		return "" + val;
	}
}
