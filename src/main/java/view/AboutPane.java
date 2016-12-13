package view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Creates the about pane
 * Created by Vlad Niculescu on 12/12/2016.
 */
public class AboutPane extends ScrollPane {
    /**
     * VBox containing all the about information
     */
    VBox content = new VBox();

    /**
     * Constructor that creates the about pane
     * @param app
     */
    public AboutPane(Main app) {
        super();
        addContent();
    }

    /**
     * Method that adds the whole content into the view.
     */
    private void addContent() {

        this.getStylesheets().add(this.getClass().getClassLoader()
                .getResource("styling.css").toExternalForm());

        content.setAlignment(Pos.CENTER);
        setFitToWidth(true);

        StackPane aboutPane = new StackPane();
        aboutPane.setStyle("-fx-background-color: rgba(65,68,75);");

        //Sorry about this guys :/
        String sAbout = "This is a standalone application designed to support A Level Economic students" +
                " with access to visualisations of macro-economic data. \n" +
                "All the indicators present in this application have been cherry-picked from the AQA A Level syllabus." +
                " Thus providing students with comprehensive, thorough information about the current and historic " +
                "macro-economic state of many relevant economies.\n" +
                "\n" +
                "Key features:\n" +
                "- Compare up to 43 countries across 21 different indicators\n" +
                "- Visualises macro-economic data using: Bar Charts, Line Charts, Tabular data and Pie Charts\n" +
                "- Compare and query multiple macro-economic data at the same time - side by side\n" +
                "- Keep up-to-date with the latest Economic news from the BBC without leaving the app\n" +
                "- Keep up-to-date with the current Exchange Rates for 31 different currencies\n";

        //Creates a label with about text
        Label label = new Label(sAbout);
        //Centres text, word wraps text and styles background
        label.setAlignment(Pos.CENTER);
        label.setWrapText(true);
        label.setStyle("-fx-background-color: rgba(65,68,75);" +
                "-fx-text-fill: white;" +
                "-fx-text-alignment: center;" +
                "fx-font-size:15");
        //Adds label to title pane
        aboutPane.getChildren().add(label);

        //About title pane that contains a lebel containing a short description about the application and it's key features.
        TitledPane aboutTitle = new TitledPane("About", aboutPane);
        aboutTitle.setAlignment(Pos.CENTER);
        aboutTitle.setStyle("-fx-text-fill: #ffffff;" +
                "-fx-padding:20,0,0,0;" +
                "-fx-font-size: 17;" +
                "-fx-color: rgba(65,68,75)");

        content.getChildren().add(aboutTitle);
        aboutTitle.setExpanded(false);


        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: rgba(65,68,75)");
        Label teamlabel = new Label();
        pane.getChildren().add(teamlabel);

        teamlabel.setText("Khalil Ahmed\nVlad Niculescu\nCătălin Buruiană\nJacob Klerfelt\nJihwan Sang");
        //Team title pane that contains the names of the team members
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
        jdom.setEditable(false);
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
        json.setText("http://mvnrepository.com/artifact/org.json/json/20160810");
        json.setEditable(false);
        TitledPane jsonPane = new TitledPane("JSON", json);
        jsonPane.setExpanded(false);
        json.setStyle("-fx-text-fill: #ffffff;" +
                "-fx-padding:20,0,0,0;" +
                "-fx-font-size: 15");

        TextArea sqllite = new TextArea();
        sqllite.setEditable(false);
        sqllite.setText("http://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.15.1");
        sqllite.setEditable(false);
        TitledPane SQLLitePane = new TitledPane("SQLite", sqllite);
        SQLLitePane.setExpanded(false);
        sqllite.setStyle("-fx-text-fill: #ffffff;" +
                "-fx-padding:20,0,0,0;" +
                "-fx-font-size: 15");

        TextArea fixerio = new TextArea();
        fixerio.setEditable(false);
        fixerio.setText("http://fixer.io");
        TitledPane fixerioPane = new TitledPane("Fixer.io", fixerio);
        fixerioPane.setExpanded(false);
        fixerio.setStyle("-fx-text-fill: #ffffff;" +
                "-fx-padding:20,0,0,0;" +
                "-fx-font-size: 15");

        TextArea flag = new TextArea();
        flag.setEditable(false);
        flag.setText("http://flagpedia.net");
        TitledPane flagPane = new TitledPane("Flagpedia", flag);
        flagPane.setExpanded(false);
        flag.setStyle("-fx-text-fill: #ffffff;" +
                "-fx-padding:20,0,0,0;" +
                "-fx-font-size: 15");

        Accordion acordion = new Accordion();

        acordion.getPanes().addAll(jdomPane, junitPane, hamcrestPane, jsonPane, SQLLitePane,fixerioPane,flagPane);
        acordion.setStyle("-fx-background-color: rgba(65,68,75);" +
                   "-fx-border-color: transparent;" +
                "-fx-border-style:none;" +
                "-fx-background-insets: 0;" +
                "-fx-padding:0");

        TitledPane licenseTitle = new TitledPane("Licenses", acordion);
        licenseTitle.setAlignment(Pos.CENTER);
        licenseTitle.setStyle("-fx-text-fill: #FFFFFF;" +
                "   -fx-padding:20,0,0,0;" +
                "-fx-font-size: 17;" +
                "fx-border-color:transparent;" +
                "fx-border-style:none;" +
                "-fx-color: rgba(65,68,75)");
        licenseTitle.setExpanded(false);

        content.getChildren().add(licenseTitle);

        setContent(content);
    }

    // Retrieves the text from the txt file.

    /**
     * Method that parses the text from the txt files and appendts it to the TextArea.
     * @param textlabel
     * @param path
     */
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
