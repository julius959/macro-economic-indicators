package charts;

import api_model.Model;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeMap;


/**
 * Created by jacobklerfelt on 2016-11-25.
 */
public class PieCharts extends StackPane {

    private PieChart pieChart;
    private ObservableList<PieChart.Data> pieChartData;
    private double totalValues;
    private TranslateTransition tt;

    public PieCharts(ArrayList<TreeMap<Integer, Number>> data) {

        super();
        totalValues = 0;
        pieChart = new PieChart();
        String pieChartTitle = "Average " + Model.getInstance().currentObjectIndicator.getLabelFromCode(Model.getInstance().currentIndicator);
        pieChart.setTitle(pieChartTitle);
        //     BigDecimal bd = new BigDecimal(2);


        this.addData(data);
        this.getChildren().add(pieChart);
        for (final PieChart.Data dataInPie : pieChart.getData()) {
            dataInPie.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    BigDecimal nodeValue = new BigDecimal(dataInPie.getPieValue());
                    DecimalFormat yValFormat = new DecimalFormat(".###");
                    Tooltip.install(dataInPie.getNode(), new Tooltip("Country: " + dataInPie.getName() + "\n" + pieChartTitle + ": " + yValFormat.format(nodeValue) + "\nPercentage: " + Math.round(((dataInPie.getPieValue() / totalValues) * 100)) + "%"));
                }
            });
        }

        Thread anim = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadAnimate();
            }
        });
        anim.start();

        this.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                anim.stop();
                if (tt != null) {
                    tt.stop();
                    System.out.println("Animation thread stopped!!!");
                }

            } else {

            }
        });


    }

    private void loadAnimate() {
        pieChartData.stream().forEach(pieData -> {

            Bounds b1 = pieData.getNode().getBoundsInLocal();
            double newX = ((b1.getWidth()) / 2 + b1.getMinX()) / 7;
            double newY = ((b1.getHeight()) / 2 + b1.getMinY()) / 7;
            // Make sure pie wedge location is reset
            pieData.getNode().setTranslateX(0);
            pieData.getNode().setTranslateY(0);
            tt = new TranslateTransition(
                    Duration.millis(1500), pieData.getNode());
            tt.setByX(newX);
            tt.setByY(newY);
            tt.setAutoReverse(true);
            tt.setCycleCount(2);
            tt.play();

        });
    }

    private void addData(ArrayList<TreeMap<Integer, Number>> data) {
        pieChart.getData().clear();
        pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < data.size(); i++) {
            double average = 0;
            for (Number value : data.get(i).values()) {
                average += value.doubleValue();
            }
            double valueToInsert = average / data.get(i).size();
            totalValues += valueToInsert;
            pieChartData.add(new PieChart.Data(Model.getInstance().countries[Model.getInstance().currentCountries.get(i)].getName(), valueToInsert));
        }
        pieChart.getData().addAll(pieChartData);
    }
}