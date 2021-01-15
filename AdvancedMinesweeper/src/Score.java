import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Score {
	private SimpleStringProperty name;
	private SimpleIntegerProperty score;
	private SimpleStringProperty map;
	private SimpleIntegerProperty mines;
	
	public Score(String name, Integer score, Integer xSize, Integer ySize, Integer mines) {
		this.name = new SimpleStringProperty(name);
		this.score = new SimpleIntegerProperty(score);
		this.map = new SimpleStringProperty(xSize + "x" + ySize);
		this.mines = new SimpleIntegerProperty(mines);
	}
	public Score(String name, Integer score, String map, Integer mines) {
		this.name = new SimpleStringProperty(name);
		this.score = new SimpleIntegerProperty(score);
		this.map = new SimpleStringProperty(map);
		this.mines = new SimpleIntegerProperty(mines);
	}
	
	public String getName() {
		return name.get();
	}
	public int getScore() {
		return score.get();
	}
	public String getMap() {
		return map.get();
	}
	public int getMines() {
		return mines.get();
	}
	
	
}
