import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
/*
 * Made by:
 * 		Basic window settings: By all
 * 		Input validation: Kevin Moore, s204462
 * */



public class Main extends Application {
	private static int xSize;
	private static int ySize;
	private static int mines;

	public static void main(String[] args) {
    	 
    	// Handling arguments from the command line.
		try {
			if (args.length != 3) {
				throw new IllegalArgumentException("Not correct number of arguments, please only enter 3"); 
			} else {
				xSize = Integer.parseInt(args[0]);
				ySize = Integer.parseInt(args[1]);
				mines = Integer.parseInt(args[2]);
				if (xSize > 100 || xSize < 4 || ySize > 100 || ySize < 4 || mines > 2000 || mines < 5 || mines >= (xSize*ySize)) {
					throw new IllegalArgumentException("Illegal size arguments given");
				}
			}
			// Launches the program
			launch(args);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Please enter 3 integers.");
		}
    }

	
	// Basic window settings and initializing.
	public void start(Stage primaryStage) throws Exception {

		GameModel gameModel = new GameModel(xSize, ySize, mines);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view.fxml"));
		loader.setControllerFactory(new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> aClass) {
				return new Controller(gameModel);
			}
		});

		GridPane root = (GridPane) loader.load();
		primaryStage.setTitle("Basic-Minesweeper");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
