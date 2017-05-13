package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SpeechRecognition;

public class MainInterfaceClass {
	MainInterfaceController MIC = new MainInterfaceController();
	private FXMLLoader fxmlLoader = null;
	private VBox root = null;
	private Scene scene;
	private Stage stage;
	SpeechRecognition SR = new SpeechRecognition();
	
	MainInterfaceClass(MainInterfaceController c)
	{
		MIC = c;
		try
		{
			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainInterfaceController.fxml"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
