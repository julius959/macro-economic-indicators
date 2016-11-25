import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class TableViewPane extends StackPane {
    private TableView table;

    public TableViewPane() {
        super();

        final Label tableTitle = new Label("Table Title");
        tableTitle.setFont(new Font("Arial", 20));

        table.setEditable(false);

    }
}
