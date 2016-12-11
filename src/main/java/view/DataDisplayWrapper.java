package view;

import api_model.Model;
import bar_chart.BarChartPane;
import charts.LineCharts;
import charts.PieCharts;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import table_view.TableViewPane;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

class DataDisplayWrapper extends Stage {

    private ArrayList<TreeMap<Integer, Number>> data = new ArrayList<>();
    private BorderPane mainPane;
    private ScrollPane spCountryTables;
    private ArrayList<Integer> inCountries = new ArrayList<>();
    private Thread displayingDataThread;
    private BarChartPane bp;
    private VBox vbCountryTables;
    private ProgressIndicator pi;

    DataDisplayWrapper() {
        super();
        Model.getInstance();
        inCountries = new ArrayList<>(Model.currentCountries);

        pi = new ProgressIndicator(-1);
        pi.setMaxWidth(100);
        pi.setMaxHeight(100);


        this.setTitle(Model.currentObjectIndicator.getLabelFromCode(Model.currentIndicator));
        mainPane = new BorderPane();
        Scene scene = new Scene(mainPane, 800, 500);

        //Creates scroll pane for all country tables
        vbCountryTables = new VBox(10);
        spCountryTables = new ScrollPane(vbCountryTables);
        spCountryTables.setFitToWidth(true);


        bp = new BarChartPane(data);
        this.setCenterPane(bp);

        startThread();
        mainPane.setTop(generateTopBar());
        this.setScene(scene);

        //Prevents resizing stage to smaller than initial size
        setMinHeight(500);
        setMinWidth(800);
    }

    void startThread() {
        if (displayingDataThread != null) {
            displayingDataThread.stop();
        }
        inCountries = new ArrayList<>(Model.currentCountries);
        Task task = newTask();
        pi.progressProperty().bind(task.progressProperty());
        this.setCenterPane(pi);

        displayingDataThread = new Thread(task);
        displayingDataThread.start();
    }

    private Task<ArrayList> newTask() {
        return new Task<ArrayList>() {

            @Override
            protected ArrayList call() throws Exception {
                return setData(Model.getInstance().gatherData());
            }

            @Override
            protected void running() {
                super.running();
                {

                }
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println(data.size());
                bp.passData(getValue());
                setCenterPane(bp);
                //Create and add a table for each country in the data
                for (int i = 0; i < data.size(); ++i) {
                    vbCountryTables.getChildren().add(new TableViewPane(data.get(i),
                            Model.countries[Model.currentCountries.get(i)].getName()));
                }
            }
        };
    }

    private ArrayList setData(ArrayList<TreeMap<Integer, BigDecimal>> inData) {
        data.clear();
        for (TreeMap<Integer, BigDecimal> val : inData) {
            TreeMap<Integer, Number> toAdd = new TreeMap<>();
            for (Map.Entry<Integer, BigDecimal> entry : val.entrySet()) {
                toAdd.put(entry.getKey(), entry.getValue());
            }
            data.add(toAdd);
        }
        return data;
    }

    private void setCenterPane(Node node) {
        mainPane.setCenter(node);
    }

    void clearData() {
        data.clear();
    }

    private HBox generateTopBar() {
        HBox toReturn = new HBox();

        toReturn.setStyle(" -fx-pref-height: 40px; -fx-background-color: #F55028;");

        Button chartButton = new Button("Bar charts");
        chartButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        chartButton.setPadding(new Insets(10));

        Button tableButton = new Button("Table");
        tableButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        tableButton.setPadding(new Insets(10));

        Button lineButton = new Button("Line Chart");
        lineButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        lineButton.setPadding(new Insets(10));

        toReturn.getChildren().add(chartButton);
        toReturn.getChildren().add(lineButton);
        toReturn.getChildren().add(tableButton);

        if (Model.currentCountries.size() > 1) {
            Button pieButton = new Button("Pie Chart");
            pieButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
            pieButton.setPadding(new Insets(10));
            toReturn.getChildren().add(pieButton);
            pieButton.setOnMouseClicked(e -> this.setCenterPane(new PieCharts(data)));
        }

        //Display tables in center of scene
        tableButton.setOnMouseClicked(e -> this.setCenterPane(spCountryTables));

        lineButton.setOnMouseClicked(e -> this.setCenterPane(new LineCharts(data)));

        chartButton.setOnMouseClicked(e -> this.setCenterPane(new BarChartPane(data)));

        return toReturn;
    }

    ArrayList<Integer> getInCountries() {
        return inCountries;
    }

}
