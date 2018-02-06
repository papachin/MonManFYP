import java.util.ArrayList;

class stockItems {
	private ArrayList <Item> stock;
	private ArrayList <Item> sold;
	
	//#(those i :: stock :- i.itemName = n);
	public int freqItem (Name n) {
		int count = 0;
		for (Item i: stock) {
			if (n.equals (i.getName () )) {
				count++;
			}
		}
		return count;
	}
	
	//#(those s :: stock :- s = i)*i.price;
	public int itemValue (Item n) {
		int count = 0;
		for (Item i: stock) {
			if (n.getName ().equals (i.getName () )) {
				count ++;
			}
		}
		return (n.getPrice () * count);
	}
	
	//for those i :: stock :- freqItem(i.itemName) = f yield i;
	public ArrayList <Item> givenFreq (Integer f) {
		ArrayList <Item> tmp = new ArrayList <> ();
		for (Item i : stock) {
			if (freqItem (i.getName () ) == f) {
				tmp.add (i);
			}
		}
		return tmp;
	}
	
	
	//+ over(for si:: sold yield si.price);
	public int totalSales () {
		int sales = 0;
		for (Item i: stock) {
			sales ++;
		}
		return sales;
	}
	
	//#(those si :: sold :- n = si.itemName);	
	public int itemSales (Name n) {
		int sales = 0;
		for (Item i: stock) {
			if (n.equals (i.getName ())) {
				sales ++;
			}
		}
		return sales;
	}
	
	
	//for those si :: sold :- itemSales(si.itemName) > freqItem(si.itemName) yield si;
	public ArrayList <Item> mostSales () {
		ArrayList <Item> tmp = new ArrayList <> ();
		for (Item i : sold) {
			if ( itemSales (i.getName () ) > freqItem (i.getName () )) {
				tmp.add (i);
			}
		}
		return tmp;
	}
	
	//for those i :: stock :- freqItem(i.itemName) > itemSales(i.itemName) yield i;
	public ArrayList <Item> leastSold () {
		ArrayList <Item> tmp = new ArrayList <> ();
		
		for (Item i : stock) {
			if ( freqItem (i.getName () ) > itemSales (i.getName () )) {
				
			}
		}
		return tmp;
	}
	
	
	public void add (Item i) {
		assert !stock.contains (i);
		stock.add (i);
	}
	
	public void remove (Item i) {
		assert stock.contains (i);
		stock.remove (i);
	}
	
	public void sold (Item i) {
		assert stock.contains (i);
		stock.remove (i);
		sold.add (i);
	}
	
}


final class Name{
	private final String name;
	
	public Name (String name){
		assert name.length () >0 && name.length () <12;
		this.name = name;
	}
}

final class Item {
	private final Name name;
	private final Integer price;
	
	public Item (Name name, Integer price) {
		this.name = name; this.price = price;
	}

	public Name getName () {return name;}
	public Integer getPrice () {return price;}
	
	
	public boolean equals (Object ob) {
		if (!(ob instanceof Item)) return false;
		Item i = (Item)ob;
		return name.equals (i.getName ()) && price.equals (i.getPrice ());
	}
	
	public int hashCode (){
		return 31 * name.hashCode () * price.hashCode ();
	}

}