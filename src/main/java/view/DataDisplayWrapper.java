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
import javafx.scene.control.Spinner;
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

    /**
     * Used to hold the data for all necessary countries
     */
    private ArrayList<TreeMap<Integer, Number>> data = new ArrayList<>();
    /**
     * Reference for the main pane, holding everything
     */
    private BorderPane mainPane;
    /**
     * Reference to the scroll pane keeping all tables (when there are multiple countries)
     */
    private ScrollPane spCountryTables;
    /**
     * Reference to current countries in the wrapper
     */
    private ArrayList<Integer> inCountries = new ArrayList<>();
    /**
     * Reference to the Thread used to load the data from the internet/cache
     */
    private Thread displayingDataThread;
    /**
     * Reference to the Bar Chart Pane (first one shown)
     */
    private BarChartPane bp;
    /**
     * Reference to the Vbox keeping the multiple countries tables
     */
    private VBox vbCountryTables;
    /**
     * Reference to the loading spinner
     */
    private ProgressIndicator pi;

    /**
     *Constructor for the wrapper. It will automatically load a bar chart pane, having other possible view types accessible from this pane's topbar
     */
    DataDisplayWrapper() {
        super();
        //get model instance
        Model.getInstance();
        //init inCountries
        inCountries = new ArrayList<>(Model.currentCountries);

        //init loading spinner
        pi = new ProgressIndicator(-1);
        pi.setStyle("-fx-progress-color: #F55028");
        pi.setMaxWidth(100);
        pi.setMaxHeight(100);

        //sets the title
        this.setTitle(Model.currentObjectIndicator.getLabelFromCode(Model.currentIndicator));
        mainPane = new BorderPane();
        Scene scene = new Scene(mainPane, 800, 500);

        //load css
        scene.getStylesheets().add(this.getClass().getClassLoader()
                .getResource("extra.css").toExternalForm());

        //Creates scroll pane for all country tables
        vbCountryTables = new VBox(10);
        spCountryTables = new ScrollPane(vbCountryTables);
        spCountryTables.setFitToWidth(true);

        //init the main pane (bar chart pane)
        bp = new BarChartPane(data);
        this.setCenterPane(bp);
        //starts the thread
        startThread();
        //sets the top bar
        mainPane.setTop(generateTopBar());
        this.setScene(scene);

        //Prevents resizing stage to smaller than initial size
        setMinHeight(500);
        setMinWidth(800);
    }

    /**
     * Method used to start/restart the loading thread with the current values in the Model.
     */
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

    //factory method for a new task
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

            //when suceeded it will pass the data to bar chart pane (main one)
            @Override
            protected void succeeded() {
                super.succeeded();
                bp.passData(getValue());
                setCenterPane(bp);
                //Create and add a table for each country in the data
                vbCountryTables.getChildren().clear();
                for (int i = 0; i < data.size(); ++i) {
                    vbCountryTables.getChildren().add(new TableViewPane(data.get(i),
                            Model.countries[Model.currentCountries.get(i)].getName()));
                }
            }
        };
    }

    //method used to populate the data inside given some other data input
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

    //sets central pane
    private void setCenterPane(Node node) {
        mainPane.setCenter(node);
    }

    /**
     * Method used to clear the data inside the wrapper
     */
    void clearData() {
        data.clear();
    }

    private HBox generateTopBar() {
        //placeholder
        HBox toReturn = new HBox();
        toReturn.setStyle(" -fx-pref-height: 40px; -fx-background-color: #F55028;");

        //chart button
        Button chartButton = new Button("Bar Chart");
        chartButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        chartButton.setPadding(new Insets(10));
        //table button
        Button tableButton = new Button("Table");
        tableButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        tableButton.setPadding(new Insets(10));
        //line chart button
        Button lineButton = new Button("Line Chart");
        lineButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
        lineButton.setPadding(new Insets(10));

        //start spinner
        Spinner<Integer> startSpinner = new Spinner<>(1960, Model.currentYear - 1 , Integer.parseInt(Model.timeRanges.get(Model.currentIndicator).getStartYear()),1);
        startSpinner.setPadding(new Insets(10));
        startSpinner.setStyle("-fx-background-color: transparent;");

        //end spinner
        Spinner<Integer> endSpinner = new Spinner(1960, Model.currentYear - 1, Integer.parseInt(Model.timeRanges.get(Model.currentIndicator).getEndYear())-1,1);
        endSpinner.setPadding(new Insets(10));
        endSpinner.setStyle("-fx-background-color: transparent;");

        //listener to start spinner
        startSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            Model.timeRanges.get(Model.currentIndicator).setStartYear(Integer.toString((int) startSpinner.getValue()));
            startThread();

        });

        //listener to end spinner
        endSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            Model.timeRanges.get(Model.currentIndicator).setEndYear(Integer.toString(endSpinner.getValue()));
            startThread();
        });


        toReturn.getChildren().add(chartButton);
        toReturn.getChildren().add(lineButton);
        toReturn.getChildren().add(tableButton);


        //pie chart button if there are multiple countries
        if (Model.currentCountries.size() > 1) {
            Button pieButton = new Button("Pie Chart");
            pieButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-size: 16px");
            pieButton.setPadding(new Insets(10));
            toReturn.getChildren().add(pieButton);
            pieButton.setOnMouseClicked(e -> this.setCenterPane(new PieCharts(data)));
        }


        toReturn.getChildren().add(startSpinner);
        toReturn.getChildren().add(endSpinner);

        //Display tables in center of scene
        tableButton.setOnMouseClicked(e -> this.setCenterPane(spCountryTables));

        lineButton.setOnMouseClicked(e -> this.setCenterPane(new LineCharts(data)));

        chartButton.setOnMouseClicked(e -> this.setCenterPane(new BarChartPane(data)));

        return toReturn;
    }

    /**
     * Method used to get the number of countries currently in the wrapper.
     * @return no of countries in the wrapper
     */
    ArrayList<Integer> getInCountries() {
        return inCountries;
    }

}
