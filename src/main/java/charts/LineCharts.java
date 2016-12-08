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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TreeMap;

public class LineCharts extends StackPane {

    private LineChart<String,Number> lineChart;
    private String chartName;

    public LineCharts(ArrayList<TreeMap<Integer, Number>> data) {

        super();

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        lineChart = new LineChart<String, Number>(xAxis, yAxis);
        chartName = Model.getInstance().currentObjectIndicator.getLabelFromCode(Model.getInstance().currentIndicator);
        lineChart.setTitle(chartName);
        xAxis.setLabel("Date");
        yAxis.setLabel(Model.getInstance().currentObjectIndicator.getUnit());
        this.addData(data);
        this.getChildren().add(lineChart);
        for(final XYChart.Series<String, Number> series : lineChart.getData()) {
            for (final XYChart.Data<String, Number> node : series.getData()) {
                node.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String dateToDisplay = node.getXValue();
                        BigDecimal valueToDisplay = new BigDecimal(node.getYValue().doubleValue());
                        valueToDisplay = valueToDisplay.setScale(3, RoundingMode.HALF_DOWN);
                        if (lineChart.getTitle().equals("GDP")) {
                            int nodeIndex = series.getData().indexOf(node);


                            if (nodeIndex > 0 && nodeIndex < series.getData().size() - 1) {
                                double valueBefore = series.getData().get(nodeIndex - 1).getYValue().doubleValue();
                                double valueAfter = series.getData().get(nodeIndex + 1).getYValue().doubleValue();
                                double value = node.getYValue().doubleValue();

                                if (valueBefore < value && value > valueAfter) {
                                    Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + String.valueOf(valueToDisplay) + "\nEconomic Cycle: Expansion Peak"));
                                } else if (valueBefore > value && valueAfter > value) {
                                    Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + String.valueOf(valueToDisplay) + "\nEconomic Cycle: Low Peak"));
                                } else if (valueBefore < value && valueAfter > value) {
                                    Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + String.valueOf(valueToDisplay) + "\nEconomic Cycle: Economic Recovery"));
                                } else if (value < valueBefore && valueAfter < value) {
                                    Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + String.valueOf(valueToDisplay) + "\nEconomic Cycle: Economic Contraction"));
                                }
                            } else {
                                Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + String.valueOf(valueToDisplay)));
                            }
                        } else {
                            Tooltip.install(node.getNode(), new Tooltip("Date: " + dateToDisplay + "\n" + chartName + ": " + String.valueOf(valueToDisplay)));
                        }
                    }
                });
            }
        }
    }

    private void addData(ArrayList <TreeMap<Integer,Number>> data){
        lineChart.getData().clear();
        //populating the series with data
        for (int i = 0; i < data.size(); i++) {
            XYChart.Series series = new XYChart.Series();
            series.setName(Model.getInstance().countries[Model.getInstance().currentCountries.get(i)].getName());
            for (Integer date : data.get(i).keySet()) {
                series.getData().add(new XYChart.Data(String.valueOf(date), data.get(i).get(date)));
            }
            lineChart.getData().add(series);
        }
    }
}
