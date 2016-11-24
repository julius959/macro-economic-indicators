import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;

public class BarChartPane extends Pane {
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


    }
}