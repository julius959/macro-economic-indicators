package news_feed;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import view.Main;
import java.util.ArrayList;

/**
 * Class that visually represents the NewsFeed instance.
 * Contains a list of NewsArticlePane's - one for each News entry
 */
public class NewsFeedPane extends ScrollPane {

    /**
     * Constructor for a NewsFeedPane
     * @param app The reference to the JavaFX application - needed for opening link in web browser
     */
    public NewsFeedPane(Main app) {
        super();

        //List of news article objects
        ArrayList<NewsArticle> alstNews = new ArrayList<>();

        //Add ScrollPane for articles
        VBox vbArticles = new VBox();
        vbArticles.setAlignment(Pos.CENTER);
        vbArticles.setSpacing(10.0);

        //Sets vbox container as content for the scroll pane
        setContent(vbArticles);

        //Retrieve news
        try {
            alstNews = NewsFeed.getNews();
        } catch (Exception e) {
            //If there's issues connecting to the RSS feed
            vbArticles.getChildren().add(new Label("Failed to retrieve News. " +
                    "Please ensure you have an active Internet connection"));
        }

        //Set styling via CSS
        setId("articles-wrapper");
        this.applyCss();

        //Ensures news feed is resizable
        setFitToWidth(true);
        setPrefViewportHeight(300);

        //If any news articles exist
        if (alstNews.size() > 0) {
            //Add each news article title to the scroll pane
            for (NewsArticle na : alstNews) {
                vbArticles.getChildren().add(new NewsArticlePane(na, app));
            }
        }

    }

}
