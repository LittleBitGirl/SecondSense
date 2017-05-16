package application;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	
	Stage window;
    @Override
	public void start(Stage stage) throws IOException{
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainInterfaceController.fxml"));
    	Scene scene = new Scene(root);
		System.out.println("-----");
		
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/unmute.png")));
		stage.setScene(scene);
        stage.setTitle("SECOND SENSE");
		stage.show();
		System.out.println(root.toString());
		
	}
    
    
	
	private void changeStageForStart() throws IOException{
		System.out.println("in method");
		Stage stage = window;
	    Parent root;
	    root = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public static void main(String[] args) {
        launch(args);
    }
	
}

