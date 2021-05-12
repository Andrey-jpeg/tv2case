package sdu.sem2.se17.domain.persistenceinterface;


import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.production.Production;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParticipantHandlerImplSample implements ParticipantHandler {
    private final ArrayList<Participant> participants = new ArrayList<>();
    private final HashMap<Long, Long> participantAssociations = new HashMap<>();

    private long participantIdCounter = 1;

    @Override
    public Optional<Participant> create(Participant participant) {

        participant.setId(participantIdCounter++);

        var newParticipant = new Participant(){{
            setId(participant.getId());
            setName(participant.getName());
        }};

        participants.add(newParticipant);

        return Optional.of(newParticipant);
    }

    @Override
    public Optional<Participant> read(long id) {
        return participants
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public void update(Participant participant) {
        var storedParticipant = participants
                .stream()
                .filter(c -> c.getId() == participant.getId())
                .findFirst();

        storedParticipant.get().setId(participant.getId());
        storedParticipant.get().setName(participant.getName());
    }

    @Override
    public void delete(long id) {
        participants.removeIf(c -> c.getId() == id);
    }
    @Override
    public ArrayList<Participant> findByName(String name) {
        return (ArrayList<Participant>) participants
                .stream()
                .filter(p -> p.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Participant> findByCredit(long id) {
        return read(participantAssociations.get(id));
    }



    public void sampleUpdateCreditWithParticipant(Credit credit) {
        participantAssociations.put(
                credit.getId(),
                read(credit.getParticipant().getId())
                        .get()
                        .getId());
    }

}
