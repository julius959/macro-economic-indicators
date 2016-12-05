package bar_chart;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

import java.util.*;

public class BarChartPane extends StackPane {
    //Bar Chart object of the Pane
    private BarChart<String, Number> barChart;

    public BarChartPane(ArrayList<TreeMap<String, Number>> data) {
        super();

        //Creates x and y axis
        CategoryAxis dateAxis = new CategoryAxis();
        NumberAxis valueAxis = new NumberAxis();

        //Initialises empty bar chart
        barChart = new BarChart<>(dateAxis, valueAxis);

        //Adds title and axis labels
        barChart.setTitle("Bar Chart Title");
        dateAxis.setLabel("Date");
        valueAxis.setLabel("Value");

        //Populate chart with given data
        passData(data);

        //Add chart to container
        getChildren().add(barChart);
    }

    private void passData(ArrayList<TreeMap<String, Number>> data) {
        //Loop over data for every country
        for (TreeMap<String, Number> hm : data) {
            //Create new series of data for each country
            XYChart.Series<String, Number> tempSeries = new XYChart.Series<>();
            //Get the date values
            ArrayList<String> alstKeys = new ArrayList<>(hm.keySet());
            Collections.reverse(alstKeys);

            //Add each data instance to the series of data
            for (String sKey : alstKeys)
                tempSeries.getData().add(new XYChart.Data<>(sKey, hm.get(sKey)));

            //Add the countries data to the chart
            barChart.getData().add(tempSeries);
        }
    }
}