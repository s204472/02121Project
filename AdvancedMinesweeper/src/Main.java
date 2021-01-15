import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
<<<<<<< HEAD
import javax.sound.sampled.AudioInputStream;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
=======
import java.sql.*;
>>>>>>> bd6da0375fee66705b36c5840b3466312c81ae38

public class Main extends Application {

	
	public static void main(String[] args) {
		launch(args); //lauches the method start
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

		FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
		BorderPane root = (BorderPane) loader.load();
		
		primaryStage.setTitle("Minesweeper");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}