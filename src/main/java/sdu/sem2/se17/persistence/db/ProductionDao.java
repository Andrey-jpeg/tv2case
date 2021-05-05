package sdu.sem2.se17.persistence.db;

import sdu.sem2.se17.persistence.data.DataSource;

import java.util.ArrayList;
import java.util.Optional;

public class ProductionDao implements GenericCrud {

    private final DataSource dataSource;

    public ProductionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<Object[]> readAll(){
        return null;
    }

    public ArrayList<Object[]> findByCompanyId(long id){
        return null;
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
