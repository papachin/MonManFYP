package DevelopmentPack;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class tabPlanDB {
	
	
	// gets dates, used to single out data
	static ObservableList <dateRange> getDates () {
		// set variables
		ObservableList <dateRange> dates = FXCollections.observableArrayList ();
		Integer userID = User.getID ();
		
		try {
	    	// connection
			Connection conn = DBConnections.getPlanConn ();
			Statement st    = conn.createStatement ();
			
			// get result set
			String query = "SELECT * FROM spendingHistory WHERE user_id = "+userID+" ORDER BY spend_date DESC";
			ResultSet rs = st.executeQuery (query);
			
			
			// cycle through data
			while (rs.next ()) {
				// create spending history data
				Date date 	= rs.getDate ("spend_date");
				
				// add to array list
				dates.add (new dateRange (date) );
				
			}
			
			
		} catch (Exception e) { e.printStackTrace(); }
		
		return dates;
		
	}
	
	
	//==========//
	//	Bar DB	//
	//==========//
	
	//returns amount of times user has shopped
	static ObservableList <spendingStats> getSpendingData () {
		// set variables
		ObservableList <spendingStats> data = FXCollections.observableArrayList ();
		Integer userID = User.getID ();
		
		
		try {
	    	// connection
			Connection conn = DBConnections.getPlanConn ();
			Statement st = conn.createStatement ();
			
			// get result set
			String query = "SELECT * FROM spendingHistory WHERE user_id = "+userID;
			ResultSet rs = st.executeQuery (query);
			
			
			// cycle through data
			while (rs.next () ) {
				// create spending history data
				Date date 	= rs.getDate 	("spend_date");
				Double cost = rs.getDouble  ("spend_amount");
				
				// add to array list
				data.add (new spendingStats (date, cost) );
				
			}
			
		} catch (Exception e) { e.printStackTrace (); }
		// return data
		return data;
		
	}
	
	
	//==========//
	//	Pie DB	//
	//==========//
	
	//returns amount of times user has shopped
	static ObservableList <foodGroupStats> getFoodGroupData () {
		// set variables
		ObservableList <foodGroupStats> data = FXCollections.observableArrayList ();
		Integer userID = User.getID ();
		
		
		try {
			
	    	// connection
			Connection conn = DBConnections.getPlanConn ();
			Statement st = conn.createStatement ();
			
			// get result set
			String query = "SELECT * FROM foodgroupstats WHERE user_id = "+userID+" ";
			ResultSet rs = st.executeQuery (query);
		
			long startTime = System.nanoTime ();
			// cycle through data
			while (rs.next () ) {
				// create spending history data
				Date date 	 	= rs.getDate ("stat_date");
				Integer type 	= rs.getInt ("stat_type");
				String name 	= rs.getString ("stat_name");
				Integer count 	= rs.getInt ("stat_count");
				Double price 	= rs.getDouble ("stat_amont");
				
				// add to array list
				data.add (new foodGroupStats (date, type, name, count, price) );
				
			}
			long endTime  = System.nanoTime ();
			long duration = (endTime - startTime) / 1000000;
			System.out.println ("Time to complete: " + duration + "milliseconds");
				
		} catch (Exception e) { e.printStackTrace (); }
		// return data
		return data;
	}
	
}

