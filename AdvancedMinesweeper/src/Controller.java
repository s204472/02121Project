import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;

import java.net.URL;
import java.util.*;
import java.io.*;
import javax.sound.sampled.*;


//Initializable makes the class able to interact with FXML file.
public class Controller implements Initializable {
	@FXML // loading GridPane from the FXML file
	private GridPane gameGrid;
	@FXML
	private Button[][] buttons;
	@FXML
	private TextField inputX, inputY, inputMines;
	@FXML
	private Label timer, points, difficulty;
	@FXML
	private TableView<Score> tableView;
    @FXML
    private TableColumn<Score, Integer> scoreColumn, minesColumn;
    @FXML
    private TableColumn<Score, String> mapColumn;
	
	
	public File Bomb = new File("src\\audio\\bombSound.wav");
	public File clickSound = new File("src\\audio\\clickSound.wav");
	public File uLovLigtInput = new File("src\\audio\\ulovligtinput.wav");
	public File backGroundMusic = new File("src\\audio\\John_Bartmann_-_07_-_African_Moon (online-audio-converter.com).wav");
	public File Flag = new File("src\\audio\\flag.wav");
	public File winSound = new File("src\\audio\\Ta Da-SoundBible.com-1884170640.wav");
	
	private GameModel gameModel;
	private GameObjects[][] currentBoard;
	
	private ObservableList<Score> scores;
	
	private Timer clock;
	private boolean isTimerRunning;
	
	private int screenHeight, fontSize;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		screenHeight = (int) (Screen.getPrimary().getBounds().getHeight() - 100);
		gameGrid.setPrefSize(screenHeight, screenHeight - 100);
		
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		mapColumn.setCellValueFactory(new PropertyValueFactory<>("map"));
		minesColumn.setCellValueFactory(new PropertyValueFactory<>("mines"));

