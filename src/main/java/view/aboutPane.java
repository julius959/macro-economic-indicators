package view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


/**
 * Created by vladniculescu on 12/12/2016.
 */
public class aboutPane extends ScrollPane {
    VBox content = new VBox();
    public aboutPane(Main app){
        super();
        addText();
    }
    private void addText(){

        content.setAlignment(Pos.CENTER);
        setFitToWidth(true);


        TextArea aboutlabel = new TextArea();
        aboutlabel.setEditable(false);
        TitledPane aboutTitle = new TitledPane("About", aboutlabel);
        aboutTitle.setAlignment(Pos.CENTER);
        aboutTitle.setStyle("-fx-text-fill: #FFFFFF;" +
                "   -fx-padding:20,0,0,0;" +
                   "-fx-font-size: 15");
        content.getChildren().add(aboutTitle);
        aboutTitle.setExpanded(false);



        TextArea teamlabel = new TextArea();
        teamlabel.setText("Khalil Ahmed\nVlad Niculescu\nCătălin Buruiană\nJacob Klerfelt\nJihwan Song");
        teamlabel.setStyle("-fx-text-fill: #000000;" +
                " -fx-padding:20,0,0,0;" +
                "-fx-font-size: 15;" +
                "-fx-text-alignment: center; " +
                "-fx-alignment: center;");
        teamlabel.setCenterShape(true);
        teamlabel.setEditable(false);
        TitledPane teamTitle = new TitledPane("Team", teamlabel);
        teamTitle.setAlignment(Pos.CENTER);
        teamTitle.setStyle("-fx-text-fill: #ffffff;" +
                "   -fx-padding:20,0,0,0;" +
                "-fx-font-size: 15");
        content.getChildren().add(teamTitle);
        teamTitle.setExpanded(false);



        TextArea jUnit = new TextArea();
        jUnit.setEditable(false);
        try{
            initialize(jUnit,getClass().getClassLoader().getResource("licenses/junitLicense.txt").getPath());
        }catch (Exception e){}

        TitledPane junitPane = new TitledPane("JUnit",jUnit);
        jUnit.setWrapText(true);
        jUnit.setStyle("-fx-text-fill: #000000;" +
                "   -fx-padding:20,0,0,0;" +
                "-fx-font-size: 10");

        TextArea hamcrest = new TextArea();
        hamcrest.setEditable(false);
        try{
            initialize(hamcrest,getClass().getClassLoader().getResource("licenses/hamcrestLicense.txt").getPath());
        }catch (Exception e){}

        TitledPane hamcrestPane = new TitledPane("Hamcrest",hamcrest);
        hamcrest.setWrapText(true);
        hamcrest.setStyle("-fx-text-fill: #000000;" +
                "   -fx-padding:20,0,0,0;" +
                "-fx-font-size: 10");

        TextArea jdom = new TextArea();
        hamcrest.setEditable(false);
        try{
            initialize(jdom,getClass().getClassLoader().getResource("licenses/jdomLicense.txt").getPath());
        }catch (Exception e){}

        TitledPane jdomPane = new TitledPane("JDOM",jdom);
        jdom.setWrapText(true);
        jdom.setStyle("-fx-text-fill: #000000;" +
                "   -fx-padding:20,0,0,0;" +
                "-fx-font-size: 10");


        Accordion acordion = new Accordion();
        acordion.getPanes().addAll(jdomPane,junitPane,hamcrestPane);
        acordion.setStyle("-fx-border-color: rgb(65,68,72)");




        TitledPane licenseTitle = new TitledPane("Licenses",acordion);
        licenseTitle.setAlignment(Pos.CENTER);
        licenseTitle.setStyle("-fx-text-fill: #FFFFFF;" +
                "   -fx-padding:20,0,0,0;" +
                "-fx-font-size: 15");


        content.getChildren().add(licenseTitle);
        licenseTitle.setExpanded(false);





        setContent(content);
    }


    public void initialize(TextArea textlabel, String  path) {
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
