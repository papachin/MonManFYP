package DevelopmentPack;

import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;


public class tabPlan {

	
	// loads all pane items
	public static void startTabPlan (BorderPane bPane) {
		// defines views for sides
		TabPane tPane = new TabPane ();
		
		// initialise UI and Data
		tabPlanInits.buildStatData ();
		
		// builds tabs
		buildTabs (tPane);
		
		// add to border pane
		bPane.setCenter (tPane);
		
	}
	
	
	// adds tabs to pane
	private static void buildTabs (TabPane tPane) {
		// tab style
		tPane.setStyle ("-fx-padding: 0;");
		tPane.setSide  (Side.TOP);
		
        for (int i = 1; i <= 3; i++) {
        	// creation of tabs
            Tab tab = new Tab ();
            addDataToTabs (tab, i);
            
            // adds tab to pane
            tPane.getTabs ().add (tab);
            
        }
        
	}
	
	
	// tab data addition
	private static void addDataToTabs (Tab tab, Integer i) {
		// pane used to hold each tab
		BorderPane bPane0 = new BorderPane ();
		
		// tab style
		tab.setStyle ("-fx-padding: 2;");
		tab.setClosable (false);
		
		// tabs
        if ( i == 1 ) {
        	// bar chart 
        	tab.setGraphic (new Label(" Economic Breakdown "));
        	tabPlanBarChart.buildEconomicBreakdown (bPane0);
        	tab.setContent (bPane0);
        	
        	
        } else if ( i == 2 ) {
        	// pie chart
        	tab.setGraphic (new Label (" FoodGroup Breakdown "));
        	tabPlanPieChart.buildFoodGroupBreakdown (bPane0);
        	tab.setContent (bPane0);
            
        	
        } else if ( i == 3 ) {
        	// line chart
        	tab.setGraphic(new Label (" Economic Projection "));
        	tabPlanInits.buildEconomicProjection (bPane0);
        	tab.setContent (bPane0);

        }
        
	}
	
	
}
