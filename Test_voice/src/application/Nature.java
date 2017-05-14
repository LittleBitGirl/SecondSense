package application;
// controller
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.LineUnavailableException;

import edu.cmu.sphinx.api.SpeechResult;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.SpeechRecognition;

public class Nature extends Pane {
	
	public static Stage window;
	
	@FXML
	private ImageView animation;
	@FXML
	private Label Mainlabel;
	@FXML
	private Label BottomLabel;
	private SpeechRecognition SR;
	@FXML
	private void initialize() {
		//soundPlay(SoundGenerator());
	}
	String imgUrl = null;
	public String answer = null;
	private String SoundGenerator(){
		String url      = "jdbc:mysql://localhost:3306/";
		String user     = "root";
		String password = "";
		String uurrll   = null;
		
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

			
			ResultSet rs = stmnt.executeQuery("SELECT * FROM animals WHERE id = " + randID + "");
			
			while(rs.next()){
				uurrll = rs.getString("url");
				imgUrl = rs.getString("image");
				answer = rs.getString("name");
				/*Timer timer = new Timer();
				TimerTask task = new TimerTask()
				{
				        public void run()
				        {
				            animation.setImage(new Image(imgUrl));       
				        }

				};
				
				//timer.schedule(task,5000);*/
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return uurrll;
	}
	
	@FXML
	private void imageClicked() throws LineUnavailableException{
		    SR = new SpeechRecognition();
			soundPlay(SoundGenerator());
			getSpeech();
	}
	private void soundPlay(String path){
		Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
	}
	
	private void getSpeech(){
		System.out.println("Entering start speech thread");

		if (SpeechRecognition.speechThread != null && SpeechRecognition.speechThread.isAlive())
			return;

		// initialise
		SpeechRecognition.speechThread = new Thread(() -> {

			// Allocate the resources
			SpeechRecognition.recognizerStopped = false;
			SpeechRecognition.logger.log(Level.INFO, "You can start to speak...\n");
			
			try {
				while (!SpeechRecognition.recognizerStopped) {
					//This method will determine the end of speech.
					
					SpeechResult speechResult = SpeechRecognition.recognizer.getResult();
					System.out.println("///");
					if (speechResult != null) {
						
						SpeechRecognition.result = speechResult.getHypothesis();
						System.out.println("You said: [" + SpeechRecognition.result + "]\n");
						CheckAnswer(SpeechRecognition.result);
					} else
						SpeechRecognition.logger.log(Level.INFO, "I can't understand you.\n");

				}
			} catch (Exception ex) {
				SpeechRecognition.logger.log(Level.WARNING, null, ex);
				SpeechRecognition.recognizerStopped = true;
			}

			SpeechRecognition.logger.log(Level.INFO, "SpeechThread has exited...");
		});

		// Start
		SpeechRecognition.speechThread.start();
	}
	
	private void CheckAnswer(String speech){
		
		SpeechRecognition.logger.log(Level.INFO, "Checking answer...");
		if(speech.equals(answer)){
			SpeechRecognition.textToSpeech.speak("You are right", 1.5f, false, true);
			SpeechRecognition.recognizer.stopRecognition();
			SpeechRecognition.logger.log(Level.INFO, "SpeechThread has been closed...");
		}
		else{
			SpeechRecognition.textToSpeech.speak("The answer is" + answer, 1.5f, false, true);
			SpeechRecognition.recognizer.stopRecognition();
		}
	}
}