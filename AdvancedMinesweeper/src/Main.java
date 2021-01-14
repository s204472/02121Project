import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.sql.*;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args); //launches the method start
    }

	public void start(Stage primaryStage) throws Exception {
		
		DatabaseConfig dbConf = new DatabaseConfig();
		Connection con  = dbConf.getCon();
		try {
			if (con != null) {
				String query = "SELECT * FROM testtabel";
				Statement st = con.createStatement();
		     	ResultSet rs = st.executeQuery(query);
			      
			    // iterate through the java resultset
		     	while (rs.next()){
		     		int id = rs.getInt("Id");
			        String name = rs.getString("Navn");
			        System.out.println(id + ":" + name);
		     	}	
		     	st.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
			
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view.fxml"));

		BorderPane root = (BorderPane) loader.load();
		primaryStage.setTitle("Minesweeper");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
