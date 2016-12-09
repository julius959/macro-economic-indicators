package api_exchange;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import java.util.ArrayList;
import java.util.HashMap;
import view.Main;

public class ExchangeRatesPane extends StackPane{

    HashMap<String, String> data;

    public ExchangeRatesPane(Main app) {

        super();
        this.data = ExchangeRatesModel.getData();

        TableView<TableData> myTable = new TableView<TableData>();
        ArrayList<TableData> temp = new ArrayList<TableData>();
        for(String dataInMap : data.keySet()) {
            temp.add(new TableData(dataInMap, data.get(dataInMap)));
        }

        ObservableList<TableData> myTableData = FXCollections.observableArrayList();
        myTableData.addAll(temp);

        TableColumn currencyColumn = new TableColumn("Currency");
        currencyColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("one"));

        TableColumn rateColumn = new TableColumn("Rate (Base = £)");
        rateColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("two"));

        myTable.setItems(myTableData);
        myTable.getColumns().addAll(currencyColumn,  rateColumn);
        myTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        currencyColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<TableData, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        this.setStyle("-fx-font-family: \"Roboto\"");
                        this.setStyle("-fx-font-size: 12px;");
                        this.setStyle("-fx-background-color: #3e4249");
                        this.setTextFill(Color.LIGHTGRAY);
                        this.setText(item);
                    }
                };
            }
        });


        rateColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<TableData, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            this.setTextFill(Color.GREEN);
                            if (item.contains("-")) {
                                item = item.substring(1);

                                this.setTextFill(Color.RED);
                            }
                            this.setStyle("-fx-font-family: \"Roboto\"");
                            this.setStyle("-fx-font-size: 12px;");
                            this.setStyle("-fx-background-color: #3e4249");
                            this.setText(item);
                        }
                    }
                };
            }
        });
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(myTable);
        getChildren().add(vbox);
    }

    public static class TableData {
        SimpleStringProperty one, two;

        public TableData(String one, String two) {
            this.one = new SimpleStringProperty(one);
            this.two = new SimpleStringProperty(two);
        }
        public String getOne(){return one.get();}
        public void setOne(String one){this.one.set(one);}
        public String getTwo(){return two.get();}
        public void setTwo(String three){this.two.set(three);
        }


    }
}