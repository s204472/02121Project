// This class extends GameObjects, and can be placed in the GameObject arrays in the GameModel. 
// It has a value property corresponding to neighbouring mines
public class Number extends GameObjects {
	private int val;

	public Number(int val) {
		this.val = val;
	}
	
	public String toString() {
		return "" + val;
	}
	public int getValue() {
		return val;
	}
}

