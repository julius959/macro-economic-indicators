import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class TableViewPane extends StackPane {
    private TableView<TableModelData> table;

    public TableViewPane() {
        super();

        //Creates table title
        final Label tableTitle = new Label("Table Title");
        tableTitle.setFont(new Font("Arial", 20));

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
        ObservableList<TableModelData> data =
                FXCollections.observableArrayList(
                        new TableModelData("2016", 120),
                        new TableModelData("2015", 100),
                        new TableModelData("2014", 95),
                        new TableModelData("2013", 89),
                        new TableModelData("2012", 101)
                );

        tbcolDate.setCellValueFactory(
                new PropertyValueFactory<TableModelData, String>("x"));

        tbcolValue.setCellValueFactory(
                new PropertyValueFactory<TableModelData, Number>("y"));

        table.setItems(data);

        //Adds table to container
        getChildren().add(table);
    }

     public static class TableModelData {
        private final SimpleStringProperty x;
        private final SimpleStringProperty y;

        private TableModelData(String xIn, Number yIn) {
            x = new SimpleStringProperty(xIn);
            y = new SimpleStringProperty(String.valueOf(yIn));
        }

        public String getX() { return x.get(); }

        public void setX(String x) { this.x.set(x); }

        public String getY() { return y.get(); }

        public void setY(String y) { this.y.set(y); }
    }
}
