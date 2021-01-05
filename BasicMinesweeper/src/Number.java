
public class Number extends GameObjects {
	private boolean visible;
	private int val;
	
	public Number(int val) {
		this.val = val;
		this.visible = false;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public String toString() {
		return "" + val;
	}
}
