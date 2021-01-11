import java.util.Date;

public class ScoreModel {
	private Date timeStart;
	private int Score;
	
	public ScoreModel (int mapSizeTotal, int mines) {
		
	}
	
	
	// Timing part of score
	public void startTimer() {
		timeStart = new Date();
	}
	
	public int getTimeElapsed() {
		Date timeNow = new Date();
		int timeElapsed = (int)((timeNow.getTime() - timeStart.getTime()) / 1000);
		
		return timeElapsed;
	}
}
