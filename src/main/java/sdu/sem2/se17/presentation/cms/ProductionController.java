package sdu.sem2.se17.presentation.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sdu.sem2.se17.domain.CreditManagementController;

import java.util.ArrayList;

public class ProductionController extends Controller {
    private ArrayList<String> rolesTitles;
    private String productionName;

    @FXML
    public VBox credits;

    @FXML
    private Label productionLabel;

    @FXML
    private Button addNewCreditButton;

    @FXML
    private Button sendButton;

    @FXML
    private Button rightButton;

    public ProductionController(CreditManagementController creditManagementController, String productionName) {
        super(creditManagementController);
        this.productionName = productionName;
        this.rolesTitles = creditManagementController.getRoleTitles();
    }

    public void initialize() {
        productionLabel.setText(productionName);
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
        if (event.getSource() == sendButton) {
            long pIndex = creditManagementController.getProductions().indexOf(creditManagementController.findProduction(productionName));
            var c = creditManagementController.findProduction(pIndex).getCredits();
            c.removeAll(c);
            for (Node i: credits.getChildren()) {
                String name = ((TextField)((HBox)i).getChildren().get(0)).getText();
                String role = (String)((ComboBox)((HBox)i).getChildren().get(1)).getSelectionModel().getSelectedItem();
                if (!name.isBlank() && (role != null)){
                    creditManagementController.addCreditToProduction(pIndex, name, role);

                }
            }

            for (var i: c){
                System.out.println(i.toString());
            }
        }
    }
}
