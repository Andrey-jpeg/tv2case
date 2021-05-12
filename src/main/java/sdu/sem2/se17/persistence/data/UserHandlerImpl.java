package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.auth.Admin;
import sdu.sem2.se17.domain.auth.Producer;
import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.persistenceinterface.UserHandler;
import sdu.sem2.se17.persistence.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserHandlerImpl implements UserHandler {
    private final DataSource dataSource;

    public UserHandlerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> create(User user) {
         Optional<User> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    INSERT INTO User (username, email, password, usertype)
                    VALUES (?, ?, ?, ?)
                    RETURNING *
                """)
        ) {
            configureStatement(user, statement);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    result = Optional.of(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    @Override
    public Optional<User> read(long id) {
        Optional<User> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    SELECT * FROM \"User\"
                    WHERE id = ?
                    RETURNING *
                """)
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    result = Optional.of(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    @Override
    public void update(User user) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    UPDATE User
                    SET (name) = (username, password, email)
                    (?, ?, ?)
                    WHERE id = ?
                """
                )
        ) {
            configureStatement(user, statement);
            statement.setLong(1, user.getId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM Username")
        ) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Optional<User> findByUsername(String username) {
           Optional<User> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM User
                    WHERE username = ?
                """)
        ) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = Optional.of(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    private void configureStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
    }


    private User map(ResultSet resultSet) throws SQLException {
        User user;


        var userType = resultSet.getString("user_type");

        if (userType.equals("admin")) {
            user = new Admin(resultSet.getString("username"),"password","email");
        } else
            user = new Producer(resultSet.getString("username"),"password","email");

        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));

        return user;
    }
}

