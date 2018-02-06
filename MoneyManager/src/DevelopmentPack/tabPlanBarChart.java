package DevelopmentPack;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class tabPlanBarChart {
	// pane height(600) and width(900)
	// top sizing
	private final static double tHeight = 120.0;
	private final static double tWidth  = 900.0;
	// central sizing
	private final static double sHeight = 600.0 - tHeight;
	private final static double sWidth  = 100.0;
	
	// formatter
	private static final DecimalFormat df = new DecimalFormat("#.00");
	
	// panel style
	private static String  panelColor   = "#EAEDED";
	private static String  borderColor  = "#2F4F4F";
	private static Integer insets  		= 4;
	private static Integer border  		= 2;
	private static Integer padding 		= 2;
	private static Integer bottomPad	= 6;
	private static Integer radius  		= 5;
	
	
	private static ObservableList <dateRange>       dates 		 = tabPlanInits.getDateRange();
	private static ObservableList <spendingStats>   spendingData = tabPlanInits.getSpendingStats();
	
	
	public static void buildEconomicBreakdown (BorderPane bPane) {
		final VBox vboxL = new VBox ();
		final HBox hboxB = new HBox ();
		
		final NumberAxis   YAxis = new NumberAxis (0, 250, 10);
        final CategoryAxis XAxis = new CategoryAxis ();
        
        BarChart <String, Number> barChart = new BarChart <String, Number> (XAxis, YAxis);
        barChart.setTitle ("Economic Breakdown");
        YAxis.setLabel	  ("Money Spent");
        XAxis.setLabel	  ("Dates Shopped");
        
        barChart.setStyle ("-fx-padding:"+padding+"; -fx-border-style:solid inside; "
        				+ "-fx-border-width:"+border+"; -fx-border-insets:"+insets+"; "
                		+ "-fx-border-radius:"+radius+"; -fx-border-color:"+borderColor+";"
                		+ "-fx-background-color:"+panelColor+";-fx-background-radius: "+radius+"; "
                		+ "-fx-background-insets:"+insets+";");
        
        
        // builds controls
        buildBarSideUI 	 (barChart, hboxB, vboxL);      
        buildBarBottomUI (hboxB);
        
        // sets to pane
        bPane.setCenter (barChart);
        bPane.setLeft 	(vboxL);
        bPane.setBottom (hboxB);
        
	}
	
	private static void addDataBarChart (BarChart<String, Number> barChart, Integer setDate) {
		// set series
		XYChart.Series <String, Number> series = new XYChart.Series <String, Number> ();
		
		// set date format
		DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
		
		// add variables
		for (spendingStats x: spendingData) {
			// gets date and formats to string
			Date realDate  = x.getDate ();
			Calendar cal = Calendar.getInstance();
			cal.setTime (realDate);
			Integer monthDate = cal.get(Calendar.MONTH);
			
			String dateName = getMonthName (realDate);
			if (setDate.equals (monthDate)) {
				series.setName (dateName);
				String date = df.format (realDate);
				// gets price
				Double price = x.getPrice ();
				// adds to series
				series.getData ().add (new XYChart.Data <String, Number> (date, price));

			}
		}   
		
        // adds to bar chart
        barChart.getData ().add (series);
        
        
	}
	
	// build side bar
	private static void buildBarSideUI (BarChart <String, Number> barChart, HBox hboxB, VBox vbox) {	
		vbox.setPrefHeight (sHeight);
		vbox.setPrefWidth  (sWidth);
		vbox.setSpacing    (2);
		vbox.setStyle      ("-fx-padding: "+padding+"; -fx-border-style: solid inside; "
						+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
						+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
						+ "-fx-background-color: "+panelColor+"; -fx-background-radius: "+radius+";"
						+ "-fx-background-insets: "+insets+";");
		
		// gets date number from calendar
		Calendar cal = Calendar.getInstance ();
		

		// create buttons
		ListView <Date> dateList = new ListView <Date> ();
		Label listHeader = new Label (" • Dates");
		setLabelHead (listHeader);
		// go through dates and set data
		Integer startCounter = 0;
		for (dateRange d: dates) {
			Date date = d.getDate ();

			// sets initial data
			if (startCounter == 0) {
				cal.setTime (date);
				int monthNumber = cal.get (Calendar.MONTH);
				addDataBarChart (barChart, monthNumber);
           		buildBarBottomStats (hboxB, monthNumber);
			}
           	startCounter++;
           	
           	// adds data to list
			dateList.getItems ().add (date);
		}
		vbox.getChildren ().addAll (listHeader, dateList);
		
		
		// set action
		dateList.setOnMouseClicked (new EventHandler <MouseEvent> () {
			@Override
	        public void handle (MouseEvent event) {
				barChart.getData  ().clear ();
				hboxB.getChildren ().clear ();
				
				// gets month selected
				Date dateSelected = dateList.getSelectionModel ().getSelectedItem ();
				cal.setTime (dateSelected);
				int monthNumber = cal.get (Calendar.MONTH);
				
				// adds data
	           	addDataBarChart 	(barChart, monthNumber);
	           	buildBarBottomStats (hboxB, monthNumber);
			}
		});

	}
	
	
	// styles the bottom bar
	private static void buildBarBottomUI (HBox hbox) {
		// bottom panel size and style
		hbox.setPrefHeight (tHeight);
		hbox.setPrefWidth  (tWidth);
		hbox.setSpacing    (10);
		
		hbox.setStyle      ("-fx-padding: "+bottomPad+"; -fx-border-style: solid inside; "
						+ "-fx-border-width: "+border+"; -fx-border-insets: "+insets+"; "
						+ "-fx-border-radius: "+radius+"; -fx-border-color: "+borderColor+";"
						+ "-fx-background-color: "+panelColor+"; -fx-background-radius: "+radius+";"
						+ "-fx-background-insets: "+insets+";");
		
	}
	
	
	// sets data to bottom pane
	private static void buildBarBottomStats (HBox hbox, Integer month) {
		Label week1= new Label(), week2=new Label(), week3=new Label(), week4=new Label(), week5=new Label();
		String w1=new String(), w2=new String(), w3=new String(), w4=new String(), w5=new String();
		
		VBox vbox0 = new VBox (5);
		VBox vbox1 = new VBox (5);
		VBox vbox2 = new VBox (5);
		VBox vbox3 = new VBox (5);
		VBox vbox4 = new VBox (5);
		VBox vbox5 = new VBox (5);
		
		Calendar cal 	   = Calendar.getInstance ();
		Date thisDate 	   = null;
		Double totalSpend  = 0.0; 
		Integer groupCount = 0;
		
		for (spendingStats x : spendingData) {

			Date date 	 = x.getDate  ();
			Double price = x.getPrice ();
			String cost  = df.format(price);
			
			// use calendar to get months
			cal.setTime (date);
			Integer monthCheck = cal.get (Calendar.MONTH);	
			
			if (month.equals (monthCheck) ) {
				groupCount++;
				if (groupCount == 1) {
					w1 += "•Amount spent\n on the "+getMonthDay (date)+"\n of "+getMonthName (date)+"\n was €"+cost+".";
					week1.setText  (w1);
					setLabelAsideH3 (week1);
					vbox1.getChildren ().add (week1);
				}
				if (groupCount == 2) {
					w2 += "•Amount spent\n on the "+getMonthDay (date)+"\n of "+getMonthName (date)+"\n was €"+cost+".";
					week2.setText  (w2);
					setLabelAsideH3 (week2);
					vbox2.getChildren ().add (week2);
				}
				if (groupCount == 3) {
					w3 += "•Amount spent\n on the "+getMonthDay (date)+"\n of "+getMonthName (date)+"\n was €"+cost+".";
					week3.setText  (w3);
					setLabelAsideH3 (week3);
					vbox3.getChildren ().add (week3);
				}
				if (groupCount == 4) {
					w4 += "•Amount spent\n on the "+getMonthDay (date)+"\n of "+getMonthName (date)+"\n was €"+cost+".";
					week4.setText (w4);
					setLabelAsideH3 (week4);
					vbox4.getChildren ().add (week4);
				}
				if (groupCount == 5) {
					w5 += "•Amount spent\n on the "+getMonthDay (date)+"\n of "+getMonthName (date)+"\n was €"+cost+".";
					week5.setText (w5);
					vbox5.getChildren ().add (week5);
				}
				
				// updates total spending amount
				totalSpend += price;
				thisDate   = date;
			}
			
		}
		
		// adds head container
		Label total = new Label ("•You spent a\n total of\n €"+df.format(totalSpend)+"\n this month\n ("+getMonthName (thisDate)+")");
		setLabelAsideH3 (total);
		vbox0.getChildren ().add (total);
		
		// builds containers
		buildBottomStatsHead (vbox0); buildBottomStats (vbox1); buildBottomStats (vbox2);
		buildBottomStats  (vbox3); buildBottomStats (vbox4); buildBottomStats (vbox5);
		
		// adds all containers
		hbox.getChildren ().addAll (vbox0, vbox1, vbox2, vbox3, vbox4, vbox5);
		
	}
	
	//==========//
		//	 Dates	//
		//==========//
		
		// gets month name
		private static String getMonthName (Date d) {
			// gets date number from calendar
			Calendar cal = Calendar.getInstance ();
			cal.setTime (d);
			int monthNumber = cal.get (Calendar.MONTH);
			
			// sets variables
			String monthName = "";
			DateFormatSymbols dfs = new DateFormatSymbols ();
			
			// pulls month name
			String [] monthNames = dfs.getMonths ();
			if (monthNumber >= 0 && monthNumber <= 11 ) {
				monthName = monthNames [monthNumber];
			}
			// returns month name
			return monthName;
			
		}
		
		// gets day from date
		private static String getMonthDay (Date d) {
			// gets day out of month
			DateFormat df = new SimpleDateFormat ("dd");
			String day = df.format(d);
			
			return day;
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
