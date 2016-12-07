package view;

import api_model.Model;
import bar_chart.BarChartPane;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import table_view.TableViewPane;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by loopiezlol on 05/12/2016.
 */
public class DataDisplayWrapper extends Stage {

    ArrayList<TreeMap<Integer, Number>> data = new ArrayList<>();
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


    public void setData(ArrayList<TreeMap<Integer, BigDecimal>> inData) {
        data.clear();
        for (TreeMap<Integer, BigDecimal> val : inData) {
            TreeMap<Integer, Number> toAdd = new TreeMap<>();
            for(Map.Entry<Integer,BigDecimal> entry : val.entrySet()) {
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
        //chartButton.setStyle("-fx-effect: dropshadow(gaussian, #000, 10, 0, 0,0);");

        toReturn.getChildren().add(chartButton);

        Button tableButton = new Button("Table");
        tableButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        tableButton.setPadding(new Insets(10));

        //Creates scroll pane for all country tables
        VBox vbCountryTables = new VBox(10);
        ScrollPane spCountryTables = new ScrollPane(vbCountryTables);
        spCountryTables.setFitToWidth(true);

        //Create and add a table for each country in the data
        for (int i = 0; i < data.size(); ++i) {
            vbCountryTables.getChildren().add(new TableViewPane(data.get(i),
                    Model.getInstance().countries[Model.getInstance().currentCountries.get(i)].getName()));
        }

        //Display tables in center of scene
        tableButton.setOnMouseClicked(e -> {
            this.setCenterPane(spCountryTables);
        });

        toReturn.getChildren().add(tableButton);

        chartButton.setOnMouseClicked(e -> {
            this.setCenterPane(new BarChartPane(data));
            //chartButton.setStyle("-fx-effect: dropshadow(gaussian, #000, 10, 0, 0,0);");
            //chartButton.setEffect(new DropShadow("gaussian", "#000", 10, 0, 0, 0));
        });


        return toReturn;
    }
}
