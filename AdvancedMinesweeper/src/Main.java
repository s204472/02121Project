import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	public static void main(String[] args) {
		launch(args); //launches the method start
    }

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
		BorderPane root = (BorderPane) loader.load();
		
		primaryStage.setTitle("Minesweeper");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
    public void stop(){
        System.exit(0);
    }
}