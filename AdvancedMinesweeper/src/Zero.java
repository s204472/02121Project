
public class Zero extends GameObjects{
	private boolean visible = true;
	private boolean visited = false;
	
	
	public void toggleZeroVisited() {
		visible = !visible;
	}
	public boolean getZeroVisited () {
		return visited;
	}
	
	public void toggleZeroVisible() {
		visible = !visible;
	}
	public boolean getZeroVisible () {
		return visible;
	}
	
	
}
