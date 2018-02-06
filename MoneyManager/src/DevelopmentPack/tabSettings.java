package DevelopmentPack;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class tabSettings {
	// pane height(600) and width(900)
	// total sizing
	private final static Integer tHeight = 600;
	private final static Integer tWidth  = 900;
	
	// central sizing
	private final static Integer cHeight = 420;
	
	// bottom sizing
	private final static Integer bHeight = (tHeight - cHeight);
	
	
	// panel colour set
	private static String panelColor  = "#EAEDED";
	private static String borderColor = "#2F4F4F";
	
	
	// panel sizing
	private static Integer insets  = 6;
	private static Integer border  = 2;
	private static Integer padding = 12;
	private static Integer topPad  = 20;
	private static Integer radius  = 6;
	
	
	// initialises settings tab
	public static void startTabSettings (BorderPane bPane) {
		// defines views for sides
		GridPane gPaneT = new GridPane ();
		GridPane gPaneB = new GridPane ();
		
		// initialise UI and Data
		tabSettingsInits.addShopsToList ();
		buildTopUI 		(gPaneT);
		buildBottomUI 	(gPaneB);
		
		
		// add to border pane
		bPane.setCenter (gPaneT);
		bPane.setBottom (gPaneB);
		
	}
	
	
	// build UI
	private static void buildTopUI (GridPane gPaneT) {
		
		// top box
		gPaneT.setPrefHeight (cHeight);
		gPaneT.setPrefWidth  (tWidth);
		gPaneT.setStyle ("-fx-padding: "+topPad+" "+padding+"; -fx-border-style: solid inside; "
						+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
                		+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
                		+ "-fx-background-color: "+panelColor+";-fx-background-radius: "+radius+";"
                		+ "-fx-background-insets: "+insets+";");
		
		// adds items to top pane
		tabSettingsInits.initializeComboBox (gPaneT, tWidth);
		
	}
	
	
	// build top UI
	private static void buildBottomUI (GridPane gPaneB) {
			
		// bottom box
		gPaneB.setPrefHeight (bHeight);
		gPaneB.setPrefWidth  (tWidth);
		gPaneB.setStyle ("-fx-padding: "+topPad+" "+padding+"; -fx-border-style: solid inside; "
						+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
                		+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
                		+ "-fx-background-color: "+panelColor+"; -fx-background-radius: "+radius+";"
                		+ "-fx-background-insets: "+insets+";");
		
		// adds items to bottom pane
		tabSettingsInits.initializeUserBox (gPaneB, tWidth);
		
	}
	
}
