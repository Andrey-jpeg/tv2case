package sdu.sem2.se17.persistence.db;


import org.apache.ibatis.jdbc.ScriptRunner;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.*;
import java.sql.*;

/* Hampus Fink
*  Casper Jensen
*  Casper Andresen
* */
public class DataSource {
    private Connection connection;
    private final PGSimpleDataSource simpleDataSource;
    private static final String dbName = "tv2";

    public DataSource(String pgServerUrl, String user, String password){
        simpleDataSource = new PGSimpleDataSource();
        simpleDataSource.setUrl(pgServerUrl + dbName);
        simpleDataSource.setUser(user);
        simpleDataSource.setPassword(password);
    }

    public Connection getConnection() throws SQLException {
        connection = simpleDataSource.getConnection();
        return connection;
    }

    public void close() throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }

    /* Casper Brøchner Andresen */
    public void generateDatabase(){
        var oldUrl = simpleDataSource.getURL();

        simpleDataSource.setUrl(simpleDataSource.getURL().replace(dbName, ""));
        ensureDbExists();

        simpleDataSource.setUrl(oldUrl);
        runScript();
    }

    private void ensureDbExists(){
        try (
                Connection connection = getConnection();
                PreparedStatement databaseExistsPst = connection.prepareStatement("SELECT 1 FROM pg_database WHERE datname=?");
                PreparedStatement dropPst = connection.prepareStatement("DROP DATABASE " + dbName);
                PreparedStatement createPst = connection.prepareStatement("CREATE DATABASE " + dbName);
        ) {
            databaseExistsPst.setString(1, dbName);
            if(databaseExistsPst.executeQuery().next()) {
                dropPst.execute();
            }
            createPst.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void runScript() {
        try (
                Connection connection = getConnection();
                Reader reader = new BufferedReader(new FileReader(DataSource.class.getResource("databaseCreateScript.sql").getFile().replace("%20", " ")));
        ) {
            ScriptRunner sr = new ScriptRunner(connection);
            sr.runScript(reader);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

}
