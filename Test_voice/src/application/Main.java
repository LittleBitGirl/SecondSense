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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.SpeechRecognition;

public class Main extends Application {

	Stage window;
  
    private SpeechRecognition SR = new SpeechRecognition();
    @Override
	public void start(Stage stage) throws IOException{
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainInterfaceController.fxml"));
    	/*Platform.runLater(()->{
			try {
				root = FXMLLoader.load(getClass().getResource("/fxml/MainInterfaceController.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});*/
    	Scene scene = new Scene(root);
    	System.out.println();
		System.out.println("-----");
		
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/unmute.png")));
		stage.setScene(scene);
        stage.setTitle("SECOND SENSE");
        
        SR.startSpeechThread();
		SR.sayWelcome();
		
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				SR.stopSpeechThread();
			}
		});
		System.out.println(root.toString());
		while(true){
			if(SR.recognizer.getResult() != null){
				if(SR.startSaid(SR.recognizer.getResult().getHypothesis())){
					SR.textToSpeech.speak("Okay", 1.9f, false, true);
					System.out.println("Staart");
					changeStageForStart();
				}
				else{
					System.out.println("no");
				}
			}
			else{
				SR.textToSpeech.speak("Say start", 1.9f, false, true);
			}
		}
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
    
	
}

