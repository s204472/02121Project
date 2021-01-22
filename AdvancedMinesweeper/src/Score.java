import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/*
 * Made by:
 * 		Magnus Siegumfeldt, s204472
 * 
 * */

// This class represents a score object. A score object can be easily placed in the scoreboard.

public class Score {
	private SimpleIntegerProperty score;
	private SimpleStringProperty map;
	private SimpleIntegerProperty mines;
	
	public Score(Integer score, Integer xSize, Integer ySize, Integer mines) {
		this.score = new SimpleIntegerProperty(score);
		this.map = new SimpleStringProperty(xSize + "x" + ySize);
		this.mines = new SimpleIntegerProperty(mines);
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
