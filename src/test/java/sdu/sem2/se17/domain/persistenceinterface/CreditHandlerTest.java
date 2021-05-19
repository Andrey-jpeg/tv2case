package sdu.sem2.se17.domain.persistenceinterface;

import org.junit.jupiter.api.*;
import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.persistence.data.CreditHandlerImpl;
import sdu.sem2.se17.persistence.data.ParticipantHandlerImpl;
import sdu.sem2.se17.persistence.db.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class CreditHandlerTest {

    private CreditHandler handler;

    private final boolean connectToDb = false;

    @BeforeEach
    void setUp() {
        if (connectToDb){
            var ds = new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            handler = new CreditHandlerImpl(ds, new ParticipantHandlerImpl(ds));
        } else {
            handler = new CreditHandlerImplSample();
        }
    }

    @DisplayName("CRUD operations")
    @Nested
    class Crud {
        @Test
        void create() {
            var credit = new Credit(new Participant("Sample"), Role.ANIMATION, 1);
            var result = handler.create(credit);

            assertTrue(result.isPresent());
        }
        @Test
        void read() {
            var credit = new Credit(new Participant("Sample"), Role.ANIMATION, 1);
            var savedCredit = handler.create(credit);

            var result = handler.read(savedCredit.get().getId());

            assertTrue(result.isPresent());
        }
        @Test
        void update() {
            if(!connectToDb){
                return;
            }

            var tempCredit = new Credit(new Participant("Sample"), Role.ANIMATION, 1);
            var startCredit = handler.create(tempCredit);

            var tempId = startCredit.get().getParticipant().getId();
            startCredit.get().setParticipant(new Participant("New"));
            startCredit.get().getParticipant().setId(tempId);
            startCredit.get().setRole(Role.BILLEDKUNSTNERE);

            System.out.print(startCredit.get().getRole().toString());

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
            var credit = new Credit(new Participant("Sample"), Role.ANIMATION, 1);
            var savedCredit = handler.create(credit);

            handler.delete(savedCredit.get().getId());

            var result = handler.read(savedCredit.get().getId());

            assertFalse(result.isPresent());
        }
    }

    @Test
    void findByProductionId() {
        if(connectToDb){
            return;
        }

        var credit1 = handler.create(new Credit(new Participant("Sample1"), Role.ANIMATION, 1)).get();
        var credit2 = handler.create(new Credit(new Participant("Sample2"), Role.EDITOR, 1)).get();
        var credit3 = new Credit(new Participant("Sample3"), Role.DIRIGENTER, 1);
        var credit4 = new Credit(new Participant("Sample4"), Role.DUKKESKABER, 1);

        var production = new Production() {{
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