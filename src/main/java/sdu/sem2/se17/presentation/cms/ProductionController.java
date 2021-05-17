package sdu.sem2.se17.presentation.cms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sdu.sem2.se17.domain.CreditManagementHandler;
import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.Approval;
import sdu.sem2.se17.domain.production.Production;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
/*
Casper Jensen
Hampus Fink
Casper Andresen
Nicolas Heeks
 */
public class ProductionController extends Controller {
    private final ArrayList<String> rolesTitles;
    private final Production production;

    @FXML
    public VBox credits;

    @FXML
    private TextArea comments;

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

    public ProductionController(CreditManagementHandler creditManagementHandler, long productionId) {
        super(creditManagementHandler);
        this.production = creditManagementHandler
                .findProduction(productionId);
        this.rolesTitles = (ArrayList<String>) Arrays
                .stream(Role.class.getEnumConstants())
                .map(Role::toString)
                .collect(Collectors.toList());

    }

    public void initialize() {
        productionLabel.setText(production.getName());

        if(creditManagementHandler.isAdmin()){
            denyButton.setVisible(true);
            approveButton.setVisible(true);
            jsonButton.setVisible(true);
        } else {
            sendButton.setVisible(true);
            addNewCreditButton.setVisible(true);
        }

        production.getCredits().forEach(x -> {
            this.credits
                    .getChildren()
                    .add(createNewCredit(x.getParticipant().getName(), x.getRole().toString()));
        });
        comments.setText(production.getComments());
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
        ArrayList<Credit> productionCredits = production.getCredits();

        for (Node i: credits.getChildren()) {
            String name = ((TextField)((HBox)i).getChildren().get(0)).getText();
            String role = (String)((ComboBox)((HBox)i).getChildren().get(1)).getSelectionModel().getSelectedItem();
            if (name != null && (role != null && !name.equals(""))){
                productionCredits.add(new Credit(production.getId()){{
                    setParticipant(new Participant(name));
                    setRole(Role.getRole(role));
                }});
            }
        }
        production.setComments(comments.getText());

        creditManagementHandler.updateProduction(production);
        returnToChooseProduction();
    }


    public void approve(ActionEvent actionEvent) {
        production.setApproval(Approval.APPROVED);
        send(null);
    }

    public void deny(ActionEvent actionEvent) {
        production.setApproval(Approval.NOT_APPROVED);
        send(null);
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
        Writer writer = new FileWriter( production.getName() + ".json", StandardCharsets.UTF_8);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        gson.toJson(production, writer);
        writer.flush();
        writer.close();
    }
}
