package view;

import api_model.Model;
import bar_chart.BarChartPane;
import charts.LineCharts;
import charts.PieCharts;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import table_view.TableViewPane;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by loopiezlol on 05/12/2016.
 */
public class DataDisplayWrapper extends Stage {

    ArrayList<TreeMap<Integer, Number>> data = new ArrayList<>();
    BorderPane mainPane;
    ScrollPane spCountryTables;
    ArrayList<Integer> inCountries = new ArrayList<>();
    Thread displayingDataThread;
    BarChartPane bp;

    VBox vbCountryTables;
    ProgressBr bar;

    public DataDisplayWrapper() {
        super();

        inCountries = new ArrayList<>(Model.currentCountries);

        bar = new ProgressBr();


        this.setTitle(Model.getInstance().currentObjectIndicator.getLabelFromCode(Model.getInstance().currentIndicator));
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


    public void startThread() {
        if (displayingDataThread != null) {
            displayingDataThread.stop();
        }
        inCountries = new ArrayList<>(Model.currentCountries);
        Task task = newTask();
        bar.activateProgressBar(task);
        bar.getDialogStage().show();

        displayingDataThread = new Thread(task);
        displayingDataThread.start();
    }

    private Task<ArrayList<TreeMap<Integer, Number>>> newTask() {
        return new Task<ArrayList<TreeMap<Integer, Number>>>() {

            @Override
            protected ArrayList<TreeMap<Integer, Number>> call() throws Exception {
                ArrayList<TreeMap<Integer, Number>> res = setData(Model.getInstance().gatherData());

                return res;
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
                bar.getDialogStage().close();
                //Create and add a table for each country in the data
                for (int i = 0; i < data.size(); ++i) {
                    vbCountryTables.getChildren().add(new TableViewPane(data.get(i),
                            Model.getInstance().countries[Model.getInstance().currentCountries.get(i)].getName()));
                }
            }
        };
    }

    public ArrayList setData(ArrayList<TreeMap<Integer, BigDecimal>> inData) {
        data.clear();
        for (TreeMap<Integer, BigDecimal> val : inData) {
            TreeMap<Integer, Number> toAdd = new TreeMap<>();
            for (Map.Entry<Integer, BigDecimal> entry : val.entrySet()) {
                toAdd.put(entry.getKey(), entry.getValue());
            }
            data.add(toAdd);
        }
        return data;
        // this.setCenterPane(new BarChartPane(data));
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

        Button chartButton = new Button("Bar Chart");
        chartButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        chartButton.setPadding(new Insets(10));
        //chartButton.setStyle("-fx-effect: dropshadow(gaussian, #000, 10, 0, 0,0);");

        Button tableButton = new Button("Table");
        tableButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        tableButton.setPadding(new Insets(10));

        Button lineButton = new Button("Line Chart");
        lineButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        lineButton.setPadding(new Insets(10));
        System.out.println(Model.timeRanges.get(Model.currentIndicator).getStartYear()+" "+Model.timeRanges.get(Model.currentIndicator).getEndYear());
        Spinner<Integer> startSpinner = new Spinner<>(1960, Model.getInstance().currentYear - 1 , Integer.parseInt(Model.timeRanges.get(Model.currentIndicator).getStartYear()),1);
        Spinner<Integer> endSpinner = new Spinner(1960, Model.getInstance().currentYear - 1, Integer.parseInt(Model.timeRanges.get(Model.currentIndicator).getEndYear())-1,1);
//        startSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1960,Model.getInstance().currentYear-1,Integer.parseInt(Model.timeRanges.get(Model.currentIndicator).getStartYear())));
//        endSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1960,Model.getInstance().currentYear-1,Integer.parseInt(Model.timeRanges.get(Model.currentIndicator).getEndYear())));


        startSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                Model.getInstance().timeRanges.get(Model.getInstance().currentIndicator).setStartYear(Integer.toString((int) startSpinner.getValue()));
                System.out.println("START YEAR "+Model.timeRanges.get(Model.currentIndicator).getStartYear());
                System.out.println(startSpinner.getValue());
                startThread();

            }
        });


        endSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                Model.getInstance().timeRanges.get(Model.getInstance().currentIndicator).setEndYear(Integer.toString((int) endSpinner.getValue()));
                 System.out.println("END YEAR "+Model.timeRanges.get(Model.currentIndicator).getEndYear());
                System.out.println(endSpinner.getValue());
                startThread();
            }
        });


        toReturn.getChildren().add(chartButton);
        toReturn.getChildren().add(lineButton);
        toReturn.getChildren().add(tableButton);



        if (Model.currentCountries.size() > 1) {
            Button pieButton = new Button("Pie Chart");
            pieButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
            pieButton.setPadding(new Insets(10));
            toReturn.getChildren().add(pieButton);
            pieButton.setOnMouseClicked(e -> {

                this.setCenterPane(new PieCharts(data));
            });
        }



        toReturn.getChildren().add(startSpinner);
        toReturn.getChildren().add(endSpinner);

        //Display tables in center of scene
        tableButton.setOnMouseClicked(e -> {
            this.setCenterPane(spCountryTables);
        });

        lineButton.setOnMouseClicked(e -> {
            this.setCenterPane(new LineCharts(data));
        });

        chartButton.setOnMouseClicked(e -> {
            this.setCenterPane(new BarChartPane(data));
        });


        return toReturn;
    }


    public ArrayList<Integer> getInCountries() {
        return inCountries;
    }

}
