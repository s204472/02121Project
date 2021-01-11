import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
<<<<<<< HEAD
import javafx.scene.input.MouseButton;
=======
import javafx.scene.control.TextField;
>>>>>>> e06909357796364aee444cde45e6b85745cf9b4a
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

	
//Initializable makes the class able to interact with FXML file.
public class Controller implements Initializable {
	@FXML //loading GridPane from the FXML file
	public GridPane gameGrid;
	@FXML
	public Button[][] buttons;
	@FXML 
	public TextField inputX;
	@FXML 
	public TextField inputY;
	@FXML 
	public TextField inputMines;
	
	private GameModel gameModel;
	private int xSize;
	private int ySize;
	private int mines;
	private int screenHeight;
	private int fontSize;
	
	//construct the Controller objekt, with a given GameModel
	public Controller() {
		
	}
	
    @Override 
    public void initialize(URL location, ResourceBundle resources) {    	
    	screenHeight = (int) (Screen.getPrimary().getBounds().getHeight() - 200);
    	gameGrid.setPrefSize(screenHeight, screenHeight - 100);
    }
   //creates all the Buttons and makes clickable.
    public void createButtons() {
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				buttons[i][j] = new Button();
				
				// Setting button layout to fit screen
				buttons[i][j].setPrefSize(screenHeight / ySize+1, (screenHeight / ySize) + 1);
				buttons[i][j].getStyleClass().add("gameButtons");
				buttons[i][j].setStyle(String.format("-fx-font-size: %dpx;", fontSize));

				gameGrid.add(buttons[i][j], i, j);
				
				int x = i;
				int y = j;
				buttons[i][j].setOnMouseClicked(event -> 
				{
					if(event.getButton() == MouseButton.PRIMARY) {
						handleClick(x, y);
					} else if (event.getButton() == MouseButton.SECONDARY) {
						handleLeftClick(x, y);
					}
				});
				
				} //makes each button able to handle a click. 
			}
		}
    //changing the appearance of a button 
    public void updateButton(int x, int y) {
    	GameObjects[][] board = gameModel.getCurrentBoard();
    	buttons[x][y].setText(board[x][y].toString());
    }
    
    //handling user click-input
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
	
	public void handleLeftClick(int x, int y) {
		GameObjects[][] board = gameModel.getCurrentBoard();
		if (!gameModel.getGameOver()) {
			if(gameModel.checkFlag(x, y)) {
				gameModel.removeFlag(x, y);
				updateButton(x, y);
			} else if (board[x][y] == null) {
				gameModel.setFlag(x, y);
				updateButton(x,y);
			}
			
		}
	}
    
    public void showAll() {
    	GameObjects[][] finalBoard = gameModel.showAll();
    	for (int i = 0; i < xSize; i++) {
    		for (int j = 0; j < ySize; j++) {
    			buttons[i][j].setText(finalBoard[i][j].toString());
    		}
    	}
    }
    
    public void cleanBoard() {
    	for (int i = 0; i < xSize; i++) {
    		for (int j = 0; j < ySize; j++) {
    			gameGrid.getChildren().remove(buttons[i][j]);
    		}
    	}
    	
    	
    }
    
    public void startGame() {
    	cleanBoard();
    	
    	xSize = Integer.parseInt(inputX.getText());
    	ySize = Integer.parseInt(inputY.getText());
    	mines = Integer.parseInt(inputMines.getText());
    	
    	gameModel = new GameModel(xSize, ySize, mines);
    	
    	buttons = new Button[xSize][ySize];
    	
    	
    	double fontMultiplier = xSize > 50 || ySize > 50 ? 0.7 : 0.3; 
        this.fontSize = (int)(fontMultiplier * screenHeight / ySize);
    	
    	createButtons();
    	
    }
    
    
}