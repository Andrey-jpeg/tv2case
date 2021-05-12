package sdu.sem2.se17.domain.persistenceinterface;

import org.junit.jupiter.api.*;
import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.Production;

import static org.junit.jupiter.api.Assertions.*;

class CreditHandlerTest {

    private CreditHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CreditHandlerImplSample();
    }

    @DisplayName("CRUD operations")
    @Nested
    class Crud {
        @Test
        void create() {
            var credit = new Credit(new Participant("Sample"), Role.ANIMATION);
            var result = handler.create(credit);

            assertTrue(result.isPresent());
        }
        @Test
        void read() {
            var credit = new Credit(new Participant("Sample"), Role.ANIMATION);
            var savedCredit = handler.create(credit);

            var result = handler.read(savedCredit.get().getId());

            assertTrue(result.isPresent());
        }
        @Test
        void update() {
            var tempCredit = new Credit(new Participant("Sample"), Role.ANIMATION);
            var startCredit = handler.create(tempCredit);

            startCredit.get().setParticipant(new Participant("New"));
            startCredit.get().setRole(Role.BILLEDKUNSTNERE);

            handler.update(startCredit.get());

            var result = handler.read(startCredit.get().getId());

            assertAll("Credit attributes should be changed",
                    () -> assertEquals(
                            startCredit.get().getParticipant().getName(),
                            result.get().getParticipant().getName()),
                    () -> assertEquals(
                            startCredit.get().getRole(),
                            result.get().getRole())
            );
        }
        @Test
        void delete() {
            var credit = new Credit(new Participant("Sample"), Role.ANIMATION);
            var savedCredit = handler.create(credit);

            handler.delete(savedCredit.get().getId());

            var result = handler.read(savedCredit.get().getId());

            assertFalse(result.isPresent());
        }
    }

    @Test
    void findByProductionId() {
        var credit1 = handler.create(new Credit(new Participant("Sample1"), Role.ANIMATION)).get();
        var credit2 = handler.create(new Credit(new Participant("Sample2"), Role.EDITOR)).get();
        var credit3 = new Credit(new Participant("Sample3"), Role.DIRIGENTER);
        var credit4 = new Credit(new Participant("Sample4"), Role.DUKKESKABER);

        var production = new Production(){{
            setName("SampleProduction");
            createCredit(credit1);
            createCredit(credit2);
            createCredit(credit3);
            createCredit(credit4);
        }};


        var castedHandler = (CreditHandlerImplSample) handler;
        castedHandler.sampleUpdateProductionWithCredits(production);

        assertEquals(
                4,
                handler.findByProductionId(production.getId()).size());
    }
}