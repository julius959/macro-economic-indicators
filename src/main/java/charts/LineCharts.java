package charts;

import api_model.Model;
import javafx.event.EventHandler;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import view.DataDisplayWrapper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Class for creating LineCharts
 * @author jacobklerfelt
 * Created 2016-11-25
 *
 */

public class LineCharts extends StackPane {

    private LineChart<String,Number> lineChart; // Object of a LineChart
    DataDisplayWrapper currentWrapper;
    public LineCharts(ArrayList<TreeMap<Integer, Number>> data, DataDisplayWrapper currentWrapper) {

        super();

        final CategoryAxis xAxis = new CategoryAxis(); // CategoryAxis that is going to work as the x-axis of the linechart
        final NumberAxis yAxis = new NumberAxis(); // NumberAxis that is going to work as the y-axis of the chart
        this.currentWrapper = currentWrapper;
        lineChart = new LineChart<String, Number>(xAxis, yAxis);
        String chartName = currentWrapper.getcurrentIndicatorObject().getLabelFromCode(currentWrapper.getcurrentIndicatorCode()); // Setting the name of the linechart by checking which indicator is currently chosen in the model
        lineChart.setTitle(chartName);
        xAxis.setLabel("Date");
        yAxis.setLabel(chartName);
        this.addData(data); // calling method that will add all the data to the linechart
        this.getChildren().add(lineChart); // adding lineChart to pane
        for(final XYChart.Series<String, Number> series : lineChart.getData()) { // adding eventhandler to the different nodes in the linechart
            for (final XYChart.Data<String, Number> node : series.getData()) {
                node.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String dateToDisplay = node.getXValue();
                        BigDecimal valueToDisplay = new BigDecimal(node.getYValue().doubleValue());

                        DecimalFormat yValFormat = new DecimalFormat(".###"); // Setting a maximum number of 3 decimals to be displayed

                        if (chartName.equals("GDP") || chartName.equals("GDP per capita") || chartName.equals("GDP growth")) { // GDP has been chosen as indicator, economic cycle can be calculated and added to tooltip
                            int nodeIndex = series.getData().indexOf(node);

                            if (nodeIndex > 0 && nodeIndex < series.getData().size() - 1) { // Node is not the first or last node in chart
                                double valueBefore = series.getData().get(nodeIndex - 1).getYValue().doubleValue(); // value of the node before
                                double valueAfter = series.getData().get(nodeIndex + 1).getYValue().doubleValue(); // value of the node after
                                double value = node.getYValue().doubleValue(); // value of the current node

                                if (valueBefore < value && value > valueAfter) { // current nodes value is larger than node before and after, economic cyle is expansion peak
                                    Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + yValFormat.format(valueToDisplay) + "\nEconomic Cycle: Expansion Peak"));
                                } else if (valueBefore > value && valueAfter > value) { // current nodes value is smaller than node before and after, economic cycle is low peak
                                    Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + yValFormat.format(valueToDisplay) + "\nEconomic Cycle: Low Peak"));
                                } else if (valueBefore < value && valueAfter > value) { // current nodes value has increased since node before, but is smaller than node after, economic cycle is recovery
                                    Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + yValFormat.format(valueToDisplay) + "\nEconomic Cycle: Economic Recovery"));
                                } else if (value < valueBefore && valueAfter < value) { // value has decreased since node before, but is decreasing further - economic cycle is economic contraction
                                    Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + yValFormat.format(valueToDisplay) + "\nEconomic Cycle: Economic Contraction"));
                                }
                            } else { // node is either first or last node in chart, economic cycle can not be calculated
                                Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + yValFormat.format(valueToDisplay)));
                            }
                        } else { // Chart is not displaying GDP-related data so economic cycle can not be calculated, adding default tooltip with information of the date and value of every node
                            Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + yValFormat.format(valueToDisplay)));
                        }
                    }
                });
            }
        }
    }
    
    /**
     * Method that iterates through list of data given by the model and adds the data to XYSeries that each is added to the LineChart
     * @param data ArrayList of TreeMaps, each containing data of some sort for every country chosen by user
     */
    private void addData(ArrayList <TreeMap<Integer,Number>> data){
        lineChart.getData().clear();
        //populating the series with data
        for (int i = 0; i < data.size(); i++) { // looping through every country in the list and adding its data to an XYChart series that is then added to the linechart
            XYChart.Series series = new XYChart.Series();
            series.setName(Model.getInstance().countries[currentWrapper.getInCountries().get(i)].getName());
            for (Integer date : data.get(i).keySet()) {
                series.getData().add(new XYChart.Data(String.valueOf(date), data.get(i).get(date)));
            }
            lineChart.getData().add(series);
        }
    }
}
