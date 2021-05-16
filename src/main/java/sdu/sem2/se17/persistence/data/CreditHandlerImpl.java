package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.persistenceinterface.CreditHandler;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.ArrayList;
import java.util.Optional;

public class CreditHandlerImpl implements CreditHandler {
    private final DataSource dataSource;

    public CreditHandlerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ArrayList<Credit> findByProductionId(long id) {
        return null;
    }

    @Override
    public Optional<Credit> create(Credit credit, long productionId) {
        return Optional.empty();
    }

    @Override
    public Optional<Credit> create(Credit credit) {
        return Optional.empty();
    }

    @Override
    public Optional<Credit> read(long id) {
        return Optional.empty();
    }

    @Override
    public void update(Credit credit) {

    }

    @Override
    public void delete(long id) {

    }
}
