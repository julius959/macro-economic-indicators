package view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


/**
 * Creates the about pane
 * Created by Vlad Niculescu on 12/12/2016.
 */
public class AboutPane extends ScrollPane {
    VBox content = new VBox();

    public AboutPane(Main app) {
        super();
        addContent();
    }

    private void addContent() {

        content.setAlignment(Pos.CENTER);
        setFitToWidth(true);

        StackPane aboutPane = new StackPane();
        aboutPane.setStyle("-fx-background-color: rgba(65,68,75);");

        TextArea aboutlabel = new TextArea();

        try {
            initialize(aboutlabel, getClass().getClassLoader().getResource("about.txt").getPath());
        } catch (Exception e) {
        }
        aboutlabel.setStyle("-fx-background-color: rgba(65,68,75);" +
                "-fx-text-fill: white;" +
                "fx-font-size:15");
        aboutPane.getChildren().add(aboutlabel);


        TitledPane aboutTitle = new TitledPane("About", aboutPane);
        aboutTitle.setAlignment(Pos.CENTER);
        aboutTitle.setStyle("-fx-text-fill: #ffffff;" +
                "-fx-padding:20,0,0,0;" +
                "-fx-font-size: 17;");

        content.getChildren().add(aboutTitle);
        aboutTitle.setExpanded(false);







        StackPane pane = new StackPane();
       // pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color: rgba(65,68,75)");
        Label teamlabel = new Label();
        pane.getChildren().add(teamlabel);

        //teamlabel.setEditable(false);
        teamlabel.setText("Khalil Ahmed\nVlad Niculescu\nCătălin Buruiană\nJacob Klerfelt\nJihwan Sang");



        TitledPane teamTitle = new TitledPane("Team", pane);


        teamTitle.setAlignment(Pos.CENTER);
        teamTitle.setStyle("-fx-text-fill: #ffffff;" +
                " -fx-padding:20,0,0,0;" +
                "-fx-font-size: 17;" +
                "-fx-color: rgba(65,68,75)");
        teamlabel.setStyle("-fx-text-fill: #ffffff;" +
                "-fx-font-size: 15;" +
                "-fx-text-alignment: center; " +
                "-fx-background-color: rgba(65,68,75);");
        content.getChildren().add(teamTitle);
        teamTitle.setExpanded(false);


        TextArea jUnit = new TextArea();
        jUnit.setEditable(false);
        try {
            initialize(jUnit, getClass().getClassLoader().getResource("licenses/junitLicense.txt").getPath());
        } catch (Exception e) {
        }

        TitledPane junitPane = new TitledPane("JUnit", jUnit);
        jUnit.setWrapText(true);
        jUnit.setStyle("-fx-text-fill: #ffffff;" +
                "-fx-padding:20,0,0,0;" +
                "-fx-font-size: 15");

        TextArea hamcrest = new TextArea();
        hamcrest.setEditable(false);
        try {
            initialize(hamcrest, getClass().getClassLoader().getResource("licenses/hamcrestLicense.txt").getPath());
        } catch (Exception e) {
        }

        TitledPane hamcrestPane = new TitledPane("Hamcrest", hamcrest);
        hamcrest.setWrapText(true);
        hamcrest.setStyle("-fx-text-fill: #ffffff;" +
                "   -fx-padding:20,0,0,0;" +
                "-fx-font-size: 15");

        TextArea jdom = new TextArea();
        hamcrest.setEditable(false);
        try {
            initialize(jdom, getClass().getClassLoader().getResource("licenses/jdomLicense.txt").getPath());
        } catch (Exception e) {
        }


        TitledPane jdomPane = new TitledPane("JDOM", jdom);
        jdom.setWrapText(true);
        jdom.setStyle("-fx-text-fill: #ffffff;" +
                "-fx-padding:20,0,0,0;" +
                "-fx-font-size: 15");


        TextArea json = new TextArea();
        json.setEditable(false);
        TitledPane jsonPane = new TitledPane("JSON", json);
        jsonPane.setExpanded(false);
        jsonPane.setCollapsible(false);

        TextArea sqllite = new TextArea();
        sqllite.setEditable(false);
        TitledPane SQLLitePane = new TitledPane("SQLLite", sqllite);
        SQLLitePane.setExpanded(false);
        SQLLitePane.setCollapsible(false);

        TextArea fixerio = new TextArea();
        sqllite.setEditable(false);
        TitledPane fixerioPane = new TitledPane("Fixer.io", fixerio);
        fixerioPane.setExpanded(false);
        fixerioPane.setCollapsible(false);


        TextArea flag = new TextArea();
        flag.setEditable(false);
        TitledPane flagPane = new TitledPane("Flagpedia", flag);
        flagPane.setExpanded(false);
        flagPane.setCollapsible(false);



        Accordion acordion = new Accordion();

        acordion.getPanes().addAll(jdomPane, junitPane, hamcrestPane, jsonPane, SQLLitePane,fixerioPane,flagPane);
        acordion.setStyle("-fx-background-color: rgba(65,68,75)");



        TitledPane licenseTitle = new TitledPane("Licenses", acordion);
        licenseTitle.setAlignment(Pos.CENTER);
        licenseTitle.setStyle("-fx-text-fill: #FFFFFF;" +
                "   -fx-padding:20,0,0,0;" +
                "-fx-font-size: 17");
        licenseTitle.setExpanded(false);




        content.getChildren().add(licenseTitle);



        setContent(content);
    }

    // Retrieves the text from the txt file.
    private void initialize(TextArea textlabel, String path) {
        try {
            Scanner s = new Scanner(new File(path)).useDelimiter("\\s+");
            while (s.hasNext()) {
                if (s.hasNextInt()) {
                    textlabel.appendText(s.nextInt() + " ");
                } else {
                    textlabel.appendText(s.next() + " ");
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}
