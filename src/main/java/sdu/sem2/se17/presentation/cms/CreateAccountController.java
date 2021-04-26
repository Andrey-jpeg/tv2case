package sdu.sem2.se17.presentation.cms;

import javafx.event.ActionEvent;
import javafx.scene.control.SelectionModel;
import sdu.sem2.se17.domain.CreditManagementController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sdu.sem2.se17.domain.CreditManagementControllerImplDomain;
import sdu.sem2.se17.domain.production.ProductionCompany;

import java.io.IOException;
import java.util.ArrayList;

/*
Casper B. Andresen
 */

public class CreateAccountController extends Controller {

        private ArrayList<ProductionCompany> productionCompanies;

        @FXML
        private TextField userNameInput;

        @FXML
        private TextField passwordInput;

        @FXML
        private TextField emailInput;

        @FXML
        private ComboBox<String> productionCompanySelector;

        @FXML
        private Button createAccountButton;

        public CreateAccountController(CreditManagementController creditManagementController) {
            super(creditManagementController);
            this.productionCompanies = creditManagementController.getCompanies();
        }

        public void initialize(){
            for (ProductionCompany productionCompany:
                    productionCompanies
            ) {
               productionCompanySelector.getItems().add(productionCompany.getName());
            }
        }

        @FXML
        void createAccount(ActionEvent event) {
            Long selectedItemIndex = null;
            for (ProductionCompany productionCompany:
                    productionCompanies
                 ) {
                if (productionCompany.getName().equals(productionCompanySelector.getSelectionModel().getSelectedItem())){
                    selectedItemIndex = productionCompany.getId();
                }
            }
            if (selectedItemIndex != null) {
                creditManagementController.createProducer(userNameInput.getText(),passwordInput.getText(),emailInput.getText(),
                        selectedItemIndex);
                returnToChooseProduction();
            }
        }

        private void returnToChooseProduction() {
            try {
                userNameInput.getScene().setRoot(
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
