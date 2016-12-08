package charts;

import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by jacobklerfelt on 2016-11-28.
 */
public class PhillipsCurve extends StackPane {

    private LineChart<Number,Number> lineChart;

    public PhillipsCurve(ArrayList<TreeMap<Integer, Integer>> data) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Inflation");
        yAxis.setLabel("Unemployment");
        //creating the chart
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Phillips Curve");
        this.addData(data);
        this.getChildren().add(lineChart);

        for(XYChart.Series<Number, Number> series : lineChart.getData()) {
            for (final XYChart.Data<Number, Number> node : series.getData()) {
                node.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Label caption = new Label("");
                        caption.setStyle("-fx-font: 35 arial;");
                        caption.setStyle("-fx-background-color: #FFFFFF");
                        caption.setTranslateX(event.getSceneX());
                        caption.setTranslateY(event.getSceneY());
                        caption.setText(String.valueOf(node.getXValue()) + ", " + String.valueOf(node.getYValue()));
                        getChildren().add(caption);
                    }
                });
                node.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        getChildren().remove(getChildren().size() - 1);
                    }
                });
            }
        }
    }


    private void addData(ArrayList<TreeMap<Integer, Integer>> data) {
        lineChart.getData().clear();
        for (TreeMap<Integer, Integer> temp : data) {
            XYChart.Series series = new XYChart.Series();
            series.setName("Country"); // wrapper from Vlad
            for (Integer inflation : temp.keySet()) {
                XYChart.Data toAdd = new XYChart.Data(inflation, temp.get(inflation));
                series.getData().add(toAdd);
            }
            lineChart.getData().add(series);
        }
    }
}
