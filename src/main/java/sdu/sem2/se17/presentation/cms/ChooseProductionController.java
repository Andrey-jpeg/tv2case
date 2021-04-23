package sdu.sem2.se17.presentation.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import sdu.sem2.se17.domain.CreditManagementController;
import sdu.sem2.se17.domain.production.Production;

import java.util.ArrayList;

public class ChooseProductionController extends Controller {

    @FXML
    private ComboBox<String> comboBoxProductions;

    @FXML
    private Button selectButton;

    @FXML
    private Button createButton;

    public ChooseProductionController(CreditManagementController creditManagementController) {
        super(creditManagementController);
    }

    public void initialize() {
        ArrayList<Production> productions = this.creditManagementController.getProductions();

        for (Production production: productions) {
            comboBoxProductions.getItems().add(production.getName());
        }
    }

    @FXML
    void create(ActionEvent event) {

    }

    @FXML
    void select(ActionEvent event) {

    }

}