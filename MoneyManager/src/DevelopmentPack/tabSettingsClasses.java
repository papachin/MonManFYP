package DevelopmentPack;

public class tabSettingsClasses {}

// user settings class
class UserSettings {
	private static Integer count;
	private static Integer shop_id;
	private static Double  budget;
	
	
	// set object
  	UserSettings (Integer c, Integer s, Double b) {
  		count   = c;
  		shop_id = s;
  		budget  = b;
  	}
  	
  	
  	// setters
  	public static void setCount (Integer c) {
  		count = c;
  	}
  	public static void setShopID (Integer s) {
  		shop_id = s;
  	}
  	public static void setBudget (Double b) {
  		budget = b;
  	}
  	
  	
  	// getters
  	public static Integer getCount () {
  		return count;
  	}
  	public static Integer getShopID () {
  		return shop_id;
  	}
  	public static Double getBudget () {
  		return budget;
  	}

}


//user settings class
class FoodGroupSettings {
	static Integer  foodGroup_id;
	static Boolean  foodGroup_bool;
	
	
	// set object
	public FoodGroupSettings (Integer i, Boolean b) {
		foodGroup_id   = i;
		foodGroup_bool = b;
	}
	
	
	// getters
	public Integer getFoodGroupID () {
		return foodGroup_id;
	}
	public static Boolean getFoodGroupBool () {
		return foodGroup_bool;
	}
	
	
	// setters
	public static void setFoodGroupID (Integer i) {
		foodGroup_id = i;
	}
	public static void setFoodGroupBool (Boolean b) {
		foodGroup_bool = b;
	}


}


// rules class
class RuleSettings {
	static Boolean veg, fru, gra, mea, spi, dai, con, alc, hou, hea;
	
	RuleSettings (Boolean v,Boolean f,Boolean g,Boolean m,Boolean s,Boolean d,Boolean c,Boolean a,Boolean h,Boolean p) {
		veg = v; 
		fru = f;
		gra = g; 
		mea = m; 
		spi = s; 
		dai = d; 
		con = c; 
		alc = a; 
		hou = h; 
		hea = p;
	}
	
	// setters
	public static void setVegBool (Boolean v) {
		veg = v;
	}
	public static void setFruitBool (Boolean f) {
		fru = f;
	}
	public static void setGrainBool (Boolean g) {
		gra = g;
	}
	public static void setMeatBool (Boolean m) {
		mea = m;
	}
	public static void setSpiceBool (Boolean s) {
		spi = s;
	}
	public static void setDairyBool (Boolean d) {
		dai = d;
	}
	public static void setConfectionBool (Boolean c) {
		con = c;
	}
	public static void setAlcoholBool (Boolean a) {
		alc = a;
	}
	public static void setHouseBool (Boolean h) {
		hou = h;
	}
	public static void setHealthBool (Boolean p) {
		hea = p;
	}
	
	
	// getters
	public static Boolean getVegBool () {
		return veg;
	}
	public static Boolean getFruitBool () {
		return fru;
	}
	public static Boolean getGrainBool () {
		return gra;
	}
	public static Boolean getMeatBool () {
		return mea;
	}
	public static Boolean getSpiceBool () {
		return spi;
	}
	public static Boolean getDairyBool () {
		return dai;
	}
	public static Boolean getConfectionBool () {
		return con;
	}
	public static Boolean getAlcoholBool () {
		return alc;
	}
	public static Boolean getHouseBool () {
		return hou;
	}
	public static Boolean getHealthBool () {
		return hea;
	}
	
}
