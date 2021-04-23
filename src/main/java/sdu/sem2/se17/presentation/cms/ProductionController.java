package sdu.sem2.se17.presentation.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sdu.sem2.se17.domain.CreditManagementController;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.Production;

import java.util.ArrayList;

public class ProductionController extends Controller {
    private ArrayList<String> rolesTitles;
    private Production production;

    @FXML
    public VBox credits;

    @FXML
    private Label productionName;

    @FXML
    private Button addNewCreditButton;

    @FXML
    private Button sendButton;

    @FXML
    private Button rightButton;

    public ProductionController(CreditManagementController creditManagementController, String productionName) {
        super(creditManagementController);
        this.production = creditManagementController.findProduction(productionName);
        this.rolesTitles = creditManagementController.getRoleTitles();
    }

    public void initialize() {

    }

    private HBox createNewCredit (String name, String role) {
        return new HBox() {{
            setPrefHeight(35.0);
            setPrefWidth(268.0);
            setSpacing(5.0);
            setPadding(new Insets(0, 2.5, 5, 2.5));
            getChildren().add(new TextField() {{
                setText(name);
            }});
            getChildren().add(new ComboBox<String>() {{
                setPrefWidth(150.0);
                getItems().addAll(rolesTitles);
                getSelectionModel().select(role);
            }});
        }};
    }

    @FXML
    void addNewCredit(ActionEvent event) {
        HBox credit = createNewCredit(null, null);
        credits.getChildren().add(credit);
    }

    @FXML
    void send(ActionEvent event) {

    }

}
