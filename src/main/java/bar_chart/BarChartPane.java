package bar_chart;

import api_model.Model;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeMap;

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
        String chartTitle = Model.getInstance().currentObjectIndicator.getLabelFromCode(Model.getInstance().currentIndicator);
        barChart.setTitle(chartTitle);
        dateAxis.setLabel("Year");
        valueAxis.setLabel(Model.getInstance().currentObjectIndicator.getUnit());

        //Populate chart with given data
        passData(data);
        //Add chart to container

        getChildren().add(barChart);


    }


    public void passData(ArrayList<TreeMap<Integer, Number>> data) {
        barChart.getData().clear();

        String chartTitle = Model.getInstance().currentObjectIndicator.getLabelFromCode(Model.getInstance().currentIndicator);

        DecimalFormat yValFormat = new DecimalFormat(".###");
        //Loop over data for every country
        for (int i = 0; i < data.size(); ++i) {
            //Create new series of data for each country
            XYChart.Series<String, Number> tempSeries = new XYChart.Series<>();
            //Add key to graph for country
            String country = Model.getInstance().countries[Model.getInstance().currentCountries.get(i)].getName();
            tempSeries.setName(country);
            //Get the date values
            ArrayList<Integer> alstKeys = new ArrayList<>(data.get(i).keySet());
            System.out.println("values " + tempSeries);
            //Add each data instance to the series of data
            for (Integer sKey : alstKeys)
                tempSeries.getData().add(new XYChart.Data<>(Integer.toString(sKey), data.get(i).get(sKey)));

            //Add the countries data to the chart
            barChart.getData().add(tempSeries);
            barChart.setAnimated(false);

            for (final XYChart.Data<String, Number> node : tempSeries.getData()) {
                Tooltip.install(node.getNode(),
                        new Tooltip("Country: " + country + "\nDate: " + node.getXValue() +
                        "\n" + chartTitle + ": " + yValFormat.format(node.getYValue())));
            }

        }
        System.out.println(barChart.getData());

    }
}