package DevelopmentPack;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class mainDB {
	
	
	// gets data from database
    static void setUser (Integer id) throws Exception {
    	final int userID = id;
    	
    	try {
    		// shop connection
    		Connection conn = DBConnections.getLoginConn ();
    		
    		if (conn != null) {
    			Statement st = conn.createStatement ( );
    			ResultSet rs = st.executeQuery ("SELECT * FROM login_table WHERE user_id = " + userID);
    			
    			while (rs.next () ) {
    				// pull specific result set data
    				String userName   = rs.getString ("user_name");
    				String userEmail  = rs.getString ("user_email");
    				Date firstLogDate = rs.getDate	 ("user_firstLogDate");
    				Integer logCount  = rs.getInt	 ("user_logCount");
					    
    				
    				// add user
    				User.setID    		 (userID); 
    				User.setName  		 (userName);
    				User.setEmail 		 (userEmail);
    				User.setFirstLogDate (firstLogDate);
    				User.setLogCount     (logCount);
    				
    			}
    			
    		}
    		
    	} catch (Exception e) { e.printStackTrace(); }

    }
    
    
    // sets user if correct connection is made
    static boolean connectCheck (String a, String b) throws Exception {
		Boolean bool = false;
		String user  = a;
		String pass  = b;
		
		try{
			// connection link
			Connection conn = DBConnections.getLoginConn ();
			
			
			if (conn != null) {
				// statement method
				Statement st = conn.createStatement ();
				ResultSet rs = st.executeQuery ("SELECT * FROM login_table WHERE 1 ");
				
				
				while (rs.next () ) {
					// pull specific result set data
					Integer userID    = rs.getInt 	 ("user_id");
				    String userName   = rs.getString ("user_name");
				    String userEmail  = rs.getString ("user_email");
				    String word 	  = rs.getString ("user_pass");
				    Date firstLogDate = rs.getDate	 ("user_firstLogDate");
    				Integer logCount  = rs.getInt	 ("user_logCount");
    				
    				
				    // compare against input data
				    if (user.equals (userName) && pass.equals (word)) {
	    				// add user
	    				User.setID    		 (userID); 
	    				User.setName  		 (userName);
	    				User.setEmail 		 (userEmail);
	    				User.setFirstLogDate (firstLogDate);
	    				User.setLogCount     (logCount);
	    				
	    				// sets settings variables
	    				tabSettingsDB.getSettingsData ();
	    				tabSettingsDB.getRulesData    ();
					    
					    // sets true
				    	bool = true;
				    }
				    
				}
			}
		} catch (Exception e) { e.printStackTrace(); }
		
		if (bool) {
			
			try {
				// updates log count
				Connection conn = DBConnections.getLoginConn ();
	   			PreparedStatement addQuery;
	   			
	   			addQuery = conn.prepareStatement ("UPDATE login_table SET user_logCount=user_logCount+1 WHERE user_id="+User.getID ()+";");
	   			
	   			// executes query
			    addQuery.execute ();
			    
			    // closes connection
	   			conn.close ();
	   			
			} catch (Exception e) { e.printStackTrace(); }
		}
		
		// returns boolean
		return bool;
		
	}
   
    
    //========================//
    // Only used on first log //
    //========================//

    // only used if user's first login
    static void buildStatData () {
    	// sets user and date
    	Integer user_id = User.getID ();
    	Date date_set = new java.sql.Date (new java.util.Date ().getTime () );
    	
    	try {
    		// connection link
    		Connection conn = DBConnections.getPlanConn ();
    		Connection con1 = DBConnections.getShopConn ();
    		PreparedStatement addQuery;
    		
    		if (conn != null) {
    			
    			// build item count row
    			String addCountRow = "INSERT INTO itemHistory (user_id, item_id, item_lastBuy) VALUES ("+user_id+", 0, \""+date_set+"\")";
    			addQuery = conn.prepareStatement (addCountRow);
    			addQuery.execute ();
    			
    			// builds list for data extraction
    			Statement st = con1.createStatement ();
    			ResultSet rs = st.executeQuery ("SELECT * FROM itemList WHERE 1 ");
    			
    			// loops through result set
    			while (rs.next ()) {
    				// gets item id
    				Integer item_id = rs.getInt ("item_id");
    				
    				// sets data
    				String query = "INSERT INTO itemHistory (user_id, item_id, item_lastBuy) "
   								+ "VALUES ("+user_id+", "+item_id+", '"+date_set+"')";
    				
    				// executes
    				addQuery = conn.prepareStatement (query);
    		    	addQuery.execute ();

    			}
    		}
		    // close connection
		    conn.close ();
		    con1.close ();
		    
    	} catch (Exception e) { e.printStackTrace (); }
    	
    }
    
    // for settings
    static void buildSettingsData () {
    	// sets user and date
    	Integer user_id = User.getID ();
    	
    	try {
    		// connection link
    		Connection conn = DBConnections.getSettingsConn ();
    		PreparedStatement addQuery;
    		
    		if (conn != null) {
    			
    			// build item count row
    			String query = "INSERT INTO userSettings (user_id) VALUES ("+user_id+")";
    			addQuery = conn.prepareStatement (query);
    			addQuery.execute ();
    			
    			// adds user settings
    			for (int i=1; i<=10; i++) {
    				String query1 = "INSERT INTO foodGroupRules (foodgroup_id, user_id) VALUES ("+i+","+user_id+")";
    				addQuery = conn.prepareStatement (query1);
        			addQuery.execute ();
    			}
    			
    		}
		    // close connection
		    conn.close ();
		    
    	} catch (Exception e) { e.printStackTrace(); }
    	
    }
    
    
}

