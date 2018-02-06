package DevelopmentPack;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// initialises settings tab
public class tabSettingsInits {	

	// pane height(600) and width(900)
	private static Integer insets  	= 10;
	private static Integer padding 	= 5;
	private static Integer vgap  	= 10;
	private static Integer hgap  	= 20;
	private static Integer space    = 25;
	
	private static ObservableList <String> shopTypeList = FXCollections.observableArrayList ();
	
	
	// add shops to list
	static void addShopsToList () {
		// adds shops
		shopTypeList.addAll ("SuperValu","Tesco","Lidl");
	}

	
	// initialises combo select box
	static void initializeComboBox (GridPane gPane, Integer cWidth) {
		//
		VBox left  = new VBox (10);
		VBox right = new VBox (10);
		
		initializesLeft  (left);
		initializesRight (right);
		
		left.setScaleZ  (300);
		right.setScaleZ (300);
		
		Label settingsLabel = new Label ("• Settings ");
		setLabelAsideH1 (settingsLabel);
	    
	    // sets spacing
	    gPane.setHgap(hgap);
	    gPane.setVgap(vgap);
	    
	    // sets to grid
	    //(Node, colIndex, rowIndex, colSpan, rowSpan):
	    gPane.add (settingsLabel, 0, 0, 2, 1);
	    gPane.add (left,  0, 2);
	    gPane.add (right, 1, 2);
	    
	}
	
	
	// builds left settings pane
	static void initializesLeft (VBox vbox) {
		
		vbox.setSpacing (space);
		vbox.setStyle("-fx-padding: 5 200 5 5");
		
		// container boxes
		HBox box0 = new HBox (5);
		HBox box1 = new HBox (5);
		HBox box2 = new HBox (5);
		
		
		Label listRules     = new Label ("•Rules for Recipes");
		setLabelAsideH2 (listRules);
		Button ListRulesBtn = new Button ("Save"); setSaveBtnStyle (ListRulesBtn);
				
		// sets favourite shop
		Label shopLabel = new Label ("Select Shop: ");
				
		// adds data
		ComboBox <String> shopType = new ComboBox <String> ();
		for (String st : shopTypeList) {
			shopType.getItems ().add (st);
		}

		// sets value for shop
		shopType.setValue (""+shopTypeList.get (UserSettings.getShopID ())+"");
		
				
		// sets persons to feed
		Label personLabel = new Label ("Select person: ");
		ObservableList <Integer> personList = FXCollections.observableArrayList();
		personList.addAll (1, 2, 3, 4, 5, 6, 7, 8, 9);
		
		// defines combo box
		ComboBox <Integer> personCount = new ComboBox <> ();
		personCount.setItems (personList);
				
		// sets value for count
		Integer selectedCount = UserSettings.getCount();
		personCount.getSelectionModel ().select (selectedCount);
		
		
		// sets amount
		Label budgetLabel = new Label ("Input Budget: ");
		TextField budgetAmount = new TextField();
		// sets value for budget
		budgetAmount.setText("€"+UserSettings.getBudget ());
		
		
		// sets styles
		setLabelAsideH3 (shopLabel);
	    shopType.setStyle ("-fx-padding: "+padding+"; -fx-insets: "+insets+";");

	    setLabelAsideH3 (personLabel);
	    personCount.setStyle ("-fx-padding: "+padding+"; -fx-insets: "+insets+";");
			    
	    setLabelAsideH3 (budgetLabel);
	    budgetAmount.setStyle ("-fx-padding: "+padding+"; -fx-insets: "+insets+";");
	    
	    
	    // adds to boxes
	    box0.getChildren ().addAll (shopLabel, shopType);
	    box1.getChildren ().addAll (personLabel, personCount);
	    box2.getChildren ().addAll (budgetLabel, budgetAmount);
	    
	    
	    ListRulesBtn.setOnAction (new EventHandler <ActionEvent> () {
	    	@Override
	    	public void handle (ActionEvent event) {
	    		Integer shop = 0;
	    		// gets values from fields
	    		String selectedShop = shopType.getValue    ();
	    		Integer set_Person  = personCount.getValue ();
			        	
	    		// removes price and converts to double
	    		Double dub = Double.parseDouble (budgetAmount.getText().replaceAll ("€", ""));
	    		Double set_Price = dub;
			    
	    		if (selectedShop.equals("SuperValu")) {
	    			shop = 0;
	    		}	    		
	    		if (selectedShop.equals("Tesco")) {
	    			shop = 1;
	    		}
	    		if (selectedShop.equals("Lidl")) {
	    			shop = 2;
	    		}
	    		
	    		
	    		// sets for real use
	    		UserSettings.setShopID (shop);
	    		UserSettings.setCount  (set_Person);
	    		UserSettings.setBudget (dub);
			        	
	    		// sends to database module
	    		saveSettingsData (selectedShop, set_Person, set_Price);
	    	}
	    	
	    });
	    
	    vbox.getChildren ().addAll (listRules, box0, box1, box2, ListRulesBtn);
	   	
	}
	
	
	// builds right settings pane
	static void initializesRight (VBox vbox) {
		
		vbox.setSpacing (space);
		
		// container boxes
		HBox box0 = new HBox (5) ;HBox box5 = new HBox (5);
		HBox box1 = new HBox (5); HBox box6 = new HBox (5);
		HBox box2 = new HBox (5); HBox box7 = new HBox (5);
		HBox box3 = new HBox (5); HBox box8 = new HBox (5);
		HBox box4 = new HBox (5); HBox box9 = new HBox (5);
		VBox l    = new VBox (5); VBox r    = new VBox (5);
		
		l.setSpacing (space-5);
		l.setStyle("-fx-padding-right:150;");
		r.setSpacing (space-5);
		
		// defines user view
		Label genRules      = new Label ("•Rules for Shopping");
		Button GenRulesBtn  = new Button ("Save"); setSaveBtnStyle (GenRulesBtn);
		setLabelAsideH2 (genRules);
		
		
	    // define labels and style
	    Label veg    = new Label ("Vegetables"); 			setLabelAsideH3 (veg);
	    Label fruit  = new Label ("Fruit"); 				setLabelAsideH3 (fruit);
	    Label grain  = new Label ("Grains"); 				setLabelAsideH3 (grain);
	    Label meat   = new Label ("Meats"); 				setLabelAsideH3 (meat);
	    Label spice  = new Label ("Spices"); 				setLabelAsideH3 (spice);
	    Label dairy  = new Label ("Dairy"); 				setLabelAsideH3 (dairy);
	    Label confec = new Label ("Confections"); 			setLabelAsideH3 (confec);
	    Label alco   = new Label ("Alcohol"); 				setLabelAsideH3 (alco);
	    Label house  = new Label ("Household Products");	setLabelAsideH3 (house);
	    Label health = new Label ("Health Products");   	setLabelAsideH3 (health);
	    
	    // define check boxes
	    CheckBox vegBox = new CheckBox (); CheckBox fruBox = new CheckBox ();
	    vegBox.setSelected(RuleSettings.getVegBool()); fruBox.setSelected(RuleSettings.getFruitBool());
	    CheckBox graBox = new CheckBox (); CheckBox meaBox = new CheckBox ();
	    graBox.setSelected(RuleSettings.getGrainBool()); meaBox.setSelected(RuleSettings.getMeatBool());
	    CheckBox spiBox = new CheckBox (); CheckBox daiBox = new CheckBox ();
	    spiBox.setSelected(RuleSettings.getSpiceBool()); daiBox.setSelected(RuleSettings.getDairyBool());
	    CheckBox conBox = new CheckBox (); CheckBox alcBox = new CheckBox ();
	    conBox.setSelected(RuleSettings.getConfectionBool()); alcBox.setSelected(RuleSettings.getAlcoholBool());
	    CheckBox houBox = new CheckBox (); CheckBox heaBox = new CheckBox ();
	    houBox.setSelected(RuleSettings.getHouseBool()); heaBox.setSelected(RuleSettings.getHealthBool());
	    
	    
	    box0.getChildren ().addAll (vegBox,veg);    box1.getChildren ().addAll (fruBox,fruit);
	    box2.getChildren ().addAll (graBox,grain);  box3.getChildren ().addAll (meaBox,meat);
	    box4.getChildren ().addAll (spiBox,spice);  box5.getChildren ().addAll (daiBox,dairy);
	    box6.getChildren ().addAll (conBox,confec); box7.getChildren ().addAll (alcBox,alco);
	    box8.getChildren ().addAll (houBox,house);  box9.getChildren ().addAll (heaBox,health);
		
	    l.getChildren ().addAll (box0, box1, box2, box3, box4);
	    r.getChildren ().addAll (box5, box6, box7, box8, box9);
	    
	    // on button click, save selections
		GenRulesBtn.setOnAction (new EventHandler <ActionEvent> () {
	        @Override
	        public void handle (ActionEvent event) {
	    	    // booleans for selection
	    	    Boolean v = vegBox.isSelected (); Boolean f = fruBox.isSelected (); 
	    	    Boolean g = graBox.isSelected (); Boolean m = meaBox.isSelected ();
	    	    Boolean s = spiBox.isSelected (); Boolean d = daiBox.isSelected ();
	    	    Boolean c = conBox.isSelected (); Boolean a = alcBox.isSelected ();
	    	    Boolean h = houBox.isSelected (); Boolean p = houBox.isSelected ();
	    	    
	    	    // sets rules
	    	    RuleSettings.setVegBool	 	   (v);RuleSettings.setFruitBool	(f);
	    	    RuleSettings.setGrainBool	   (g);RuleSettings.setMeatBool		(m);
	    	    RuleSettings.setSpiceBool	   (s);RuleSettings.setDairyBool	(d);
	    	    RuleSettings.setConfectionBool (c);RuleSettings.setAlcoholBool	(a);
	    	    RuleSettings.setHouseBool	   (h);RuleSettings.setHealthBool	(p);
	    	    
	    	    // saves rules
	    	    tabSettingsDB.saveRulesData ();
	        	
	        }
	    });
		
		HBox box = new HBox ();
		box.getChildren().addAll (l, r);
		
		vbox.getChildren ().addAll (genRules, box, GenRulesBtn);
	}
	
	
	// initialises buttons
	static void initializeUserBox (GridPane gPaneB, Integer tWidth) {
		//title label
		Label title = new Label ("• Update Your User Info");
		
		// user name
		String userName = User.getName ();
		Label userLabel = new Label ("Your Name: ");
		TextField userNameChange = new TextField ();
		userNameChange.setText (userName);
		
		// user email
		String userEmail = User.getEmail ();
		Label emailLabel = new Label ("Your Email: ");
		TextField userEmailChange = new TextField ();
		userEmailChange.setText (userEmail);
		
		// save button
		Button saveBtn = new Button(" Save ");
		saveBtn.setOnAction (new EventHandler <ActionEvent> () {
	        @Override
	        public void handle (ActionEvent event) {
	        	
	        	String name  = userNameChange.getText  ();
	        	String email = userEmailChange.getText ();
	        	
	        	saveUserData (name, email);
	        }
	    });

		// gets spacing
	    gPaneB.setHgap (hgap);
	    gPaneB.setVgap (vgap);
	    
	    // sets style
	    setLabelAsideH1 (title);
	    
	    setLabelAsideH2 (userLabel);
		userNameChange.setStyle ("-fx-padding:"+padding+";-fx-insets:"+insets+";");
		
		setLabelAsideH2 (emailLabel);
		userEmailChange.setStyle ("-fx-padding:"+padding+";-fx-insets:"+insets+";");
		
		saveBtn.setStyle ("-fx-padding:"+padding+";-fx-insets:"+insets+";");
	    
	    // sets items to grid
		//colIndex, rowIndex, colSpan, rowSpan
		gPaneB.add (title, 0, 0, 2, 1); // section label
		
		gPaneB.add (userLabel, 0, 1); // label
		gPaneB.add (userNameChange, 1, 1); // text area
		
		gPaneB.add (emailLabel, 0, 2); // label
		gPaneB.add (userEmailChange, 1, 2); // text area
		
		gPaneB.add (saveBtn, 2, 2); // save button
		
	}
	
	
	//==========//
	//	BUTTONS	//
	//==========//
	
