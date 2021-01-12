public class Flag extends GameObjects {
	private boolean visible = false;

	public void toggleVisible() {
		visible = !visible;
	}

	public String toString() {
		if (visible) {
			return " ";
		} else {
			return "P";
		}

	}
}