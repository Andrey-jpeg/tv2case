package sdu.sem2.se17.presentation.cms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sdu.sem2.se17.domain.CreditManagementController;

public class LoginController extends Controller {
    public LoginController(CreditManagementController creditManagementController) {
        super(creditManagementController);
    }

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    @FXML
    public void login(ActionEvent event) {
        System.out.println(this.creditManagementController.login(usernameInput.getText(), passwordInput.getText()));
    }
}

