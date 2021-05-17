package sdu.sem2.se17.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sdu.sem2.se17.domain.persistenceinterface.ProductionHandler;
import sdu.sem2.se17.domain.production.Approval;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.persistence.data.ProductionHandlerImpl;
import sdu.sem2.se17.persistence.db.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class ConsumerHandlerTest {

    private ConsumerHandler handler;
    private final boolean connectToDb = false;
    private ProductionHandler productionHandler;

    @BeforeEach
    void setUp() {
        if (connectToDb){
            DataSource dataSource = new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            handler = new ConsumerHandlerImpl(dataSource);
            productionHandler = new ProductionHandlerImpl(dataSource);
        }
    }
    @Test
    void findProduction() {
        if (!connectToDb){
            return;
        }

        var productionId = productionHandler.create(sampleProduction()).get().getId();

        var production = handler.findProduction(productionId);

        assertTrue(production.isPresent());
        assertEquals(productionId, production.get().getId());
    }

    @Test
    void findProductionName() {
        if (!connectToDb){
            return;
        }

        var savedProduction = productionHandler.create(sampleProduction()).get();

        var productions = handler.findProduction(savedProduction.getName());

        boolean foundSavedProduction = productions.stream().anyMatch(c -> c.getId() == savedProduction.getId());
        boolean onlyFoundMatching = productions.stream().anyMatch(c -> !c.getName().equals(savedProduction.getName()));

        assertTrue(foundSavedProduction);
        assertTrue(onlyFoundMatching);
    }

    @Test
    void allProductions() {

    }

    private Production sampleProduction(){
        return new Production(){{
            setCompanyId(1);
            setApproval(Approval.APPROVED);
            setName("Sample");
            setComments("Comments");
        }};
    }


}