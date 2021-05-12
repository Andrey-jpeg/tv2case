package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
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

    public CreditHandlerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ArrayList<Credit> findByProductionId(long id) {
        return null;
    }

    @Override
    public Optional<Credit> create(Credit credit) {
        Optional<Credit> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    INSERT INTO Credit (id_participant, role)
                    VALUES (?, ?)
                    RETURNING *
                """)
        ) {
            configureStatement(credit, statement);

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
        return Optional.empty();
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
            configureStatement(credit, statement);
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

    private void configureStatement(Credit credit, PreparedStatement statement) throws SQLException {
        statement.setString(1, participant.getName()); // obv needs to be questioned
    }

    private Credit map(ResultSet resultSet) throws SQLException {
        var credit = new Credit();

        credit.setId(resultSet.getLong(CreditHandlerColumn.ID.label));
        credit.setRole(resultSet.getString(CreditHandlerColumn.ROLE.label));

        return credit;
    }

    enum CreditHandlerColumn{
        ID("id"),
        ROLE("role");

        final String label;

        CreditHandlerColumn(String label){
            this.label = label;
        }
    }
}
