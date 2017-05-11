package model;

import java.sql.*;
import java.util.Random;

public class SoundGenerator {
	
	private static int randID;
	SoundGenerator(){
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
			randID = r.nextInt(High-Low) + Low;
			
			
			ResultSet rs = stmnt.executeQuery("SELECT * FROM animals WHERE id = " + randID + "");
			
			while(rs.next()){
				uurrll = rs.getString("url");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
