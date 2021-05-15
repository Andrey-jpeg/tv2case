package sdu.sem2.se17.presentation.cms;

import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import sdu.sem2.se17.domain.CreditManagementHandler;
import sdu.sem2.se17.domain.production.Approval;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.google.gson.Gson;

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

    @FXML
    private Button jsonButton;

    public ProductionController(CreditManagementHandler creditManagementHandler, String productionName) {
        super(creditManagementHandler);
        this.productionName = productionName;
        //this.rolesTitles = creditManagementHandler.getRoleTitles();

    }

    public void initialize() {
        productionLabel.setText(productionName);

        if(creditManagementHandler.isAdmin()){
            denyButton.setVisible(true);
            approveButton.setVisible(true);
            jsonButton.setVisible(true);
        } else {
            sendButton.setVisible(true);
            addNewCreditButton.setVisible(true);
        }


        creditManagementHandler.findProduction(getProductionId()).getCredits().forEach(x -> {
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
        ArrayList c = creditManagementHandler.findProduction(getProductionId()).getCredits();
        c.removeAll(c);
        for (Node i: credits.getChildren()) {
            String name = ((TextField)((HBox)i).getChildren().get(0)).getText();
            String role = (String)((ComboBox)((HBox)i).getChildren().get(1)).getSelectionModel().getSelectedItem();
            if (name != null && (role != null)){
               // creditManagementHandler.addCreditToProduction(getProductionId(), name, role);
            }
        }

        returnToChooseProduction();
    }

    private long getProductionId(){
        return creditManagementHandler.getProductions().indexOf(creditManagementHandler.findProduction(productionName));
    }

    public void approve(ActionEvent actionEvent) {
       // creditManagementHandler.validateProduction(getProductionId(), Approval.APPROVED);
        returnToChooseProduction();
    }

    public void deny(ActionEvent actionEvent) {
        //creditManagementHandler.validateProduction(getProductionId(), Approval.NOT_APPROVED);
        returnToChooseProduction();

    }

    private void returnToChooseProduction() {
        try {
            approveButton.getScene().setRoot(
                    MainFX.loadFXML(
                            "ChooseProduction",
                            new ChooseProductionController(
                                    creditManagementHandler)
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* <-- Casper Brøchner Andresen --> */
    @FXML
    void convertToJson(ActionEvent event) throws IOException {
        Writer writer = new FileWriter( productionName + ".json", StandardCharsets.UTF_8);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        gson.toJson( creditManagementHandler.findProduction(productionName), writer);
        writer.flush();
        writer.close();
    }
}
