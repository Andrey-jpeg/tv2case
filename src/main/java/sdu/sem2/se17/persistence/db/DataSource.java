package sdu.sem2.se17.persistence.db;


import org.postgresql.ds.PGSimpleDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

/* Hampus */
public class DataSource {
    private Connection connection;
    private final PGSimpleDataSource simpleDataSource;

    public DataSource(String url, String user, String password){
        simpleDataSource = new PGSimpleDataSource();
        simpleDataSource.setUrl(url);
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

    /* Casper Jensen */
    private boolean existDatabase(Statement statement, String dbname) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT 1 FROM pg_database WHERE datname='" + dbname + "'");
        int count = 0;
        while (resultSet.next()) {
            count += 1;
        }
        return count == 1;
    }


    /* Casper Brøchner Andresen */
    public void generateDatabase() throws SQLException {
        Statement statement = getConnection().createStatement();
        if( existDatabase(statement, "tv2")) {
            statement.executeUpdate("DROP DATABASE tv2");
        }
        statement.executeUpdate("CREATE DATABASE tv2");
        this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tv2", "postgres", "" );
        /* PreparedStatement preparedStatement = this.connection.prepareStatement("CREATE TABLE \"user\"(id int primary key)");
        preparedStatement.execute();
         */
        ScriptRunner sr = new ScriptRunner(this.connection);
        //Creating a reader object
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/Users/casperandresen/Documents/SDU/sem2/Semesterprojekt/tv2case-main/src/main/resources/sql/sampleScript.sql"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Running the script
        sr.runScript(reader);
        close();
    }
}
