import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class BarChartPane extends StackPane {
    //Bar Chart object of the Pane
    private BarChart<String, Number> barChart;

    public BarChartPane() {
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

        //Creates a series of data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Country");
        series1.getData().add(new XYChart.Data<>("2012", 130));
        series1.getData().add(new XYChart.Data<>("2013", 120));
        series1.getData().add(new XYChart.Data<>("2014", 100));
        series1.getData().add(new XYChart.Data<>("2015", 115));
        series1.getData().add(new XYChart.Data<>("2016", 130));

        barChart.getData().add(series1);

        getChildren().add(barChart);
    }
}