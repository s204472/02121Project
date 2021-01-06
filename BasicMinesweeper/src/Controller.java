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
    	GameObjects[][] board = gameModel.getCurrentBoard();

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				buttons[i][j] = new Button();
				buttons[i][j].setPrefHeight(50);
				buttons[i][j].setPrefWidth(50);
				gameGrid.add(buttons[i][j], i, j);
				int x = i;
				int y = j;
				buttons[i][j].setOnAction(event -> handleClick(x, y));
				buttons[i][j].setText(board[i][j] == null ? "" : board[i][j].toString());
			}
		}
	}
    public void updateButton(int x, int y) {
    	GameObjects[][] board = gameModel.getCurrentBoard();
    	buttons[x][y].setText(board[x][y].toString());
    	
    }
    
    
    public void handleClick(int x, int y) {
    	gameModel.clickField(x, y);
    	updateButton(x, y);
    	
    }

}



