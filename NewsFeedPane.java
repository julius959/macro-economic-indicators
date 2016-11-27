import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class NewsFeedPane extends BorderPane {

    public NewsFeedPane() {
        super();

        //Add title to news feed
        Label lblTitle = new Label("Latest news from the BBC");
        HBox hbTitle = new HBox(lblTitle);
        hbTitle.setAlignment(Pos.CENTER);
        setTop(hbTitle);

        //Hash map of news articles URL->Title
        HashMap<String, String> hmNews = new HashMap<>();

        //Retrieve news
        try {
            hmNews = NewsFeed.getNews();
        } catch(Exception e) {
            setCenter(new Label("Failed to retrieve News. " +
                    "Please ensure you have an active Internet connection"));
        }

        //Add ScrollPane for articles
        VBox vbArticles = new VBox();
        vbArticles.setAlignment(Pos.CENTER);
        vbArticles.setSpacing(10.0);
        ScrollPane spArticles = new ScrollPane(vbArticles);
        spArticles.setFitToHeight(true);
        spArticles.setFitToWidth(true);
        setCenter(spArticles);

        //Add each news article title to the scroll pane
        if (hmNews.size() > 0) {
            for (Map.Entry<String, String> entry : hmNews.entrySet()) {
                Label lblArticle = new Label(entry.getValue());
                lblArticle.setStyle("-fx-border-color: black");
                vbArticles.getChildren().add(lblArticle);
            }
        }

    }
}
