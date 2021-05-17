package sdu.sem2.se17.presentation.consumersystem;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import sdu.sem2.se17.domain.ConsumerHandler;
import sdu.sem2.se17.domain.ConsumerHandlerImpl;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* Casper Brøchner Andresen */

public class ConsumersysController {

    private final ConsumerHandler consumerHandler = new ConsumerHandlerImpl(new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "postgres"));

    private final ObservableList<Production> dataList = FXCollections.observableArrayList();

    Alert alert = new Alert(Alert.AlertType.NONE);


    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<Production> predictionTable;

    @FXML
    private TableColumn<Production, String> name;

    @FXML
    private WebView tvTidView;



    public void initialize() {
        tvTidView.getEngine().load("https://tvtid.tv2.dk/");

        name.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getName()));

        dataList.addAll(consumerHandler.allProductions());

        FilteredList<Production> filteredData = new FilteredList<>(dataList, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate( production -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                return production.getName().toLowerCase().contains(newValue.toLowerCase());
            });
        });

        SortedList<Production> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(predictionTable.comparatorProperty());
        predictionTable.setItems(sortedData);
    }


    @FXML
    void displayCredits(ActionEvent event) throws Exception{
        if (predictionTable.getSelectionModel().getSelectedItem() == null) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Ingen produktion valgt");
            alert.setHeaderText("Da ingen produktion er valgt kan ingen krediteringer fremvises.");
        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle(predictionTable.getSelectionModel().getSelectedItem().getName());
            alert.setHeaderText("Kreditteringerne tilhørende " + predictionTable.getSelectionModel().getSelectedItem().getName() + " er følgende: " );
            alert.setContentText( predictionTable.getSelectionModel().getSelectedItem().getCredits().toString() );
        }
        alert.showAndWait();
    }
}
