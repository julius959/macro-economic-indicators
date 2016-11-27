import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.Date;

public class NewsArticlePane extends StackPane {

    public NewsArticlePane(String titleIn, String linkIn, String descriptionIn, Date publishDateIn) {
        super();

        Label lblTitle = new Label(titleIn);
        Label lblDescription = new Label(descriptionIn);
        Label lblDate = new Label(String.valueOf(publishDateIn));

        getChildren().addAll(lblTitle, lblDescription, lblDate);

        setAlignment(lblTitle, Pos.TOP_CENTER);
        setAlignment(lblDescription, Pos.BOTTOM_CENTER);
        setAlignment(lblDate, Pos.TOP_RIGHT);
    }
}
