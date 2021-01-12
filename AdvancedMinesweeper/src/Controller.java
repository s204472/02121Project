import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

//Initializable makes the class able to interact with FXML file.
public class Controller implements Initializable {
	@FXML // loading GridPane from the FXML file
	public GridPane gameGrid;
	@FXML
	public Button[][] buttons;
	@FXML
	public TextField inputX;
	@FXML
	public TextField inputY;
	@FXML
	public TextField inputMines;
	@FXML
	public Label timer;
	private GameModel gameModel;
	private int xSize;
	private int ySize;
	private int mines;
	private int screenHeight;
	private int fontSize;
	private GameObjects[][] currentBoard;
	private boolean isTimerRunning;
	private Timer clock;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		screenHeight = (int) (Screen.getPrimary().getBounds().getHeight() - 200);
		gameGrid.setPrefSize(screenHeight, screenHeight - 100);
	}
	
	public void startGame() {
		cleanBoard();
		if (isTimerRunning) {
			clock.cancel();
		}
		xSize = getInteger(inputX.getText());
		ySize = getInteger(inputY.getText());
		mines = getInteger(inputMines.getText());
		
		if (isInputValid()) {
			gameModel = new GameModel(xSize, ySize, mines);

			buttons = new Button[xSize][ySize];	

			double fontMultiplier = xSize > 50 || ySize > 50 ? 0.7 : 0.3;
			this.fontSize = (int) (fontMultiplier * screenHeight / ySize);

			createButtons();
		} else {
			inputX.setText("");
			inputY.setText("");
			inputMines.setText("");
		}
	}
	
	public boolean isInputValid() {
		if (xSize >= 4 && xSize <= 100) {
			if (ySize >= 4 && ySize <= 100) {
				if (mines >= 4 && mines <= (int) xSize*ySize*0.9) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static Integer getInteger(String text) {
		if (text == null) {
			return 0;
		} else {
			try {
				return Integer.parseInt(text);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	// creates all the Buttons and makes clickable.
	public void createButtons() {
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				buttons[i][j] = new Button();

				// Setting button layout to fit screen
				buttons[i][j].setPrefSize(screenHeight / ySize + 1, screenHeight / ySize + 1);
				buttons[i][j].getStyleClass().add("gameButtons");
				buttons[i][j].setStyle(String.format("-fx-font-size: %dpx;", fontSize));

				gameGrid.add(buttons[i][j], i, j);

				int x = i;
				int y = j;
				buttons[x][y].setOnMouseClicked(event -> {
					if (event.getButton() == MouseButton.PRIMARY) {
						handleLeftClick(x, y);
					} else if (event.getButton() == MouseButton.SECONDARY) {
						handleRightClick(x, y);
					}
				});

			}
		}
	}
	
	

	// changing the appearance of a button
	public void updateButton(int x, int y) {
		currentBoard = gameModel.getCurrentBoard();
		if (currentBoard[x][y] instanceof Number) {
			Number num = (Number) currentBoard[x][y];
			if (num.getValue() == 0) {
				for (int i = x - 1; i <= x + 1; i++) {
					for (int j = y - 1; j <= y + 1; j++) {
						if ((i != x || j != y) && i >= 0 && i < currentBoard.length && j >= 0 && j < currentBoard[i].length
								&& buttons[i][j].getText() == "") {
							buttons[i][j].setText(currentBoard[i][j].toString()); 
							updateButton(i, j);
						}
					}
				}
			}
		}
		
		if (currentBoard[x][y] instanceof Flag) {
			buttons[x][y].setText("P");
		} else if (currentBoard[x][y] instanceof Mine || currentBoard[x][y] instanceof Number) {
			buttons[x][y].setText(currentBoard[x][y].toString());
		} else {
			buttons[x][y].setText("");
		}
	}

	public void handleLeftClick(int x, int y) {
		if (gameModel.getDisplayedFields() == 0) {
			gameModel.getScoreModel().startTimer();
			startTimer();
		}
		if (!gameModel.getGameover()) {
			gameModel.clickField(x, y);
			updateButton(x, y);
			if (gameModel.checkWin()) {
				getFinalBoard();
				buttons[x][y].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				buttons[x][y].getStyleClass().add("button-won");
				System.out.println("handleLeftClick:" + gameModel.getScoreModel().calculateEndScore());
			}
			if (gameModel.checkGameover(x, y)) {
				getFinalBoard();
				buttons[x][y].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				buttons[x][y].getStyleClass().add("button-lost");	
			}
		}
	}

	public void handleRightClick(int x, int y) {
		currentBoard = gameModel.getCurrentBoard();
		if (!gameModel.getGameover()) {
			if (gameModel.checkFlag(x, y)) {
				gameModel.removeFlag(x, y);
				updateButton(x, y);
			} else if (currentBoard[x][y] == null) {
				gameModel.setFlag(x, y);
				updateButton(x, y);
			}

		}
	}

	public void getFinalBoard() {
		GameObjects[][] finalBoard = gameModel.getFinalBoard();
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

	public void updateTimer() {
		Platform.runLater(() -> timer.setText(gameModel.getScoreModel().getTimeElapsed()));
	}

	public void startTimer() {
		clock = new Timer();
		isTimerRunning = true;
		clock.schedule(new TimerTask() {
			@Override
			public void run() {
				if (gameModel.checkWin() || gameModel.getGameover()) {
					clock.cancel();
					isTimerRunning = false;
				}
				updateTimer();
			}
		}, 0, 1000);
	}

}