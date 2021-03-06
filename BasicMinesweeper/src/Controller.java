import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;


/*
 * Made by:
 * 		Generating buttons: Kevin Moore, s204462 and Anders Gad, s204496
 * 		Updating and handling clicks: Anders Gad, s204496
 */

	
// Initializable makes the class able to interact with FXML file.
public class Controller implements Initializable {
	@FXML
	public GridPane gameGrid;
	@FXML
	public Button[][] buttons;
	
	private GameModel gameModel;
	private int xSize;
	private int ySize;
	private int screenHeight;
	private int fontSize;
	private int biggestSide;
	
	// Construct the Controller object, with a given GameModel
	public Controller(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	
    @Override 
    public void initialize(URL location, ResourceBundle resources) {
    	this.xSize = gameModel.getXSize();
    	this.ySize = gameModel.getYSize();
    	
    	this.buttons = new Button[xSize][ySize];
    	
        this.screenHeight = (int) (Screen.getPrimary().getBounds().getHeight() - 200);
		gameGrid.setPrefSize(screenHeight, screenHeight - 100);

        double fontMultiplier = xSize > 50 || ySize > 50 ? 0.7 : 0.5;
		biggestSide = xSize > ySize ? xSize : ySize;

		this.fontSize = (int) (fontMultiplier * screenHeight / biggestSide);

    	
    	createButtons();
    	
    }
    
   // Creates all the Buttons and adds eventhandlers
    public void createButtons() {
    	
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				buttons[i][j] = new Button();
				buttons[i][j].setPrefSize(screenHeight / ySize + 1, screenHeight / biggestSide + 1);
				buttons[i][j].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				gameGrid.add(buttons[i][j], i, j);
				
				int x = i;
				int y = j;
				buttons[i][j].setOnAction(event -> handleClick(x, y)); // Makes each button able to handle a click. 
			}
		}
	}
    
    // Changing the appearance of a button 
    public void updateButton(int x, int y) {
    	GameObjects[][] board = gameModel.getCurrentBoard();
    	buttons[x][y].setText(board[x][y].toString());
    }
    
    // Called on userclick. Checking for gameover, updates data and buttons.
	public void handleClick(int x, int y) {
		if (!gameModel.getGameOver()) {
			gameModel.clickField(x, y);
			updateButton(x, y);
			if (gameModel.checkWin()) {
				showAll();
				buttons[x][y].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				buttons[x][y].getStyleClass().add("button-won");
			}
			if (gameModel.checkGameover(x, y)) {
				showAll();
				buttons[x][y].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				buttons[x][y].getStyleClass().add("button-lost");
			}

		}
	}
    
	// Reveals all buttons to user
    public void showAll() {
    	GameObjects[][] finalBoard = gameModel.showAll();
    	for (int i = 0; i < xSize; i++) {
    		for (int j = 0; j < ySize; j++) {
    			buttons[i][j].setText(finalBoard[i][j].toString());
    		}
    	}
    }
    
    
}



