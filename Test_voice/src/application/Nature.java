package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.LineUnavailableException;

import edu.cmu.sphinx.api.SpeechResult;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Nature extends AnchorPane{
	private int counter = 0;
	public static Stage window;
	@FXML
	private ImageView animation;
	@FXML
	private Label Mainlabel;
	@FXML
	public static Label BottomLabel; 
	@FXML
	private Button StartThread; 
	
	String imgUrl = null;
	public static String answer = null;
	
	private SpeechRecognition SR = new SpeechRecognition();
	
	Nature(){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/nature.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, " FXML can't be loaded!", ex);
		}
	}
	
	@FXML
	private void initialize(){
		StartThread.disableProperty().bind(SR.speechRecognizerThreadRunningProperty());
		StartThread.setOnAction(a -> {
			SR.startSpeechRecognition();
		});
	}
	private String SoundGenerator(){
		String url      = "jdbc:mysql://localhost:3306/";
		String user     = "root";
		String password = "";
		String uurrll   = null;
		String table    = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url, user, password);
			
			Statement stmnt = conn.createStatement();
			
			stmnt.execute("USE sounds");
			
			int randID;
			Random r = new Random();
			int Low = 1;
			int High = 18;
			randID = r.nextInt(High-Low) + Low;

			if(Game.CATEGORY == 1){
				
				table = "animals";
				
			}else{
				
				table = "characters";
				
			}
			ResultSet rs = stmnt.executeQuery("SELECT * FROM " + table + " WHERE id = " + randID + "");
			
			while(rs.next()){
				uurrll = rs.getString("url");
				imgUrl = rs.getString("image");
				answer = rs.getString("name");
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return uurrll;
	}
	
	@FXML
	private void imageClicked(){
		SR.ignoreSpeechRecognitionResults();
		soundPlay(SoundGenerator());
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(4000),
		        ae -> SR.stopIgnoreSpeechRecognitionResults()));
		timeline.play();
		
	}
	private void soundPlay(String path){
		Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.seek(new Duration(mediaPlayer.getCurrentTime().toMillis() +5000));

        mediaPlayer.play();
	}
}