import javafx.fxml.FXML;
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
	@FXML
	public GridPane gameGrid;
	@FXML
	public Button[][] buttons;
	
	private GameModel gameModel;
	private int n;
	private int m;
	
	public Controller(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	this.n = gameModel.getN();
    	this.m = gameModel.getM();
    	
    	gameGrid.add(new Button(), 1, 1);
    	buttons = new Button[30][30];
    	createButtons();
    }
    
    public void createButtons() {
    	System.out.print(n);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				buttons[i][j] = new Button();
				gameGrid.add(buttons[i][j], i, j);
				int x = i;
				int y = j;
				buttons[i][j].setOnAction(event -> handleClick(x, y));
			}
		}
	}
    
    
    public void handleClick(int x, int y) {
    	gameModel.clickField(x, y);
    	
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

