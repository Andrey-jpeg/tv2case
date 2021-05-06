package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.credit.Participant;

import java.util.Optional;

public interface ParticipantHandler extends GenericCrud<Participant> {
    Optional<Participant> findByName(String name);
}
