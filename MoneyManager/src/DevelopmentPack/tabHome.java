package DevelopmentPack;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;


// home tab
public class tabHome {
	// pane height(600) and width(900)
	
	// panel style
	private static String panelColor  	 = "#355656";
	private static String borderColor 	 = "#374C4C";
	
	private static Integer insets        = 15;
	private static Integer padding       = 6;
	private static Integer border        = 5;
	private static Integer radius        = 5;
	
	private static Integer boxInsets     = 2;
	private static Integer boxPadding    = 2;
	
	
	// initialises home tab
	static void startTabHome (BorderPane bPane, TabPane tPane) {
		// define pane
		GridPane gPane = new GridPane ();
		
		// add items to pane
		addToGPane (gPane, tPane);
		styleGrid  (gPane);
		
		// add to border pane
		bPane.setCenter ( gPane );
		
	}
	
	
	// builds grid pane
	private static void addToGPane (GridPane gPane, TabPane tPane) {
		// defines boxes
		VBox box0 = new VBox ();
		VBox box1 = new VBox ();
		VBox box2 = new VBox ();
		VBox box3 = new VBox ();
		
		// sets user name
		String userName = User.getName ();
		
		// sets gPane size
	     for (int i = 0; i<=1; i++) {
	    	 ColumnConstraints column;
	    	 RowConstraints row;
	    	 
	    	 // adds columns and rows to grid
	    	 if (i==0) {
	    		 column = new ColumnConstraints (420);
	    		 row    = new RowConstraints    (150);
	    		 
	    	 } else {
	    		 column = new ColumnConstraints (420);
	    		 row    = new RowConstraints    (350);
	    		 
	    	 }
	         
	    	 // adds to pane
	         gPane.getColumnConstraints ().add (column);
	         gPane.getRowConstraints    ().add (row);
	     }
	     
		
		// digi shop logo
		Label logoLabel = new Label		 ("DigiShop");
		ImageView logo  = new ImageView  ("file:Images/digiShop.png");
		
		// sets style
		logo.setFitWidth  		(60.0); 
		logo.setFitHeight 		(60.0);
		logoLabel.setGraphic 	(logo);
		setLabelAsideH1 	 	(logoLabel);
		logoLabel.setAlignment 	(Pos.TOP_LEFT);
		
		// defines welcome label, sets style
		Label userLabel = new Label ("Welcome, "+ userName +".");
		userLabel.setAlignment  	(Pos.CENTER);
		setLabelAsideH2 			(userLabel);
		userLabel.setTranslateX 	(180);
		
		// defines date
		Date today = new Date ();
	    SimpleDateFormat df = new SimpleDateFormat ("dd-MM-yyyy");
	    Label date = new Label 	("Date: "+df.format(today));
	    date.setAlignment		(Pos.TOP_RIGHT);
	    setLabelAsideH3 		(date);
	    date.setTranslateX		(210);
		
		// shop button
		Label shopLabel  = new Label  ("Build Your Shop List");
		Button shopBtn   = new Button ("");
		ImageView image1 = new ImageView ("file:Images/cart_large.png");
		shopLabel.setAlignment 		(Pos.TOP_CENTER);
		setLabelAsideH2 			(shopLabel);
		shopLabel.setTranslateX 	(100);
		styleButton 				(shopBtn);
        image1.setFitWidth  		(260.0);
        image1.setFitHeight 		(260.0);
		shopBtn.setGraphic			(image1);
		shopBtn.setTranslateX 		(75);
		
		// sets view to shop tab
		shopBtn.setOnAction (new EventHandler <ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
            	tPane.getSelectionModel ().select (1);
            }
            
        });
		
		
		// graphs button
		Label graphLabel = new Label  ("View Your Plan");
		Button graphBtn  = new Button ();
		ImageView image2 = new ImageView ("file:Images/graphs_large.png");
		graphLabel.setAlignment 	 (Pos.TOP_CENTER);
		setLabelAsideH2 			 (graphLabel);
		graphLabel.setTranslateX 	 (120);
		styleButton 				 (graphBtn);
        image2.setFitWidth  		 (260.0);
        image2.setFitHeight 		 (260.0);
		graphBtn.setGraphic    		 (image2);
		graphBtn.setTranslateX 		 (68);
		
		// sets view to graphs tab
		graphBtn.setOnAction (new EventHandler <ActionEvent> () {
            @Override
            public void handle (ActionEvent event) {
            	tPane.getSelectionModel ().select (3);
            }
            
        });
		
		
		// set alignments
		box0.getChildren ().add 	(logoLabel); 			styleBox (box0);
		box1.getChildren ().addAll 	(userLabel, date);		styleBox (box1);
		box2.getChildren ().addAll 	(shopLabel, shopBtn);   styleBox (box2);
		box3.getChildren ().addAll 	(graphLabel, graphBtn); styleBox (box3);
		
		// adds item to pane
		gPane.add (box0, 0, 0);
		gPane.add (box1, 1, 0);
		gPane.add (box2, 0, 1);
		gPane.add (box3, 1, 1);

	}
	
	
	//==========//
	//	STYLES	//
	//==========//
	
	// styles labels
	static void setLabelAsideH1 (Label label) {
		label.setStyle ("-fx-font: 34px Georgia;"
						+ "-fx-text-fill: linear-gradient(white, #d0d0d0);"
						+ "-fx-padding: 2 2 2 2;"
						+ "-fx-insets: 2;");
		
		label.setMinSize (10.0, 10.0);
		
	}
	static void setLabelAsideH2 (Label label) {
		label.setStyle ("-fx-font: 22px Georgia;"
						+ "-fx-text-fill: linear-gradient(white, #d0d0d0);"
						+ "-fx-padding: 8;"
						+ "-fx-insets:  2;"
						+ "-fx-text-alignment: center;");
		
		label.setMinSize (10.0, 10.0);
		
	}
	static void setLabelAsideH3 (Label label) {
		label.setStyle ("-fx-font: 18px Georgia;"
						+ "-fx-text-fill: linear-gradient(white, #d0d0d0);"
						+ "-fx-padding: 5;"
						+ "-fx-insets:  2;"
						+ "-fx-text-alignment: center;");
		
		label.setMinSize (10.0, 10.0);
		
	}
	
	
	// button style
	private static void styleButton (Button btn) {
		btn.setStyle ("-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
						+"linear-gradient(#20262b, #191d22), "
						+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
        			+"-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0;"
    				+"-fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
    				+"-fx-font-family: Arial; -fx-text-fill: linear-gradient(white, #d0d0d0);"
    				+"-fx-font-size: 12px; -fx-padding: 10 20 10 20;");
		
		
		// on hover
		btn.setOnMouseEntered (new EventHandler <MouseEvent> () {
	        @Override
	        public void handle (MouseEvent event) {
	        	btn.setStyle ("-fx-background-color: #292e38, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
						+"linear-gradient(#20262b, #191d22), "
						+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
        			+"-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0;"
    				+"-fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
    				+"-fx-font-family: Arial; -fx-text-fill: linear-gradient(white, #d0d0d0);"
    				+"-fx-font-size: 12px; -fx-padding: 10 20 10 20;");
	        }
	    });

		
		// hover ended
	    btn.setOnMouseExited (new EventHandler <MouseEvent> () {
	        @Override
	        public void handle (MouseEvent event) {
	        	btn.setStyle ("-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
						+"linear-gradient(#20262b, #191d22), "
						+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
        			+"-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0;"
    				+"-fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
    				+"-fx-font-family: Arial; -fx-text-fill: linear-gradient(white, #d0d0d0);"
    				+"-fx-font-size: 12px; -fx-padding: 10 20 10 20; -fx-insets: 10;");
	        }
	    });
	    
	}
	
	// styles grid
	private static void styleGrid (GridPane gPane) {
		// format grid
		gPane.setStyle ("-fx-padding: "+padding+"; -fx-border-style: solid inside; "
				+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
        		+ "-fx-border-radius: "+radius+"; -fx-background-radius: "+radius+";"
        		+ "-fx-background-insets: "+insets+"; -fx-border-color: "+borderColor+";"
        		+ "-fx-background-color: "+panelColor+";");
	}
	
	// styles boxes
	private static void styleBox (VBox box) {
		// format grid
		GridPane.setHalignment(box, HPos.CENTER);
		GridPane.setValignment(box, VPos.CENTER);
		box.setStyle ("-fx-padding: "+boxPadding+"; -fx-background-insets: "+boxInsets+";");
	}
		
}
