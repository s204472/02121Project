import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Score {
	private SimpleStringProperty name;
	private SimpleIntegerProperty score;
	private SimpleIntegerProperty xSize;
	private SimpleIntegerProperty ySize;
	private SimpleIntegerProperty mines;
	
	public Score(String name, int score, int xSize, int ySize, int mines) {
		this.name = new SimpleStringProperty(name);
		this.score = new SimpleIntegerProperty(score);
		this.xSize = new SimpleIntegerProperty(xSize);
		this.ySize = new SimpleIntegerProperty(ySize);
		this.mines = new SimpleIntegerProperty(mines);
	}
	public void setName() {
		
	}
	public void setScore() {
		
	}
	public void setYSize() {
		
	}
	public void setXSize() {
		
	}
	public void setMines() {
		
	}
	public String getName() {
		return name.get();
	}
	public int getScore() {
		return score.get();
	}
	public int getYSize() {
		return xSize.get();
	}
	public int getXSize() {
		return ySize.get();
	}
	public int getMines() {
		return mines.get();
	}
	
}
