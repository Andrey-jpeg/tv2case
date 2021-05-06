package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.persistenceinterface.UserHandler;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.Optional;

public class UserHandlerImpl implements UserHandler {
    private final DataSource dataSource;

    public UserHandlerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> create(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> read(long id) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
}
