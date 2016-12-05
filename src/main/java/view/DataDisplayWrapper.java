package view;

import api_model.Model;
import bar_chart.BarChartPane;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import table_view.TableViewPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by loopiezlol on 05/12/2016.
 */
public class DataDisplayWrapper extends Stage {

    ArrayList<HashMap<String, Number>> data = new ArrayList<>();
    BorderPane mainPane;

    public DataDisplayWrapper() {
        super();
//        this.data = inData.stream().map( => {
//                return new HashMap<String, Number>()
//        });

        mainPane = new BorderPane();
        Scene scene = new Scene(mainPane, 800, 500);
        this.setData(Model.getInstance().gatherData());
        mainPane.setTop(generateTopBar());
        this.setScene(scene);

    }


    public void setData(ArrayList<HashMap<String, Double>> inData) {
        data.clear();
        for (HashMap<String, Double> val : inData) {
            HashMap<String, Number> toAdd = new HashMap<>();
            for(Map.Entry<String,Double> entry : val.entrySet()) {
                toAdd.put(entry.getKey(), entry.getValue());
            }
            data.add(toAdd);
        }
        this.setCenterPane(new BarChartPane(data));
    }

    public void setCenterPane(Node node) {
        mainPane.setCenter(node);
    }

    public void clearData() {
        data.clear();
    }

    public HBox generateTopBar() {
        HBox toReturn = new HBox();
        toReturn.setStyle(" -fx-pref-height: 40px; -fx-background-color: #F55028;");
        Button chartButton = new Button("Bar chart");
        chartButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        chartButton.setPadding(new Insets(10));
        chartButton.setStyle("-fx-effect: dropshadow(gaussian, #000, 10, 0, 0,0);");

        toReturn.getChildren().add(chartButton);

        if (data.size() == 1) {
            Button tableButton = new Button("Table");
            tableButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
            tableButton.setPadding(new Insets(10));

            tableButton.setOnMouseClicked(e -> {
                this.setCenterPane(new TableViewPane(data.get(0)));
            });

            toReturn.getChildren().add(tableButton);

        }

        chartButton.setOnMouseClicked(e -> {
            this.setCenterPane(new BarChartPane(data));
            //chartButton.setStyle("-fx-effect: dropshadow(gaussian, #000, 10, 0, 0,0);");
            //chartButton.setEffect(new DropShadow("gaussian", "#000", 10, 0, 0, 0));
        });


        return toReturn;
    }
}
