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

import java.util.ArrayList;
import java.util.HashMap;

public class TableViewPane extends StackPane {
    private TableView<TableModelData> table;
    private ObservableList<TableModelData> data = FXCollections.observableArrayList();

    public TableViewPane(/*ArrayList<HashMap<String, Number>> dataIn*/) {
        super();

        //Creates table title
        final Label lblTableTitle = new Label("Table Title");
        lblTableTitle.setFont(new Font("Arial", 20));

        HBox hbTableTitle = new HBox(lblTableTitle);
        hbTableTitle.setAlignment(Pos.CENTER);

        //Initialises uneditable table
        table = new TableView<>();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Initialises table columns and adds them to the table
        TableColumn<TableModelData, String> tbcolDate = new TableColumn<>("X");
        TableColumn<TableModelData, Number> tbcolValue = new TableColumn<>("Y");
        tbcolDate.setStyle("-fx-alignment: CENTER;");
        tbcolValue.setStyle("-fx-alignment: CENTER;");
        table.getColumns().addAll(tbcolDate, tbcolValue);

        //Defines sample data
      /*  data =
                FXCollections.observableArrayList(
                        new TableModelData("2016", 120),
                        new TableModelData("2015", 100),
                        new TableModelData("2014", 95),
                        new TableModelData("2013", 89),
                        new TableModelData("2012", 101)
                );*/

        data.add(new TableModelData("2016", 120));

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

        private TableModelData(String xIn, Number yIn) {
            x = new SimpleStringProperty(xIn);
            y = new SimpleStringProperty(String.valueOf(yIn));
        }

        public String getX() { return x.get(); }

        public void setX(String x) { this.x.set(x); }

        public String getY() { return y.get(); }

        public void setY(String y) { this.y.set(y); }
    }

    /*private void passData(ArrayList<HashMap<String, Number>> dataIn) {
        //Loop over data for every country
        for (HashMap<String, Number> hm : data) {
            //Create new series of data for each country
            XYChart.Series<String, Number> tempSeries = new XYChart.Series<>();
            //Get the date values
            ArrayList<String> alstKeys = new ArrayList<>(hm.keySet());
            Collections.reverse(alstKeys);

            //Add each data instance to the series of data
            for (String sKey : alstKeys)
                tempSeries.getData().add(new XYChart.Data<>(sKey, hm.get(sKey)));

            //Add the countries data to the chart
            barChart.getData().add(tempSeries);
        }
    }*/
}
