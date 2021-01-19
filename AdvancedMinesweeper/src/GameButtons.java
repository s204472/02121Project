import javafx.scene.control.Button;



public class GameButtons extends Button {
	private int fontSize;
	
	public GameButtons(int ySize, int screenHeight, int biggestSide, int fontSize) {
		this.fontSize = fontSize;
		super.setPrefSize(screenHeight / ySize + 1, screenHeight / biggestSide + 1);
		super.getStyleClass().add("gameButtons");
		super.setStyle(String.format("-fx-font-size: %dpx;", fontSize));
	}
	
	public void setFlag(Flag flag) {
		super.setGraphic(flag.getFlagImage(fontSize));
	}
	public void setNumber(Number num) {
		super.setGraphic(null);
		super.setText(num.toString());
		super.getStyleClass().add("number" + num.toString());

	}
	public void setMine(Mine mine) {
		super.setGraphic(mine.getMineImage(fontSize));
	}
	public void setZero() {
		super.setGraphic(null);
		super.getStyleClass().add("blank");
	}
	public void setEmpty() {
		super.setGraphic(null);
	}
	public void styleWin() {
		super.setStyle(String.format("-fx-font-size: %dpx;", fontSize));
		super.getStyleClass().add("button-won");
	}
	public void styleGameover() {
		super.setStyle(String.format("-fx-font-size: %dpx;", fontSize));
		super.getStyleClass().add("button-lost");
	}
}
