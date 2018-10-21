package com.util.community;
import java.text.SimpleDateFormat;

public class TimeTool {
	public static String timeTool(java.sql.Timestamp time) {
		
		String timeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
		return timeString;
	}
}
