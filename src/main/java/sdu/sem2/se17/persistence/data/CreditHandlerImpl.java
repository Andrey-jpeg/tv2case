package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.persistenceinterface.CreditHandler;
import sdu.sem2.se17.persistence.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CreditHandlerImpl implements CreditHandler {
    private final DataSource dataSource;
    private ParticipantHandlerImpl participantHandler;

    public CreditHandlerImpl(DataSource dataSource, ParticipantHandlerImpl participantHandler) {
        this.dataSource = dataSource;
        this.participantHandler = participantHandler;
    }

    @Override
    public ArrayList<Credit> findByProductionId(long id) { // jwliuw
        ArrayList<Credit> result = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    SELECT * FROM Credit
                    WHERE production_id = ?
                    RETURNING *
                """)
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Credit credit = map(resultSet);
                    result.add(credit);
                }
            } catch (SQLException throwables) {
            throwables.printStackTrace();
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


    @Override
    public Optional<Credit> create(Credit credit) {
        Optional<Credit> result = Optional.empty();
        long id_participant = participantHandler.create(credit.getParticipant()).get().getId();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    INSERT INTO Credit (id_participant, role)
                    VALUES (?, ?)
                    RETURNING *
                """)
        ) {
            configureStatement(credit, id_participant, statement);

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
    public Optional<Credit> read(long id) {
        Optional<Credit> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    SELECT * FROM Credit
                    WHERE id = ?
                    RETURNING *
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
    public void update(Credit credit) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    UPDATE Credit
                    SET (id_participant, role) =
                    (?, ?)
                    WHERE id = ?
                """
                )
        ) {
            configureStatement(credit, credit.getParticipant().getId(), statement);
            statement.setLong(2, credit.getId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM Credit WHERE id = ?")
        ) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void configureStatement(Credit credit, long id_participant,PreparedStatement statement) throws SQLException {
        statement.setLong(1, id_participant);
        statement.setString(2, credit.getRole().toString());
    }

    private Credit map(ResultSet resultSet) throws SQLException {
            var credit = new Credit();

            credit.setId(resultSet.getLong(CreditHandlerColumn.ID.label));
            credit.setParticipant(participantHandler.findByCredit(credit.getId()).get());
            credit.setRole(resultSet.getString(CreditHandlerColumn.ROLE.label));

            return credit;
        }

    enum CreditHandlerColumn{
        ID("id"),
        ROLE("role"),
        ID_PARTICIPANT("id_participant");

        final String label;

        CreditHandlerColumn(String label){
            this.label = label;
        }
    }
}
