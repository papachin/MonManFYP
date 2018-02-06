package DevelopmentPack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class tabRecipeDB {
	
	
	// gets data from database
    static void getRecipeData (ObservableList <RecipeItem> data) throws Exception {		
    	
    	try {
    		// shop connection
    		Connection conn = DBConnections.getShopConn ();
    		
    		if (conn != null) {
    			Statement st = conn.createStatement ( );
    			ResultSet rs = st.executeQuery ("SELECT * FROM recipeList WHERE 1 ");
    			
    			
    			while (rs.next () ) {
    				// pull specific result set data
    				Integer id 		= rs.getInt ("recipe_id");
    				String name 	= rs.getString ("recipe_name");
    				String cost 	= Double.toString (rs.getDouble ("recipe_cost"));
    				Integer type 	= rs.getInt ("recipe_type");
    				Integer vegType = rs.getInt ("recipe_vType");
    				
					    
    				// add items to list
    				data.add (new RecipeItem (id, name, cost, type, vegType));
    				
    			}
    			
    		}
    		// closes connection
    		conn.close();
    		
    	} catch (Exception e) { e.printStackTrace(); }

    }
    
    
    // add items array based on recipe id
    static void fillRecipes (ObservableList <RecipeItem> recipeData, ObservableList <RecipeShopItems> recipeShopData) {
    	// variables
    	Integer count = 0;
    	Integer c = 0;
    	Integer array[];
    	
    	
    	// cycles through recipes
    	for (RecipeItem x : recipeData) {
    		count++;
    		
    		// gets recipe id
    		Integer recipe_id = x.getID ();
    		array = new Integer[10];
    		
    		
    		try {
    			// shop connection
        		Connection conn = DBConnections.getShopConn ();
        		
        		// result statement
    			Statement st = conn.createStatement ( );
    			ResultSet rs = st.executeQuery ("SELECT * FROM recipeShopList WHERE recipe_id = "+count);
    			
    			// count variable, used to cycle through the data
    			c = 0;
    			
    			// cycles through result set
    			while (rs.next () ) {
    				
    				// if selected recipe is the recipe in the database
    				if (recipe_id.equals (rs.getInt ("recipe_id")) ) {
    					
    					// gets all items belonging to the recipe
    					Integer item_id = rs.getInt ("item_id");
    					array [c] = item_id;
    					c++;
    					
    				}
    				
    			}
    			
    			// add items to object
    			recipeShopData.add(new RecipeShopItems(recipe_id, array));
    			
    		} catch (Exception e) { e.printStackTrace(); }
    		
    	}
    }
    
    
    //===================//
    //	item data entry  //
    //===================//
	
    // adds recipe items to shopping list
    static void onRecipeAddTheseItems (ObservableList <RecipeItem> data) {
    	// shopping list data
    	ObservableList <FoodItem> itemData = tabShopInits.getItemsData ();
    	ObservableList <FoodItem> shopData = FXCollections.observableArrayList ();
    	
    	// list made from items in recipe
    	ObservableList <RecipeShopItems> recipeShopData = tabRecipeInits.getRecipeShopData();
    	List <Integer> array;
    	
    	
    	// cycles through recipe items
    	for (RecipeItem i: data) {
    		// sets ID
    		Integer ID = i.getID ();
    		array = new ArrayList <Integer> ();

	    	// sets array
	    	for (RecipeShopItems x: recipeShopData) {
	    		
	    		// if the id is equal to the selected id
	    		if (ID.equals (x.getID ()) ) {
	    			
	    			// gets item id
	    			for (Integer t: x.getRecipeArray () ) {
	    				
	    				if (t != null) {
	    					// adds to array
	    					if (UserSettings.getCount() <=2){
	    						array.add (t);
	    					} else if (UserSettings.getCount() > 2 && UserSettings.getCount() <= 4) {
	    						array.add (t); array.add (t);
	    					} else if (UserSettings.getCount() > 4 && UserSettings.getCount() <= 6) {
	    						array.add (t); array.add (t); array.add (t);
	    					} else if (UserSettings.getCount() > 6 && UserSettings.getCount() <= 9) {
	    						array.add (t); array.add (t); array.add (t); array.add (t);
	    					} 
	    					
	    				}
	    			}
	    		}
	    		
	    	}
	    	
	    	
	    	// loops through recipe array data
	    	for (Integer a: array) {
	    		// adds from items to shop data
	    		for (FoodItem x: itemData) {
	    			// adds from array variables
	    			if (a.equals (x.getID ())) {
	    				shopData.add (x);
	    			}
	    		}
	    	}
	    	
	    	
	    	// sets data to list
	    	tabShopInits.addMoreData (shopData);
	    	tabShop.addDataToList (shopData);
	    	
	    	
	    	// auto remove data
	    	shopData.clear();
    	}
    	
    }
    
    
    // gets recipe prices
    static Double getPrices (ObservableList <RecipeItem> data) {
    	// price value
    	Double price = 0.0;
    	
    	// shopping list data
    	ObservableList <FoodItem> itemData = tabShopInits.getItemsData ();
    	
    	// list made from items in recipe
    	ObservableList <RecipeShopItems> recipeShopData = tabRecipeInits.getRecipeShopData();
    	List <Integer> array;
    	
    	
    	// cycles through recipe items
    	for (RecipeItem i: data) {
    		
    		// sets ID
    		Integer ID = i.getID ();
    		array = new ArrayList <Integer> ();

	    	// sets array
	    	for (RecipeShopItems x: recipeShopData) {
	    		
	    		if (ID.equals (x.getID () )) {
	    			
	    			// cycles through recipe array
	    			for (Integer t: x.getRecipeArray () ) {
	    				
	    				if (t != null) {
	    					// adds to array
	    					array.add (t);
	    				}
	    			}
	    		}
	    		
	    	}

	    	
	    	// loops through recipe array data
	    	for (Integer a: array) {
	    		
	    		// adds from items to shop data
	    		for (FoodItem x: itemData) {
	    			
	    			// adds from array variables
	    			if (a.equals (x.getID () )) {
	    				
	    				// sets price
	    				price = price + StringToDouble (x.getCost ());
	    				
	    			}
	    		}
	    	}
	    	
    	}
    	
    	return price;
    	
    }
    
    
	// converts for addition
	private static double StringToDouble (String string) {
		// returns price for calculation
		double dub = Double.parseDouble (string.replaceAll ("€", ""));
		return dub;
	}
	
}
