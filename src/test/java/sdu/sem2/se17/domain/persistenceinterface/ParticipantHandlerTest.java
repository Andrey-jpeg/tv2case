package sdu.sem2.se17.domain.persistenceinterface;

import org.junit.Ignore;
import org.junit.jupiter.api.*;
import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.persistence.data.CreditHandlerImpl;
import sdu.sem2.se17.persistence.data.ParticipantHandlerImpl;
import sdu.sem2.se17.persistence.db.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantHandlerTest {

    private ParticipantHandler handler;
    private final boolean connectToDb = false;
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        if (connectToDb){
            dataSource = new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            handler = new ParticipantHandlerImpl(dataSource);
        } else {
            handler = new ParticipantHandlerImplSample();
        }
    }

    @DisplayName("CRUD operations")
    @Nested
    class Crud {
        @Test
        void create() {
            var participant = new Participant("Sample");
            var result = handler.create(participant);

            assertTrue(result.isPresent());
        }
        @Test
        void read() {
            var participant = new Participant("Sample");
            var savedParticipant = handler.create(participant);

            var result = handler.read(savedParticipant.get().getId());

            assertTrue(result.isPresent());
        }
        @Test
        void update() {
            var tempParticipant = new Participant("Sample");
            var startParticipant = handler.create(tempParticipant);

            startParticipant.get().setName("Changed name");

            handler.update(startParticipant.get());

            var result = handler.read(startParticipant.get().getId());

            assertEquals(
               startParticipant.get().getName(),
               result.get().getName()
            );

        }
        @Test
        void delete() {
            var tempParticipant = new Participant("Sample");
            var savedParticipant = handler.create(tempParticipant);

            handler.delete(savedParticipant.get().getId());

            var result = handler.read(savedParticipant.get().getId());

            assertFalse(result.isPresent());
        }
    }
    
    @Test
    void findByName() {
        handler.create(new Participant("Sample")).get();
        var participant2 = handler.create(new Participant("Sample2")).get();

        var result = handler.findByName(participant2.getName());

        assertEquals(participant2.getName(), result.get(0).getName());
    }

    //Currently not working due to creditHandler not being implemented.
    @Test
    @Disabled
    void findByCredit() {
        if (!connectToDb){
            var participant = handler.create(new Participant("Sample")).get();
            var credit = new Credit();
            credit.setParticipant(participant);

            var castedHandler = (ParticipantHandlerImplSample) handler;
            castedHandler.sampleUpdateCreditWithParticipant(credit);

            var result = handler.findByCredit(credit.getId());
            assertTrue(result.isPresent());
        } else {
            CreditHandler creditHandler = new CreditHandlerImpl(dataSource);

            var participant = handler.create(new Participant("Sampler"));
            var credit = creditHandler.create(new Credit(){{
                setRole(Role.ANIMATION);
                setParticipant(participant.get());
            }});

            var result = handler.findByCredit(credit.get().getId());

            assertTrue(result.isPresent());
        }
    }
}