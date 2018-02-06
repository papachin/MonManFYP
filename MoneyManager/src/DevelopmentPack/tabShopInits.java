package DevelopmentPack;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.function.Function;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class tabShopInits {
	// pane height(600) and width(900)
	// top sizing
	private final static double tHeight = 5.0;
	private final static double tWidth  = 900.0;
	
	// central sizing
	private final static double sHeight = 600.0 - ( tHeight * 2.0 );
	private final static double sWidth  = 140.0;
	private final static double cWidth  = tWidth - ( sWidth * 2.0 );
	
	
	// shop data
	private static final ObservableList <FoodItem> itemData = FXCollections.observableArrayList ();
	private static ObservableList <FoodItem> shopData       = FXCollections.observableArrayList ();
	
	
	// returns items data
	public static ObservableList <FoodItem> getItemsData () {
		return itemData;
	}
	
	
	// returns shop data
	public static ObservableList <FoodItem> getShopData () {
		return shopData;
	}
	
	
    // initialises columns
    void initializeColumns (TableView <FoodItem> tv) {
    	tv.getColumns ().add (column ("ID",		FoodItem :: ItemID, 	0) ); // hidden
    	tv.getColumns ().add (column ("Name", 	FoodItem :: ItemName, 	125) );
    	tv.getColumns ().add (column ("Weight", FoodItem :: ItemWeight, 50) );
    	tv.getColumns ().add (column ("Cost", 	FoodItem :: ItemCost, 	50) );
    	tv.getColumns ().add (column ("Type", 	FoodItem :: ItemType, 	0) ); // hidden
    	
    	// sets height
    	tv.setPrefHeight (sHeight);
    	tv.setPrefWidth (cWidth/2);
    	
    	// table style
    	tv.setColumnResizePolicy (TableView.UNCONSTRAINED_RESIZE_POLICY);// allows resize
    	tv.setStyle ("-fx-border-width: 0; -fx-padding:0");
    	
    }
    
    
    // adds buttons column
    void initializeColumnButtons (TableView <FoodItem> itemList, TableView <FoodItem> shopList) {		
    	// add to list
    	TableColumn <FoodItem, FoodItem> addColumn = column ("Add", ReadOnlyObjectWrapper <FoodItem> :: new, 50);
    	itemList.getColumns ().add (addColumn);
    	
    	
    	// sets button to column
    	addColumn.setCellFactory (col -> {
    		Button addBtn = new Button ("Add");
    		// button style
    		addBtn.setStyle ("-fx-alignment:center; -fx-padding: 1 8 1 8;");
    		
    		// sets data to column
    		TableCell <FoodItem, FoodItem> cell = new TableCell <FoodItem, FoodItem> ( ) {
    			@Override
    			public void updateItem (FoodItem item, boolean empty) {
    				super.updateItem (item, empty);
    				if (empty) {
    					setGraphic (null);
    				} else {
    					setGraphic (addBtn);
    				}
    			}
    		};
	            
    		// sets on button click
    		addBtn.setOnAction (e -> addToList (shopList, cell.getItem ( ) ) );
    		return cell ;
	            
    	});
    	
    	
    	// remove from list
    	TableColumn <FoodItem, FoodItem> delColumn = column ("Remove", ReadOnlyObjectWrapper <FoodItem> :: new, 65);
    	shopList.getColumns ().add (delColumn);
    	
    	
    	// adds delete column
    	delColumn.setCellFactory (col -> {
    		Button delBtn = new Button ("Remove");
    		delBtn.setStyle ("-fx-alignment:center; -fx-padding: 1 2 1 2;");
    		
    		TableCell <FoodItem, FoodItem> cell = new TableCell <FoodItem, FoodItem> () {
    			@Override
    			public void updateItem (FoodItem item, boolean empty) {
    				super.updateItem (item, empty);
    				if (empty) {
    					setGraphic (null);
    				} else {
    					setGraphic (delBtn);
    				}
    			}
    		};
	            
    		delBtn.setOnAction (e -> removeFromList (shopList, cell.getItem ( )) );
    		return cell ;
	        
    	});
	        
    }
    
    
	// sets column widths
    private static <S, T> TableColumn <S, T> column (String columnName, Function <S, ObservableValue <T>> ObjectProperty, Integer columnWidth) {
        TableColumn <S,T> col = new TableColumn <> (columnName);
        // set data
        col.setCellValueFactory (cellData -> ObjectProperty.apply ( cellData.getValue ()) );
        col.setResizable (false);
        // set width
        if (columnName.equals ("ID") || columnName.equals ("Type")) {
        	//col.setStyle("-fx-visibility: hidden; -fx-padding:0;");
        	col.setGraphic (null);
        	col.setPrefWidth (columnWidth);
        } else {
        	// sets width
        	col.setPrefWidth (columnWidth);
        }
        
	    return col;
    }
    
    
    // =======================
    // || Buttons and Boxes ||
    // =======================
    
    // adds buttons to top box
    void initializeTopButtons (ComboBox <String> comboBox, TableView <FoodItem> List, Integer type) {    	
    	// 1.Vegetables 2.Fruits 3.Grains 4.Meats 5.Spices 6.Dairy 7.Confections 8.Alcohol 9.House 10.Health
    	ObservableList <String> options = FXCollections.observableArrayList(
				"All","Vegetables","Fruits","Grains","Meats","Spices","Dairy","Confections",
				"Alcohol", "Household Products", "Health Products");
    	
		comboBox.setItems (options);
		comboBox.setTranslateX (145);
		comboBox.setTranslateY (1);
		comboBox.getSelectionModel ().selectFirst ();
    	
		comboBox.valueProperty ().addListener (new ChangeListener <String> () {
			@Override
			public void changed (@SuppressWarnings ("rawtypes") ObservableValue ov, String oldVal, String newVal) {
	        	if ( newVal.equals ("All") ) {
	        		addData (List, 0, type);
	        		
	        	} else if (newVal.equals ("Vegetables")) { // vegetables
	        		addData (List, 1, type);
	        		
	        	} else if (newVal.equals ("Fruits")) { // fruits
	        		addData (List, 2, type);
	        		
	        	} else if (newVal.equals ("Grains")) { // grains
	        		addData (List, 3, type);
	        		
	        	} else if (newVal.equals ("Meats")) { // meats
	        		addData (List, 4, type);
	        		
	        	} else if (newVal.equals ("Spices")) { // spices
	        		addData (List, 5, type);
	        		
	        	} else if (newVal.equals ("Dairy")) { // dairy
	        		addData (List, 6, type);
	        		
	        	} else if (newVal.equals ("Confections")) { // confections
	        		addData (List, 7, type);
	        		
	        	} else if (newVal.equals ("Alcohol")) { // alcohol
	        		addData (List, 8, type);
	        		
	        	} else if (newVal.equals ("Household Products")) { // house
	        		addData (List, 9, type);
	        		
	        	} else if (newVal.equals ("Health Products")) { // health
	        		addData (List, 10, type);
	        		
	        	}
	        }
			
		});
		
    }
    
    
    // initialises side panes
    void initializeSideButtons (TableView <FoodItem> shopList, VBox R) {
    	// x spacing
    	Double xPos = 18.0;
 
    	//============//
    	//	generate  //
    	//============//
    	
    	//generate shopping list
    	Button genBtn = new Button (" Generate\n Shopping\n List ");
    	genBtn.setOnAction (new EventHandler <ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
            	generateShoppingList (shopList);
            }
            
        });
    	
    	// styles button
    	styleButton (genBtn);
    	
    	// sets position
    	genBtn.setTranslateX (xPos);
		genBtn.setTranslateY (40.0);
		
		// adds to pane
    	R.getChildren ().add (genBtn);
    	

    	//=========//
    	//	clear  //
    	//=========//
    	
    	// clear button
    	Button clearBtn = new Button (" Clear\n Shopping\n List ");
    	clearBtn.setOnAction (new EventHandler <ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                clearShoppingList (shopList);
            }
            
        });
    	
    	// styles button
    	styleButton (clearBtn);
    	
    	// sets position
    	clearBtn.setTranslateX (xPos);
		clearBtn.setTranslateY (70.0);
		
		// adds to pane
    	R.getChildren ().add (clearBtn);

    	
    	//========//
    	//	save  //
    	//========//
    	
    	// save shopping list
    	Button saveBtn = new Button (" Save\n Shopping\n List ");
    	saveBtn.setOnAction (new EventHandler <ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
            	Date today = new java.sql.Date(new java.util.Date ().getTime () );
            	if (tabShopDB.dateExists (today) ) {
            		// adds and saves new table
                	saveData ();
            	} else {
            		// removes and updates table
            		saveData ();
            	}
            }
        });
    	
    	// styles button
    	styleButton (saveBtn);
    	
    	// sets position
    	saveBtn.setTranslateX (xPos);
		saveBtn.setTranslateY (100.0);
		
		// adds to pane
    	R.getChildren ().add (saveBtn);
    	
    	
    	//==========//
    	//	price	//
    	//==========//
    	
    	// sets cost of shopping list
    	final TextArea priceLbl = new TextArea ();
    	priceLbl.setEditable (false);
    	priceLbl.setText (" Shopping \n Total: €" + 0.00);
    	
    	// on change listener for price updates
    	shopData.addListener ((Change <? extends FoodItem> change) -> {
    		while (change.next ()) {
    			if (change.wasAdded ()) {
    				priceLbl.setText(" Shopping\n"
        					+ "Total: €" + getCost());
    			}
    			if (change.wasRemoved ()) {
    				priceLbl.setText(" Shopping\n"
        					+ "Total: €" + getCost());
    			}
    		}
    	});
    	
    	priceLbl.setStyle ("-fx-background:white;-fx-padding:2;-fx-text-alignment:center;"
    					+"-fx-font-smoothing-type:lcd;-fx-font-size:14;");
    	
    	priceLbl.setPrefHeight (70);
    	priceLbl.setPrefWidth  (50);
    	priceLbl.setTranslateX (xPos-18);
    	priceLbl.setTranslateY (250.0);
    	R.getChildren ().add (priceLbl);
    	
    }
    
    // ============================
    // || methods deal with data ||
    // ============================
    
    // adds item to list
    private void addToList (TableView <FoodItem> shopList, FoodItem item) {
    	shopData.add (item);
    	shopList.getItems ().add (item);
    }
		
    
    // removes a single item from list
    private void removeFromList (TableView <FoodItem> shopList, FoodItem item) {
    	shopData.remove (item);
    	shopList.getItems ().remove (item);
    }
    
    
    // removes all items from shopping list
    private void clearShoppingList (TableView <FoodItem> shopList) {
    	shopData.clear ();
    	shopList.getItems ().clear ();
    }
    
    
    // generates shopping list
    private void generateShoppingList (TableView <FoodItem> shopList) {
    	tabShopDB.genShopList (shopData);
        shopList.getItems ().addAll (shopData);
    }
    
    
    // returns cost
    private static double getCost () {
    	final DecimalFormat df = new DecimalFormat ("#.00");
    	double cost = 0.00;
    	
    	for (FoodItem x : shopData) {
    		cost = cost + StringToDouble (x.getCost ());
    	}
    	
    	return StringToDouble (df.format (cost));
    }
    
    
	// pulls usable price from table
	private static double StringToDouble (String val) {
		// returns price for calculation
		double dub = Double.parseDouble (val.replaceAll ("€", ""));
		return dub;
	}
	
	
    // =====================
    // || Data structures ||
    // =====================
    
    // adds test data to table
    void buildDataStructure (TableView <FoodItem> itemList) {
    	try {
    		// gets shop data from database
			tabShopDB.getShopData (itemList, itemData);
			
		} catch (Exception e) { e.printStackTrace(); }
    }
    
    
    // if there is data in user shopping list, add to list
    void addUserData (TableView <FoodItem> List) {
    	tabShopDB.getUserListData (shopData);
    	List.getItems ().addAll (shopData);
    	List.refresh();
    }
    
    
    // used to add items from recipe list
    static void addMoreData (ObservableList <FoodItem> data){
    	shopData.addAll (data);
    }
    
    
    // adds data based on combo box
    void addData (TableView <FoodItem> List, Integer i, Integer type) {
    	// clears list
    	List.getItems ().clear ();
    	
    	// for item data
    	if (type == 0) {
        	if (i != 0) {
        		for (FoodItem x : itemData) {
        			if(x.getType () == i) {
        				List.getItems ().add (x);
        			}
        		}
        	} else {
        		// sets all
        		List.getItems ().addAll (itemData);
        	}
        	
        // for shop data
    	} else {
        	if (i != 0) {
        		for (FoodItem x : shopData) {
        			if(x.getType () == i) {
        				List.getItems ().add (x);
        			}
        		}
        	} else {
        		// sets all
        		List.getItems ().addAll (shopData);
        	}
        	
    	}
    	
    }
    
    
    //saves shopping data
    private void saveData () {
    	// confirmation alert
    	Alert alert = new Alert (AlertType.CONFIRMATION);
    	alert.setTitle 		("DigiShopper");
    	alert.setHeaderText ("Are you sure you want to save this list?");
    	
    	// set icon to alert
    	Stage stage = (Stage) alert.getDialogPane ().getScene ().getWindow ();
    	stage.getIcons ().add (new Image ("file:Images/digiShop.png") );
    	
    	// option to save or cancel
    	Optional <ButtonType> result = alert.showAndWait ();
    	
    	if (result.get () == ButtonType.OK) {
    		tabShopDB.setShopData (shopData);
    		
    	} else {
    		System.out.println ("data was NOT saved");
    		
    	}
			
    }
    
    
	// button style
	private static void styleButton (Button btn) {
		btn.setStyle("-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
						+"linear-gradient(#20262b, #191d22), "
						+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
        			+"-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0;"
    				+"-fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
    				+"-fx-font-family: Georgia; -fx-text-fill: linear-gradient(white, #d0d0d0);"
    				+"-fx-font-size: 14px; -fx-padding: 10; -fx-text-alignment: center;-fx-font-smoothing-type: lcd;");
		
		
		// on hover
		btn.setOnMouseEntered (new EventHandler <MouseEvent> () {
	        @Override
	        public void handle (MouseEvent event) {
	        	btn.setStyle("-fx-background-color: #404651, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
						+"linear-gradient(#20262b, #191d22), "
						+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
        			+"-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0;"
    				+"-fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
    				+"-fx-font-family: Georgia; -fx-text-fill: linear-gradient(white, #d0d0d0);"
    				+"-fx-font-size: 14px; -fx-padding: 10; -fx-text-alignment: center;-fx-font-smoothing-type: lcd;");
	        }
	    });

		
		// hover ended
	    btn.setOnMouseExited (new EventHandler <MouseEvent> () {
	        @Override
	        public void handle (MouseEvent event) {
	        	btn.setStyle("-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
						+"linear-gradient(#20262b, #191d22), "
						+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
        			+"-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0;"
    				+"-fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
    				+"-fx-font-family: Georgia; -fx-text-fill: linear-gradient(white, #d0d0d0);"
    				+"-fx-font-size: 14px; -fx-padding: 10; -fx-text-alignment: center;-fx-font-smoothing-type: lcd;");
	        }
	    });
	    
	}
    
//end of class	
}
