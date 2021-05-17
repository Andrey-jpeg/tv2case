package sdu.sem2.se17.presentation.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import sdu.sem2.se17.domain.CreditManagementHandler;
import sdu.sem2.se17.domain.production.ProductionCompany;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/*
Casper Andresen
Hampus Fink
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

        public CreateAccountController(CreditManagementHandler creditManagementHandler) {
            super(creditManagementHandler);
        }

        public void initialize(){
            fetchProductionCompanies();
        }

        private void fetchProductionCompanies(){
            this.productionCompanies = creditManagementHandler.getCompanies();
            productionCompanySelector.getItems().clear();

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
                creditManagementHandler.createUser(userNameInput.getText(),passwordInput.getText(),emailInput.getText(), selectedItemIndex);
                returnToChooseProduction();
            }
        }

        private void returnToChooseProduction() {
            try {
                userNameInput.getScene().setRoot(
                        MainFX.loadFXML(
                                "ChooseProduction",
                                new ChooseProductionController(
                                        creditManagementHandler)
                        ));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void newCompany(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("CMS Dialog");
        dialog.setHeaderText("Nyt produktions selskab");
        dialog.setContentText("Indtast navnet på selskabet:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            creditManagementHandler.createCompany(result.get());
            fetchProductionCompanies();
        }
    }

    }
