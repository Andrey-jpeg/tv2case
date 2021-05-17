package sdu.sem2.se17.persistence.db;

import java.sql.SQLException;

/*
Casper Andresen
Hampus Fink
 */
public class DbCreator {
    public static void main(String[] args) throws SQLException {
        DataSource ds = new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
        ds.generateDatabase();
    }
}
