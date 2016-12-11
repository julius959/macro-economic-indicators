package news_feed;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.Main;

/**
 * Class that represents the visual representation - as a Pane - for a NewsArticle instance
 */
public class NewsArticlePane extends BorderPane {

    /**
     * Constructor for a NewsArticlePane
     * @param article The NewsArticle instance that this pane is representing
     * @param app The reference to the JavaFX application - needed for opening link in web browser
     */
    public NewsArticlePane(NewsArticle article, Main app) {
        //Call parent constructor of BorderPane
        super();

        this.setId("article-wrapper");

        //Creates and styles label containing title
        Label lblTitle = new Label(article.getTitle());
        lblTitle.setId("article-title");
        lblTitle.setStyle("-fx-effect: dropshadow(gaussian, #000, 5, 0, 0,0);");
        lblTitle.setUnderline(true);

        //Creates and styles label containing description
        Label lblDescription = new Label(article.getDescription());
        lblDescription.setId("article-description");
        lblDescription.setWrapText(true);

        //Creates and styles label containing date
        Label lblDate = new Label(article.getPublishDate());
        lblDate.setId("article-date");

        //Adds title, description and date labels to container
        VBox vbArticle = new VBox();
        vbArticle.setId("article-info");
        vbArticle.getChildren().addAll(lblTitle, lblDescription, lblDate);
        //Add container of labels to centre of pane
        setCenter(vbArticle);

        //Retrieves news articles image from url, resizes image to reduce memory usage
        //Stores retrieved image in an image view
        ImageView imageView = new ImageView(new Image(article.getImgURL(), 140, 140, true, true, true));
        imageView.setCache(true);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, #000, 10, 0, 0,0);");
        imageView.setId("article-image");
        //Adds image to the left slot in border pane
        setLeft(imageView);

        //Apply CSS styling to pane
        this.applyCss();

        //Action Listener for clicking on an article
        //Opens up article in the user's default browser
        setOnMouseClicked(event -> {
            //Displays article in default browser
            app.showLink(article.getLink());
        });
    }
}
