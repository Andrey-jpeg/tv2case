package sdu.sem2.se17.domain.persistenceinterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantHandlerTest {

    private ParticipantHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ParticipantHandlerImplSample();
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

    @Test
    void findByCredit() {

        var participant = handler.create(new Participant("Sample")).get();
        var credit = new Credit();
        credit.setParticipant(participant);

        var castedHandler = (ParticipantHandlerImplSample) handler;
        castedHandler.sampleUpdateCreditWithParticipant(credit);

        var result = handler.findByCredit(credit.getId());

        assertTrue(result.isPresent());
    }
}