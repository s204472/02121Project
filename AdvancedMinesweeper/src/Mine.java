import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Mine extends GameObjects {
	private boolean visible = true;

	public void toggleMineBoolean() {
		visible = !visible;
	}
	
	public boolean getMineVisible () {
		return visible;
	}
	
	public ImageView getMineImage(int fontSize) throws FileNotFoundException {
		Image bomb = new Image(new FileInputStream("src/Images/BombSolid.png"));
		ImageView viewBomb = new ImageView(bomb);
		viewBomb.setFitHeight(fontSize + 2);
		viewBomb.setFitWidth(fontSize + 2);
		return viewBomb;
	}
}
