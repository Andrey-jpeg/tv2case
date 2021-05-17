package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.persistenceinterface.ProductionHandler;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.persistence.data.ProductionHandlerImpl;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.ArrayList;
import java.util.Optional;

/*
Casper Andresen
 */
public class ConsumerHandlerImpl implements ConsumerHandler {

    private final ProductionHandler productionHandler;
    DataSource dataSource = new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "postgres");

    public ConsumerHandlerImpl() {
        productionHandler = new ProductionHandlerImpl(this.dataSource);
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
