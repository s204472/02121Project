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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;	

public class Controller implements Initializable {
	@FXML
	public GridPane gameGrid;
	@FXML
	public Button[][] buttons;
	
	private GameModel gameModel;
	private int xSize;
	private int ySize;
	private int screenHeight;
	
	public Controller(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	this.xSize = gameModel.getXSize();
    	this.ySize = gameModel.getYSize();
    	
    	buttons = new Button[xSize][ySize];
    	
    	
        screenHeight = (int) (Screen.getPrimary().getBounds().getHeight() - 200);
        
    	
    	createButtons();
    	
    	gameGrid.setPrefSize((screenHeight/ySize+1)*xSize, (screenHeight/ySize+1)*ySize);
    }
    
    public void createButtons() {
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				buttons[i][j] = new Button();
				
				// Setting button layout to fit screen
				buttons[i][j].setPrefSize(screenHeight/ySize+1,(screenHeight/ySize)+1);
				buttons[i][j].setStyle(String.format("-fx-font-size: %dpx;", (int)(0.8*screenHeight/ySize)));

				gameGrid.add(buttons[i][j], i, j);
				
				int x = i;
				int y = j;
				buttons[i][j].setOnAction(event -> handleClick(x, y));
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



