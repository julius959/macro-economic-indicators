import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class TableViewPane extends StackPane {
    private TableView table;

    public TableViewPane() {
        super();

        //Creates table title
        final Label tableTitle = new Label("Table Title");
        tableTitle.setFont(new Font("Arial", 20));

        //Initialises uneditable table
        table = new TableView();
        table.setEditable(false);

    }
}
