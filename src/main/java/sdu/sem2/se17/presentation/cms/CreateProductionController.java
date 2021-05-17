package sdu.sem2.se17.presentation.cms;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import sdu.sem2.se17.domain.CreditManagementHandler;

import java.io.IOException;

public class CreateProductionController extends Controller {
    public TextField productionTextField;

    public CreateProductionController(CreditManagementHandler creditManagementHandler) {
        super(creditManagementHandler);
    }


    public void create(ActionEvent actionEvent) {
        var productionName = productionTextField.getText();

        if(!productionName.isEmpty()){
            creditManagementHandler.createProduction(productionName);
            cancel(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Produktionen skal have et navn.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        try {
            productionTextField.getScene().setRoot(
                    MainFX.loadFXML(
                            "ChooseProduction",
                            new ChooseProductionController(
                                    creditManagementHandler)
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
