package DevelopmentPack;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class tabShopClasses {
	// dummy class
}


// food item class
class FoodItem {
	// food item variables
	private IntegerProperty ID 		= new SimpleIntegerProperty ();
	private StringProperty name 	= new SimpleStringProperty ();
	private StringProperty weight 	= new SimpleStringProperty ();
	private StringProperty cost		= new SimpleStringProperty ();
	private IntegerProperty type 	= new SimpleIntegerProperty ();

	
	// object create
  	public FoodItem (Integer i, String n, String w, String c, Integer t) {
  		setID 		(i); 
  		setName 	(n); 
  		setWeight 	(w); 
  		setCost 	(c); 
  		setType 	(t);
  	}
  	
  	
  	// object data
  	public final IntegerProperty ItemID () {
  		// item ID
  		return this.ID;
  	}
  	
  	public final StringProperty ItemName () {
  		// name of item
        return this.name;
    }
  	
  	public final StringProperty ItemWeight () {
  		// weight or amount of item
  		return this.weight;
  	}
  	
  	public final StringProperty ItemCost () {
  		// price of item
  		return this.cost;
  	}
  	
  	public final IntegerProperty ItemType () {
  		return this.type;
  	}
  	
  	
  	// getters
  	public java.lang.Integer getID () {
  		return this.ItemID ().get ();
  	}
  	
  	public java.lang.String getName () {
  		return this.ItemName ().get ();
  	}
  	
  	public java.lang.String getWeight () {
  		return ItemWeight ().get ();
  	}
  	
  	public java.lang.String getCost () {
  		return ItemCost ().get ();
  	}
  	
  	public java.lang.Integer getType () {
  		return ItemType ().get ();
  	}
  	
  	
  	// setters
  	public void setID (Integer i) {
  		this.ItemID ().set (i);
  	}
  	
  	public void setName(String n) {
  		this.ItemName().set(n);
  	}
  	
  	public void setWeight (String w) {
  		this.ItemWeight ().set (w);
  	}
  	
  	public void setCost (String c) {
  		this.ItemCost ().set ("€"+c);
  	}
  	
  	public void setType (Integer t) {
  		this.ItemType ().set (t);
  	}
  	
  	
  	// searchers
  	@Override
  	public boolean equals (Object obj) {
  		if (this == obj) {
  			return true;
  			
  		}
  		if (obj == null || getClass() != obj.getClass ()) {
  			return false;
  			
  		}
  		
  		FoodItem item = (FoodItem) obj;

  		if(name != null ? !name.equals (item.name) : item.name != null) {
  			return false;
  			
  		} else {
  			return true;
  			
  		}
  		
  	}
  	
  	// used in hash table
  	@Override
  	public int hashCode ( ) {
  		return name != null ? name.hashCode ( ) * 31 : 0;
  		
  	}
  	
}
