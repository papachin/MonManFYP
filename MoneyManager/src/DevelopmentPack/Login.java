package DevelopmentPack;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {

	
	// launcher
    public static void main (String [] args) {
    	// launches arguments
        launch (args);
    }

    @Override
    public void start (Stage logStage) {
    	// sets stage
        logStage.setTitle ("DigiShop  Sign-In");
        logStage.getIcons ().add (new Image ("file:Images/digiShop.png"));
        GridPane grid = new GridPane ();
        initUI (logStage,grid);
        
        // sets scene
        Scene scene = new Scene (grid, 350, 300);
        logStage.setResizable (false);
        logStage.setScene (scene);
        logStage.show ();
        
    }
    
    
    // creates login user interface
    private void initUI (Stage logStage,GridPane grid){
        grid.setAlignment (Pos.CENTER);
        grid.setHgap (10);
        grid.setVgap (10);
        grid.setPadding (new Insets (30));

        
        // sets title and image
        Label scenetitle = new Label ("Welcome");
        ImageView image  = new ImageView ("file:Images/digiShop.png");
        image.setFitWidth  (60.0);
        image.setFitHeight (60.0);
        scenetitle.setGraphic (image);
        scenetitle.setFont (Font.font ("Tahoma", FontWeight.NORMAL, 20));
        grid.add (scenetitle, 0, 0, 2, 1);

        
        // sets labels and text input for login
        Label userName = new Label ("User Name:");
        grid.add (userName, 0, 1);
        TextField userText = new TextField ();
        grid.add (userText, 1, 1);

        Label pw = new Label ("Password:");
        grid.add (pw, 0, 2);
        PasswordField pwText = new PasswordField ();
        grid.add (pwText, 1, 2);

        
        // sign in button
        Button signIn = new Button ("Sign in");
        HBox hbBtn = new HBox (10);
        hbBtn.setAlignment (Pos.BOTTOM_RIGHT);
        hbBtn.getChildren ().add (signIn);
        grid.add (hbBtn, 1, 4);

        
        // sets failure action
        final Text actionT = new Text ();
        grid.add (actionT, 1, 6);
        
        
        // button action, if statement for testing
        boolean test = false;
        if (test == false) {
        	// button action for live
        	buttonHandle (logStage, signIn, actionT, userText, pwText);
        	
        } else {
        	// test stage
        	Main.mainWindow ();
        	
        }
        	
    }
    
    
    // handles login
    private void buttonHandle (Stage logStage, Button signin, Text actionT, TextField userText, PasswordField pwText) {
    	
    	// sign in button action
        signin.setOnAction (new EventHandler <ActionEvent> () {
            @Override
            public void handle (ActionEvent e) {
            	// sets text input
            	String name = userText.getText ().toString ();
            	String pass = pwText.getText   ().toString ();

            	try {
            		
            		if (mainDB.connectCheck (name, pass) == true) {
            			
            			// if users first log, build users statistics tables
            			if (User.getLogCount () < 1) {
            				// build statistic tables
            				// item history and settings
            				mainDB.buildStatData     ();
            				mainDB.buildSettingsData ();
            				
            			}
            			
            			// log into application and close login window
            			logStage.hide   ();
            			Main.mainWindow ();
            			
            		} else {
            			// else, display error
            			actionT.setFill (Color.FIREBRICK);
            			actionT.setText ("Sign in failed");
            			
            		}
            		
            	} catch (Exception e1) { e1.printStackTrace();; }
            	
            }
        });
        
    }
}
