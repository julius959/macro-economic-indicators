package news_feed;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import view.Main;

public class NewsArticlePane extends BorderPane {

    public NewsArticlePane(String titleIn, String linkIn, String descriptionIn, String publishDateIn, String imgURLIn, Main app) {
        super();

        //Temporary styling
        setStyle("-fx-border-color: black");

        //Creates labels containing title, description and publish date
        Label lblTitle = new Label(titleIn);
        Label lblDescription = new Label(descriptionIn);
        Label lblDate = new Label(publishDateIn);

        //Centre title and date labels
        HBox hbTitle = new HBox(lblTitle);
        hbTitle.setAlignment(Pos.CENTER);
        HBox hbDate = new HBox(lblDate);
        hbDate.setAlignment(Pos.CENTER);

        //Add labels to container
        setTop(hbTitle);
        setCenter(lblDescription);
        setBottom(hbDate);

        //Retrieves news articles image from url
        /*ImageView imageView = ImageViewBuilder.create()
                .image(new Image(imgURLIn))
                .build();*/

        //Resizes the image to have width of 100 while preserving the ratio and using
        // higher quality filtering method; this ImageView is also cached to
        // improve performance
        /*imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        //Adds the articles image to the news entry
        setLeft(imageView);*/

        //Action Listener for clicking on an article
        //Opens up article in web view
        setOnMouseClicked(event -> {
            //Creates web view for article
            app.showLink(linkIn);
        });
    }
}
