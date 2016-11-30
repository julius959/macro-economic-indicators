package news_feed;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import news_feed.NewsArticle;
import news_feed.NewsFeed;

import java.util.ArrayList;

public class NewsFeedPane extends BorderPane {

    public NewsFeedPane(NewsFeedTest app) {
        super();

        //Add title to news feed
        Label lblTitle = new Label("Latest news from the BBC");
        HBox hbTitle = new HBox(lblTitle);
        hbTitle.setAlignment(Pos.CENTER);
        setTop(hbTitle);

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
        spArticles.setFitToHeight(true);
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

    }
}
