package news_feed;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import news_feed.NewsFeedPane;

public class NewsFeedTest extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Economy News from the BBC");

        Scene primaryScene = new Scene(new NewsFeedPane(this), 640, 480);
        primaryStage.setScene(primaryScene);

        primaryStage.show();

    }

    public void showLink(String url) {
        getHostServices().showDocument(url);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
