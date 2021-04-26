package sdu.sem2.se17.presentation.cms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sdu.sem2.se17.domain.CreditManagementController;
import sdu.sem2.se17.domain.CreditManagementControllerImplDomain;
import sdu.sem2.se17.domain.production.Production;

import java.io.IOException;
import java.util.HashMap;

public class MainFX extends Application {
    private static final CreditManagementController creditManagementController = new CreditManagementControllerImplDomain();

    @Override
    public void start(Stage stage) throws IOException{
        Scene scene = new Scene(loadFXML("Login", new LoginController(creditManagementController)), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static Parent loadFXML(String fxml, Controller controller) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory((aClass) -> {
            return controller;
        });
        loader.setLocation(MainFX.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
