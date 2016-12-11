package news_feed;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.Main;

import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class NewsFeedPane extends ScrollPane {

    public NewsFeedPane(Main app) {
        super();

        //List of news article objects
        ArrayList<NewsArticle> alstNews = new ArrayList<>();

        //Add ScrollPane for articles
        VBox vbArticles = new VBox();
        vbArticles.setAlignment(Pos.CENTER);
        vbArticles.setSpacing(10.0);

        setContent(vbArticles);

        //Retrieve news
        try {
            alstNews = NewsFeed.getNews();
        } catch (Exception e) {
            vbArticles.getChildren().add(new Label("Failed to retrieve News. " +
                    "Please ensure you have an active Internet connection"));
        }

        setId("articles-wrapper");
        setFitToWidth(true);

        setPrefViewportHeight(300);

        //Creates web view only once - reduce memory usage
        //Avoids creating a new one every time an article is opened

        //Add each news article title to the scroll pane
        if (alstNews.size() > 0) {
            for (NewsArticle na : alstNews) {
                vbArticles.getChildren().add(new NewsArticlePane(na, app));
            }
        }

        this.applyCss();
    }

    }
