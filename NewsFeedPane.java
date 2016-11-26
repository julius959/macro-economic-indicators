import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class NewsFeedPane extends BorderPane {

    public NewsFeedPane() {
        super();

        //Add title to news feed
        Label lblTitle = new Label("Latest news from the BBC");
        HBox hbTitle = new HBox(lblTitle);
        hbTitle.setAlignment(Pos.CENTER);
    }
}
