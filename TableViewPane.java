import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class TableViewPane extends StackPane {
    private TableView<String> table;

    public TableViewPane() {
        super();

        //Creates table title
        final Label tableTitle = new Label("Table Title");
        tableTitle.setFont(new Font("Arial", 20));

        //Initialises uneditable table
        table = new TableView<>();
        table.setEditable(false);

        //Initialises table columns and adds them to the table
        TableColumn<String, String> tbcolDate = new TableColumn<>("X");
        TableColumn<String, Number> tbcolValue = new TableColumn<>("Y");
        table.getColumns().addAll(tbcolDate, tbcolValue);

    }
}
