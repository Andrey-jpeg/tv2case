package sdu.sem2.se17.persistence.db;


import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

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
}
