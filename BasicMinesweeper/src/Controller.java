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

public class Controller implements Initializable {
	public GridPane gameGrid;
	public Button[][] buttons;
	private GameModel gameModel;
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	buttons = new Button[10][10];
    	gameModel = new GameModel(10,10,5);
    	createButtons(10,10);
    }
    
    public void createButtons(int nsize, int msize) {
		for(int i = 0; i < nsize; i++) {
			for(int j = 0; j < msize; j++) {
				buttons[i][j] = new Button();
				gameGrid.add(buttons[i][j], i, j);
				int x = i;
				int y = j;
				buttons[i][j].setOnAction(event -> handleClick(x, y));
			}
		}
	}
    
    
    public void handleClick(int x, int y) {
    	gameModel.updateField(x, y);
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

