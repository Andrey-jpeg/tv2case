package sdu.sem2.se17.persistence.db;

import sdu.sem2.se17.persistence.data.DataSource;

import java.util.Optional;

public class UserDao implements GenericCrud {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Object[]> findByUsername(String username){
        return Optional.empty();
    }

    @Override
    public Optional<Object[]> create(Object[] objects) {
        return Optional.empty();
    }

    @Override
    public Optional<Object[]> read(long id) {
        return Optional.empty();
    }

    @Override
    public void update(Object[] objects) {

    }

    @Override
    public void delete(long id) {

    }
}
