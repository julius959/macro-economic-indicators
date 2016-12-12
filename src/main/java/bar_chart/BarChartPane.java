package bar_chart;

import api_model.Model;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import view.DataDisplayWrapper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Represents a Pane containing a BarChart populated by given data of a query
 */
public class BarChartPane extends StackPane {
    //Bar Chart object of the Pane
    private BarChart<String, Number> barChart;

    /**
     * Constructor for a BarChartPane
     * @param data ArrayList containing a TreeMap for every country that has data to be displayed
     */
    private DataDisplayWrapper currentWrapper;
    public BarChartPane(ArrayList<TreeMap<Integer, Number>> data, DataDisplayWrapper currentWrapper) {
        super();

        //Creates x and y axis
        CategoryAxis dateAxis = new CategoryAxis();
        NumberAxis valueAxis = new NumberAxis();

        //Initialises empty bar chart
        barChart = new BarChart<>(dateAxis, valueAxis);
        this.currentWrapper = currentWrapper;
        //Adds title and axis labels
        String chartTitle = currentWrapper.getcurrentIndicatorObject().getLabelFromCode(currentWrapper.getcurrentIndicatorCode());
        barChart.setTitle(chartTitle);
        dateAxis.setLabel("Year");
        valueAxis.setLabel(currentWrapper.getcurrentIndicatorObject().getSubIndicatorUnitFromCode(currentWrapper.getcurrentIndicatorCode()));

        //Populate chart with given data
        passData(data);

        //Add chart to container
        getChildren().add(barChart);
    }

    /**
     * Updates bar chart with given data
     * @param data ArrayList containing a TreeMap for every country that has data to be displayed
     */
    public void passData(ArrayList<TreeMap<Integer, Number>> data) {
        //Clears current data in bar chart - when year's requested has changed
        barChart.getData().clear();

        //Retrieves name of current indicator
        String chartTitle = currentWrapper.getcurrentIndicatorObject().getLabelFromCode(currentWrapper.getcurrentIndicatorCode());

        //Store data to 3 decimal places
        DecimalFormat yValFormat = new DecimalFormat(".###");

        //Loop over data for every country
        for (int i = 0; i < data.size(); ++i) {
            //Create new series of data for each country
            XYChart.Series<String, Number> tempSeries = new XYChart.Series<>();
            //Retrieve corresponding country for this series of data and add it to name of this series
            String country = Model.getInstance().countries[currentWrapper.getInCountries().get(i)].getName();
            tempSeries.setName(country);
            //Get the date values
            ArrayList<Integer> alstKeys = new ArrayList<>(data.get(i).keySet());
            //Add each data instance to the series of data
            for (Integer sKey : alstKeys)
                tempSeries.getData().add(new XYChart.Data<>(Integer.toString(sKey), data.get(i).get(sKey)));

            //Add the countries data to the chart
            barChart.getData().add(tempSeries);
            barChart.setAnimated(false);

            //Add a on-mouse-hover tooltip displaying the (x,y) value of the node and country it represents
            for (final XYChart.Data<String, Number> node : tempSeries.getData()) {
                Tooltip.install(node.getNode(),
                        new Tooltip("Country: " + country + "\nDate: " + node.getXValue() +
                        "\n" + chartTitle + ": " + yValFormat.format(node.getYValue())));
            }

        }
    }
}