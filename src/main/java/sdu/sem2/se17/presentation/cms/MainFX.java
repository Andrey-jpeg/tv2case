package sdu.sem2.se17.presentation.cms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sdu.sem2.se17.domain.CreditManagementHandler;
import sdu.sem2.se17.domain.CreditManagementHandlerImpl;

import java.io.IOException;

/*
Casper Jensen
Hampus Fink
 */
public class MainFX extends Application {
    private static final CreditManagementHandler creditManagementHandler = new CreditManagementHandlerImpl();

    @Override
    public void start(Stage stage) throws IOException{
        Scene scene = new Scene(loadFXML("Login", new LoginController(creditManagementHandler)), 640, 480);
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
