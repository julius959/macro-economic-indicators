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

public class TableViewPane extends StackPane {
    private TableView<TableModelData> table;
    private ObservableList<TableModelData> data = FXCollections.observableArrayList();

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

        //Initialises table columns and adds them to the table
        TableColumn<TableModelData, String> tbcolDate = new TableColumn<>("Year");
        TableColumn<TableModelData, Number> tbcolValue = new TableColumn<>(Model.getInstance().currentObjectIndicator.getLabelFromCode(Model.getInstance().currentIndicator)+" "+Model.getInstance().currentObjectIndicator.getUnit());
        tbcolDate.setStyle("-fx-alignment: CENTER;");
        tbcolValue.setStyle("-fx-alignment: CENTER;");
        table.getColumns().addAll(tbcolDate, tbcolValue);

        passData(dataIn);

        //Link data with data model
        tbcolDate.setCellValueFactory(
                new PropertyValueFactory<TableModelData, String>("x"));

        tbcolValue.setCellValueFactory(
                new PropertyValueFactory<TableModelData, Number>("y"));

        table.setItems(data);

        final VBox vbRoot = new VBox();
        vbRoot.setSpacing(5);
        vbRoot.setPadding(new Insets(10, 0, 0, 10));
        vbRoot.getChildren().addAll(hbTableTitle, table);

        //Adds table to container
        getChildren().add(vbRoot);
    }

     //Data Model for the Table
     public static class TableModelData {
        //x and y values for each data entry
        private final SimpleStringProperty x, y;

        private TableModelData(Integer xIn, Number yIn) {
            x = new SimpleStringProperty(String.valueOf(xIn));
            y = new SimpleStringProperty(String.valueOf(yIn));
        }

        public String getX() { return x.get(); }

        public void setX(String x) { this.x.set(x); }

        public String getY() { return y.get(); }

        public void setY(String y) { this.y.set(y); }
    }

    private void passData(TreeMap<Integer, Number> dataIn) {
        data.clear();
        //Extract the dates from the data
        ArrayList<Integer> alstKeys = new ArrayList<>(dataIn.keySet());
        //Collections.reverse(alstKeys);

        DecimalFormat yValFormat = new DecimalFormat(".###");

        //Add each data entry to the table
        for (Integer sKey : alstKeys)
            data.add(new TableModelData(sKey, Double.valueOf(yValFormat.format(dataIn.get(sKey)))));
    }
}
