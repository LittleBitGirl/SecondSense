package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Pusher_nature extends Application{

	Nature nature = new Nature();

	public static Stage window;

	@Override
	public void start(Stage stage) {
		try {

			// Scene
			Scene scene = new Scene(nature);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Primary Stage
			window = stage;
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/unmute.png")));
			stage.setScene(scene);
			stage.setOnCloseRequest(close->System.exit(0));
			stage.setTitle("SECOND SENSE");
			stage.show();
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, " Error loading the Main class", ex);
		}
	}
	
	public static void main(String args[]){
		Application.launch(args);
	}

}
