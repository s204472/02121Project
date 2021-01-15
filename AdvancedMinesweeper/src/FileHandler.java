import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.Scanner;

public class FileHandler {
	public FileHandler() {
		
	}
	public void saveScores(ObservableList<Score> scores) {
		try {
			
			FileWriter fw = new FileWriter("scoreboard.txt");
			boolean firstRun = true;
			for (Score score : scores) {
				if(firstRun) {
					fw.write(score.getName() + " " + score.getScore() + " " + score.getMap() + " " + score.getMines());
					firstRun = !firstRun;
				} else {
					fw.write("\n" + score.getName() + " " + score.getScore() + " " + score.getMap() + " " + score.getMines());
				}
				
			}
		    fw.close();
    	} catch (IOException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }	
	}
	public ObservableList<Score> readScores() {
		ObservableList<Score> scores = FXCollections.observableArrayList();
		try {
		      File f = new File("scoreboard.txt");
		      Scanner fr = new Scanner(f);
		      while (fr.hasNextLine()) {
		    	  scores.add(new Score(fr.next(), fr.nextInt(), fr.next(), fr.nextInt()));
		      }
		      return scores;
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		      return null;
		    }
	}
	
	
}
