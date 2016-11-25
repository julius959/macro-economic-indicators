import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import java.util.HashMap;

public class LineCharts extends StackPane {

    private javafx.scene.chart.LineChart<String,Number> lineChart;

    public LineCharts(ArrayList<HashMap<String, Integer>> data) {

        super();

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        lineChart = new javafx.scene.chart.LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle("GDP");
        this.passData(data);
        this.getChildren().add(lineChart);
    }

    private void passData(ArrayList <HashMap<String,Integer>> data){

        //populating the series with data
        int i = 0;
        for (HashMap<String, Integer> temp : data) {
            XYChart.Series series = new XYChart.Series();
            series.setName("Country" + Integer.toString(i++));
            for (String date : temp.keySet()) {
                series.getData().add(new XYChart.Data(date, temp.get(date)));
            }
            lineChart.getData().add(series);
        }
    }
}
