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

import javax.sound.sampled.AudioInputStream;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;


public class Main extends Application {

	
	public static void main(String[] args) {
		launch(args); //lauches the method start
    }

	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
		BorderPane root = (BorderPane) loader.load();
		
		primaryStage.setTitle("Minesweeper");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}