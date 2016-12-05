package bar_chart;

import api_model.Model;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

import java.util.*;

public class BarChartPane extends StackPane {
    //Bar Chart object of the Pane
    private BarChart<String, Number> barChart;

    public BarChartPane(ArrayList<TreeMap<Integer, Number>> data) {
        super();

        //Creates x and y axis
        CategoryAxis dateAxis = new CategoryAxis();
        NumberAxis valueAxis = new NumberAxis();

        //Initialises empty bar chart
        barChart = new BarChart<>(dateAxis, valueAxis);

        //Adds title and axis labels
        barChart.setTitle(Model.getInstance().currentIndicator);
        dateAxis.setLabel("Year");
        valueAxis.setLabel(Model.getInstance().currentIndicator);

        //Populate chart with given data
        passData(data);

        //Add chart to container
        getChildren().add(barChart);
    }

    private void passData(ArrayList<TreeMap<Integer, Number>> data) {
        //Loop over data for every country
        for (int i = 0; i < data.size(); ++i) {
            //Create new series of data for each country
            XYChart.Series<String, Number> tempSeries = new XYChart.Series<>();
            //Add key to graph for country
            tempSeries.setName(Model.getInstance().countries[Model.getInstance().currentCountries.get(i)].getName());
            //Get the date values
            ArrayList<Integer> alstKeys = new ArrayList<>(data.get(i).keySet());

            //Add each data instance to the series of data
            for (Integer sKey : alstKeys)
                tempSeries.getData().add(new XYChart.Data<>(Integer.toString(sKey), data.get(i).get(sKey)));

            //Add the countries data to the chart
            barChart.getData().add(tempSeries);
        }
    }
}