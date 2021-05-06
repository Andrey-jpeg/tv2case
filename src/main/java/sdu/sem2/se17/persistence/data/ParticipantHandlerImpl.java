package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.persistenceinterface.ParticipantHandler;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.Optional;

public class ParticipantHandlerImpl implements ParticipantHandler {

    private final DataSource dataSource;

    public ParticipantHandlerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Participant> create(Participant participant) {
        return Optional.empty();
    }

    @Override
    public Optional<Participant> read(long id) {
        return Optional.empty();
    }

    @Override
    public void update(Participant participant) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Optional<Participant> findByName(String name) {
        return Optional.empty();
    }
}
