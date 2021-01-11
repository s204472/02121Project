import java.util.IllegalFormatException;
import java.util.InputMismatchException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {
	private static int xSize;
	private static int ySize;
	private static int mines;

	public static void main(String[] args) {
    		
		launch(args); //lauches the method start
		
    }

	public void start(Stage primaryStage) throws Exception {
		

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view.fxml"));
		loader.setControllerFactory(new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> aClass) {
				return new Controller();
			}
		});

		BorderPane root = (BorderPane) loader.load();
		primaryStage.setTitle("Minesweeper");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
