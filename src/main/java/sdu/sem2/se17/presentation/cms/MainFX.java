package sdu.sem2.se17.presentation.cms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class MainFX extends Application {
    private HashMap<String, Controller> controllers = new HashMap<>;

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("Login"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /* Taget fra VOP */
    private static Parent loadFXML(String fxml) throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory((aClass) -> {
            return new this.controllers.get(fxml)();
        });
        loader.setLocation(MainFX.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    /*Object foo(Class type) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return type.getDeclaredConstructor().newInstance(PARAMETER);
    }*/
}
