package bar_chart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class BarChartTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bar Chart Test");

        TreeMap<Integer, Number> hm1 = new TreeMap<>();
        hm1.put(2016, 130);
        hm1.put(2015, 115);
        hm1.put(2014, 100);
        hm1.put(2013, 120);
        hm1.put(2012, 130);

        TreeMap<Integer, Number> hm2 = new TreeMap<>();
        hm2.put(2016, 140);
        hm2.put(2015, 145);
        hm2.put(2014, 105);
        hm2.put(2013, 120);
        hm2.put(2012, 110);

        ArrayList<TreeMap<Integer, Number>> maps = new ArrayList<>();
        maps.add(hm1);
        maps.add(hm2);

        Scene primaryScene = new Scene(new BarChartPane(maps));
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
