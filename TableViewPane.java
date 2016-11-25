import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.util.HashMap;

public class TableViewPane extends StackPane {
    private TableView<Pair<String, Number>> table;

    public TableViewPane() {
        super();

        //Creates table title
        final Label tableTitle = new Label("Table Title");
        tableTitle.setFont(new Font("Arial", 20));

        //Initialises uneditable table
        table = new TableView<>();
        table.setEditable(false);

        //Initialises table columns and adds them to the table
        TableColumn<Pair<String, Number>, String> tbcolDate = new TableColumn<>("X");
        TableColumn<Pair<String, Number>, Number> tbcolValue = new TableColumn<>("Y");
        table.getColumns().addAll(tbcolDate, tbcolValue);

        //Defines sample data
        ObservableList<Pair<String, Number>> data =
                FXCollections.observableArrayList(
                        new Pair<String, Number>("2016", 120),
                        new Pair<String, Number>("2015", 100),
                        new Pair<String, Number>("2014", 95),
                        new Pair<String, Number>("2013", 89),
                        new Pair<String, Number>("2012", 101)
                );

        tbcolDate.setCellValueFactory(
                new PropertyValueFactory<Pair<String, Number>, String>("x"));

        tbcolValue.setCellValueFactory(
                new PropertyValueFactory<Pair<String, Number>, Number>("y"));

        table.setItems(data);

        //Adds table to container
        getChildren().add(table);
    }
}
