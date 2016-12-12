package table_view;

import api_model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Represents a Pane holding a visual Table for a country's data in a query
 */
public class TableViewPane extends StackPane {
    //TableView of TabelModelData (static inner class containing data of table)
    private TableView<TableModelData> table;
    //Observable list of TabelModelData
    private ObservableList<TableModelData> data = FXCollections.observableArrayList();

    /**
     * Constructor for a TableViewPane
     * @param dataIn TreeMap of query data for a single country, Year->Value
     * @param country The country who's data is being represented in this table
     */
    public TableViewPane(TreeMap<Integer, Number> dataIn, String country) {
        super();

        //Creates table title
        final Label lblTableTitle = new Label(country);
        lblTableTitle.setFont(new Font("Arial", 20));
        HBox hbTableTitle = new HBox(lblTableTitle);
        hbTableTitle.setAlignment(Pos.CENTER);

        //Initialises uneditable table
        table = new TableView<>();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setPadding(new Insets(0));


        //Initialises table columns and adds them to the table
        TableColumn<TableModelData, String> tbcolDate = new TableColumn<>("Year");
        TableColumn<TableModelData, Number> tbcolValue = new TableColumn<>(Model.getInstance().currentObjectIndicator.getLabelFromCode(Model.getInstance().currentIndicator)+" "+Model.getInstance().currentObjectIndicator.getSubIndicatorUnitFromCode(Model.currentIndicator));
        tbcolDate.setStyle("-fx-alignment: CENTER;");
        tbcolValue.setStyle("-fx-alignment: CENTER;");
        table.getColumns().addAll(tbcolDate, tbcolValue);

        //Sets observable array list as the data holder for this table
        table.setItems(data);

        //Adds query data from the TreeMap into the table
        passData(dataIn);

        //Link data with data model
        tbcolDate.setCellValueFactory(
                new PropertyValueFactory<TableModelData, String>("x"));

        tbcolValue.setCellValueFactory(
                new PropertyValueFactory<TableModelData, Number>("y"));

        //Adds table title and table view to container
        final VBox vbRoot = new VBox();
        vbRoot.setSpacing(5);
        vbRoot.setPadding(new Insets(10, 0, 0, 10));
        vbRoot.getChildren().addAll(hbTableTitle, table);

        //Adds container of title and table view to outer pane
        getChildren().add(vbRoot);
    }

    /**
     * Data Model for the table view
     */
    public static class TableModelData {
        //x and y values for each data entry
        private final SimpleStringProperty x, y;

        //Constructr for a single table entry
        private TableModelData(Integer xIn, Number yIn) {
            x = new SimpleStringProperty(String.valueOf(xIn));
            y = new SimpleStringProperty(String.valueOf(yIn));
        }

        /**
         * Retrieve this entries x value
         * @return x value as String
         */
        public String getX() { return x.get(); }

        /**
         * Set the x value of the table entry
         * @param x new X value of this table entry
         */
        public void setX(String x) { this.x.set(x); }

        /**
         * Retrieve this entries y value
         * @return y value as String
         */
        public String getY() { return y.get(); }

        /**
         * Set the y value of the table entry
         * @param y new Y value of this table entry
         */
        public void setY(String y) { this.y.set(y); }
    }

    //Adds query data from the TreeMap into the table
    private void passData(TreeMap<Integer, Number> dataIn) {
        data.clear();
        //Extract the dates from the data
        ArrayList<Integer> alstKeys = new ArrayList<>(dataIn.keySet());
        //Collections.reverse(alstKeys);

        //Store data to 3 decimal places
        DecimalFormat yValFormat = new DecimalFormat(".###");

        //Add each data entry to the table
        for (Integer sKey : alstKeys)
            data.add(new TableModelData(sKey, Double.valueOf(yValFormat.format(dataIn.get(sKey)))));
    }
}
