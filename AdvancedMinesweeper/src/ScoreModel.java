
import java.util.Date;

public class ScoreModel {
	private Date timeStart;
	private int score;
	private int timeElapsed;
	
	public ScoreModel (int mapSizeTotal, int mines) {
		score = mapSizeTotal * (1 + mines / mapSizeTotal);
	}
	
	
	// Timing part of score
	public void startTimer() {
		timeStart = new Date();
	}
	
	public String getTimeElapsed() {
		Date timeNow = new Date();
		timeElapsed = (int) ((timeNow.getTime() - timeStart.getTime()) / 1000);
		String minutes = timeElapsed / 60 < 10 ? "0" + (timeElapsed / 60) : "" + (timeElapsed / 60);
		String seconds = timeElapsed % 60 < 10 ? "0" + (timeElapsed % 60) : "" + (timeElapsed % 60);

		
		
		String timeString = minutes + ":" + seconds;
		
		return timeString;
	}
	
	public String getScore() {
		int endScore = score - timeElapsed;
		if (endScore < 0) {
			endScore = 0;
		}
		return "" + endScore;
	}
}