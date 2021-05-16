package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.persistenceinterface.ParticipantHandler;
import sdu.sem2.se17.persistence.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ParticipantHandlerImpl implements ParticipantHandler {

    private final DataSource dataSource;

    public ParticipantHandlerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Participant> create(Participant participant) {
        Optional<Participant> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    INSERT INTO Participant (name)
                    VALUES (?)
                    RETURNING *
                """)
        ) {
            configureStatement(participant, statement);


            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    result = Optional.of(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            var lmao = throwables.getMessage();
            var aa = lmao;
        }

        return result;
    }

    @Override
    public Optional<Participant> read(long id) {
        Optional<Participant> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    SELECT * FROM Participant
                    WHERE id = ?
                """)
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    result = Optional.of(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    @Override
    public void update(Participant participant) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    UPDATE Participant
                    SET name = ?
                    WHERE id = ?
                """
                )
        ) {
            configureStatement(participant, statement);
            statement.setLong(2, participant.getId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM Participant WHERE id = ?")
        ) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ArrayList<Participant> findByName(String name) {
        ArrayList<Participant> participants = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Participant WHERE name = ?");
        ) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    participants.add(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return participants;
    }

    @Override
    public Optional<Participant> findByCredit(long id) {
        Optional<Participant> result = Optional.empty();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM Participant
                    INNER JOIN Credit
                    ON Participant.id = Credit.participantId
                    WHERE Credit.id = ?
                """);
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = Optional.of(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    private void configureStatement(Participant participant, PreparedStatement statement) throws SQLException {
        statement.setString(1, participant.getName());
    }

    private Participant map(ResultSet resultSet) throws SQLException {
        var participant = new Participant();

        participant.setId(resultSet.getLong(ParticipantHandlerColumn.ID.label));
        participant.setName(resultSet.getString(ParticipantHandlerColumn.NAME.label));

        return participant;
    }

    enum ParticipantHandlerColumn{
        ID("id"),
        NAME("name");

        final String label;

        ParticipantHandlerColumn(String label){
            this.label = label;
        }
    }

}
