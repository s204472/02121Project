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

