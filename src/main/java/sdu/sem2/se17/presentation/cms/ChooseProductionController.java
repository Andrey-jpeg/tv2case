package sdu.sem2.se17.presentation.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import sdu.sem2.se17.domain.CreditManagementHandler;
import sdu.sem2.se17.domain.production.Production;

import java.io.IOException;
import java.util.ArrayList;

public class ChooseProductionController extends Controller {
    private final ArrayList<Production> productions;

    @FXML
    private ComboBox<String> comboBoxProductions;

    @FXML
    private Button selectButton;

    @FXML
    private Button createButton;

    @FXML
    private Button deleteButton;

    public ChooseProductionController(CreditManagementHandler creditManagementHandler) {
        super(creditManagementHandler);
        this.productions = creditManagementHandler.getProductions();
    }

    public void initialize() {
        deleteButton.setVisible(false);

        for (Production production: productions) {
            comboBoxProductions.getItems().add(production.getName());
        }

        //Admin
        if (creditManagementHandler.isAdmin()){
            createButton.setText(createButton.getText() + " ny bruger");
            deleteButton.setVisible(true);
        } else {
            createButton.setText(createButton.getText() + " ny produktion");
        }
    }

    @FXML
    void create(ActionEvent event) {
        if(creditManagementHandler.isAdmin()){
            createUser();
        } else {
            createProduction();
        }
    }

    private void createProduction() {
        try {
            comboBoxProductions.getScene().setRoot(
                    MainFX.loadFXML(
                            "CreateProduction",
                            new CreateProductionController(
                                    creditManagementHandler))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createUser() {
        try {
            comboBoxProductions.getScene().setRoot(
                    MainFX.loadFXML(
                            "CreateAccount",
                            new CreateAccountController(
                                    creditManagementHandler))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delete() {
        try {
            comboBoxProductions.getScene().setRoot(
                    MainFX.loadFXML(
                            "DeleteAccount",
                            new DeleteAccountController(
                                    creditManagementHandler))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void select(ActionEvent event) {
        var selectedName = comboBoxProductions.getSelectionModel().getSelectedItem();
        if(selectedName != null && !selectedName.isEmpty()){
            try {
                comboBoxProductions.getScene().setRoot(
                        MainFX.loadFXML(
                                "Production",
                                new ProductionController(
                                        creditManagementHandler,
                                        selectedName))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout(ActionEvent actionEvent) {
        try {
            comboBoxProductions.getScene().setRoot(
                    MainFX.loadFXML(
                            "Login",
                            new LoginController(
                                    creditManagementHandler))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
