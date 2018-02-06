package DevelopmentPack;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class Main extends Application {
	
	// set window size
	private final static double height = 600.0;
	private final static double width  = 900.0;
	
	// set final variables
	private final static Stage primaryStage = new Stage   ();
	private final static TabPane tabPane    = new TabPane ();
	
	// pane background colour
	private static String background_style = "#2F4F4F";

	
	// main window for application
    public static void mainWindow () {   
    	// sets image, application name and user name to title
    	primaryStage.setTitle ("DigiShopper user: " + User.getName () );
    	primaryStage.getIcons ().add (new Image ("file:Images/digiShop.png"));
    	
    	// creates panes
        Group root  = new Group ();
        Scene scene = new Scene (root, width, height, Color.GREY);
        
        // create tabs
        buildTabUI (scene, tabPane);   
        
        // sign out button
        Button sign = new Button ();
        buttonUI (sign);
        
        // add pane
        root.getChildren ().addAll (tabPane, sign);
        
        // create scene
        primaryStage.setResizable 	(false);
        primaryStage.setScene 		(scene);
        primaryStage.show 			();
        
    }
    
    
    // builds tabs
    public static void buildTabUI (Scene scene, TabPane tPane) {
        // populate tabs
        for (int i = 1; i <= 5; i++) {
            Tab tab = new Tab ();
            tabInitialisation (tPane, tab, i);
            tPane.getTabs ().add (tab);
        }
        
        
        // bind to take available space
        tPane.prefHeightProperty ().bind (scene.heightProperty () );
        tPane.prefWidthProperty  ().bind (scene.widthProperty  () );
        
        // set style ID
        tPane.setId ("tabPane");
        
    }
    
    
    // sets tab style
    public static void tabInitialisation (TabPane tPane, Tab tab, int i) {
    	// main pane view
    	BorderPane bPane = new BorderPane ();
    	
    	// central view
    	GridPane gPane = new GridPane ();
    	
    	// uniform tab style
        tab.setStyle ("#tabPane{-fx-background-color: grey;}"
        			+".tab-button{"
        				+ "-fx-text-fill: #C4D8DE; -fx-border-color: transparent;"
        				+ "-fx-border-width: 0; -fx-background-radius: 0;"
        				+ "-fx-background-color: transparent;}"
        			+".tab-button: hover{-fx-background-color: white;}");
        
        
        // removes close icon
        tab.setClosable (false);
        
        
        // sets background
        bPane.setStyle ("-fx-background-color: "+background_style+";");
        
        
        // adds tabs
        if (i == 1) {
        	// main tab
        	ImageView homeImage = new ImageView ("file:Images/home.png");
        	Label homeLabel 	= new Label 	("Home ");
        	
        	// sets icon to label
        	homeImage.setScaleZ		(8.0);
        	homeLabel.setGraphic	(homeImage);
        	tab.setGraphic 			(homeLabel);
        	
        	// calls tab methods
        	tabHome.startTabHome (bPane, tPane);
            tab.setContent		 (bPane);
        	

        } else if (i == 2) {
        	// shop tab
        	ImageView shopImage = new ImageView ("file:Images/shop.png");
        	Label shopLabel 	= new Label 	("Shop ");
        	
        	// sets icon to label
        	shopImage.setScaleZ		(15.0);
        	shopLabel.setGraphic	(shopImage);
        	tab.setGraphic 		 	(shopLabel);
        	
        	// calls tab methods
            tabShop.startShopTab (bPane, gPane);
            tab.setContent		 (bPane);
            
            
        } else if (i == 3) {
        	// recipe tab
        	ImageView recipeImage = new ImageView ("file:Images/recipe.png");
        	Label recipeLabel     = new Label 	  ("Recipes ");
        	
        	// sets icon to label
        	recipeImage.setScaleZ	(10.0);
        	recipeLabel.setGraphic	(recipeImage);
        	tab.setGraphic 			(recipeLabel);
        	
        	// calls tab methods
        	tabRecipe.startTabRecipe (bPane);
            tab.setContent			 (bPane);
            
            
        } else if (i == 4) {
        	// plan/statistics tab
        	ImageView planImage = new ImageView ("file:Images/graphs.png");
        	Label planLabel     = new Label 	("Your Plan ");
        	
        	// sets icon to label
        	planImage.setScaleZ	 (10.0);
        	planLabel.setGraphic (planImage);
        	tab.setGraphic 		 (planLabel);
        	
        	// calls tab methods
            tabPlan.startTabPlan (bPane);
            tab.setContent		 (bPane);
            
            
        } else if (i == 5) {
        	// settings tab
        	ImageView settingsImage = new ImageView ("file:Images/settings.png");
        	Label settingsLabel     = new Label 	("Settings ");
        	
        	// sets icon to label
        	settingsImage.setScaleZ	 (10.0);
        	settingsLabel.setGraphic (settingsImage);
        	tab.setGraphic 			 (settingsLabel);
        	
        	// calls tab methods
            tabSettings.startTabSettings (bPane);
            tab.setContent 				 (bPane);
            
            
        }
        
    }
    
    
    // sign out button
    public static void buttonUI (Button sign) {
    	// aligns button
        sign.setAlignment 	(Pos.TOP_RIGHT);
        sign.setText 		(" Sign Out ");
        sign.setStyle 		("-fx-padding: 3;");
        sign.setTranslateX 	(width - 52.0);
        sign.setTranslateY 	(1.8);
        
        
        // button handler
        sign.setOnAction (new EventHandler <ActionEvent> () {
        	
            @Override 
            public void handle (ActionEvent event) {
            	// gets stage
            	Stage stage = (Stage) sign.getScene ().getWindow ();
            	
            	// set user to null
            	User.setID (null);
            	
            	// closes window
                stage.close ();
                
                // terminates program
                System.exit (0);
                
            }
            
        });
    }
    
}
