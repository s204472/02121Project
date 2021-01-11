import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
<<<<<<< HEAD
import javafx.scene.control.TextField;
=======
>>>>>>> parent of 13c7208... Merge branch 'main' of https://github.com/s204472/02121Project into main
=======
>>>>>>> parent of 308f202... Flag added
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

	
//Initializable makes the class able to interact with FXML file.
public class Controller implements Initializable {
	@FXML //loading GridPane from the FXML file
	public GridPane gameGrid;
	@FXML
	public Button[][] buttons;
	
	private GameModel gameModel;
	private int xSize;
	private int ySize;
	private int screenHeight;
	private int fontSize;
	
	//construct the Controller objekt, with a given GameModel
	public Controller(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	
    @Override 
    public void initialize(URL location, ResourceBundle resources) {
    	this.xSize = gameModel.getXSize();
    	this.ySize = gameModel.getYSize();
    	
    	this.buttons = new Button[xSize][ySize];
    	
        this.screenHeight = (int) (Screen.getPrimary().getBounds().getHeight() - 200);
        double fontMultiplier = xSize > 50 || ySize > 50 ? 0.7 : 0.3; 
        this.fontSize = (int)(fontMultiplier * screenHeight / ySize);
    	
    	createButtons();
    	
    	gameGrid.setPrefSize((screenHeight / ySize + 1) * xSize, (screenHeight / ySize + 1) * ySize);
    }
   //creates all the Buttons and makes clickable.
    public void createButtons() {
    	
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				buttons[i][j] = new Button();
				
				// Setting button layout to fit screen
				buttons[i][j].setPrefSize(screenHeight / ySize+1, (screenHeight / ySize) + 1);
				
				buttons[i][j].setStyle(String.format("-fx-font-size: %dpx;", fontSize));

				gameGrid.add(buttons[i][j], i, j);
				
				int x = i;
				int y = j;
				buttons[i][j].setOnAction(event -> handleClick(x, y)); //makes each button able to handle a click. 
			}
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
				System.out.println(gameModel.getScoreModel().getTimeElapsed());
			}
			if (gameModel.checkGameover(x, y)) {
				showAll();
				buttons[x][y].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				buttons[x][y].getStyleClass().add("button-lost");
				System.out.println(gameModel.getScoreModel().getTimeElapsed());
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
    
    
}