package sdu.sem2.se17.presentation.cms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import sdu.sem2.se17.domain.CreditManagementHandler;

import java.io.IOException;

public class LoginController extends Controller {

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    public LoginController(CreditManagementHandler creditManagementHandler) {
        super(creditManagementHandler);
    }

    @FXML
    public void login(ActionEvent event) {
        var username = usernameInput.getText();
        var password = passwordInput.getText();
        if(!username.isBlank()  && !password.isEmpty()){
            var loginResult = this.creditManagementHandler.login(usernameInput.getText(), passwordInput.getText());

            if(loginResult){
                displayChooseProduction();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Brugernavn eller password var ukorrekt.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    private void displayChooseProduction(){
            try {
                loginButton.getScene().setRoot(
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

