package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.SpeechRecognition;

public class Game{
	@FXML
	private Button b1;
	@FXML
	private Button b2;
	@FXML
	private Button b3;
	@FXML private Button start;
	public int CATEGORY = 1;
	
	public static Stage window;
	//public SpeechRecognition SpeechRecognition = new SpeechRecognition();
	@FXML
	private void initialize() {
		//SpeechRecognition.startSpeechThread();
	}
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
	     Stage stage = (Stage) b1.getScene().getWindow();
	     Parent root;
	     Stage newStage = new Stage();
	     root = FXMLLoader.load(getClass().getResource("/fxml/nature.fxml"));
	     Scene scene = new Scene(root);
	     stage.close();
	     newStage.setScene(scene);
	     newStage.show();
	      
	}
}