package news_feed;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.Main;

public class NewsArticlePane extends BorderPane {

    public NewsArticlePane(String titleIn, String linkIn, String descriptionIn, String publishDateIn, String imgURLIn, Main app) {
        super();
        this.setId("article-wrapper");

        //Creates labels containing title, description and publish date
        Label lblTitle = new Label(titleIn);
        lblTitle.setId("article-title");
        lblTitle.setStyle("-fx-effect: dropshadow(gaussian, #000, 5, 0, 0,0);");
        lblTitle.setUnderline(true);
        Label lblDescription = new Label(descriptionIn);
        lblDescription.setId("article-description");
        lblDescription.setWrapText(true);
        Label lblDate = new Label(publishDateIn);
        lblDate.setId("article-date");

        //Centre title and date labels
//        HBox hbTitle = new HBox(lblTitle);
//        hbTitle.setAlignment(Pos.CENTER_LEFT);
//        HBox hbDate = new HBox(lblDate);
//        hbDate.setAlignment(Pos.CENTER_LEFT);

        //Add labels to container
        //setTop(hbTitle);
        VBox article = new VBox();
        article.setId("article-info");
        article.getChildren().addAll(lblTitle, lblDescription, lblDate);
        setCenter(article);
        //setBottom(hbDate);

        //Retrieves news articles image from url
        ImageView imageView = new ImageView(new Image(imgURLIn, 140, 140, true, true, true));
        imageView.setCache(true);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, #000, 10, 0, 0,0);");
        imageView.setId("article-image");

        setLeft(imageView);

        this.applyCss();

        //Action Listener for clicking on an article
        //Opens up article in web view
        setOnMouseClicked(event -> {
            //Creates web view for article
            app.showLink(linkIn);
        });
    }
}
