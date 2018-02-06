package DevelopmentPack;

import java.sql.Date;
import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class tabPlanPieChart {
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
	private static final DecimalFormat df = new DecimalFormat ("#.00");
	
	
	// statistical data
	private static ObservableList <dateRange>       dates 		  = tabPlanInits.getDateRange ();
	private static ObservableList <foodGroupStats>  foodGroupList = tabPlanInits.getFoodGroupStats ();
	
	
	
	public static void buildFoodGroupBreakdown (BorderPane bPane) {
		// data list, for pie chart data
		ObservableList <PieChart.Data> pData = FXCollections.observableArrayList ();
		
		// creates pie chart and pane's to hold data
		PieChart pieChart = new PieChart (pData);
		final VBox vboxL = new VBox ();
		final HBox hboxB = new HBox (10);
		
		// build UI
		buildPieChartUI (pieChart);
		buildPieSideUI  (vboxL);
		buildPieChartButtons (pData, pieChart, vboxL, 0, hboxB);
		
		// set to pane
		bPane.setCenter (pieChart);
	    bPane.setLeft   (vboxL);
	    bPane.setBottom (hboxB);
	    
	}
	
	// adds custom colours to pie chart
	private static void applyCustomColorSequence (ObservableList <PieChart.Data> pData, String... Colours) {
		int i = 0;
		// adds colour to pie chart
	    for (PieChart.Data data : pData) {
	    	data.getNode ().setStyle ("-fx-pie-color: "+Colours[i%Colours.length]+";");
	    	i++;
	    }
	}
	
	
	private static void buildPieChartUI (PieChart pieChart) {
		pieChart.setTitle 		  ("Food Group Breakdown");
	    pieChart.setLegendSide    (Side.RIGHT);
	    pieChart.setClockwise	  (false);
	    pieChart.setLabelsVisible (true);
    	pieChart.setLegendVisible (true);
    	pieChart.setPrefSize 	  (cWidth+sWidth, sHeight);
    	
    	// adds style
    	pieChart.setStyle ("-fx-padding: "+padding+"; -fx-border-style: solid inside; "
    					+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
                		+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
                		+ "-fx-background-color: "+panelColor+"; -fx-background-radius: "+radius+";"
                		+ "-fx-background-insets: "+insets+";");
    	
	}
	
	
	private static void buildPieSideUI (VBox vbox) {
		// side panel size and style
		vbox.setPrefHeight (sHeight);
		vbox.setPrefWidth  (sWidth);
		vbox.setSpacing    (2);
		
		// adds style
		vbox.setStyle ("-fx-padding: "+padding+"; -fx-border-style: solid inside; "
					+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
					+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
					+ "-fx-background-color: "+panelColor+"; -fx-background-radius: "+radius+";"
					+ "-fx-background-insets: "+insets+";");
		
	}
	
	
	// builds pie chart interface
	private static void buildPieChartButtons (ObservableList <Data> pData, PieChart chart, VBox vbox, Integer i, HBox hboxB) {
		// creates list to store dates
		ListView <Date> dateList = new ListView <Date> ();
		
		
		// sets header label
		Label listHeader = new Label (" • Dates");
		setLabelHead (listHeader);
		
		
		// go through dates and set data
		Integer startCounter = 0;
		for (dateRange d: dates) {
			// sets date variable
			Date date = d.getDate ();
			
			// sets initial data
			if (startCounter == 0) {
				applyCustomColorSequence (pData,"aqua","bisque","chocolate","coral","crimson","brown","green","blue","gray","red");
	           	addDataToPieChart (pData, date);
	           	buildPieBottomUI  (hboxB, date, 0);
	           	
	           	// makes sure this only happens once
	           	startCounter++;
			}
           	
			
           	// adds dates to list
			dateList.getItems ().add (date);

		}
		vbox.getChildren ().addAll (listHeader, dateList);
		
		
		// set action
		dateList.setOnMouseClicked (new EventHandler <MouseEvent> () {
			@Override
	        public void handle (MouseEvent event) {
				// clears data, removes duplication
				pData.clear ();
				chart.getData ().clear ();
				hboxB.getChildren ().clear ();
				
				Date dateSelected = dateList.getSelectionModel ().getSelectedItem ();
				
				// gets data and adds
				applyCustomColorSequence (pData,"aqua","bisque","chocolate","coral","crimson","brown","green","blue","gray","red");
	           	addDataToPieChart (pData, dateSelected);
	           	buildPieBottomUI  (hboxB, dateSelected, 0);
	           	
			}
		});
		
	}
	
	
	// gets food group data and adds to pie data
	private static void addDataToPieChart (ObservableList <PieChart.Data> pData, Date dateCheck) {
		// used to skip all in data search
		String all = "All";
		
	    for (foodGroupStats fg: foodGroupList) {
	    	// sets variables from food group class
	    	Date date 	  = fg.getDate  ();
	    	String name   = fg.getName  ();
	    	Integer count =	fg.getCount ();
	    	
	    	
	    	// adds to data list, if selected date matches
	    	if (dateCheck.equals (date) && !all.equals (name) ) {
	    		// add method
	    		pData.add (new PieChart.Data (name, count));
	    		
	    	}
	    }
	    
	}
	
	
	// adds information about food groups to bottom
	private static void buildPieBottomUI (HBox hbox, Date selectedDate, Integer startCount) {
		// defines grouping counter, used to group data into v box
		Integer groupCounter = new Integer (startCount);
		
		// defines labels and text
		Label dateLabel = new Label (), genLabel0 = new Label (), genLabel1 = new Label (); 
		Label genLabel2 = new Label (), genLabel3 = new Label (), genLabel4 = new Label ();
		String total = new String (), gen0 = new String (), gen1 = new String ();
		String gen2  = new String (), gen3 = new String (), gen4 = new String ();
		
		
		// defines v boxes, used to display data
		VBox vbox0 = new VBox (5);	buildBottomStats (vbox0);
		VBox vbox1 = new VBox (5);	buildBottomStats (vbox1);
		VBox vbox2 = new VBox (5);	buildBottomStats (vbox2);
		VBox vbox3 = new VBox (5);	buildBottomStats (vbox3);
		VBox vbox4 = new VBox (5);	buildBottomStats (vbox4);
		VBox vbox5 = new VBox (5);	buildBottomStats (vbox5);
		
		
		// bottom panel size and style
		hbox.setPrefHeight (tHeight);
		hbox.setPrefWidth  (tWidth);
		hbox.setSpacing    (10);
		hbox.setStyle      ("-fx-padding: "+bottomPad+"; -fx-border-style: solid inside; "
						+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
						+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
						+ "-fx-background-color: "+panelColor+"; -fx-background-radius: "+radius+";"
						+ "-fx-background-insets: "+insets+";");
		
		
		// gets total count of items
		Integer total_amount = 0;
		
		
		// loops through food group data
		for (foodGroupStats fg: foodGroupList) {
			// sets variables
	    	Date date 	  	= fg.getDate  ();
	    	String name   	= fg.getName  ();
	    	Double count	= fg.getPrice ();
	    	Integer amount 	= fg.getCount ();
	    	
	    	
	    	// add data based on selected date
	    	if ( selectedDate.equals (date) ) {
	    		
	    		// adds Label grouping
	    		if (groupCounter == 0) { 
	    			total = "•Your total\n was €"+df.format (count);
	    			total_amount = amount;
	    			
	    		} else if (groupCounter == 1 || groupCounter == 2) {
	    			gen0 += "•You spent €"+df.format (count)+" on\n "
	    					+amount+" "+name+"("+ Math.round ((amount * 100) / total_amount)+"%)\n\n";
	    			
	    		} else if (groupCounter == 3 || groupCounter == 4) {
	    			gen1 += "•You spent €"+df.format(count)+" on\n "
	    					+amount+" "+name+"("+ Math.round ((amount * 100) / total_amount)+"%)\n\n";
	    			
	    		} else if (groupCounter == 5 || groupCounter == 6) {
	    			gen2 += "•You spent €"+df.format(count)+" on\n "
	    					+amount+" "+name+"("+ Math.round ((amount * 100) / total_amount)+"%)\n\n";
	    			
	    		} else if (groupCounter == 7 || groupCounter == 8) {
	    			gen3 += "•You spent €"+df.format(count)+" on\n "
	    					+amount+" "+name+"("+ Math.round ((amount * 100) / total_amount)+"%)\n\n";
	    			
	    		} else if (groupCounter == 9 || groupCounter == 10) {
	    			gen4 += "•You spent €"+df.format(count)+" on\n "
	    					+amount+" "+name+"("+ Math.round ((amount * 100) / total_amount)+"%)\n\n";
	    			
	    		}
	    		// increments grouping counter
	    		groupCounter++;
	    	}
	    }
		
		
		// define labels
    	dateLabel = new Label ("•Date of:\n"+selectedDate+"\n\n "+total); setLabelAsideH3 (dateLabel);
    	genLabel0 = new Label (gen0); setLabelAsideH3 (genLabel0);
    	genLabel1 = new Label (gen1); setLabelAsideH3 (genLabel1);
    	genLabel2 = new Label (gen2); setLabelAsideH3 (genLabel2);
    	genLabel3 = new Label (gen3); setLabelAsideH3 (genLabel3);
    	genLabel4 = new Label (gen4); setLabelAsideH3 (genLabel4);
    	
    	
    	// builds containers
		vbox0.getChildren ().add (dateLabel); buildBottomStatsHead (vbox0);
		vbox1.getChildren ().add (genLabel0); buildBottomStats (vbox1);
		vbox2.getChildren ().add (genLabel1); buildBottomStats (vbox2);
		vbox3.getChildren ().add (genLabel2); buildBottomStats (vbox3);
		vbox4.getChildren ().add (genLabel3); buildBottomStats (vbox4);
		vbox5.getChildren ().add (genLabel4); buildBottomStats (vbox5);
		
		
		// add containers to pane
		hbox.getChildren ().addAll (vbox0, vbox1, vbox2, vbox3, vbox4, vbox5);
		
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


	// build container style
	private static void buildBottomStatsHead (VBox box) {
		box.setPrefHeight (tHeight);
		box.setPrefWidth  ((tWidth/6)-30);
		box.setStyle 	  ("-fx-padding: 5 1 5 6;");
		
	}
	private static void buildBottomStats (VBox box) {
		box.setPrefHeight (tHeight);
		box.setPrefWidth  (tWidth/5);
		box.setStyle 	  ("-fx-padding: 5 1 5 5;");
		
	}
	
}
