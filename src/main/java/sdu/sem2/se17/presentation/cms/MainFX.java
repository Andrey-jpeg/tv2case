package sdu.sem2.se17.presentation.cms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sdu.sem2.se17.domain.CreditManagementController;

import java.io.IOException;
import java.util.HashMap;

public class MainFX extends Application {
    private static CreditManagementController creditManagementController = new CreditManagementController();

    @Override

    public void start(Stage stage) throws IOException{
        Scene scene = new Scene(loadFXML("Login", new LoginController(creditManagementController)), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml, Controller controller) throws IOException {
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
