import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;
import java.io.FileNotFoundException;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
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
	@FXML
	public Label points;
	
	public File Bomb = new File("src\\audio\\bombSound.wav");
	public File clickSound = new File("src\\audio\\clickSound.wav");
	public File uLovLigtInput = new File("src\\audio\\ulovligtinput.wav");
	public File backGroundMusic = new File("src\\audio\\John_Bartmann_-_07_-_African_Moon (online-audio-converter.com).wav");
	public File Flag = new File("src\\audio\\flag.wav");
	public File winSound = new File("src\\audio\\Ta Da-SoundBible.com-1884170640.wav");
	
	
	private GameModel gameModel;
	private int xSize;
	private int ySize;
	private int mines;
	private int screenHeight;
	private int fontSize;
	private GameObjects[][] currentBoard;
	private boolean isTimerRunning;
	private Timer clock;
	private int biggestSide;

    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		screenHeight = (int) (Screen.getPrimary().getBounds().getHeight() - 100);
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

			biggestSide = xSize > ySize ? xSize : ySize;
			double fontMultiplier = biggestSide > 50 ? 1.2 : 0.5;
			

			this.fontSize = (int) (fontMultiplier * screenHeight / biggestSide);

			createButtons();
		} else {
			playAudio(uLovLigtInput);
			inputX.setText("");
			inputY.setText("");
			inputMines.setText("");
		}
	}
	
	public boolean isInputValid() {
		if (xSize >= 4 && xSize <= 100) {
			if (ySize >= 4 && ySize <= 100) {
				if (mines >= 4 && mines <= (int) xSize * ySize * 0.9) {
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

				
				buttons[i][j].setPrefSize(screenHeight / ySize + 1, screenHeight / biggestSide + 1);
				buttons[i][j].setMinSize(30, 30);
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
	public void updateButton(int x, int y) throws FileNotFoundException {

		currentBoard = gameModel.getCurrentBoard();
		if (currentBoard[x][y] instanceof Flag) {
			buttons[x][y].setGraphic(((Flag) currentBoard[x][y]).getFlagImage(fontSize));
			playAudio(Flag);

		} else if (currentBoard[x][y] == null) {
			buttons[x][y].setGraphic(null);
			
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
	
	public void checkZero(int x, int y) throws FileNotFoundException {
		GameObjects[][] finalBoard = gameModel.getFinalBoard();
		currentBoard = gameModel.getCurrentBoard();
		if (finalBoard[x][y] instanceof Zero) {
			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {	
					if ((i != x || j != y) && i >= 0 && i < finalBoard.length && j >= 0 && j < finalBoard[i].length && !currentBoard[i][j].getVisited()) {
						gameModel.clickField(i, j);
						updateButton(i, j);	
						currentBoard[i][j].setVisited();
						checkZero(i, j);
					}
				}
			}
		}

	}

	public void handleLeftClick(int x, int y) throws FileNotFoundException {
		if (gameModel.getDisplayedFields() == 0) {
			startTimer();
			playAudio(backGroundMusic);
		}
		if (!gameModel.getGameover()) {
			gameModel.clickField(x, y);
			playAudio(clickSound);
			updateButton(x, y);
			checkZero(x, y);

			if (gameModel.checkWin()) {
				getFinalBoard();
				buttons[x][y].setStyle(String.format("-fx-font-size: %dpx;", fontSize));
				buttons[x][y].getStyleClass().add("button-won");
				playAudio(winSound);
				
				
				
				
			}
			if (gameModel.checkGameover(x, y)) {
				playAudio(Bomb);
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
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
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

	public void cleanBoard() {
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				gameGrid.getChildren().remove(buttons[i][j]);
			}
		}
	}

	public void updateUI() {
		Platform.runLater(() -> {
			gameModel.getScoreModel().incSeconds();
			timer.setText(gameModel.getScoreModel().getTimeElapsed());
			points.setText(gameModel.getScoreModel().getScore());
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
