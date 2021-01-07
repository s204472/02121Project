import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
	private boolean vertical;
	private int windowSize;
	
	//construct the Controller objekt, with a given GameModel
	public Controller(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	
    @Override 
    public void initialize(URL location, ResourceBundle resources) {
    	this.xSize = gameModel.getXSize();
    	this.ySize = gameModel.getYSize();
    	this.vertical = xSize > ySize ? false : true;
    	
    	buttons = new Button[xSize][ySize];
    
    	createButtons();
    
    	windowSize = (int) (Screen.getPrimary().getBounds().getHeight()) - 200;
    	gameGrid.setPrefSize(windowSize, windowSize);
    	System.out.print(windowSize/xSize);
    }
    
   //creates all the Buttons and makes clickable.
    public void createButtons() {
    	
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				buttons[i][j] = new Button();
				
				// Setting button layout to fit screen
				if (vertical) {
					buttons[i][j].setHeight(windowSize / xSize)
				} else {
					buttons[i][j].setMinSize(windowSize / xSize, windowSize / xSize);
					buttons[i][j].setMaxSize(windowSize / xSize + 1, windowSize / xSize + 1);
				}
				
				// String fontSize = "-fx-font-size:" + 10 + "px;";
				// buttons[i][j].setStyle("-fx-font-size: 10px;");
				
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
    	gameModel.clickField(x, y);
    	updateButton(x, y);
    	if (gameModel.checkWin()) {
    		showAll();
    		buttons[x][y].setStyle("-fx-background-color: #75ff42;");
    	}
    	if (gameModel.checkGameover(x, y)) {
    		showAll();
    		buttons[x][y].setStyle("-fx-background-color: #ff4242;");
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



