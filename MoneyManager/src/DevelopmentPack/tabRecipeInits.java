package DevelopmentPack;

import java.text.DecimalFormat;
import java.util.function.Function;
import javafx.util.Callback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class tabRecipeInits {
	// pane height(600) and width(900)
	// top sizing
	private final static double tHeight = 0.0;
	private final static double tWidth  = 900.0;
	// central sizing
	private final static double sHeight = 600.0 - (tHeight * 2);
	private final static double sWidth  = 150.0;
	private final static double cWidth  = tWidth - (sWidth * 2.0);
	
	
	// observable list to save data
	private static final ObservableList <RecipeItem> recipeData   		 = FXCollections.observableArrayList ();
	private static final ObservableList <RecipeItem> selectedData 		 = FXCollections.observableArrayList ();
	private static final ObservableList <RecipeShopItems> recipeShopData = FXCollections.observableArrayList ();
	
	
	// panel style
	private static String panelColor  = "#EAEDED";
	private static String borderColor = "#2F4F4F";
	private static Integer insets 	  = 4;
	private static Integer border 	  = 2;
	private static Integer padding	  = 2;
	private static Integer radius  	  = 5;
	
	
	//returns list of recipe items
	public static ObservableList <RecipeShopItems> getRecipeShopData () {
		return recipeShopData;
	}
	
	
	// generates lists
	public static void initViews (ListView <RecipeItem> recipeList, ListView <RecipeItem> selectedList) {
		addConstraints (recipeList);
		addConstraints (selectedList);
	}
	
	
	// sets style variables to list
	public static void addConstraints (ListView <RecipeItem> listView) {
		listView.setPrefHeight 		(sHeight);
		listView.setPrefWidth 		(cWidth/2);
		listView.setEditable 		(false);
		listView.getSelectionModel 	().setSelectionMode (SelectionMode.MULTIPLE);
		listView.setCellFactory 	(new StringListCellFactory () );
		
		// list style
		listView.setStyle ("-fx-padding: 2; -fx-border-style: solid inside; "
						+"-fx-border-width: 1; -fx-border-insets: 1; "
						+"-fx-border-radius: 5; -fx-border-color: #2F4F4F;");
		
	}
	
	
	// generates grid for tables
	static void initGrid (GridPane gPane, VBox vbox, ListView <RecipeItem> left, ListView <RecipeItem> right) {
		
		// build components
		initViews  (left, right);
		addColumns (gPane);
		buildComponents (vbox, left, right);
		
		// sets style
		gPane.setStyle ("-fx-padding: "+padding+"; -fx-border-style: solid inside; "
						+"-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
                		+"-fx-border-radius:"+radius+"; -fx-border-color: "+borderColor+";"
                		+"-fx-background-color:"+panelColor+";-fx-background-radius: "+radius+";"
                		+"-fx-background-insets: "+insets+";");
		
		
		// sets sizes
		gPane.setPrefHeight (sHeight);
		gPane.setPrefWidth  (cWidth);
		
		
		// sets gaps
		gPane.setHgap (5);
		gPane.setVgap (5);
		
	}
	
	
	// adds shopping data to table
	public static void populateData (ListView <RecipeItem> List) {
		// real data
		try {
			// gets data
			tabRecipeDB.getRecipeData (recipeData);
			tabRecipeDB.fillRecipes   (recipeData, recipeShopData);
			
		} catch (Exception e) {	e.printStackTrace(); }
		
		// adds data to list
		List.getItems ().addAll (recipeData);
		
	}
	
	
	// adds columns to grid
	public static void addColumns (GridPane gPane) {
		double centre = 35.0;
		double cols   = (cWidth /2) - (centre /2);
		
		ColumnConstraints column1 = new ColumnConstraints (cols);
		ColumnConstraints column2 = new ColumnConstraints (centre);
		ColumnConstraints column3 = new ColumnConstraints (cols);
		
		gPane.getColumnConstraints ().addAll (column1, column2, column3);
		
	}
	
	
    // initialises columns
    void initializeColumns (TableView <RecipeItem> tv, double sHeight) {
    	tv.getColumns ().add (column ("ID", 	RecipeItem :: ItemID, 	0) ); // hidden
    	tv.getColumns ().add (column ("Name", 	RecipeItem :: ItemName, 115) );
    	tv.getColumns ().add (column ("Cost", 	RecipeItem :: ItemCost, 55) );
    	tv.getColumns ().add (column ("Type", 	RecipeItem :: ItemType, 0) ); // hidden
    	
    	// sets height
    	tv.setPrefHeight (sHeight);
    	tv.setColumnResizePolicy (TableView.UNCONSTRAINED_RESIZE_POLICY);// allows resize
    	tv.setStyle ("-fx-border-width: 0; -fx-padding:0");
    	
    }
    
    
	// sets column data and widths
    private static <S, T>TableColumn <S, T>column (String columnName, Function<S, ObservableValue <T>> ObjectProperty, Integer columnWidth) {
        TableColumn <S,T> col = new TableColumn <> (columnName);
        // set data
        col.setCellValueFactory (cellData -> ObjectProperty.apply ( cellData.getValue ()) );
        col.setResizable (false);
        
        // set width
        if (columnName.equals ("ID") || columnName.equals ("Type")) {
        	col.setGraphic 	 (null);
        	col.setPrefWidth (columnWidth);
        	
        } else {
        	// sets width
        	col.setPrefWidth (columnWidth);
        	
        }
        
        // returns columns
	    return col;
	    
    }
    
	
	// adds string to list
	public static class StringListCellFactory implements Callback <ListView <RecipeItem>, ListCell <RecipeItem>> {
		@Override
		public ListCell <RecipeItem> call (ListView <RecipeItem> itemListView) {
			// returns new string cell for list
			return new StringListCell ();
			
		}
		
		// gets data for list
		class StringListCell extends ListCell <RecipeItem> {
			@Override
			protected void updateItem (RecipeItem item, boolean bool) {
				super.updateItem (item, bool);
				if (item != null) {
					// sets text from item
					setText (item.getName ());
					
				} else {
                    setText (null);
                    
                }
			}
		}
	}
	
	
	// combo box
	static void initializeComboBox (ComboBox <String> comboBox, Integer type, ListView <RecipeItem> List) {
		final ObservableList <String> options = FXCollections.observableArrayList(
			"All Meals","Breakfast Meals","Lunch Meals","Dinner Meals","Budget Meals","Vegitarian Meals");
    	
		
		// combo box set up
		comboBox.setItems		(options);
		comboBox.setTranslateX	(145);
		comboBox.setTranslateY	(1);
		comboBox.getSelectionModel ().selectFirst ();
    	
		
		// combo box to sort items
		comboBox.valueProperty ().addListener (new ChangeListener <String> () {
			
			@Override
			public void changed (ObservableValue <? extends String> ov, String oldVal, String newVal) {
	        	if ( newVal.equals("All Meals") ) {
	        		addData (List, 0, type, false);
	        		
	        	} else if (newVal.equals("Breakfast Meals")) {
	        		addData (List, 1, type, false);
	        		
	        	} else if (newVal.equals("Lunch Meals")) {
	        		addData (List, 2, type, false);
	        		
	        	} else if (newVal.equals("Dinner Meals")) {
	        		addData (List, 3, type, false);
	        		
	        	} else if (newVal.equals("Budget Meals")) {
	        		addData (List, 4, type, false);
	        		
	        	} else if (newVal.equals("Vegitarian Meals")) {
	        		addData (List, 5, type, true);
	        	}
	        	
			}
			
		});
		
	}
	
	
	// buttons
	public static void buildComponents (VBox vbox, ListView <RecipeItem> left, ListView <RecipeItem> right) {
		
		//============//
		//	clickers  //
		//============//
		
		// double click add
		left.setOnMouseClicked (new EventHandler <MouseEvent> () {
			@Override
		    public void handle (MouseEvent event) {
		    	RecipeItem RL = left.getSelectionModel ().getSelectedItem ();
		        if (event.getButton ().equals (MouseButton.PRIMARY)) {
		            if (event.getClickCount() == 2 ) {
		            	if (RL != null) {
		    				// adds to list
		    				selectedData.add (RL);
		    				right.getItems ().add (RL);
		    			}
		            }
		        }
		    }
		});
		
		
		// double click remove
		right.setOnMouseClicked (new EventHandler <MouseEvent> () {
			@Override
		    public void handle (MouseEvent event) {
				
		    	RecipeItem SL = right.getSelectionModel ().getSelectedItem ();
		    	
		        if (event.getButton ().equals (MouseButton.PRIMARY)) {
		        	
		            if (event.getClickCount () == 2) {
		            	
		            	if (SL != null) {
		    				// adds to list
		    				selectedData.remove (SL);
		    				right.getItems ().remove (SL);
		    			}
		            }
		        }
		    }
		});
		
		// v box buttons
		vbox.setSpacing (20);
		
		
		//============//
		//	buttons   //
		//============//
		
		// add
		Button sendRightButton = new Button (" -> ");
		sendRightButton.setId 	 ("right");
		sendRightButton.setStyle ("#right{-fx-text-alignment:center; -fx-padding: 2 5 2 5;}");
		
		// action listener for list
		sendRightButton.setOnAction ((ActionEvent event) -> {
			RecipeItem RL = left.getSelectionModel ().getSelectedItem ();
			if (RL != null) {
				// adds to list
				selectedData.add (RL);
				right.getItems ().add (RL);
			}
			
		});
		
		
		// remove
    	Button sendLeftButton = new Button (" <- ");
    	sendLeftButton.setId ("left");
    	sendLeftButton.setStyle ("#left{-fx-text-alignment:center; -fx-padding: 2 5 2 5;}");
    	
    	// action listener for list
    	sendLeftButton.setOnAction ((ActionEvent event) -> {
    		RecipeItem SL = right.getSelectionModel ().getSelectedItem ();
    		
    		if (SL != null) {
    			// removes from list
    			selectedData.remove (SL);
    			
    			right.getItems ().remove (SL);
    			// clear selection
    			right.getSelectionModel ().clearSelection ();
      		}
    		
    	});
    	
    	
    	// position centre
    	vbox.setAlignment (Pos.CENTER);
    	
    	// set buttons to v box
    	vbox.getChildren ().addAll (sendRightButton, sendLeftButton);
    	
	}
	
	
	// builds panel buttons
	static void initializeSideButtons (ListView <RecipeItem> right, VBox R) {
		// x position
    	Double xPos = 18.0;

    	
    	// clear button
    	Button clearBtn = new Button (" Clear\n Recipe\n List ");
    	
    	clearBtn.setOnAction (new EventHandler <ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
                clearList (right);
            }
            
        });
    	
    	// styles button
    	styleButton (clearBtn, 0);
    	
    	// sets position
    	clearBtn.setTranslateX(xPos);
		clearBtn.setTranslateY(40.0);
		
		// adds to pane
    	R.getChildren ().add (clearBtn);

    	
    	
    	// save shopping list
    	Button saveBtn = new Button (" Add\n Items To\n Shopping\n List ");
    	saveBtn.setOnAction (new EventHandler <ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
            	// add items to shopping list
                tabRecipeDB.onRecipeAddTheseItems (selectedData);
            }
            
        });
    	
    	// styles button
    	styleButton (saveBtn, 1);
    	
    	// sets position
    	saveBtn.setTranslateX (xPos);
		saveBtn.setTranslateY (60.0);
		
		// adds to pane
    	R.getChildren ().add (saveBtn);
    	
    	
    	
    	// sets cost of shopping list
    	final TextArea priceLbl = new TextArea ();
    	priceLbl.setEditable (false);
    	priceLbl.setText 	 (" Shopping \n Total: €" + 0.00);
    	
    	// updates price
    	selectedData.addListener ((Change<? extends RecipeItem> change) -> {
    		
    		while (change.next ()) {
    			// add to list
    			if (change.wasAdded ()) {
    				priceLbl.setText(" Shopping\n"
        					+ "Total: €" + getCost ());
    			}
    			
    			// remove from list
    			if (change.wasRemoved ()) {
    				priceLbl.setText (" Shopping\n Total: €" + getCost ());
    			}
    		}
    		
    	});
    	
    	priceLbl.setStyle ("-fx-background:white;-fx-padding:2;-fx-text-alignment:center;"
    					+"-fx-font-smoothing-type:lcd;-fx-font-size:14;");
    	
    	priceLbl.setPrefHeight (65);
    	priceLbl.setPrefWidth  (50);
    	
    	priceLbl.setTranslateX (xPos-18);
    	priceLbl.setTranslateY (280.0);
    	
    	R.getChildren ().add (priceLbl);
    	
    }


	// add data control
	public static void addData (ListView <RecipeItem> List, Integer i, Integer type, Boolean veg) {
    	// clears list
    	List.getItems ().clear ();
    	Integer bool = 1;
    	
    	// for item data
    	if (type == 0) {
    		
    		// if all is NOT selected
        	if (i != 0) {
        		
        		// for all items in data
        		for (RecipeItem x : recipeData) {
        			// add where item type matches data type
        			if (x.getType () == i) {
        				// meal type is equal to data type
        				List.getItems ().add (x);
    				}
        			if (i == 4 && Double.parseDouble (x.getCost ()) <= UserSettings.getBudget()) {
    					// budget
    					List.getItems ().add (x);
    				} 
        			if (i == 5 && bool.equals(x.getVeg()) ) {
    					// vegetarian
    					List.getItems ().add (x);
    				}
        			
        		}
        	} else {
        		
        		// sets all
        		List.getItems ().addAll (recipeData);
        	}
        	
        // for shop data
    	} else {
    		
        	if (i != 0) {
        		
        		for (RecipeItem x : selectedData) {
        			if (x.getType () == i) {
        				List.getItems ().add (x);
    				}
        			if (i == 4 && Double.parseDouble(x.getCost()) < 2.0) {
    					// budget
    					List.getItems ().add (x);
    				} 
        			if (i == 5 && bool.equals(x.getVeg()) ) {
    					// vegetarian
    					List.getItems ().add (x);
    				}
        		}
        		
        	} else {
        		// sets all
        		List.getItems ().addAll (selectedData);
        	}
    	}
	}
	
	
	// returns total cost
	private static double getCost () {
    	final DecimalFormat df = new DecimalFormat ("#.##");  	
		Double cost = 0.0;

		// gets cost from array break down
    	cost = tabRecipeDB.getPrices (selectedData);
    	
    	// formats and returns
    	return StringToDouble (df.format(cost));
    }
	
	
	// converts for addition
	private static double StringToDouble (String string) {
		// returns price for calculation
		double dub = Double.parseDouble (string.replaceAll ("€", ""));
		return dub;
	}
	
	
    // removes all items from shopping list
    private static void clearList (ListView <RecipeItem> right) {
    	selectedData.clear ();
    	right.getItems ().clear ();
    }
    
    
	// button style
	private static void styleButton (Button btn, Integer i) {
		Integer fontSize;
		Integer padSize;
		
		// handle smaller text
		if (i == 0) {
			fontSize = 15;
			padSize  = 15;
		} else {
			fontSize = 14;
			padSize  = 10;
		}
		
		btn.setStyle("-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
						+"linear-gradient(#20262b, #191d22), "
						+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
        			+"-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0;"
    				+"-fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
    				+"-fx-font-family: Georgia; -fx-text-fill: linear-gradient(white, #d0d0d0);"
    				+"-fx-font-size: "+fontSize+"px; -fx-padding: "+padSize+"; -fx-text-alignment: center;-fx-font-smoothing-type: lcd;");
		
		
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
    				+"-fx-font-size: "+fontSize+"px; -fx-padding: "+padSize+"; -fx-text-alignment: center;-fx-font-smoothing-type: lcd;");
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
    				+"-fx-font-size: "+fontSize+"px; -fx-padding: "+padSize+"; -fx-text-alignment: center;-fx-font-smoothing-type: lcd;");
	        }
	    });
	    
	}
    
}
