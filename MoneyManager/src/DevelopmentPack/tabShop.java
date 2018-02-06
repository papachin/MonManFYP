package DevelopmentPack;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class tabShop {
	// pane height(600) and width(900)
	// top sizing
	private final static double tHeight = 0.0;
	private final static double tWidth  = 900.0;
	
	// central sizing
	private final static double sHeight = 600.0 - ( tHeight * 2.0 );
	private final static double sWidth  = 140.0;
	private final static double cWidth  = tWidth - ( sWidth * 2.0 );
	
	
	// panel style
	private static String panelColor  = "#EAEDED";
	private static String borderColor = "#2F4F4F";
	private static Integer insets     = 4;
	private static Integer border     = 2;
	private static Integer padding    = 2;
	private static Integer radius     = 5;
	
	
	// list data
	private static final TableView <FoodItem> itemList = new TableView <> ( );
	private static final TableView <FoodItem> shopList = new TableView <> ( );
	
	
	// local reference to initialisation class
	private static final tabShopInits tabShopInits     = new tabShopInits ();
	
	
	// main control for shop tab
	public static void startShopTab ( BorderPane bPane, GridPane gPane ) {
		// defines views for sides
		VBox vBoxR = new VBox ();
		VBox vBoxL = new VBox ();
		
		// initialise GUI
		buildCentralUI  (gPane);
		buildSideUI 	(vBoxR, vBoxL);
		
		// initialises data
		tabShopInits.buildDataStructure (itemList );
		tabShopInits.addData 			(itemList, 0, 0);
		tabShopInits.addUserData 		(shopList);
		
		// add to border pane
		bPane.setCenter ( gPane );
		bPane.setRight  ( vBoxR );
		bPane.setLeft   ( vBoxL );
		
	}
	
	
	// testing unit
	public static void tester () {
		long startTime = System.nanoTime ();
		
		long endTime  = System.nanoTime ();
		long duration = (endTime - startTime) / 1000000;
		System.out.println ("Time to complete: " + duration + " milliseconds");
		
	}
	
	
	// central setup
	private static void buildCentralUI (GridPane gPane) {
		// build list labels
		final Label itemLabel = new Label ("Item List");		
		final Label shopLabel = new Label ("Shopping List");
		buildListLabel (itemLabel);
		buildListLabel (shopLabel);
		
		
		// build list combination boxes
		final Label itemBoxLabel = new Label ("Organize by Food Group: ");		
		final Label shopBoxLabel = new Label ("Organize by Food Group: ");
		buildBoxLabel (itemBoxLabel);
		buildBoxLabel (shopBoxLabel);
		
		
		// define combo boxes
		final ComboBox <String> itemBox = new ComboBox <> ();
		final ComboBox <String> shopBox = new ComboBox <> ();
		
		
		// initialise combo boxes
		tabShopInits.initializeTopButtons (itemBox, itemList, 0);
		tabShopInits.initializeTopButtons (shopBox, shopList, 1);
		
		
		// build column components
		tabShopInits.initializeColumns (itemList);
		tabShopInits.initializeColumns (shopList);
		tabShopInits.initializeColumnButtons (itemList, shopList);
		
		
		// format grid
		gPane.setStyle ("-fx-padding: "+padding+"; -fx-border-style: solid inside; "
						+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
                		+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
                		+ "-fx-background-color: "+panelColor+"; -fx-background-radius: "+radius+";"
                		+ "-fx-background-insets: "+insets+";");
		
		
		// sets gaps
		gPane.setVgap (2);
		gPane.setHgap (5);
		
		// sets sizes
		gPane.setPrefHeight (sHeight);
		gPane.setPrefWidth (cWidth);
		
		
		// grid setup
		gPane.add (itemLabel, 0, 0);
		gPane.add (itemBoxLabel, 0, 1);
		gPane.add (itemBox, 0, 1);
		gPane.add (itemList, 0, 2);
		
		gPane.add (shopLabel, 1, 0);
		gPane.add (shopBoxLabel, 1, 1);
		gPane.add (shopBox, 1, 1);
		gPane.add (shopList, 1, 2);
		
	}
	
	
	// builds side panels
	private static void buildSideUI (VBox R, VBox L) {
		// right box
		R.setPadding 	(new Insets(10));
		R.setPrefHeight (sHeight);
		R.setPrefWidth  (sWidth);
		R.setStyle ("-fx-padding: "+padding+"; -fx-border-style: solid inside; "
				+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
        		+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
        		+ "-fx-background-color: "+panelColor+"; -fx-background-radius: "+radius+";"
        		+ "-fx-background-insets: "+insets+";");
		
		// builds side control panel
		tabShopInits.initializeSideButtons (shopList, R);
		
		
		// left box
		L.setPadding 	(new Insets (5));
		L.setPrefHeight (sHeight);
		L.setPrefWidth  (sWidth);
		
		// set styles
		L.setStyle ("-fx-padding: "+padding+"; -fx-border-style: solid inside; "
				+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
        		+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
        		+ "-fx-background-color: "+panelColor+"; -fx-background-radius: "+radius+";"
        		+ "-fx-background-insets: "+insets+";");
		
		
		// sets image
		String saleUrl = "";
		if (UserSettings.getShopID() == 0) {
			saleUrl = "file:Images/saleFrenchSuper.png";
			
		} else if (UserSettings.getShopID() == 1) {
			saleUrl = "file:Images/saleFrenchTesco.png";
			
		} else if (UserSettings.getShopID() == 2) {
			saleUrl = "file:Images/saleFrenchLidl.png";
			
		} else {
			saleUrl = "file:Images/saleFrench.png";
			
		}
		
		final ImageView selectedImage = new ImageView ();
		Image saleImage = new Image (saleUrl, true);
		
		// styles image
		selectedImage.setImage		(saleImage);
		selectedImage.setFitHeight	(sHeight - 35);
		selectedImage.setFitWidth	(sWidth  - 10);
		
		// add image to pane
		L.getChildren ().add (selectedImage);
		
	}
	
	
	// builds labels for lists
	private static void buildListLabel (Label l) {
		l.setFont 		(Font.font ("Impact", 20));
		l.setTextFill 	(Color.BLACK);
		GridPane.setHalignment (l, HPos.CENTER);
		
	}
	
	
	// builds labels for panel
	private static void buildBoxLabel (Label l) {
		// sets style
		l.setStyle ("-fx-padding: 2; -fx-text-alignment: center;"
    			+ "-fx-font-smoothing-type: lcd; -fx-font-size: 12;"
    			+ "-fx-opacity: 0.8; -fx-font-family: Georgia;");
		
		//l.setTranslateX(120);
		l.setTranslateY (2);
		
	}
	
	
	// adds data to list
	static void addDataToList (ObservableList <FoodItem> data) {
		shopList.getItems ().addAll (data);
		shopList.refresh  ();
	}
	
}
