package DevelopmentPack;

import java.sql.Date;

public class tabPlanClasses {

}


// money spending stats class
class spendingStats {
	Date date;
	Double price;
	
	spendingStats (Date d, Double p) {
		date = d;
		price = p;
	}
	
	// getters
	public final Date getDate () {
		return date;
	}
	
	public final Double getPrice () {
		return price;
	}
	
	
	// setters
	public void setDate (Date d) {
		date = d;
	}
	
	public void setPrice (Double p) {
		price = p;
	}
	
}


// food group stats class
class foodGroupStats {
	Date date;
	Integer type;
	String name;
	Integer count;
	Double price;
	
	
	foodGroupStats (Date d, Integer t, String n, Integer c, Double p) {
		date  = d;
		type  = t;
		name  = n;
		count = c;
		price = p;
	}
	
	
	// getters
	public Date getDate () {
		return date;
	}
	
	public Integer getType () {
		return type;
	}
	
	public String getName () {
		return name;
	}
	
	public Integer getCount () {
		return count;
	}
	
	public Double getPrice () {
		return price;
	}
	
	
	// setters
	public void setDate (Date d) {
		date = d;
	}
	
	public void setType (Integer t) {
		type = t;
	}
	
	public void setName (String n) {
		name = n;
	}
	
	public void setCount (Integer c) {
		count = c;
	}
	
	public void setPrice (Double p) {
		price = p;
	}
	
}


// spending history class
class spendingHistory {
	Date date;
	Double price;
	
	
	// history object
	spendingHistory (Date d, Double p) {
		date  = d;
		price = p;
	}
	
	
	// getters
	public Date getDate () {
		return date;
	}
	
	public Double getPrice () {
		return price;
	}
	
	
	// setters
	public void setDate (Date d) {
		date = d;
	}
	
	public void setPrice (Double p) {
		price = p;
	}
	
}


// date range class
class dateRange {
	Date date;
	
	// date object
	dateRange (Date d) {
		date = d;
	}
	
	// gets date
	public Date getDate () {
		return date;
	}
	
	// sets date
	public void setDate (Date d) {
		date = d;
	}
	
}

