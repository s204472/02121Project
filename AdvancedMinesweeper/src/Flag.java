
public class Flag extends GameObjects {
	private boolean Visible = false;
	
	public void toggleVisible() {
		Visible = !Visible;
	}

	public String toString() {
		if (Visible) {
			return " ";
		} else {
			return "P";
		}

	}
}
