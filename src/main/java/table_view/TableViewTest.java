package table_view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.TreeMap;

public class TableViewTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Table View Test");

        TreeMap<Integer, Number> hm1 = new TreeMap<>();
        hm1.put(2016, 130);
        hm1.put(2015, 115);
        hm1.put(2014, 100);
        hm1.put(2013, 120);
        hm1.put(2012, 130);

        Scene primaryScene = new Scene(new TableViewPane(hm1));
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
