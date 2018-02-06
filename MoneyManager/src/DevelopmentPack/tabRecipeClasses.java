package DevelopmentPack;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class tabRecipeClasses {}


//recipe object
class RecipeItem {
	// object variables
	private IntegerProperty ID 		= new SimpleIntegerProperty ();
	private StringProperty name 	= new SimpleStringProperty 	();
	private StringProperty cost 	= new SimpleStringProperty 	();
	private IntegerProperty type 	= new SimpleIntegerProperty ();
	private IntegerProperty veg  	= new SimpleIntegerProperty ();
	
	
	// object create
  	public RecipeItem (Integer i, String n, String c, Integer t, Integer b) {
  		setID 	(i); 
  		setName (n); 
  		setCost (c); 
  		setType (t);
  		setVeg  (b);
  	}
  	
  	
  	// object data
  	public final IntegerProperty ItemID ( ) {
  		// item ID
  		return this.ID;
  	}
  	
  	public final StringProperty ItemName ( ) {
  		// name of item
        return this.name;
  	}
  	
  	public final StringProperty ItemCost ( ){
  		// price of item
  		return this.cost;
  	}
  	
  	public final IntegerProperty ItemType ( ) {
  		// item type example: meats(1)/spices(2)/vegetable(3)
  		return this.type;
  	}
  	
  	public final IntegerProperty ItemVeg ( ) {
  		// returns if vegetarian
  		return this.veg;
  	}
  	
  	
  	// getters
  	public java.lang.Integer getID ( ) {
  		return this.ItemID ().get ();
  	}
  	
  	public java.lang.String getName ( ) {
  		return this.ItemName ().get ();
  	}
  	
  	public java.lang.String getCost ( ) {
  		return ItemCost ().get ();
  	}
  	
  	public java.lang.Integer getType ( ) {
  		return ItemType ().get ();
  	}
  	
  	public java.lang.Integer getVeg ( ) {
  		return ItemVeg ().get ();
  	}
  	
  	
  	// setters
  	public void setID (Integer i) {
  		this.ItemID ().set (i);
  	}
  	
  	public void setName(String n) {
  		this.ItemName ().set(n);
  	}
  	
  	public void setCost (String c) {
  		this.ItemCost ().set (c);
  	}
  	
  	public void setType (Integer t) {
  		this.ItemType ().set (t);
  	}
  	
  	public void setVeg (Integer b) {
  		this.ItemVeg ().set (b);
  	}
  	
	
}


// sets recipe up with items
class RecipeShopItems {
	private IntegerProperty itemID = new SimpleIntegerProperty ( );
	private Integer itemArray[];
	
  	public RecipeShopItems (Integer i, Integer [] a) {
  		setID 			(i); 
  		setRecipeArray  (a); 
  	}
  	
  	// object data
  	public final IntegerProperty ItemID ( ) {
  		// item ID
  		return this.itemID;
  	}
  	
  	// setters
  	public void setID (Integer i) {
  		this.ItemID ().set (i);
  	}
  	public void setRecipeArray (Integer [] d) {
  		itemArray = d;
  	}
  	
  	// getters
  	public java.lang.Integer getID ( ) {
  		return this.ItemID ().get ();
  	}
  	public Integer[] getRecipeArray ( ) {
  		return itemArray;
  	}
}
