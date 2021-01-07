import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.input.*;
import java.awt.Color;

public class Main extends Application {

	private static final int SIZE = 3;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private Button btn1;
	private Label label1;
	private TicTacToeModel game;
	private Button[][] grid;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hello World!");
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 300, 250);
		
		label1 = new Label("This is the top");

		Label label2 = new Label("This is the bottom");
		
		btn1 = new Button("Click me");
		
		GridPane centerPane = new GridPane();
		
		BorderPane borderPane = new BorderPane();
		root.getChildren().add(borderPane);
		
		borderPane.setTop(label1);
		borderPane.setCenter(centerPane);
		
		borderPane.setBottom(label2);
		
		grid = new Button[SIZE][SIZE];
		game = new TicTacToeModel();	
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				grid[j][i] = new Button("" + game.getValue(j,i));
				grid[j][i].setId(""+ i + j);
				Button button = grid[j][i];
				grid[j][i].setOnAction(event -> handleClick(button));
				centerPane.add(grid[j][i], j, i);
				
			}
		}
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		scene.setOnMouseMoved(this::mouseMove);
	}

	private void mouseMove(MouseEvent event) {
		double mouseX = event.getSceneX();
		double mouseY = event.getSceneY();
		label1.setText("x: " + mouseX + " - y: " + mouseY);
		
		btn1.setText("Click me");
	}
	
	private void handleClick(Button button) {
		String id = button.getId();
		
		int x = Integer.parseInt(id.substring(1,2));
		int y = Integer.parseInt(id.substring(0,1));
		if(game.makeMove(x, y)) {
			game.changePlayer();
			grid[x][y].setText("" + game.getPlayer());	
		}
		
		
	}
}
