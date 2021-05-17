package sdu.sem2.se17.domain.persistenceinterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.Approval;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.persistence.data.CreditHandlerImpl;
import sdu.sem2.se17.persistence.data.ParticipantHandlerImpl;
import sdu.sem2.se17.persistence.data.ProductionHandlerImpl;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductionHandlerTest {

    private ProductionHandler handler;
    private final static boolean connectToDb = false;
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        if (connectToDb){
            dataSource = new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            handler = new ProductionHandlerImpl(dataSource, new CreditHandlerImpl(dataSource, new ParticipantHandlerImpl(dataSource)));
        }
    }

    @DisplayName("CRUD operations")
    @Nested
    class Crud {
        @Test
        void create() {
            if (!connectToDb){
                return;
            }
            var result = handler.create(sampleProduction());
            assertTrue(result.isPresent());
        }
        @Test
        void read() {
            if (!connectToDb){
                return;
            }

            var savedProduction = handler.create(sampleProduction());

            var result = handler.read(savedProduction.get().getId());

            assertTrue(result.isPresent());
        }
        @Test
        void update() {
            if (!connectToDb){
                return;
            }

            var startProduction = handler.create(sampleProduction());
            startProduction.get().setName("Changed name");

            handler.update(startProduction.get());

            var result = handler.read(startProduction.get().getId());

            assertAll(
                    () -> assertEquals(
                            startProduction.get().getId(),
                            result.get().getId()),
                    () -> assertEquals(
                            startProduction.get().getCompanyId(),
                            result.get().getCompanyId()),
                    () -> assertEquals(
                            startProduction.get().getApproval(),
                            result.get().getApproval()),
                    () -> assertEquals(
                            startProduction.get().getName(),
                            result.get().getName()),
                    () -> assertEquals(
                            startProduction.get().getComments(),
                            result.get().getComments())
            );
        }
        @Test
        void delete() {
            if (!connectToDb){
                return;
            }

            var savedProduction = handler.create(sampleProduction());

            handler.delete(savedProduction.get().getId());

            var result = handler.read(savedProduction.get().getId());

            assertFalse(result.isPresent());
        }
    }


    @Test
    void readAll() {
        if (!connectToDb){
            return;
        }

        int oldSize = handler.readAll().size();
        handler.create(sampleProduction());
        handler.create(sampleProduction());
        handler.create(sampleProduction());
        handler.create(sampleProduction());

        assertEquals(oldSize + 4, handler.readAll().size());
    }

    @Test
    void findByCompanyId() {
        if (!connectToDb){
            return;
        }

        int oldSize = handler.findByCompanyId(sampleProduction().getCompanyId()).size();
        handler.create(sampleProduction());
        handler.create(sampleProduction());
        handler.create(sampleProduction());
        handler.create(sampleProduction());

        assertEquals(oldSize + 4, handler.findByCompanyId(sampleProduction().getCompanyId()).size());
    }


    private Production sampleProduction(){
        return new Production(){{
            setCompanyId(1);
            setApproval(Approval.APPROVED);
            setName("Sample");
            setComments("Comments");
            createCredit(new Credit(new Participant("P1"), Role.ANIMATION, 0));
            createCredit(new Credit(new Participant("P2"), Role.BAND, 0));
            createCredit(new Credit(new Participant("P3"), Role.DIRIGENTER, 0));
            createCredit(new Credit(new Participant("P4"), Role.FOTOGRAFER, 0));
        }};
    }


}