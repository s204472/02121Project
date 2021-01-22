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
<<<<<<< HEAD
 * Lavet af:
 * 		Generering af knapper: Kevin og Gad
 * 		Input validering: Kevin
 * 		Autofill: Gad
 * 		Flag og ikoner: Reher
 * 		Hint: Reher
 * 		Mute/Unmute: Gad
 * 		Scoreboard: Magnus
 * 		Styling og ops�tning af menu: Magnus
 * 		Timer: Magnus
=======
 * Made by:
 * 		Generating buttons: Kevin Moore, s204462 and Anders Gad, s204496
 * 		Input validation: Kevin Moore, s204462
 * 		Autofill: Anders Gad, s204496
 * 		Flag and icons: Anders Reher, s194587
 * 		Hint: Anders Reher, s194587
 * 		Mute/Unmute: Anders Gad, s204496
 * 		Scoreboard: Magnus Siegumfeldt, s204472
 * 		Styling and layout: Magnus Siegumfeldt, s204472
 * 		Timer: Magnus Siegumfeldt, s204472
>>>>>>> ca4ce2dc5002b940ad257ae720ffad32b0f7e708
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
	
	// Checks if the user input is valid for board size and miens
	private boolean isInputValid(int width, int height, int mines) {
		return (width >= 4 && width <= 100 &&
				height >= 4 && height <= 100 &&
				mines >= 4 && mines <= (int) width * height * 0.9) 
				? true : false;
	}
	
	// Takes intger values from the input fields in the menu bar.
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

	// Creates all the Buttons and makes clickable.
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

	// Changing the appearance of a button
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
	
	// Recursive function that checks if the clicked field is a zero and then checks the surrounding fields for zero
	// and displays it for the player
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
	
	// Handles the left click on a field and what to do
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

	// Handles the right click on a field and places and removes a flag
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
	
	// Shows the finalboard when the game is over
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
	
	// Checks if the hint button has been pressed shows it to the player
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

	// Cleans the board for the buttons
	public void cleanBoard() {
		for (int i = 0; i < gameModel.getWidth(); i++) {
			for (int j = 0; j < gameModel.getHeight(); j++) {
				gameGrid.getChildren().remove(buttons[i][j]);
			}
		}
	}
	
	// Updates the times and score every second
	public void updateUI() {
		Platform.runLater(() -> {
			timer.setText(gameModel.getScoreModel().getTimeElapsed());
			gameModel.getScoreModel().incSeconds();
			points.setText("" + gameModel.getScoreModel().getScore());
		});
	}
	
	// Starts the timer when a new game has begun
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
	
	// What to do when the player has won the game
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
	
	// Mutes and unmutes the sound in the game
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