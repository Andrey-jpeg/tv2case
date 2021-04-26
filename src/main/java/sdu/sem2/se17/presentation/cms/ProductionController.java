package sdu.sem2.se17.presentation.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sdu.sem2.se17.domain.CreditManagementController;

import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.Approval;
import sdu.sem2.se17.domain.production.Production;

import java.io.IOException;
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
    private Button denyButton;

    @FXML
    private Button approveButton;

    public ProductionController(CreditManagementController creditManagementController, String productionName) {
        super(creditManagementController);
        this.productionName = productionName;
        this.rolesTitles = creditManagementController.getRoleTitles();

    }

    public void initialize() {
        productionLabel.setText(productionName);

        if(creditManagementController.isAdmin()){
            denyButton.setVisible(true);
            approveButton.setVisible(true);
        } else {
            sendButton.setVisible(true);
            addNewCreditButton.setVisible(true);
        }

        System.out.println(productionName);
        System.out.println(getProductionId());

        creditManagementController.findProduction(getProductionId()).getCredits().forEach(x -> {
            System.out.println(x.getParticipant().getName());
            this.credits.getChildren().add(createNewCredit(x.getParticipant().getName(), x.getRole().toString()));
        });
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
                System.out.println("ddddd");
                if (name != null && (role != null)){
                    System.out.println("aaaa");
                    creditManagementController.addCreditToProduction(pIndex, name, role);
                }
            }

            for (var i: c){
                System.out.println(i.toString());
            }


            returnToChooseProduction();
        }
    }
    private long getProductionId(){
        return creditManagementController.getProductions().indexOf(creditManagementController.findProduction(productionName));
    }

    public void approve(ActionEvent actionEvent) {
        creditManagementController.validateProduction(getProductionId(), Approval.APPROVED);
        returnToChooseProduction();
    }

    public void deny(ActionEvent actionEvent) {
        creditManagementController.validateProduction(getProductionId(), Approval.NOT_APPROVED);
        returnToChooseProduction();

    }

    private void returnToChooseProduction() {
        try {
            approveButton.getScene().setRoot(
                    MainFX.loadFXML(
                            "ChooseProduction",
                            new ChooseProductionController(
                                    creditManagementController)
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
