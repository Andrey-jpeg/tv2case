package sdu.sem2.se17.persistence.db;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DbCreator {
    public static void main(String[] args) throws SQLException {
        DataSource ds = new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "n98256416");
        ds.generateDatabase();
    }
}
