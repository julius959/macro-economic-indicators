import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.scene.chart.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jacobklerfelt on 2016-11-25.
 */
public class PieCharts extends StackPane {


    private PieChart pieChart;

    public PieCharts(ArrayList<HashMap<String, Integer>> data){

        super();
        pieChart = new PieChart();
        pieChart.setTitle("GDP");
        this.passData(data);
        this.getChildren().add(pieChart);
    }

    private void passData(ArrayList<HashMap<String, Integer>> data){

        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for(HashMap<String, Integer> temp : data){
            int average = 0;
            for(int value : temp.values()){
                average += value;
            }
            pieChartData.add(new javafx.scene.chart.PieChart.Data("Country", average/temp.size()));
        }
        pieChart.getData().addAll(pieChartData);
    }

}