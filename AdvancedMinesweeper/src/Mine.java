
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


// This class extends GameObjects, and can be placed in the GameObject arrays in the GameModel.
public class Mine extends GameObjects {
	
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
