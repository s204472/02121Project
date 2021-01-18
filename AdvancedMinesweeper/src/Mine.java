import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Mine extends GameObjects {
	private boolean visible = true;
	private boolean visited = false;

	public void toggleMineVisited() {
		visible = !visible;
	}

	public boolean getMineVisited() {
		return visited;
	}

	public void toggleMineBoolean() {
		visible = !visible;
	}

	public boolean getMineVisible() {
		return visible;
	}

	public ImageView getMineImage(int fontSize) {
		try {
			Image bomb = new Image(getClass().getResource("Images/BombSolid.png").toString());
			ImageView viewBomb = new ImageView(bomb);
			viewBomb.setFitHeight(fontSize + 2);
			viewBomb.setFitWidth(fontSize + 2);
			return viewBomb;
			
		} catch (Exception e) {
			return null;
		}
	}
}
