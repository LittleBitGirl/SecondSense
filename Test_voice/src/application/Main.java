package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.SpeechCalculator;

public class Main extends Application {

	public static Stage window;
	Button start, nat, characters, mix;
    Scene scene1, scene2;
    BorderPane pane1, pane2;
    Parent root;
    Scene root2;
    Scene root3;
    public SpeechCalculator speechCalculator = new SpeechCalculator();
    @Override
	public void start(Stage stage) {
    	speechCalculator.startSpeechThread();
		speechCalculator.sayWelcome();
		window = stage;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/MainInterfaceController.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
        stage.setTitle("SECOND SENSE");
        stage.show();
        
        if(stage.getScene()!=root.getScene()){
        	speechCalculator.stopSpeechThread();
        }
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}

