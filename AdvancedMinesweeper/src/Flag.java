import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Flag extends GameObjects {

	public ImageView getFlagImage(int fontSize) throws FileNotFoundException {
		Image flag = new Image(new FileInputStream("src/Images/FlagRegular.png"));
		ImageView viewFlag = new ImageView(flag);
		viewFlag.setFitHeight(fontSize + 2);
		viewFlag.setFitWidth(fontSize + 2);
		
		return viewFlag;
	}
}