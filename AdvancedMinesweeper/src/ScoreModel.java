
import java.util.Date;

public class ScoreModel {
	private Date timeStart;
	private int score;
	private int timeElapsed;
	
	public ScoreModel (int mapSizeTotal, int mines) {
		score = mapSizeTotal*(1+mines/mapSizeTotal);
	}
	
	
	// Timing part of score
	public void startTimer() {
		timeStart = new Date();
	}
	
	public String getTimeElapsed() {
		Date timeNow = new Date();
		timeElapsed = (int) ((timeNow.getTime() - timeStart.getTime()) / 1000);
		
		String timeString = timeElapsed / 60 + " min " + timeElapsed % 60 + "sec";
		
		return timeString;
	}
	
	public int calculateEndScore() {
		int endScore = score-timeElapsed;
		if (endScore < 0) {
			endScore = 0;
		}
		return endScore;
	}
}