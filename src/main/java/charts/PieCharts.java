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
 * Class for creating piecharts of economic data of countries chosen by the user of the program
 * @author jacobklerfelt
 * Created on 2016-11-25.
 */
public class PieCharts extends StackPane {

    private PieChart pieChart; // Object of PieChart that is going to be displayed
    private ObservableList<PieChart.Data> pieChartData; // ObservableList holding the data of the PieChart
    private double totalValues; // value holding the total value-amount in the PieChart, needed to calculate percentage for a pie in the tooltip that is displayed when hovering over a pie
    private TranslateTransition tt; // TranslateTransition used for animation when PieChart is first displayed

    public PieCharts(ArrayList<TreeMap<Integer, Number>> data) {

        super();
        totalValues = 0;
        pieChart = new PieChart();
        String pieChartTitle = "Average " + Model.getInstance().currentObjectIndicator.getLabelFromCode(Model.getInstance().currentIndicator);
        pieChart.setTitle(pieChartTitle);

        this.addData(data); // calling method that adds all the data to the PieChart
        this.getChildren().add(pieChart); // adding PieChart to pane
        for (final PieChart.Data dataInPie : pieChart.getData()) {
            dataInPie.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) { // adding tooltip that is displayed when user hovers over a pie. Display country, value and percentage of pie for the hovered pie
                    BigDecimal nodeValue = new BigDecimal(dataInPie.getPieValue());
                    DecimalFormat yValFormat = new DecimalFormat(".###"); // displaying only 3 decimals
                    Tooltip.install(dataInPie.getNode(), new Tooltip("Country: " + dataInPie.getName() + "\n" + pieChartTitle + ": " + yValFormat.format(nodeValue) + "\nPercentage: " + Math.round(((dataInPie.getPieValue() / totalValues) * 100)) + "%"));
                }
            });
        }

        Thread anim = new Thread(new Runnable() { // Thread for running animation when PieChart is first displayed
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
        anim.start(); // starting animation

        this.sceneProperty().addListener((obs, oldScene, newScene) -> { // stopping animation is new scene is chosen by user
            if (newScene == null) {
                anim.stop();
                if (tt != null) {
                    tt.stop();
                    System.out.println("Animation thread stopped");
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
    /**
     * Method for adding all the data given by the model to the PieChart
     * @param data ArrayList of TreeMap containing data of the economic indicator currently chosen, for every country chosen by user
     */
    private void addData(ArrayList<TreeMap<Integer, Number>> data) {
        pieChart.getData().clear(); // removing all data from PieChart
        pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < data.size(); i++) {
            double average = 0;
            for (Number value : data.get(i).values()) {
                average += value.doubleValue(); // summing all values of a country of the period chosen before calculating average
            }
            double valueToInsert = average / data.get(i).size(); // calculating average by taking the summed value divided by the amount of values
            totalValues += valueToInsert;
            pieChartData.add(new PieChart.Data(Model.getInstance().countries[Model.getInstance().currentCountries.get(i)].getName(), valueToInsert)); // adding data to PieChartData that is then added to the PieChart
        }
        pieChart.getData().addAll(pieChartData); 
    }
}
