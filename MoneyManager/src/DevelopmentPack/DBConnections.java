package DevelopmentPack;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnections {
	// DB Connection data
	private static Connection conn;
	
	// IP address
	private static String mHome		= "192.168.0.157";
	//private static String mHome		= "172.19.11.67";
	//private static String mHome		= "192.168.0.122";
	
	// port numbers
	private static String port08	= "3308";
	//private static String port09	= "3309";
	
	
	// driver name, and database variables
	private static String driver 		= "com.mysql.jdbc.Driver";
	private static String userName1 	= "MattPapa";
	private static String userPass1 	= "Pass123";
	
	
	// database links
	private static String URL_Login		= "jdbc:mysql://"+mHome+":"+port08+"/loginDB";
	private static String URL_Shop 		= "jdbc:mysql://"+mHome+":"+port08+"/shoppingDataDB";
	private static String URL_Plan 		= "jdbc:mysql://"+mHome+":"+port08+"/testDB";
	private static String URL_Settings 	= "jdbc:mysql://"+mHome+":"+port08+"/SettingsDB";

	
	//private static String SSL = "?verifyServerCertificate=false&useSSL=true&requireSSL=true";

	
	// login connection
	public static Connection getLoginConn ( ) throws Exception {
		try {
			Class.forName (driver);
			conn = DriverManager.getConnection (URL_Login, userName1, userPass1);
		} catch (Exception e) { e.printStackTrace (); }
		return conn;
	}
	
	
	// shop connection
	public static Connection getShopConn ( ) throws Exception {
		try {
			// connection link
			Class.forName (driver);
			conn = DriverManager.getConnection (URL_Shop, userName1, userPass1);
		} catch (Exception e) { e.printStackTrace (); }
		return conn;
	}
	
	
	// plan connection
	public static Connection getPlanConn ( ) throws Exception {
		try {
			Class.forName (driver);
			conn = DriverManager.getConnection (URL_Plan, userName1, userPass1);
		} catch (Exception e) { e.printStackTrace (); }
		return conn;
	}
	
	
	// settings connection
	public static Connection getSettingsConn ( ) throws Exception {
		try {
			Class.forName (driver);
			conn = DriverManager.getConnection (URL_Settings, userName1, userPass1);
		} catch (Exception e) { e.printStackTrace (); }
		return conn;
	}
	
	

	
}
