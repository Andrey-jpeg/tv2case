package sdu.sem2.se17.presentation.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import sdu.sem2.se17.domain.CreditManagementHandler;
import sdu.sem2.se17.domain.auth.User;

import java.io.IOException;
import java.util.ArrayList;

/*
Casper Andresen
 */
public class DeleteAccountController extends Controller {

    private final ArrayList<User> users;

    public DeleteAccountController(CreditManagementHandler creditManagementHandler) {
        super(creditManagementHandler);
        this.users = creditManagementHandler.getUsers();
    }

    @FXML
    private ComboBox<String> accountBox;

    @FXML
    private Button deleteButton;

    public void initialize(){
        for (User user: users) {
            accountBox.getItems().add(user.getUsername());
        }
    }

    @FXML
    void delete(ActionEvent event) {
        String targetName = accountBox.getSelectionModel().getSelectedItem();
        if (targetName == null) {
            returnToChooseProduction();
        } else
        for (User user : users) {
            if (targetName.equals(user.getUsername())) {
                creditManagementHandler.deleteUser(user.getId());
                returnToChooseProduction();
                break;
            }
        }
    }

    private void returnToChooseProduction() {
        try {
            accountBox.getScene().setRoot(
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
