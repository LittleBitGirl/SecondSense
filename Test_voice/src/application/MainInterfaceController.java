package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Game;
import application.Nature;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.SpeechCalculator;

public class MainInterfaceController implements Initializable {
	public static Stage window;
	@FXML private Button start;
	@FXML private Label statusLabel;
	@FXML private Button b1;
	private SpeechCalculator speechCalculator = new SpeechCalculator();

	@FXML
	private void initialize() {
		/*
		if(speechCalculator.startSaid(speechCalculator.result) == true){
			try {
				changeStage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			
		}*/
	}
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
	     Stage stage;
	     Parent root;
	     if(event.getSource()==start){     
	        stage=(Stage) start.getScene().getWindow();
	        //load up OTHER FXML document
	        root = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
	      }
	     else{
	       stage=(Stage) b1.getScene().getWindow();
	       root = FXMLLoader.load(getClass().getResource("/fxml/nature.fxml"));
	     }
	     //create a new scene with root and set the stage
	      Scene scene = new Scene(root);
	      stage.setScene(scene);
	      stage.show();
	}
	private void changeStage(){
		Stage stage;
	    Parent root;
	    stage=(Stage) start.getScene().getWindow();
	    try {
			root = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    stage.show();
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}	
}