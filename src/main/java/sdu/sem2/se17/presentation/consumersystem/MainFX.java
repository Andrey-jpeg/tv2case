package sdu.sem2.se17.presentation.consumersystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sdu.sem2.se17.domain.ConsumerHandler;
import sdu.sem2.se17.domain.ConsumerHandlerImpl;
import sdu.sem2.se17.domain.CreditManagementHandler;
import sdu.sem2.se17.domain.CreditManagementHandlerImpl;
import sdu.sem2.se17.persistence.db.DataSource;
import sdu.sem2.se17.presentation.cms.LoginController;

import java.sql.SQLException;
import java.util.Objects;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("consumersys.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}
