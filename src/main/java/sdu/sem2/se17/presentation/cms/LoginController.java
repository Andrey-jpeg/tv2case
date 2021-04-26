package sdu.sem2.se17.presentation.cms;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import sdu.sem2.se17.domain.CreditManagementController;

import java.io.IOException;

public class LoginController extends Controller {

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    public LoginController(CreditManagementController creditManagementController) {
        super(creditManagementController);
    }

    @FXML
    public void login(ActionEvent event) {

        var username = usernameInput.getText();
        var password = passwordInput.getText();
        if(!username.isBlank()  && !password.isEmpty()){
            var loginResult = this.creditManagementController.login(usernameInput.getText(), passwordInput.getText());

            if(loginResult){
                displayChooseProduction();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Login credentials were incorrect.", ButtonType.OK);
                alert.showAndWait();
            }
        }

        System.out.println();
    }

    private void displayChooseProduction(){
            try {
                loginButton.getScene().setRoot(
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

