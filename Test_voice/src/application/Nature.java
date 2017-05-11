package application;
// controller
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.SpeechCalculator;

public class Nature extends Pane {
	
	public static Stage window;
	
	@FXML
	private ImageView animation;
	@FXML
	private Label Mainlabel;
	@FXML
	private Label BottomLabel;

	@FXML
	private void initialize() {
		soundPlay(SoundGenerator());
	}
	
	private String SoundGenerator(){
		int randID;
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
			int High = 18;
			randID = r.nextInt(High-Low) + Low;
			
			
			ResultSet rs = stmnt.executeQuery("SELECT * FROM animals WHERE id = " + randID + "");
			
			while(rs.next()){
				uurrll = rs.getString("url");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return uurrll;
	}
	
	private void soundPlay(String path){
		Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
	}
}