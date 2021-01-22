import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;

import java.net.URL;
import java.util.*;


/*
 * Lavet af:
 * 		Generering af knapper: Kevin og Gad
 * 		Input validering: Kevin
 * 		Autofill: Gad
 * 		Flag og ikoner: Reher
 * 		Hint: Reher
 * 		Mute/Unmute: Gad
 * 		Scoreboard: Magnus
 * 		Styling og opsætning af menu: Magnus
 * 		Timer: Magnus
 *
 * */



//Initializable makes the class able to interact with FXML file.
public class Controller implements Initializable {
	// loading GridPane from the FXML file
	@FXML
	private GridPane gameGrid;
	@FXML
	private GameButtons[][] buttons;
	@FXML
	private TextField inputWidth, inputHeight, inputMines;
	@FXML
	private Label timer, points, difficultyHeader;
	@FXML
	private TableView<Score> tableView;
	@FXML
	private TableColumn<Score, Integer> scoreColumn, minesColumn;
	@FXML
	private TableColumn<Score, String> mapColumn;
	@FXML
	private Button hintButton, muteAndUnmute;

	private GameModel gameModel;

	private ObservableList<Score> scores;

	private Timer clock;
	private boolean isTimerRunning;
	
	private boolean isGameStarted;
	
	public AudioClip backGroundClip;

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
			GameSound.stopAudioloop(backGroundClip);
		}
		if (isTimerRunning) {
			clock.cancel();
		}
		
		int width = getInteger(inputWidth.getText());
		int height = getInteger(inputHeight.getText());
		int mines = getInteger(inputMines.getText());
		
		
		if (isInputValid(width, height, mines)) {

			gameModel = new GameModel(width, height, mines);
			buttons = new GameButtons[width][height];

			int biggestSide = width > height ? width : height;
			double fontMultiplier = biggestSide > 50 ? 1.2 : 0.5;
			this.fontSize = (int) (fontMultiplier * screenHeight / biggestSide);

			createButtons(width, height, biggestSide);
			difficultyHeader.setText(gameModel.getScoreModel().calculateDifficulty() + " Difficulty");
			hintButton.setText("Hints: " + gameModel.getRemainingHints());
			this.isGameStarted = true;
		} else {
			GameSound.playIllegalInputSound();
			inputWidth.setText("");
			inputHeight.setText("");
			inputMines.setText("");
		}

	}

	private boolean isInputValid(int width, int height, int mines) {
		return (width >= 4 && width <= 100 &&
				height >= 4 && height <= 100 &&
				mines >= 4 && mines <= (int) width * height * 0.9) 
				? true : false;
	}

	private static Integer getInteger(String text) {
		if (text != null) {
			try {
				return Integer.parseInt(text);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return 0;
	}

	// creates all the Buttons and makes clickable.
	private void createButtons(int width, int height, int biggestSide) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int x = i, y = j;
				buttons[i][j] = new GameButtons(height, screenHeight, biggestSide, fontSize);
				buttons[i][j].setOnMouseClicked(event -> {
					if (event.getButton() == MouseButton.PRIMARY) {
						handleLeftClick(x, y);
					} else if (event.getButton() == MouseButton.SECONDARY) {
						handleRightClick(x, y);
					}
				});
				gameGrid.add(buttons[i][j], i, j);
			}
		}
	}

	// changing the appearance of a button
	private void updateButton(int x, int y) {
		GameObjects[][] currentBoard = gameModel.getCurrentBoard();
		if (currentBoard[x][y] instanceof Flag) {
			buttons[x][y].setFlag((Flag) currentBoard[x][y]);
		} else if (currentBoard[x][y] instanceof Number) {
			buttons[x][y].setNumber((Number) currentBoard[x][y]);
		} else if (currentBoard[x][y] instanceof Zero) {
			buttons[x][y].setZero();
		} else if (currentBoard[x][y] == null) {
			buttons[x][y].setEmpty();
		}
	}

	private void checkZero(int x, int y) {
		GameObjects[][] finalBoard = gameModel.getFinalBoard();
		GameObjects[][] currentBoard = gameModel.getCurrentBoard();
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

	public void handleLeftClick(int x, int y) {
		if (gameModel.getDisplayedFields() == 0) {
			startTimer();
			if(!GameSound.getmuted()) {
			backGroundClip = GameSound.playAudioloop(GameSound.getBackgroundMusic());
			}
		}

		if (!gameModel.getGameOver()) {
			gameModel.clickField(x, y);
			if (gameModel.getDisplayedFields() == 1) {
				difficultyHeader.setText(gameModel.getScoreModel().calculateDifficulty() + " Difficulty");
			}
			updateButton(x, y);
			checkZero(x, y);
			checkWin(x, y);
			if (gameModel.checkGameover(x, y)) {
				GameSound.stopAudioloop(backGroundClip);
				GameSound.playMineSound();
				showFinalBoard();
				
				buttons[x][y].styleGameover();
			} else {
				GameSound.playClickSound();
			}
		}
	}

	public void handleRightClick(int x, int y) {
		GameObjects[][] currentBoard = gameModel.getCurrentBoard();
		if (!gameModel.getGameOver()) {
			GameSound.playFlagSound();
			if (gameModel.checkFlag(x, y)) {
				gameModel.removeFlag(x, y);
				updateButton(x, y);
			} else if (currentBoard[x][y] == null) {
				gameModel.setFlag(x, y);
				updateButton(x, y);
			}
		}

	}

	public void showFinalBoard() {
		GameObjects[][] finalBoard = gameModel.getFinalBoard();
		GameObjects[][] currentBoard = gameModel.getCurrentBoard();
		for (int i = 0; i < gameModel.getWidth(); i++) {
			for (int j = 0; j < gameModel.getHeight(); j++) {
				if (currentBoard[i][j] instanceof Flag) {
					buttons[i][j].setEmpty();
				}
				if (finalBoard[i][j] instanceof Mine) {
					buttons[i][j].setMine((Mine) finalBoard[i][j]);
					
				} else if (finalBoard[i][j] instanceof Number) {
					buttons[i][j].setNumber((Number) finalBoard[i][j]);
					
				} else { 
					buttons[i][j].setZero();
				}
			}
		}
	}
	
	public void hint() {
		if (isGameStarted && !gameModel.getGameOver() && gameModel.getRemainingHints() != 0
				&& gameModel.getDisplayedFields() != 0) {
			int[] fieldToClick = gameModel.findHint();
			gameModel.decreaseHints();
			;
			int x = fieldToClick[0];
			int y = fieldToClick[1];
			gameModel.clickField(x, y);
			checkZero(x, y);
			updateButton(x, y);
			gameModel.getScoreModel().decreaseHintScore();
			checkWin(x, y);
			hintButton.setText("Hints: " + gameModel.getRemainingHints());
		}

	}

	public void cleanBoard() {
		for (int i = 0; i < gameModel.getWidth(); i++) {
			for (int j = 0; j < gameModel.getHeight(); j++) {
				gameGrid.getChildren().remove(buttons[i][j]);
			}
		}
	}

	public void updateUI() {
		Platform.runLater(() -> {
			timer.setText(gameModel.getScoreModel().getTimeElapsed());
			gameModel.getScoreModel().incSeconds();
			points.setText("" + gameModel.getScoreModel().getScore());
		});
	}

	public void startTimer() {
		clock = new Timer();
		isTimerRunning = true;
		clock.schedule(new TimerTask() {
			@Override
			public void run() {
				if (gameModel.checkWin() || gameModel.getGameOver()) {
					clock.cancel();
					isTimerRunning = false;
				}
				updateUI();
			}
		}, 0, 1000);
	}
	
	public void checkWin(int x, int y) {
		if (gameModel.checkWin()) {
			showFinalBoard();
			buttons[x][y].styleWin();
		
			GameSound.stopAudioloop(backGroundClip);
			GameSound.playWinSound();
			Score score = new Score(gameModel.getScoreModel().getScore(), gameModel.getWidth(), gameModel.getHeight(),
					gameModel.getMines());
			scores.add(score);
			tableView.setItems(scores);
		}
	}
	public void muteAndUnmute() {
		boolean tempGameModel = gameModel == null ? false : gameModel.getGameOver();
		int tempDisplayedFields = gameModel == null ? 0 : gameModel.getDisplayedFields();
		GameSound.muteAndUnmute(backGroundClip, tempGameModel, tempDisplayedFields);
		if (!GameSound.getmuted()) {
			muteAndUnmute.setText("Unmute");
		} else {
			muteAndUnmute.setText("Mute");
		}
		GameSound.togglemute();
	}
}