		scores = FXCollections.observableArrayList();
	}
	
	public void startGame() {
		if (gameModel != null) {
			cleanBoard();
		}
		if (isTimerRunning) {
			clock.cancel();
		}
		int xSize = getInteger(inputX.getText());
		int ySize = getInteger(inputY.getText());
		int mines = getInteger(inputMines.getText());
		
		if (isInputValid(xSize, ySize, mines)) {
			gameModel = new GameModel(xSize, ySize, mines);

			buttons = new Button[xSize][ySize];	

			int biggestSide = xSize > ySize ? xSize : ySize;
			double fontMultiplier = biggestSide > 50 ? 1.2 : 0.5;
			

			this.fontSize = (int) (fontMultiplier * screenHeight / biggestSide);

			createButtons(xSize, ySize, biggestSide);
			difficulty.setText(gameModel.getScoreModel().calculateDifficulty());
		} else {
			//playAudio(uLovLigtInput);
			inputX.setText("");
			inputY.setText("");
			inputMines.setText("");
		}
	}
	
	private boolean isInputValid(int xSize, int ySize, int mines) {
		if (xSize >= 4 && xSize <= 100) {
			if (ySize >= 4 && ySize <= 100) {
				if (mines >= 4 && mines <= (int) xSize * ySize * 0.9) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static Integer getInteger(String text) {
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
	private void createButtons(int xSize, int ySize, int biggestSide) {
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				buttons[i][j] = new Button();
				buttons[i][j].setPrefSize(screenHeight / ySize + 1, screenHeight / biggestSide + 1);
				buttons[i][j].getStyleClass().add("gameButtons");
				buttons[i][j].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				gameGrid.add(buttons[i][j], i, j);

				int x = i;
				int y = j;
				buttons[x][y].setOnMouseClicked(event -> {
					if (event.getButton() == MouseButton.PRIMARY) {
						try {
							handleLeftClick(x, y);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					} else if (event.getButton() == MouseButton.SECONDARY) {
						try {
							handleRightClick(x, y);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
	}

	// changing the appearance of a button
	private void updateButton(int x, int y) throws FileNotFoundException {
		currentBoard = gameModel.getCurrentBoard();
		if (currentBoard[x][y] instanceof Flag) {
			buttons[x][y].setGraphic(((Flag) currentBoard[x][y]).getFlagImage(fontSize));
			//playAudio(Flag);
			
		} else if (currentBoard[x][y] instanceof Number) {
			buttons[x][y].setGraphic(null);
			buttons[x][y].setText(currentBoard[x][y].toString());
			String cssClass = "number" + currentBoard[x][y].toString();
			buttons[x][y].getStyleClass().add(cssClass);
			
		} else if (currentBoard[x][y] instanceof Zero) {
			buttons[x][y].setGraphic(null);
			buttons[x][y].getStyleClass().add("blank");
		}
	}
	
	private void checkZero(int x, int y) throws FileNotFoundException {
		GameObjects[][] finalBoard = gameModel.getFinalBoard();
		currentBoard = gameModel.getCurrentBoard();
		if (currentBoard[x][y] instanceof Zero) {
			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {	
					if ((i != x || j != y) && i >= 0 && i < finalBoard.length && j >= 0 && j < finalBoard[i].length && !finalBoard[i][j].getVisited()) {
						gameModel.clickField(i, j);
						updateButton(i, j);	
						finalBoard[i][j].setVisited();
						checkZero(i, j);
					}
				}
			}
		}

	}

	public void handleLeftClick(int x, int y) throws FileNotFoundException {
		if (gameModel.getDisplayedFields() == 0) {
			startTimer();
			//playAudio(backGroundMusic);
		}
		if (!gameModel.getGameover()) {
			gameModel.clickField(x, y);
			//playAudio(clickSound);
			updateButton(x, y);
			checkZero(x, y);

			if (gameModel.checkWin()) {
				getFinalBoard();
				buttons[x][y].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				buttons[x][y].getStyleClass().add("button-won");
				//playAudio(winSound);
				
				Score score = new Score(gameModel.getScoreModel().getScore(), gameModel.getXSize(), gameModel.getYSize(), gameModel.getMines());
				scores.add(score);
				tableView.setItems(scores);
				
			}
			if (gameModel.checkGameover(x, y)) {
				//playAudio(Bomb);
				getFinalBoard();
				buttons[x][y].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				buttons[x][y].getStyleClass().add("button-lost");	
			}
		}	
	}

	public void handleRightClick(int x, int y) throws FileNotFoundException {
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

	public void getFinalBoard() throws FileNotFoundException {
		GameObjects[][] finalBoard = gameModel.getFinalBoard();
		for (int i = 0; i < gameModel.getXSize(); i++) {
			for (int j = 0; j < gameModel.getYSize(); j++) {
				if (currentBoard[i][j] instanceof Flag) {
					buttons[i][j].setGraphic(null);
				}
				if (finalBoard[i][j] instanceof Mine) {
					buttons[i][j].setGraphic(((Mine) finalBoard[i][j]).getMineImage(fontSize));
				} else if (finalBoard[i][j] instanceof Number){
					Number num = (Number) finalBoard[i][j];
					
					String cssClass = "number" + num;
					buttons[i][j].getStyleClass().add(cssClass);
					buttons[i][j].setText(finalBoard[i][j].toString());
					
				} else { // instanceof Zero
					buttons[i][j].getStyleClass().add("blank");
				}				
			}
		}
	}
	
	public void hint() throws FileNotFoundException {
		if (!gameModel.getGameover()) {
			int[] fieldToClick = gameModel.findHint();
			int x = fieldToClick[0];
			int y = fieldToClick[1];
			gameModel.clickField(x, y);
			checkZero(x, y);
			updateButton(x, y);
			if (gameModel.checkWin()) {
				getFinalBoard();
				buttons[x][y].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				buttons[x][y].getStyleClass().add("button-won");
				// playAudio(winSound);

				Score score = new Score(gameModel.getScoreModel().getScore(), gameModel.getXSize(),
						gameModel.getYSize(), gameModel.getMines());
				scores.add(score);
				tableView.setItems(scores);
			}
		}
	}

	public void cleanBoard() {
		for (int i = 0; i < gameModel.getXSize(); i++) {
			for (int j = 0; j < gameModel.getYSize(); j++) {
				gameGrid.getChildren().remove(buttons[i][j]);
			}
		}
	}

	public void updateUI() {
		Platform.runLater(() -> {
			gameModel.getScoreModel().incSeconds();
			timer.setText(gameModel.getScoreModel().getTimeElapsed());
			points.setText("" + gameModel.getScoreModel().getScore());
		});
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
				updateUI();
			}
		}, 0, 1000);
	}

	public static void playAudio(File Sound) {
		try {
			Clip clip = AudioSystem.getClip() ;
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start(); 
		}
		catch(Exception e) {
		}
		
	}
}
