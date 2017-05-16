package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
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

public class Game{
	
	@FXML
	private Button b1;
	@FXML
	private Button b2;
	@FXML
	private Button b3;
	public static int CATEGORY = 1;
	Nature nature = new Nature();
	public static Stage window;
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		 CATEGORY = 1;
		Stage stage = (Stage) b1.getScene().getWindow();
		 Scene scene = new Scene(nature);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 
			// Primary Stage
			window = stage;
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/unmute.png")));
			stage.setScene(scene);
			stage.setOnCloseRequest(close -> System.exit(0));
			stage.show();
	      
	}
	
	@FXML
	private void CharactersChoosed(){
			Stage stage = (Stage) b1.getScene().getWindow();
			Scene scene = new Scene(nature);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 
			// Primary Stage
			window = stage;
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/unmute.png")));
			stage.setScene(scene);
			stage.setOnCloseRequest(close -> System.exit(0));
			stage.show();
			CATEGORY = 2;
	}
	
}