import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {
	private int n;
	private int m;
	private int mines;
	
	
    public static void main(String[] args) {
    	 if (args.length != 3) {
             System.out.println("Not correct number of arguments, please only enter 3");
         } else {
             /*int n = Integer.parseInt(args[0]);
             int m = Integer.parseInt(args[1]);
             int mines = Integer.parseInt(args[2]);*/
        	 int n = 30;
        	 int m = 30;
        	 int mines = 30;
        	  
			 launch(args);
         }
    }

    //Override
    public void start(Stage primaryStage) throws Exception{
    	
        GameModel gameModel = new GameModel(n, m, mines);
        
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Controller(gameModel);
            }
        });
    	
    	
        GridPane root = (GridPane) loader.load();
        // Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        
        
       

    }


}