	// saver for user
	static void saveUserData (String n, String e) {
		// sets variables
		String name  = n; 
		String email = e;
		
		// confirmation alert
		Alert alert = new Alert (AlertType.CONFIRMATION);
		alert.setTitle ("DigiShopper");
		alert.setHeaderText ("Are you sure you want to save these settings?");
		
		// set icon to alert
		Stage stage = (Stage) alert.getDialogPane ().getScene ().getWindow ();
	    stage.getIcons ().add (new Image ("file:Images/digiShop.png"));
	    
	    // option to save or cancel
		Optional <ButtonType> result = alert.showAndWait ();
		
		// alert result, save or cancel
		if (result.get () == ButtonType.OK) {
			// save settings to database
			User.setName  (name);
			User.setEmail (email);
			
			// save data
			tabSettingsDB.updateUserData (name, email);
			
		}
		
	}
	
	// saver for settings
	static void saveSettingsData (String s, Integer c, Double b) {
		// sets variables based on value
		Integer shop = 0;
		if (s.equals ("SuperValu")) { shop = 0; }
		if (s.equals ("Tesco")) 	{ shop = 1; }
		if (s.equals ("Lidl"))      { shop = 2; }
		
		// confirmation alert
		Alert alert = new Alert (AlertType.CONFIRMATION);
		alert.setTitle ("DigiShopper");
		alert.setHeaderText ("Are you sure you want to save these settings?");
				
		// set icon to alert
		Stage stage = (Stage) alert.getDialogPane ().getScene ().getWindow ();
	    stage.getIcons ().add (new Image ("file:Images/digiShop.png"));
			    
	    // option to save or cancel
		Optional <ButtonType> result = alert.showAndWait ();
				
		// alert result, save or cancel
		if (result.get () == ButtonType.OK) {
			// save settings to database
			tabSettingsDB.saveSettingsData (shop, c, b);
					
		}		
		
	}
	
	
	//==========//
	//	STYLES	//
	//==========//
	
	static void setLabelAsideH1 (Label label) {
		label.setStyle ("-fx-font: 20px Georgia;"
						+ "-fx-padding: 2 2 2 2;"
						+ "-fx-insets: 2;");
		
		label.setMinSize (10.0, 10.0);
		
	}
	static void setLabelAsideH2 (Label label) {
		label.setStyle ("-fx-font: 16px Georgia;"
						+ "-fx-padding: 4;"
						+ "-fx-insets:  2;"
						+ "-fx-text-alignment: center;");
		
		label.setMinSize (10.0, 10.0);
		
	}
	static void setLabelAsideH3 (Label label) {
		label.setStyle ("-fx-font: 14px Georgia;"
						+ "-fx-padding: 2;"
						+ "-fx-insets:  1;"
						+ "-fx-text-alignment: center;");
		
		label.setMinSize (10.0, 10.0);
		
	}
	
	// button style
	static void setSaveBtnStyle (Button btn) {
		btn.setAlignment (Pos.BOTTOM_RIGHT);
		//btn.setStyle ("");
	}
	
}
