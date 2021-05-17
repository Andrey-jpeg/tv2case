package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.persistenceinterface.ProductionHandler;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.persistence.data.ProductionHandlerImpl;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.ArrayList;
import java.util.Optional;

public class ConsumerHandlerImpl implements ConsumerHandler {

    private final ProductionHandler productionHandler;

    public ConsumerHandlerImpl(DataSource dataSource) {
        productionHandler = new ProductionHandlerImpl(dataSource);
    }

    @Override
    public Optional<Production> findProduction(long id) {
        if(productionHandler.read(id).isPresent()) {
            return productionHandler.read(id);
        }
        else return Optional.empty();
    }

    @Override
    public ArrayList<Production> allProductions() {
        return productionHandler.readAll();
    }
}
