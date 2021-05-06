package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.persistenceinterface.ProductionCompanyHandler;
import sdu.sem2.se17.domain.production.ProductionCompany;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.ArrayList;
import java.util.Optional;

public class ProductionCompanyHandlerImpl implements ProductionCompanyHandler {

    private final DataSource dataSource;

    public ProductionCompanyHandlerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Optional<ProductionCompany> create(ProductionCompany productionCompany) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductionCompany> read(long id) {
        return Optional.empty();
    }

    @Override
    public void update(ProductionCompany productionCompany) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public ArrayList<ProductionCompany> readAll() {
        return null;
    }
}
