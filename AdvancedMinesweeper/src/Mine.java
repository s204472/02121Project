import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Mine extends GameObjects {

	
	public ImageView getMineImage(int screenHeight, int ySize) throws FileNotFoundException {
		Image bomb = new Image(new FileInputStream("src\\Images\\BombSolid.png"));
		ImageView viewBomb = new ImageView(bomb);
		viewBomb.setFitHeight(screenHeight / ySize / 2);
		viewBomb.setFitWidth(screenHeight / ySize / 2);
		return viewBomb;
	}
}
