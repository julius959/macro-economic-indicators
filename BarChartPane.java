import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;

public class BarChartPane extends Pane {
    private BarChart<String, Number> barChart;

    public BarChartPane() {
        super();

        CategoryAxis dateAxis = new CategoryAxis();
        NumberAxis valueAxis = new NumberAxis();

        barChart = new BarChart<>(dateAxis, valueAxis);
    }
}
