import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Flag extends GameObjects {

	public ImageView getFlagImage(int fontSize) {
		try {
			Image flag = new Image(getClass().getResource("/Images/FlagRegular.png").toString());
			ImageView viewFlag = new ImageView(flag);
			viewFlag.setFitHeight(fontSize + 2);
			viewFlag.setFitWidth(fontSize + 2);
			return viewFlag;
			
		} catch (Exception e) {
			return null;
		}
	}
}