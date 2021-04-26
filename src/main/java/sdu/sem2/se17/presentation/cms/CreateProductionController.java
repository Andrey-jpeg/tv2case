package sdu.sem2.se17.presentation.cms;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import sdu.sem2.se17.domain.CreditManagementController;

import java.io.IOException;

public class CreateProductionController extends Controller {
    public TextField productionTextField;

    public CreateProductionController(CreditManagementController creditManagementController) {
        super(creditManagementController);
    }


    public void create(ActionEvent actionEvent) {
        var productionName = productionTextField.getText();

        if(!productionName.isEmpty()){
            creditManagementController.createProduction(productionName);
            cancel(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Production name must not be empty.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        try {
            productionTextField.getScene().setRoot(
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
