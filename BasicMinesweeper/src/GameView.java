import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;	

public class GameView implements Initializable {
	public GridPane gameGrid;
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	createButtons(10,10);
    }

    
    public void createButtons(int nsize, int msize) {
		for(int i=0; i<nsize; i++) {
			for(int j=0; j<msize; j++) {
				gameGrid.add(new Button(), i, j);
			}
		}
	}
}







/*

	
	public void showScene() {
		
	}
	
	public void buttonClickAction() {
		int y = GridPane.getRowIndex(buttons);
		int x = GridPane.getColumnIndex(buttons);		
	}
	
	public void seteventhandler(eventhandler) {
		button.setOnAction((eventhandler));
}
	*/

