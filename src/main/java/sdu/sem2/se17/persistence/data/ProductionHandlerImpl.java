package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.persistenceinterface.ProductionHandler;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.ArrayList;
import java.util.Optional;

public class ProductionHandlerImpl implements ProductionHandler {

    private final DataSource dataSource;

    public ProductionHandlerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Production> create(Production production) {
        return Optional.empty();
    }

    @Override
    public Optional<Production> read(long id) {
        return Optional.empty();
    }

    @Override
    public void update(Production production) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public ArrayList<Production> readAll() {
        return null;
    }

    @Override
    public ArrayList<Production> findByCompanyId(long id) {
        return null;
    }
}
