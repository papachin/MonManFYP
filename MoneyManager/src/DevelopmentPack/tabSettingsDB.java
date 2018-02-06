package DevelopmentPack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class tabSettingsDB {
	
    //===================//
    //   User Settings	 //
    //===================//
	
	// gets users selected shop from database
    static void getSettingsData () throws Exception {
    	final int userID = User.getID ();
    	
    	try {
    		// shop connection
    		Connection conn = DBConnections.getSettingsConn ();
    		
    		if (conn != null) {
    			Statement st = conn.createStatement ();
    			ResultSet rs = st.executeQuery ("SELECT * FROM userSettings WHERE user_id = " + userID+";");
    			
    			while (rs.next () ) {
    				// pull specific result set data
    				Integer shop_id    = rs.getInt    ("shop_id");
    				Integer set_person = rs.getInt    ("set_person");
    				Double  set_budget = rs.getDouble ("set_budget");
					    
    				// adds users selected shop
    				UserSettings.setShopID (shop_id);
    				UserSettings.setCount  (set_person);
    				UserSettings.setBudget (set_budget);
    				
    			}
    			
    		}
    		conn.close ();
    	} catch (Exception e) { e.printStackTrace (); }
    	
    }
    
    static void getRulesData () {
    	final int userID = User.getID ();
    	
    	try {
    		// shop connection
    		Connection conn = DBConnections.getSettingsConn ();
    		
    		if (conn != null) {
    			Statement st = conn.createStatement ();
    			ResultSet rs = st.executeQuery ("SELECT * FROM foodGroupRules WHERE user_id = " + userID+";");
    			
    			
    			while (rs.next () ) {
    				
    				Boolean b  = rs.getBoolean ("rule_FG");
    				Integer fg = rs.getInt     ("foodgroup_id");
					
    				if (fg == 1)  { RuleSettings.setVegBool   		(b); }
    				if (fg == 2)  { RuleSettings.setFruitBool 		(b); }
    				if (fg == 3)  { RuleSettings.setGrainBool 		(b); }
    				if (fg == 4)  { RuleSettings.setMeatBool  		(b); }
    				if (fg == 5)  { RuleSettings.setSpiceBool 		(b); }
    				if (fg == 6)  { RuleSettings.setDairyBool  		(b); }
    				if (fg == 7)  { RuleSettings.setConfectionBool 	(b); }
    				if (fg == 8)  { RuleSettings.setAlcoholBool		(b); }
    				if (fg == 9)  { RuleSettings.setHouseBool		(b); }
    				if (fg == 10) { RuleSettings.setHealthBool		(b); }
    				
    			}
    			
    		}
    		conn.close ();
    	} catch (Exception e) { e.printStackTrace (); }
    }
    
    
    //========//
    // savers //
    //========//
    
    // saves rules
    static void saveRulesData () {
    	Integer user_id = User.getID();
    	Boolean bool = false;
    	
    	try {
    		// get connection
			Connection conn = DBConnections.getSettingsConn ();
			
			// create query
			PreparedStatement QueryAdder;
			
			// query
			for (int fg=1; fg<=10; fg++) {
				
				// sets boolean
				if (fg == 1)  { bool = RuleSettings.getVegBool   	  (); }
				if (fg == 2)  { bool = RuleSettings.getFruitBool 	  (); }
				if (fg == 3)  { bool = RuleSettings.getGrainBool 	  (); }
				if (fg == 4)  { bool = RuleSettings.getMeatBool  	  (); }
				if (fg == 5)  { bool = RuleSettings.getSpiceBool 	  (); }
				if (fg == 6)  { bool = RuleSettings.getDairyBool  	  (); }
				if (fg == 7)  { bool = RuleSettings.getConfectionBool (); }
				if (fg == 8)  { bool = RuleSettings.getAlcoholBool	  (); }
				if (fg == 9)  { bool = RuleSettings.getHouseBool	  (); }
				if (fg == 10) { bool = RuleSettings.getHealthBool	  (); }
						
				// creates query
				String dataToAdd = "UPDATE foodgrouprules SET "
								+ "rule_FG = "+bool+" "
								+ "WHERE foodgroup_id = "+fg+" "
								+"AND user_id = "+ user_id +";";
				
				// execute
				QueryAdder = conn.prepareStatement (dataToAdd);
				QueryAdder.executeUpdate ();
				
			}
			
			conn.close ();
		} catch (Exception e) { e.printStackTrace (); }
    }
    
    // saves user settings
    static void saveSettingsData (Integer s, Integer c, Double b) {
    	final Integer userID = User.getID ();
    	final Integer shop   = s;
    	final Integer count  = c;
    	final Double  budget = b;
    	
    	try {
    		// get connection
			Connection conn = DBConnections.getSettingsConn ();
			
			// create query
			PreparedStatement QueryAdder;
			
			// query
			String dataToAdd = "UPDATE userSettings SET "
								+ "shop_id = "+shop+","
								+ "set_person = "+count+","
								+ "set_budget = "+budget+" "
								+ "WHERE user_id = "+ userID +";";
				
			// execute
			QueryAdder = conn.prepareStatement (dataToAdd);
			QueryAdder.executeUpdate ();
			
			conn.close ();
		} catch (Exception e) { e.printStackTrace (); }
    }
    
    
    //===================//
    // updates user info //
    //===================//
    
    // Updates user data
    static void updateUserData (String na, String em) {
    	final Integer userID = User.getID ();
    	final String name 	 = na;
    	final String email 	 = em;
    	
    	try {
    		// get connection
    		Connection conn = DBConnections.getLoginConn ();
    		PreparedStatement QueryAdder;
    		
    		String query = "UPDATE login_table SET "
    						+ "user_name = \""+ name +"\", "
    						+ "user_email = \""+email+"\" "
    						+ "WHERE user_id = "+ userID +";";
    		
    		QueryAdder = conn.prepareStatement (query);
    		QueryAdder.executeUpdate ();
    		
    		conn.close ();
    	} catch (Exception e) { e.printStackTrace (); }
    	
    }
}
