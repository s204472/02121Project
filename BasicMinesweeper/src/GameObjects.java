
public class GameObjects {
	private boolean visible;
	
	public GameObjects() {
		this.visible = false;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public void toggleVisible() {
		visible = !visible;
	}
	
}
