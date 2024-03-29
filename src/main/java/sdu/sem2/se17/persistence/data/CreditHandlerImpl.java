package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.persistenceinterface.CreditHandler;
import sdu.sem2.se17.domain.persistenceinterface.ParticipantHandler;
import sdu.sem2.se17.persistence.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
/*
Nicolas Heeks
Hampus Fink
 */
public class CreditHandlerImpl implements CreditHandler {
    private final DataSource dataSource;
    private ParticipantHandler participantHandler;

    public CreditHandlerImpl(DataSource dataSource, ParticipantHandler participantHandler) {
        this.dataSource = dataSource;
        this.participantHandler = participantHandler;
    }

    @Override
    public ArrayList<Credit> findByProductionId(long id) {
        ArrayList<Credit> result = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    SELECT * FROM Credit
                    WHERE productionId = ?
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
        long participantId = credit.getParticipant().getId();

        if (credit.getParticipant().getId() == 0){
            participantId = participantHandler.create(credit.getParticipant()).get().getId();
        }


        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    INSERT INTO Credit (participantId, role, productionId)
                    VALUES (?, ?::creditrole, ?)
                    RETURNING *
                """)
        ) {
            configureStatement(credit, participantId, statement);
            statement.setLong(3, credit.getProductionId());

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
                    SET role = ?::creditrole
                    WHERE id = ?
                """
                )
        ) {
            statement.setString(1, credit.getRole().toString().toUpperCase());
            statement.setLong(2, credit.getId());

            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        participantHandler.update(credit.getParticipant());
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

    private void configureStatement(Credit credit, long participantId,  PreparedStatement statement) throws SQLException {
        statement.setLong(1, participantId);
        statement.setString(2, credit.getRole().toString().toUpperCase());
    }

    private Credit map(ResultSet resultSet) throws SQLException {
        var credit = new Credit(resultSet.getLong(CreditHandlerColumn.PRODUCTIONID.label));

        credit.setId(resultSet.getLong(CreditHandlerColumn.ID.label));
        credit.setParticipant(participantHandler.findByCredit(credit.getId()).get());
        credit.setRole(resultSet.getString(CreditHandlerColumn.ROLE.label));

        return credit;
        }

    enum CreditHandlerColumn{
        ID("id"),
        ROLE("role"),
        PRODUCTIONID("productionId");

        final String label;

        CreditHandlerColumn(String label){
            this.label = label;
        }
    }
}
