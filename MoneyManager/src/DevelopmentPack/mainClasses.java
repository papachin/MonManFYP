package DevelopmentPack;

import java.sql.Date;

public class mainClasses {
 // dummy class
}


// user class
class User {
	private static Integer ID;
	private static String name;
	private static String email;
	private static Date firstLogDate;
	private static Integer logCount;
	
	
	// sets object
	User (Integer i, String n, String e, Date f, Integer c) {
		ID = i;
		name = n;
		email = e;
		firstLogDate = f;
		logCount = c;
	}
	
	
	// setters
	public static void setID (Integer id) {
		ID = id;
	}
	
	public static void setName (String n) {
		name = n;
	}
	
	public static void setEmail (String e) {
		email = e;
	}
	
	public static void setFirstLogDate (Date f) {
		firstLogDate = f;
	}
	
	public static void setLogCount (Integer c) {
		logCount = c;
	}
	
	
	// getters
	public static Integer getID () {
		return ID;
	}
	
	public static String getName () {
		return name;
	}
	
	public static String getEmail () {
		return email;
	}
	
	public static Date getfirstLogDate () {
		return firstLogDate;
	}
	
	public static Integer getLogCount () {
		return logCount;
	}
	
}
