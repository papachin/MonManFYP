package DevelopmentPack;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class tabPlanInits {
	// pane height(600) and width(900)
	// top sizing
	private final static double tHeight = 120.0;
	private final static double tWidth  = 900.0;
	// central sizing
	private final static double sHeight = 600.0 - tHeight;
	private final static double sWidth  = 100.0;
	private final static double cWidth  = tWidth - (sWidth * 2.0);
	
	
	// panel style
	private static String  panelColor   = "#EAEDED";
	private static String  borderColor  = "#2F4F4F";
	private static Integer insets  		= 4;
	private static Integer border  		= 2;
	private static Integer padding 		= 2;
	private static Integer bottomPad	= 6;
	private static Integer radius  		= 5;
	
	
	// formatter
	private static final DecimalFormat df = new DecimalFormat("#.00");
	
	
	// statistical data
	private static ObservableList <dateRange>       dates 			 = FXCollections.observableArrayList ();
	private static ObservableList <spendingStats>   spendingData 	 = FXCollections.observableArrayList ();
	private static ObservableList <foodGroupStats>  foodGroupList 	 = FXCollections.observableArrayList ();
	
	
	// builds data
	public static void buildStatData () {
		// adds database data to lists
		dates.addAll 			( tabPlanDB.getDates         () );
		spendingData.addAll  	( tabPlanDB.getSpendingData  () );
		foodGroupList.addAll 	( tabPlanDB.getFoodGroupData () );
		
	}
	
	
	public static void buildEconomicProjection (BorderPane bPane) {
		// grid axis
		final CategoryAxis xAxis = new CategoryAxis ();
	    final NumberAxis yAxis   = new NumberAxis   ();
	    
	    
	    // define chart
		final LineChart <String, Number> lineChart = new LineChart <String, Number> (xAxis, yAxis);
		lineChart.setAnimated (false);
		
		// add chart label
		xAxis.setLabel ("Times Shopped");
		yAxis.setLabel ("Amount Spent");
		
		
		// builds UI
		final VBox vboxL = new VBox ();
		final HBox hboxB = new HBox ();
		buildLineSideUI   (vboxL);
		buildLineBottomUI (hboxB);
	    
		
	    // adds data to chart
	    addLineData 	  (lineChart, vboxL, hboxB);
	    buildLineChartUI  (lineChart);
	    
	    
	    lineChart.setAnimated (false);
	    lineChart.setTitle    ("Economic Trend");
	    
	    
	    bPane.setCenter (lineChart);
	    bPane.setLeft   (vboxL);
	    bPane.setBottom (hboxB);
	}
	
	
	// adds data to list
	private static void addLineData (LineChart <String, Number> lineChart, VBox vbox, HBox hbox) {
		// creates list to store dates
		ListView <String> fgList = new ListView <String> ();
		
		// list array to hold series; for removing series
		ObservableList <String> checkList = FXCollections.observableArrayList ();
		
		// allows multiple list selections
		fgList.getSelectionModel ().setSelectionMode (SelectionMode.MULTIPLE);
		
		// sets header label
		Label listHeader = new Label ("• Groups");
		setLabelHead (listHeader);
		
		// date formatter
		DateFormat dfg = new SimpleDateFormat ("dd/MM");
		
		// go through dates and set data
		Integer groupCounter = 0;
		for (foodGroupStats fg: foodGroupList) {
			// sets date variable
			if (0 < groupCounter && groupCounter <= 10) {
				String group = fg.getName();
			
           		// adds dates to list
				fgList.getItems ().add (group);
			
				
			}
			groupCounter++;
			
		}
		
		// selects first item on list
		fgList.getSelectionModel().select(0);
		
		
		// list listener method
		fgList.setOnMouseClicked (new EventHandler <Event> () {
			@Override
			public void handle (Event event) {
				// sets selected value
				//ObservableList <String> selectedFoodGroup = fgList.getSelectionModel ().getSelectedItems ();
				String selectedFoodGroup = fgList.getSelectionModel ().getSelectedItem ();
				
				XYChart.Series <String, Number> series = new XYChart.Series <> ();
				
				
				// sets series name
				series.setName (""+selectedFoodGroup);
			
				// if group is in checklist, remove from lineChart
				if (checkList.contains (selectedFoodGroup)) {
					// cycle through line chart series
					for (XYChart.Series<String, Number> s : lineChart.getData () ) {
					    // if series exists
					    if (s.getName ().equals (selectedFoodGroup)) {
					    	// removes series from checklist and from line chart
					    	checkList.remove (selectedFoodGroup);
					    	lineChart.getData ().remove (s);
					    	s.getData ().clear ();
					    	
					    } 
					}
					
				} else {
					
					// variables for statistical analysis
					Integer averageCount  = 0;
					Integer totalAmount   = 0;
					Double  totalPrice    = 0.0;
					Label   fgName        = null;
					Label   fgStat0       = null;
					Label   fgStat1		  = null;
					
					for (foodGroupStats fgs : foodGroupList) {
					
						// set food group variables
						String  name 	= fgs.getName  ();
						Date    date 	= fgs.getDate  ();
						Double  price   = fgs.getPrice ();
						Integer fCount  = fgs.getCount ();
					
						// if food group equals selected food group
						if (selectedFoodGroup.equals (name)) {
							// set variables
							averageCount++;
							totalPrice  += price;
							totalAmount += fCount;
							
							String dateWeek = dfg.format(date);
						
							// add data to series
							series.getData ().add (new XYChart.Data <String, Number> (dateWeek, price));
						}
					}
					
					// clears box
					hbox.getChildren().clear();
					
					// set data to bottom table
					String statLabel0 = "-On Average, you spend €"+df.format(totalPrice/averageCount)+" on "+selectedFoodGroup+".";
					String statLabel1 = "-On Average, you buy "+(totalAmount/averageCount)+" "+selectedFoodGroup+" per shop.";
					fgName = new Label ("• "+selectedFoodGroup);
					fgStat0 = new Label (statLabel0);
					fgStat1 = new Label (statLabel1);
					
					// styles labels
					setLabelHead (fgName);
					
					// average price label
					setLabelAsideH2 (fgStat0);
					fgStat0.setTranslateX(5);
					fgStat0.setTranslateY(30);
					
					// average amount label
					setLabelAsideH2 (fgStat1);
					fgStat1.setTranslateX(40);
					fgStat1.setTranslateY(30);
					
					// adds items to box
					hbox.getChildren().addAll(fgName, fgStat0, fgStat1);
					
					// adds group to the check list, so that it cannot be added again
					checkList.add (selectedFoodGroup);
					
					// add data to chart if series is not null
					if (!series.equals (null)) {
						lineChart.getData ().add (series);
					}
					
				}
				
			}

		});
		
		
		// adds data to pane
		vbox.getChildren ().addAll (listHeader, fgList);
		
	}
	
	
	// builds central pane
	private static void buildLineChartUI (LineChart <String, Number> lineChart) {
		lineChart.setPrefSize (cWidth+sWidth, sHeight);
		lineChart.setStyle ("-fx-padding:"+padding+"; -fx-border-style:solid inside; "
					+ "-fx-border-width:"+border+"; -fx-border-insets:"+insets+"; "
					+ "-fx-border-radius:"+radius+"; -fx-border-color:"+borderColor+";"
					+ "-fx-background-color:"+panelColor+";-fx-background-radius: "+radius+";"
					+ "-fx-background-insets:"+insets+";");
	}
	
	
	// builds side pane
	private static void buildLineSideUI (VBox vbox) {
		vbox.setPrefHeight (sHeight);
		vbox.setPrefWidth  (sWidth);
		vbox.setStyle ("-fx-padding:"+padding+"; -fx-border-style:solid inside; "
					+ "-fx-border-width:"+border+"; -fx-border-insets:"+insets+"; "
               		+ "-fx-border-radius:"+radius+"; -fx-border-color:"+borderColor+";"
                	+ "-fx-background-color:"+panelColor+";-fx-background-radius: "+radius+";"
                	+ "-fx-background-insets:"+insets+";");
		
	}
	
	
	// builds bottom pane
	private static void buildLineBottomUI (HBox hbox) {
		hbox.setPrefHeight (tHeight);
		hbox.setPrefWidth  (tWidth);
		hbox.setStyle ("-fx-padding: "+bottomPad+"; -fx-border-style: solid inside; "
				+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
                + "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
                + "-fx-background-color: "+panelColor+"; -fx-background-radius: "+radius+";"
                	+ "-fx-background-insets: "+insets+";");
		
	}

	
	//==========//
	//	STYLES	//
	//==========//
	
	// sets labels style
	static void setLabelHead (Label label) {
		label.setStyle ("-fx-font: 16px Georgia;"
						+ "-fx-padding: 1 3 1 3;"
						+ "-fx-insets: 1;");
		
		label.setMinSize (10.0, 10.0);
		
	}
	static void setLabelAsideH1 (Label label) {
		label.setStyle ("-fx-font: 18px Georgia;"
						+ "-fx-padding: 2 0 2 0;"
						+ "-fx-insets: 1;");
		
		label.setMinSize (10.0, 10.0);
		
	}
	static void setLabelAsideH2 (Label label) {
		label.setStyle ("-fx-font: 15px Georgia;"
						+ "-fx-padding: 1;"
						+ "-fx-insets:  1;"
						+ "-fx-text-alignment: center;");
		
		label.setMinSize (10.0, 10.0);
		
	}
	static void setLabelAsideH3 (Label label) {
		label.setStyle ("-fx-font: 13px Georgia;"
						+ "-fx-padding: 1;"
						+ "-fx-insets:  1;"
						+ "-fx-text-alignment: center;");
		
		label.setMinSize (10.0, 10.0);
		
	}
	
	
	//=====================//
	//	Return data lists  //
	//=====================//
	
	public static ObservableList <spendingStats> getSpendingStats(){
		return spendingData;
	}
	
	public static ObservableList <dateRange> getDateRange () {
		return dates;
	}
	
	public static ObservableList <foodGroupStats> getFoodGroupStats () {
		return foodGroupList;
	}
	
}
