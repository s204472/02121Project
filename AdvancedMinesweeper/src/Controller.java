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
	// loading GridPane from the FXML file
	@FXML
	private GridPane gameGrid;
	//@FXML
	//private Button[][] buttons;
	@FXML
	private TextField inputWidth, inputHeight, inputMines;
	@FXML
	private Label timer, points, difficulty;
	@FXML
	private TableView<Score> tableView;
	@FXML
	private TableColumn<Score, Integer> scoreColumn, minesColumn;
	@FXML
	private TableColumn<Score, String> mapColumn;

	public File bombSound = new File("src//audio//bombSound.wav");
	public File clickSound = new File("src//audio//clickSound.wav");
	public File illegalInputSound = new File("src//audio//IllegalInput.wav");
	public File backgroundMusic = new File("src//audio//backgroundMusic.wav");
	public File flagSound = new File("src//audio//flagSound.wav");
	public File winSound = new File("src//audio//winSound.wav");
	public Clip backGroundClip;

	private GameModel gameModel;

	private ObservableList<Score> scores;

	private Timer clock;
	private boolean isTimerRunning;

	private int screenHeight, fontSize;
	
	@FXML
	private GameButtons[][] buttons;

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
			stopAudioloop(backGroundClip);
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
			difficulty.setText(gameModel.getScoreModel().calculateDifficulty());
		} else {
			playAudio(illegalInputSound);
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
			backGroundClip = playAudioloop(backgroundMusic);
		}

		if (!gameModel.getGameOver()) {
			gameModel.clickField(x, y);
			playAudio(clickSound);
			updateButton(x, y);
			checkZero(x, y);

			if (gameModel.checkWin()) {
				stopAudioloop(backGroundClip);
				playAudio(winSound);
				showFinalBoard();
				buttons[x][y].styleWin();	
				
				Score score = new Score(gameModel.getScoreModel().getScore(), gameModel.getWidth(), gameModel.getHeight(), gameModel.getMines());
				scores.add(score);
				tableView.setItems(scores);
				
			} else if (gameModel.checkGameover(x, y)) {
				stopAudioloop(backGroundClip);
				playAudio(bombSound);
				showFinalBoard();
				buttons[x][y].styleGameover();
			}
		}
	}

	public void handleRightClick(int x, int y) {
		GameObjects[][] currentBoard = gameModel.getCurrentBoard();
		if (!gameModel.getGameOver()) {
			playAudio(flagSound);
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
	
	public void hint() throws FileNotFoundException {
		if (!gameModel.getGameOver()) {
			int[] fieldToClick = gameModel.findHint();
			int x = fieldToClick[0];
			int y = fieldToClick[1];
			gameModel.clickField(x, y);
			checkZero(x, y);
			updateButton(x, y);
			if (gameModel.checkWin()) {
				showFinalBoard();
				buttons[x][y].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				buttons[x][y].getStyleClass().add("button-won");
				// playAudio(winSound);

				Score score = new Score(gameModel.getScoreModel().getScore(), gameModel.getWidth(),
						gameModel.getHeight(), gameModel.getMines());
				scores.add(score);
				tableView.setItems(scores);
			}
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
				if (gameModel.checkWin() || gameModel.getGameOver()) {
					clock.cancel();
					isTimerRunning = false;
				}
				updateUI();
			}
		}, 0, 1000);
	}
	
	public static void playAudio(File Sound) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();

		} catch (Exception e) {
		}

	}

	public static Clip playAudioloop(File Sound) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			return clip;
		} catch (Exception e) {
		}
		return null;
	}

	public void stopAudioloop(Clip clip) {

		try {
			backGroundClip.stop();
		} catch (Exception e) {
		}
	}
}