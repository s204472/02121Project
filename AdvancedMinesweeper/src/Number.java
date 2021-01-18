public class Number extends GameObjects {
	private int val;
	private boolean visible = true;
	private boolean visited = false;
	
	
	public void toggleNumVisited() {
		visible = !visible;
	}
	public boolean getNumVisited () {
		return visited;
	}

	public void toggleNumVisible() {
		visible = !visible;
	}
	
	public boolean getNumVisible () {
		return visible;
	}
	
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

