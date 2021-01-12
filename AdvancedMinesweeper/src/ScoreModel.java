
import java.util.Date;

public class ScoreModel {
	private Date timeStart;
	private int score;
	
	public ScoreModel (int mapSizeTotal, int mines) {
		score += 1000;
	}
	
	
	// Timing part of score
	public void startTimer() {
		timeStart = new Date();
	}
	
	public String getTimeElapsed() {
		Date timeNow = new Date();
		int timeElapsed = (int) ((timeNow.getTime() - timeStart.getTime()) / 1000);
		
		String timeString = timeElapsed / 60 + " min " + timeElapsed % 60 + "sec";
		
		return timeString;
	}
	
	public int calculateEndScore() {
		return 0;
	}
}