package view;

import api_exchange.*;
import api_model.Country;
import api_model.Indicator;
import api_model.Model;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import news_feed.NewsFeedPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Main extends Application {

    private ScrollPane paneCountries;
    private StackPane proceedPane;
    private Pane graphIconPane;
    private Pane rssIconPane;
    private Pane exchangeIconPane;
    private Pane exchangeRatesPane;
    private VBox countriesPlaceholder;
    private BorderPane mainPane;
    private HBox settingsPane;
    private Accordion indicatorsPlaceholder;
    private VBox controlBar;
    private Pane topBar;
    private Button proceedButton;
    private ScrollPane rssPane;
    private HashMap<String, DataDisplayWrapper> openedStages = new HashMap<>();


    @Override
    public void start(Stage primaryStage) throws Exception {
        Model.getInstance();

        exchangeRatesPane = new ExchangeRatesPane(this);
        rssPane = new NewsFeedPane(this);

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        getReferences(scene);
        stylePanes(scene);
        primaryStage.show();
        implementScreensSwitcher();
        populateGraphsFilters();
        implementAdditionalPanes();

        //Prevents resizing stage to smaller than initial size
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());


    }

    public static void main(String[] args) {
        launch(args);
    }

    private HBox generateCheckbox(Country country) {
        CheckBox cb = new CheckBox(country.getName());
        cb.setMaxHeight(30);
        cb.setPrefHeight(30);
        cb.setMinHeight(30);
        cb.setMnemonicParsing(false);
        cb.setPadding(new Insets(5));

        cb.setOnMouseClicked(event -> {
            Integer index = Arrays.asList(Model.countries).indexOf(country);
            if (cb.isSelected()) {
                // add to it
                if (!Model.currentCountries.contains(index)) {
                    Model.currentCountries.add(index);
                }

            } else {
                // remove from it
                if (Model.currentCountries.contains(index)) {
                    Model.currentCountries.remove(index);
                }

            }

            if (howManyChecked(countriesPlaceholder) > 0) {
                if (!proceedPane.isVisible()) {
                    proceedPane.setVisible(true);
                    getAnimationFor(proceedPane, true).playFromStart();
                }

            } else {
                // getAnimationFor(proceedPane, false).play();
                // ^ not working yet :(
                proceedPane.setVisible(false);

            }
        });

        HBox toReturn = new HBox();

        ImageView imageView = new ImageView();

        Image image = country.loadFlag();
        imageView = ImageViewBuilder.create()
                .image(image)
                .build();

        imageView.setFitWidth(25);
        imageView.setFitHeight(15);
        imageView.setLayoutY(5);
        toReturn.setPadding(new Insets(5));
        toReturn.getChildren().addAll(imageView, cb);
        toReturn.setAlignment(Pos.CENTER_LEFT);
        return toReturn;
    }

    private ArrayList<HBox> generateCheckBoxes(Country[] countries) {
        ArrayList<Country> countriesConverted = new ArrayList<>(Arrays.asList(countries));
        return countriesConverted.stream().map(this::generateCheckbox).collect(Collectors.toCollection(ArrayList::new));
    }

    private TitledPane generateIndicator(Indicator indicator) {
        TitledPane indicatorPane = new TitledPane();
        indicatorPane.setText(indicator.getName());

        Pane pane = new Pane();
        pane.setPadding(new Insets(0));

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(0));

        ToggleGroup toggleGroup = new ToggleGroup();

        for (String option : indicator.getSubIndicatorsLabels()) {
            RadioButton optionButton = generateIndicatorOption(option, true);
            optionButton.setOnMouseClicked(event -> {
                System.out.println(indicator.getCodeFromLabel(option));
                Model.currentIndicator = indicator.getCodeFromLabel(option);
                Model.currentObjectIndicator = indicator;
                if (howManyChecked(countriesPlaceholder) == 0) {
                    paneCountries.setVisible(true);
                    getAnimationFor(paneCountries, true).playFromStart();

                }
            });
            optionButton.setToggleGroup(toggleGroup);
            vBox.getChildren().add(optionButton);
        }

        pane.getChildren().add(vBox);
        indicatorPane.setContent(pane);

        return indicatorPane;

    }

    private static RadioButton generateIndicatorOption(String text, boolean active) {
        RadioButton button = new RadioButton(text);
        button.setPadding(new Insets(5));
        button.setMnemonicParsing(false);

        if (!active) button.setDisable(true);

        return button;
    }

    private ArrayList<TitledPane> generateTitledPanels(ArrayList<Indicator> indicators) {
        return indicators.stream().map(this::generateIndicator).collect(Collectors.toCollection(ArrayList::new));
    }

    private int howManyChecked(VBox placeholder) {
        int count = 0;
        for (Node node : placeholder.getChildren()) {
            HBox checkboxPlaceholder = (HBox) node;
            CheckBox checkBox = (CheckBox) checkboxPlaceholder.getChildren().get(1);
            if (checkBox.isSelected()) {
                count++;
            }
        }
        return count;
    }

    private void getReferences(Scene scene) {
        mainPane = (BorderPane) scene.lookup("#main-pane");
        settingsPane = (HBox) scene.lookup("#graph-pane");
        controlBar = (VBox) scene.lookup("#control-bar");
        graphIconPane = (Pane) scene.lookup("#icon-1-placeholder");
        rssIconPane = (Pane) scene.lookup("#icon-2-placeholder");
        indicatorsPlaceholder = (Accordion) scene.lookup("#indicators-wrapper");
        paneCountries = (ScrollPane) scene.lookup("#pane-countries");
        countriesPlaceholder = (VBox) paneCountries.getContent().lookup("#countries-wrapper");
        proceedPane = (StackPane) scene.lookup("#pane-proceed");
        topBar = (Pane) scene.lookup("#top-bar");
        proceedButton = (Button) scene.lookup("#proceed-button");
        exchangeIconPane = (Pane) scene.lookup("#icon-3-placeholder");
    }

    private void implementScreensSwitcher() {

        ImageView graphImage = new ImageView();
        graphImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("icon_statistics.png")));
        graphImage.setSmooth(true);
        graphImage.setCache(true);
        graphImage.setFitWidth(50);
        graphImage.setFitHeight(50);
        graphImage.setLayoutX(25);
        graphImage.setLayoutY(25);
        graphImage.setId("graph-icon");
        graphIconPane.getChildren().add(graphImage);

        ImageView rssImage = new ImageView();
        rssImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("icon_rss.png")));
        rssImage.setSmooth(true);
        rssImage.setCache(true);
        rssImage.setFitHeight(50);
        rssImage.setFitWidth(50);
        rssImage.setLayoutX(25);
        rssIconPane.getChildren().add(rssImage);

        ImageView exchangeImage = new ImageView();
        exchangeImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("exchange-rates.png")));
        exchangeImage.setSmooth(true);
        exchangeImage.setCache(true);
        exchangeImage.setFitHeight(50);
        exchangeImage.setFitWidth(50);
        exchangeImage.setLayoutX(25);
        exchangeIconPane.getChildren().add(exchangeImage);

        exchangeIconPane.setOnMouseClicked( event -> {
                if(mainPane.getCenter() != exchangeRatesPane){
                    mainPane.getChildren().remove(mainPane.getCenter());
                    exchangeIconPane.setStyle("-fx-effect: dropshadow(gaussian, #000, 15, 0, 0,0);");
                    graphIconPane.setStyle("");
                    rssIconPane.setStyle("");
                    mainPane.setCenter(exchangeRatesPane);
                    topBar.getChildren().clear();
                    topBar.getChildren().add(generateTitleText("Exchange rates"));
                }
        });

        rssIconPane.setOnMouseClicked(event -> {
            if (mainPane.getCenter() != rssPane) {
                mainPane.getChildren().remove(mainPane.getCenter());
                getAnimationFor(rssPane, true).playFromStart();
                rssIconPane.setStyle("-fx-effect: dropshadow(gaussian, #000, 15, 0, 0,0);");
                graphIconPane.setStyle("");
                exchangeIconPane.setStyle("");
                mainPane.setCenter(rssPane);
                topBar.getChildren().clear();
                topBar.getChildren().add(generateTitleText("RSS News feed"));
            }
        });

        graphIconPane.setOnMouseClicked(event -> {
            if (mainPane.getCenter() != settingsPane) {
                mainPane.getChildren().remove(mainPane.getCenter());
                mainPane.setCenter(settingsPane);
                graphIconPane.setStyle("-fx-effect: dropshadow(gaussian, #000, 15, 0, 0,0);");
                rssIconPane.setStyle("");
                exchangeIconPane.setStyle("");
                getAnimationFor(settingsPane, true).playFromStart();
                topBar.getChildren().clear();
                topBar.getChildren().add(generateTitleText("Graph generator"));
            }


        });
    }

    private void populateGraphsFilters() {
        // adding all checkboxes
        countriesPlaceholder.getChildren().addAll(generateCheckBoxes(Model.countries));
        //adding indicators
        indicatorsPlaceholder.getPanes().addAll(generateTitledPanels(Model.indicators));
        //hide countries
        paneCountries.setVisible(false);
        //hid proceed
        proceedPane.setVisible(false);
    }

    private void stylePanes(Scene scene) {

        scene.getStylesheets().add(this.getClass().getClassLoader()
                .getResource("styling.css").toExternalForm());

        topBar.getChildren().add(generateTitleText("Graph generator"));
        controlBar.setStyle("-fx-background-color: #2c3138");
        topBar.setStyle("-fx-background-color: #F55028");
        settingsPane.setStyle("-fx-background-color: #3e4249");
        graphIconPane.setStyle("-fx-effect: dropshadow(gaussian, #000, 15, 0, 0,0);");
        proceedButton.setAlignment(Pos.CENTER);

    }

    private static Label generateTitleText(String text) {
        Label textElement = new Label(text);
        textElement.setPadding(new Insets(0));
        return textElement;
    }

    private FadeTransition getAnimationFor(Node node, boolean b) {
        FadeTransition ft = new FadeTransition(Duration.seconds(1), node);
        if (b) {
            ft.setFromValue(0);
            ft.setToValue(1);
        } else {
            ft.setFromValue(1);
            ft.setToValue(0);
        }
        ft.setCycleCount(1);
        return ft;
    }

    private void implementAdditionalPanes() {
        proceedButton.setOnMouseClicked(e -> {
            String indicator = Model.currentIndicator;
            if (!openedStages.keySet().contains(Model.currentIndicator)) {
                DataDisplayWrapper wrapper = new DataDisplayWrapper();
                wrapper.show();
                wrapper.setOnCloseRequest(e1 -> {
                    openedStages.remove(indicator);
                    wrapper.clearData();
                });
                openedStages.put(indicator, wrapper);
            } else if (!openedStages.get(indicator).getInCountries().equals(Model.currentCountries)) {
                //openedStages.get(indicator).setInCountries(Model.currentCountries);
                openedStages.get(indicator).startThread();
            }

        });
    }


    public void showLink(String url) {
        getHostServices().showDocument(url);
    }


}
