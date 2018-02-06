package DevelopmentPack;

import javafx.geometry.HPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class tabRecipe {	
	// pane height(600) and width(900)
	// top sizing
	private final static double tHeight = 0.0;
	private final static double tWidth  = 900.0;
	// central sizing
	private final static double sHeight = 600.0 - (tHeight * 2);
	private final static double sWidth  = 140.0;
	private final static double cWidth  = tWidth - (sWidth * 2);
	
	
	// panel style
	private static String panelColor  = "#EAEDED";
	private static String borderColor = "#2F4F4F";
	private static Integer insets  	  = 4;
	private static Integer border 	  = 2;
	private static Integer padding	  = 2;
	private static Integer radius  	  = 5;
	
	
	// lists
	private static final ListView <RecipeItem> recipeList   = new ListView <> ();
	private static final ListView <RecipeItem> selectedList = new ListView <> ();
	
	
	// recipe tab
	public static void startTabRecipe (BorderPane bPane) {
		
		GridPane gPane = new GridPane ();
		// defines views for sides
		VBox vBoxR = new VBox ();
		VBox vBoxL = new VBox ();
		
		// initialise UI and Data
		buildCentralUI 	(gPane);
		buildSideUI 	(vBoxR, vBoxL);
		
		// add to border pane
		bPane.setCenter (gPane);
		bPane.setRight  (vBoxR);
		bPane.setLeft   (vBoxL);
		
  	}
	
	
	// build UI
	private static void buildCentralUI (GridPane gPane) {
		VBox vbox = new VBox (5);
		
		// labels for list
		final Label recipeLabel   = new Label ("Recipe List");		
		final Label selectedLabel = new Label ("Selected Recipes");
		
		
		// initialises labels
		buildListLabel (recipeLabel);
		buildListLabel (selectedLabel);
		
		
		// labels for combo box
		final Label comboShowL = new Label ("Item List: ");
		final Label comboShowR = new Label ("Item List: ");
		
		// initialises combo boxes
		buildBoxLabel (comboShowL);
		buildBoxLabel (comboShowR);
		
		// combo box
		final ComboBox <String> recipeBox = new ComboBox <> ();
		final ComboBox <String> selectBox = new ComboBox <> ();
		tabRecipeInits.initializeComboBox (recipeBox, 0, recipeList);
		tabRecipeInits.initializeComboBox (selectBox, 1, selectedList);
		
		
		// sets pane
		gPane.setPrefHeight (sHeight);
		gPane.setPrefWidth  (cWidth);
		
		// populates data
        tabRecipeInits.populateData (recipeList);
        tabRecipeInits.initGrid 	(gPane, vbox, recipeList, selectedList);
        
        
        // sets grid 
    	gPane.add (recipeLabel, 0, 0);
    	gPane.add (comboShowL, 0, 1);
    	gPane.add (recipeBox, 0, 1);
    	gPane.add (recipeList, 0, 2);
    	
    	// central buttons
    	gPane.add (vbox, 1, 2);
    	
		gPane.add (selectedLabel, 2, 0);
		gPane.add (comboShowR, 2, 1);
		gPane.add (selectBox, 2, 1);
		gPane.add (selectedList, 2, 2);
        
	}
	
	
	// right and left user interface
	private static void buildSideUI (VBox R, VBox L) {
		// right box
		R.setPrefHeight (sHeight);
		R.setPrefWidth  (sWidth);
		R.setStyle ("-fx-padding: "+padding+"; -fx-border-style: solid inside; "
				+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
            	+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
            	+ "-fx-background-color: "+panelColor+";-fx-background-radius: "+radius+";"
            	+ "-fx-background-insets: "+insets+";");
		
		// builds side control panel
		tabRecipeInits.initializeSideButtons (selectedList, R);
		
		
		// left box
		L.setPrefHeight (sHeight);
		L.setPrefWidth  (sWidth);
		L.setStyle ("-fx-padding: "+padding+"; -fx-border-style: solid inside; "
					+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
                	+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
                	+ "-fx-background-color: "+panelColor+";-fx-background-radius: "+radius+";"
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
		
		// sets image size
		selectedImage.setImage 		(saleImage);
		selectedImage.setFitHeight  (sHeight - 35);
		selectedImage.setFitWidth   (sWidth - 10);
		
		// adds image to pane
		L.getChildren ().add (selectedImage);
		
	}
	
	
	// builds labels style
	private static void buildListLabel (Label l) {
		l.setFont 		(Font.font ("Impact", 20));
		l.setTextFill 	(Color.BLACK);
		GridPane.setHalignment (l, HPos.CENTER);
		
	}
	
	
	// label for combo box style
	private static void buildBoxLabel (Label l) {
		l.setStyle ("-fx-padding: 2;-fx-text-alignment:center;"
    			+ "-fx-font-smoothing-type:lcd;-fx-font-size:12;"
    			+ "-fx-opacity: 0.8;-fx-font-family:Georgia;");
		
		l.setTranslateX (20);
		l.setTranslateY (2);
		
	}
	
}
