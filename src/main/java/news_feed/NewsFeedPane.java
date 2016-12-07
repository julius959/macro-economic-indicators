package news_feed;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.Main;

import java.util.ArrayList;

public class NewsFeedPane extends BorderPane {

    public NewsFeedPane(Main app) {
        super();

        //List of news article objects
        ArrayList<NewsArticle> alstNews = new ArrayList<>();

        //Retrieve news
        try {
            alstNews = NewsFeed.getNews();
        } catch(Exception e) {
            setCenter(new Label("Failed to retrieve News. " +
                    "Please ensure you have an active Internet connection"));
        }

        //Add ScrollPane for articles
        VBox vbArticles = new VBox();
        vbArticles.setAlignment(Pos.CENTER);
        vbArticles.setSpacing(10.0);
        ScrollPane spArticles = new ScrollPane(vbArticles);
        spArticles.setId("articles-wrapper");
        spArticles.setMaxHeight(400);
        spArticles.setFitToWidth(true);
        setCenter(spArticles);

        //Creates web view only once - reduce memory usage
        //Avoids creating a new one every time an article is opened

        //Add each news article title to the scroll pane
        if (alstNews.size() > 0) {
            for (NewsArticle na : alstNews) {
                vbArticles.getChildren().add(new NewsArticlePane(na.getTitle(), na.getLink(), na.getDescription(), na.getPublishDate(), na.getImgURL(), app));
            }
        }

        this.applyCss();

    }
}
