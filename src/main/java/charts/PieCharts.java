import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.chart.PieChart;


/**
 * Created by jacobklerfelt on 2016-11-25.
 */
public class PieCharts extends StackPane {

    private PieChart pieChart;
    private ObservableList<PieChart.Data> pieChartData;

    public PieCharts(ArrayList<TreeMap<String, Integer>> data){

        super();
        pieChart = new PieChart();
        pieChart.setTitle(""); // waiting for info from Vlad about where to get this info - wrapper.
        this.addData(data);
        this.getChildren().add(pieChart);
        pieChart.setLabelsVisible(true);
        for (final PieChart.Data dataInPie : pieChart.getData()) {
            dataInPie.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Label caption = new Label("");
                    caption.setStyle("-fx-font: 35 arial;");
                    caption.setStyle("-fx-background-color: #FFFFFF");
                    caption.setTranslateX(event.getSceneX());
                    caption.setTranslateY(event.getSceneY());
                    caption.setText(String.valueOf(dataInPie.getName()) + " - " +  dataInPie.getPieValue() + "%");
                    getChildren().add(caption);

                }
            });
            dataInPie.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    getChildren().remove(getChildren().size()-1);
                }
            });
        }
    }

    private void addData(ArrayList<TreeMap<String, Integer>> data){

        pieChartData = FXCollections.observableArrayList();

        for(HashMap<String, Integer> temp : data){
            int average = 0;
            for(int value : temp.values()){
                average += value;
            }
            // get country from wrapper provided by Vlad - leaving for now
            pieChartData.add(new PieChart.Data("Country", average/temp.size()));
        }
        pieChart.getData().addAll(pieChartData);
    }
}