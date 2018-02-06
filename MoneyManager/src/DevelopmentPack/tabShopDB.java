package DevelopmentPack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class tabShopDB {
	
	
	// gets data from database
    static void getShopData (TableView <FoodItem> itemList, ObservableList <FoodItem> itemData) throws Exception {		
    	String list_table = "";
    	
    	
    	try {
    		// shop connection
    		Connection conn = DBConnections.getShopConn ();
    		
    		if (conn != null) {
    			// gets users selected shop data
    			if (UserSettings.getShopID () == 0) {
    				list_table = "super_list";
    			} else if (UserSettings.getShopID() == 1) {
    				list_table = "tesco_list";
    			} else if (UserSettings.getShopID() == 2) {
    				list_table = "lidl_list";
    			} else {
    				list_table = "itemList";
    			}
    			
    			
    			Statement st = conn.createStatement ();
    			ResultSet rs = st.executeQuery ("SELECT * FROM "+list_table+" WHERE 1 ");
    			
    			while (rs.next () ) {
    				// pull specific result set data
    				Integer id 		= rs.getInt 	  ("item_id");
    				String name 	= rs.getString 	  ("item_name");
    				Integer weight 	= rs.getInt	 	  ("item_weight");
    				String cost 	= Double.toString (rs.getDouble ("item_cost"));
    				Integer type 	= rs.getInt 	  ("item_type");
    				Boolean wType   = rs.getBoolean	  ("item_weight_type");
    				
    				String finalWeight;
    				
    				
    				// adds weight symbol to weight
    				if (wType) {
    					if (weight >= 1000) {
    						finalWeight = Integer.toString (weight / 1000) + "L";
    						
    					} else {
    						finalWeight = Integer.toString (weight) + "ml";
    						
    					}
    				} else {
    					finalWeight = Integer.toString (weight) + "g";
    					
    				}
					    
    				
    				// add items to list
    				itemData.add (new FoodItem (id, name, finalWeight, cost, type));
    				itemList.getItems ().add (new FoodItem (id, name, finalWeight, cost, type));
    			}
    			
    		}
    		
    		// closes connection
    		conn.close ();
    		
    	} catch (Exception e) { e.printStackTrace(); }

    }
    
    
    // adds data to database
    // includes user shopping and statistics tables
    static void setShopData (ObservableList <FoodItem> shopData) {
    	// set id's
		Integer userID 		= User.getID ();
		Integer shopID 		= UserSettings.getShopID ();
		Double item_amount 	= 0.0;
    	
		
		// Veg1 Fruit2 Grain3 Meats4 spices5 dairy6 confection7 alcohol8 house9 health10
    	Integer Veg=0, Fruit=0, Grains=0, Meats=0, Spices=0;
    	Integer Dairy=0, Confections=0, Alcohol=0, House=0, Health=0;
    	
    	
    	// sets variables for prices
    	Double VegPrice=0.0, FruitPrice=0.0, GrainsPrice=0.0, MeatsPrice=0.0, SpicesPrice=0.0;
    	Double DairyPrice=0.0, ConfectionsPrice=0.0, AlcoholPrice=0.0, HousePrice=0.0, HealthPrice=0.0;
    	
    	
    	try {
    		// get connection
			Connection conn = DBConnections.getPlanConn ();
			
			// create query
			PreparedStatement addQuery;
			
			// set date
			Date today = new java.sql.Date (new java.util.Date ().getTime ());
			
			if (dateExists (today) ) {
				// remove data, update with new data VV
			}
			
			for (FoodItem item : shopData) {
				// set item details
				Integer item_id   = item.getID 	 ();
				String item_name  = item.getName ();
				Integer item_type = item.getType ();
				
				
				// sets food group counts and costs
		    	if (item_type == 1) {Veg++; 		VegPrice    += StringToDouble (item.getCost ()); } 		 
		    	if (item_type == 2) {Fruit++; 		FruitPrice  += StringToDouble (item.getCost ()); }
		    	if (item_type == 3) {Grains++; 		GrainsPrice += StringToDouble (item.getCost ()); }		 
		    	if (item_type == 4) {Meats++; 		MeatsPrice  += StringToDouble (item.getCost ()); }
		    	if (item_type == 5) {Spices++; 		SpicesPrice += StringToDouble (item.getCost ()); }		 
		    	if (item_type == 6) {Dairy++; 		DairyPrice  += StringToDouble (item.getCost ()); }
		    	if (item_type == 7) {Confections++; ConfectionsPrice += StringToDouble (item.getCost ()); } 
		    	if (item_type == 8) {Alcohol++; 	AlcoholPrice += StringToDouble (item.getCost ()); }
		    	if (item_type == 9) {House++; 		HousePrice   += StringToDouble (item.getCost ()); }		 
		    	if (item_type == 10) {Health++; 	HealthPrice  += StringToDouble (item.getCost ()); }
				
		    	
		    	// counts the amount of money spend
		    	item_amount = item_amount + StringToDouble (item.getCost () );
		    	
		    	
				// make shopping list
				String dataToAdd = "INSERT INTO userShoppingList (list_date, item_id, item_name, shop_id, user_id)"
						+ " VALUES ('"+today+"', "+item_id+", \""+item_name+"\", "+shopID+", "+userID+");";
				
				
				// adds data to list
				addQuery = conn.prepareStatement (dataToAdd);
			    addQuery.execute ();
			    
			    
			    // update item count
				String dataStatsCounts = "UPDATE itemHistory SET item_count = item_count+1, item_lastBuy=\""+today+"\" "
										+ "WHERE user_id="+userID+" AND item_id="+item_id+"";
				
				
			    // adds data to statistics
			    addQuery = conn.prepareStatement (dataStatsCounts);
			    addQuery.executeUpdate ();
			    
			}
			
			
			// set date
			Date date = new java.sql.Date (new java.util.Date ().getTime ());
			
			
			// adds food group count to database
			Integer statCount  = 0;
			Double  statAmount = 0.0;
			String  statName   = "";
			
			
			// cycle through food groups
			for (int i=0; i<=10; i++) {
				// if i = 0, sum totals
				if (i == 0) {
					statName   = "All";
					statCount  = (Veg+Fruit+Grains+Meats+Spices+Dairy+Confections+Alcohol+House+Health);
					statAmount = (VegPrice+FruitPrice+GrainsPrice+MeatsPrice+SpicesPrice+DairyPrice+
										ConfectionsPrice+AlcoholPrice+HousePrice+HealthPrice);
				}
				
		    	if (i == 1) {statName = "Vegetables";		statCount = Veg; 			statAmount = VegPrice;} 			
		    	if (i == 2) {statName = "Fruits";			statCount = Fruit; 			statAmount = FruitPrice;}
		    	if (i == 3) {statName = "Grains";			statCount = Grains; 		statAmount = GrainsPrice;}
		    	if (i == 4) {statName = "Meats";			statCount = Meats;			statAmount = MeatsPrice;}
		    	if (i == 5) {statName = "Spices";			statCount = Spices;			statAmount = SpicesPrice;} 		
		    	if (i == 6) {statName = "Dairy";			statCount = Dairy;			statAmount = DairyPrice;}
		    	if (i == 7) {statName = "Confections";		statCount = Confections;	statAmount = ConfectionsPrice;} 	
		    	if (i == 8) {statName = "Alcohol";			statCount = Alcohol;		statAmount = AlcoholPrice;}
		    	if (i == 9) {statName = "House Products";	statCount = House;			statAmount = HousePrice;} 		
		    	if (i == 10){statName = "Health Products";	statCount = Health;			statAmount = HealthPrice;}
		    	
				// update food group count
				String dataStatsFoodGroupCounts = "INSERT INTO foodGroupStats (stat_date, stat_type, stat_name, stat_count, stat_amont, user_id) "
												+"VALUES (\""+date+"\", "+i+", \""+statName+"\", "+statCount+", "+statAmount+", "+userID+")";
				
				addQuery = conn.prepareStatement (dataStatsFoodGroupCounts);
		    	addQuery.execute ();
		    	
			}
			
			// set spending amount
			String dataStatsSpending = "INSERT INTO spendingHistory (spend_date, spend_amount, user_id) "
									+ "VALUES (\""+date+"\", "+item_amount+", "+userID+")";
			
			addQuery = conn.prepareStatement (dataStatsSpending);
			addQuery.execute ();
			
			// closes connection to database
			conn.close ();
			
			// reload database
			tabPlanInits.buildStatData ();
			
		} catch (Exception e) { e.printStackTrace(); }
    	
    }
    
    
    // deletes list, if user saves data based on date
    static Boolean dateExists (Date date) {
    	
    	Integer userID = User.getID ();
    	Boolean check  = false;
    	Date today;
    	Integer delCount = 0;
    	
    	try {
    		// gets connection
    		Connection conn = DBConnections.getPlanConn ();
    		
    		if (conn != null) {
    			
    			Statement st	 = conn.createStatement ();
    			String queryFind = "SELECT * FROM spendingHistory WHERE spend_date =\""+date+"\" AND user_id = "+userID+"";
    			ResultSet rs 	 = st.executeQuery (queryFind);
    			
    			while (rs.next () ) {
	    			today = rs.getDate ("spend_date");
	    			
	    			// remove list and update data
	    			if ( today.compareTo (date) == -1 && delCount == 0 ) {
	    				// set boolean to true
	    				check = true;
	    				
	    				PreparedStatement addQuery;
	    				
	    				// Delete queries, based on user update
	    				String queryDeleteHist = "DELETE FROM spendingHistory WHERE spend_date=\""+date+"\" AND user_id = "+userID+"";
	    				String queryDeleteList = "DELETE FROM userShoppingList WHERE list_date=\""+date+"\" AND user_id = "+userID+"";
	    				String queryDeleteStat = "DELETE FROM foodGroupStats WHERE stat_date=\""+date+"\" AND user_id = "+userID+"";
	    				
	    				addQuery = conn.prepareStatement (queryDeleteHist);
	    				addQuery.execute ();
	    				
	    				addQuery = conn.prepareStatement (queryDeleteList);
	    				addQuery.execute ();
	    				
	    				addQuery = conn.prepareStatement (queryDeleteStat);
	    				addQuery.execute ();
	    				delCount++;
	    			} 
    			}
    		}
					
    	} catch (Exception e) { e.printStackTrace(); }
    	return check;
    }
    
    
    // gets users shopping list data, if date in list is today
    static void getUserListData (ObservableList <FoodItem> data) {
    	// items list
    	ObservableList <FoodItem> items = tabShopInits.getItemsData ();
    	
    	Integer userID  = User.getID (), findCount = 0;
    	Date today 		= new java.sql.Date (new java.util.Date ().getTime ());
    	Boolean check 	= false;
    	
    	try {
    		// gets connection
    		Connection conn = DBConnections.getPlanConn ();
    		
    		if (conn != null) {
    			
    			// result set
    			Statement st 	 = conn.createStatement ( );
    			String queryFind = "SELECT * FROM spendingHistory WHERE spend_date =\""+today+"\" AND user_id = "+userID+";";
    			ResultSet rs 	 = st.executeQuery (queryFind);
        		
    			// checks for same day creation
        		while (rs.next()){
    	    		Date date = rs.getDate ("spend_date");
    	    		
    	   			// remove list and update data
    	   			if ( today.compareTo (date) == 1 && findCount == 0 ) {
    	   				check = true;
    	   				findCount++;
    	   				
        			}

    			}

        		// if true, get users list for the day and add it to the shop
    			if (check == true) {
    				
    				// gets result set
    				Statement stg   = conn.createStatement ();
    				String queryGet = "SELECT * FROM userShoppingList WHERE list_date =\""+today+"\" AND user_id="+userID+";";
        			ResultSet rsg   = stg.executeQuery (queryGet);
        			
        			// cycles through result set
        			while ( rsg.next() ) {
        				
        				// gets item ID, if it exists in items list, add it.
        				Integer item_id = rsg.getInt ("item_id");
        				for (FoodItem x : items) {
        					
        					Integer thisInstance = x.getID ();
        					
        					if (thisInstance == item_id || item_id.equals (thisInstance)) {
        						// adds data
        						data.add (x);
        						
        					}
        				}
        				
        			}
        			
    			}
    			
    		}
    		
	    } catch (Exception e) { e.printStackTrace(); }
    	
    }
    
    
    // generates shopping list, based on user data
    static void genShopList (ObservableList <FoodItem> data) {
    	// items list
    	ObservableList <FoodItem> items = tabShopInits.getItemsData ();
    	
    	// user data
    	Integer userID 	 	= User.getID ();
    	Double itemRank     = 0.0;
    	Double averageRank  = 0.0;
    	Integer total_count = 0;
    	Integer daysBetween = 0;
    	Double denominator 	= 0.0; 
    	Double numerator	= 0.0;
    	
    	
    	// data setters
    	Date today = new java.sql.Date (new java.util.Date ().getTime ());
    	Date boughtDate;
    	
    	
    	try {
    		// gets connection
    		Connection conn = DBConnections.getPlanConn ();
    		
    		// get total count
    		String getCount   = "SELECT * FROM foodgroupstats WHERE user_id = "+userID+" AND stat_type = 0";
    		Statement stCount = conn.createStatement ();
    		ResultSet rsCount = stCount.executeQuery (getCount);
    		
    		// cycles through result set
    		while (rsCount.next()) {
    			total_count = total_count + rsCount.getInt("stat_count");
    		}
    		
    		
    		// gets result set
    		Statement stHist = conn.createStatement ( );
			ResultSet rsHist = stHist.executeQuery ("SELECT * FROM itemHistory WHERE user_id = "+userID+" AND item_id != 0");
			
			// cycles through result set
			while (rsHist.next()) {
				int iCount  = rsHist.getInt ("item_count");
				numerator = numerator + iCount;
				denominator++;
			}
			
			//
			averageRank = (numerator/denominator)/denominator;
			
			
			// return to first
			rsHist.beforeFirst ();
			
			
			// if last bought date was over a week and mean is
			while (rsHist.next ()) {
				Integer id_check = rsHist.getInt  ("item_id");
				Double iCount  	 = (double) rsHist.getInt  ("item_count");
				boughtDate	 	 = rsHist.getDate ("item_lastBuy");
				itemRank 		 = iCount / (double)total_count;
				daysBetween      = getDaysDifference (boughtDate, today);
				
				

				// if ranking float is higher than top twenty ranks
				// and days between is less than 3
				if (itemRank > averageRank && daysBetween > 5) {
					
					
					// add to array
					for (FoodItem x: items) {
						if (x.getID () == id_check) {
							
							// Implements user rules
							if (x.getType() == 1 && RuleSettings.getVegBool() == false) {
								data.add(x);
							}
							if (x.getType() == 2 && RuleSettings.getFruitBool() == false) {
								data.add (x);
							}
							if (x.getType() == 3 && RuleSettings.getGrainBool() == false) {
								data.add (x);
							}
							if (x.getType() == 4 && RuleSettings.getMeatBool() == false) {
								data.add (x);
							}
							if (x.getType() == 5 && RuleSettings.getSpiceBool() == false) {
								data.add (x);
							}
							if (x.getType() == 6 && RuleSettings.getDairyBool() == false) {
								data.add (x);
							}
							if (x.getType() == 7 && RuleSettings.getConfectionBool() == false) {
								data.add (x);
							}
							if (x.getType() == 8 && RuleSettings.getAlcoholBool() == false) {
								data.add (x);
							}
							if (x.getType() == 9 && RuleSettings.getHouseBool() == false) {
								data.add (x);
							}
							if (x.getType() == 10 && RuleSettings.getHealthBool() == false) {
								data.add (x);
							}
						}
					}
				}
				
			}
			conn.close ();
			
    	}catch (Exception e) { e.printStackTrace(); }
    	
    }
    
    
	// returns days between dates
	public static int getDaysDifference (Date date1, Date date2) {
		return (int)((date2.getTime () - date1.getTime ()) / (1000 *60 *60 *24));
	}
	
	
	// pulls usable price from table
	private static double StringToDouble (String val) {
		// returns price for calculation
		double dub = Double.parseDouble (val.replaceAll ("€", ""));
		return dub;
	}
	
	
}
