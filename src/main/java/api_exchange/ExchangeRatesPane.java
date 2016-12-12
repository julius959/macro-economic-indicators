package api_exchange;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class retrieves data from the ExchangeRatesModel and displays the data in a TableView in a scrollpane
 *
 * @author jacobklerfelt
 * Created 2016-12-02
 */
public class ExchangeRatesPane extends ScrollPane {

    // HashMap holding the data retrived from the ExchangeRatesModel
    private HashMap<String, String> data;

    public ExchangeRatesPane() {

        super();
        this.data = ExchangeRatesModel.getData(); // retrieving data from the ExchangeRatesModel

        TableView<TableData> myTable = new TableView<TableData>();
        ArrayList<TableData> temp = new ArrayList<TableData>();
        for(String dataInMap : data.keySet()) { // iterating through data and adding it to an arraylist of TableData
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
                            this.setTextFill(Color.web("#8EF561"));
                            if (item.contains("-")) {
                                item = item.substring(1);
                                this.setTextFill(Color.web("#F5A58F"));
                            }
                            this.setText(item);
                        }

                    }
                };
            }
        });

        final VBox vbox = new VBox();
        vbox.getChildren().add(myTable);
        setContent(vbox);
        setFitToWidth(true);
    }

    public static class TableData {
        SimpleStringProperty currencyProperty, rateProperty;

        public TableData(String one, String two) {
            this.currencyProperty = new SimpleStringProperty(one);
            this.rateProperty = new SimpleStringProperty(two);
        }
        public String getOne(){return currencyProperty.get();}
        public void setOne(String toSet){this.currencyProperty.set(toSet);}
        public String getTwo(){return rateProperty.get();}
        public void setTwo(String toSet){rateProperty.set(toSet);
        }


    }
}