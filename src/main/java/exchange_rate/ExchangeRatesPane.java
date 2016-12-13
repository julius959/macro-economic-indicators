package exchange_rate;

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

import java.util.TreeMap;

/**
 * This class retrieves data from the ExchangeRatesModel and displays the data in a TableView in a scrollpane
 *
 * @author jacobklerfelt
 * Created 2016-12-02
 */

public class ExchangeRatesPane extends ScrollPane {

    // TreeMap holding the data retrived from the ExchangeRatesModel
    private TreeMap<String, String> data;

    public ExchangeRatesPane() {

        super();
        this.data = ExchangeRatesModel.getData(); // retrieving data from the ExchangeRatesModel

        TableView<TableData> myTable = new TableView<TableData>();
        ArrayList<TableData> temp = new ArrayList<TableData>();
        for(String dataInMap : data.keySet()) { // iterating through data and adding it to an arraylist of TableData
            temp.add(new TableData(dataInMap, data.get(dataInMap)));
        }

        final VBox vbox = new VBox();
        vbox.getChildren().add(myTable);
        setContent(vbox);

        ObservableList<TableData> myTableData = FXCollections.observableArrayList();
        myTableData.addAll(temp); // adding arraylist of data to TableData

        TableColumn currencyColumn = new TableColumn("Currency"); // creating column that is going to hold currency data
        currencyColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("one"));

        TableColumn rateColumn = new TableColumn("Rate (Base = £)"); // creating column that is going to hold rates
        rateColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("two"));

        myTable.setItems(myTableData); // adding TableData to TableView
        myTable.getColumns().addAll(currencyColumn,  rateColumn); // adding both columns to TableView
        myTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        rateColumn.setCellFactory(new Callback<TableColumn, TableCell>() { // styling of rates 
            public TableCell call(TableColumn param) {
                return new TableCell<TableData, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {               
                            if (item.contains("-")) { // rate has decreased since yesterday, setting red color text color
                                item = item.substring(1);
                                this.setTextFill(Color.web("#F5A58F"));
                            }
			    else{
           		        this.setTextFill(Color.web("#8EF561")); // rate has increased, setting green text color
			    }	

                            this.setText(item);
                        }

                    }
                };
            }
        });

        setFitToWidth(true);
        //setPrefViewportHeight(300);

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
