import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Flag extends GameObjects {

	public ImageView getFlagImage(int screenHeight, int ySize) throws FileNotFoundException {
		Image flag = new Image(new FileInputStream("src\\Images\\FlagRegular.png"));
		ImageView viewFlag = new ImageView(flag);
		viewFlag.setFitHeight(screenHeight / ySize / 2);
		viewFlag.setFitWidth(screenHeight / ySize / 2);
		
		return viewFlag;
	}
}