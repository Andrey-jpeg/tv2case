package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.credit.Participant;

import java.util.ArrayList;
import java.util.Optional;

/*
Hampus Fink
 */
public interface ParticipantHandler extends GenericCrud<Participant> {
    ArrayList<Participant> findByName(String name);
    Optional<Participant> findByCredit(long id);
}
