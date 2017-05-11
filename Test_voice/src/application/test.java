package application;

import java.io.File;
import java.sql.*;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class test extends Application{
	public static void main(String args[]){
		Application.launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		String url      = "jdbc:mysql://localhost:3306/";
		String user     = "root";
		String password = "";
		String uurrll = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url, user, password);
			
			Statement stmnt = conn.createStatement();
			
			stmnt.execute("USE sounds");
			Random r = new Random();
			int Low = 1;
			int High = 8;
			int randID = r.nextInt(High-Low) + Low;
			
			
			ResultSet rs = stmnt.executeQuery("SELECT * FROM animals WHERE id = " + randID + "");
			
			while(rs.next()){
				uurrll = rs.getString("url");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
        Pane pane = new Pane();
        StackPane stackPane = new StackPane(pane);
		
        Media sound = new Media(new File(uurrll).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        primaryStage.setScene(new Scene(stackPane, 200, 200));
        primaryStage.setTitle("Countdown");
        primaryStage.show();
		
	}
}

