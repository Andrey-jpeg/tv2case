package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.persistenceinterface.CreditHandler;
import sdu.sem2.se17.domain.persistenceinterface.ProductionHandler;
import sdu.sem2.se17.domain.production.Approval;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.persistence.db.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class ProductionHandlerImpl implements ProductionHandler {

    private final DataSource dataSource;
    private final CreditHandler creditHandler;

    public ProductionHandlerImpl(DataSource dataSource) {
        this(dataSource, new CreditHandlerImpl(dataSource));
    }

    public ProductionHandlerImpl(DataSource dataSource, CreditHandler creditHandler) {
        this.dataSource = dataSource;
        this.creditHandler = creditHandler;
    }

    @Override
    public Optional<Production> create(Production production) {
        Optional<Production> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    INSERT INTO Production (companyid, approvalstatus, name, comments)
                    VALUES (?, ?::approval, ?, ?)
                    RETURNING *
                """)
        ) {
            configureStatement(production, statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    result = Optional.of(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (result.isPresent()){
            maintainCredits(production, result.get());
            result.get().setCredits(creditHandler.findByProductionId(result.get().getId()));
        }

        return result;
    }

    @Override
    public Optional<Production> read(long id) {
        Optional<Production> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    SELECT * FROM Production
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

        result.ifPresent(production -> production
                .setCredits(creditHandler
                        .findByProductionId(production.getId())));

        return result;
    }

    @Override
    public void update(Production production) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                    UPDATE Production
                    SET (companyid, approvalstatus, name, comments)
                    = (?, ?::approval, ?, ?)
                    WHERE id = ?
                """
                )
        ) {
            configureStatement(production, statement);
            statement.setLong(5, production.getId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        read(production.getId())
                .ifPresent(value -> maintainCredits(production, value));
    }

    @Override
    public void delete(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM Production WHERE id = ?")
        ) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ArrayList<Production> readAll() {
        ArrayList<Production> productions = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Production");
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    productions.add(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productions;
    }

    @Override
    public ArrayList<Production> findByCompanyId(long id) {
        ArrayList<Production> productions = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Production WHERE companyid = ?");
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    productions.add(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productions;
    }

    private void maintainCredits(Production oldProduction, Production newProduction){
        //Add new credits
        if (oldProduction.getCredits() != null){
            for (Credit credit: oldProduction.getCredits()){
                if (credit.getId() == 0){
                    creditHandler.create(credit, newProduction.getId());
                }
            }
        }

        newProduction.setCredits(creditHandler
                .findByProductionId(newProduction.getId()));

        //Delete removed credits
        if (newProduction.getCredits() != null){
            newProduction
                    .getCredits().stream()
                    .filter(cO -> oldProduction
                            .getCredits().stream()
                            .anyMatch(cN -> cN.getId() == cO.getId()))
                    .forEach(c -> creditHandler.delete(c.getId()));
        }
    }

    private void configureStatement(Production production, PreparedStatement statement) throws SQLException {
        statement.setLong(1, production.getCompanyId());
        statement.setString(2, production.getApproval().toString().toLowerCase());
        statement.setString(3, production.getName());
        statement.setString(4, production.getComments());
    }

    private Production map(ResultSet resultSet) throws SQLException {
        return new Production(){{
            setId(resultSet
                    .getLong(ProductionHandlerColumn.ID.label));
            setCompanyId(resultSet
                    .getLong(ProductionHandlerColumn.COMPANY_ID.label));
            setApproval(Approval
                    .valueOf(resultSet
                    .getString(ProductionHandlerColumn.APPROVAL_STATUS.label).toUpperCase()));
            setName(resultSet
                    .getString(ProductionHandlerColumn.NAME.label));
            setComments(resultSet
                    .getString(ProductionHandlerColumn.COMMENTS.label));

        }};
    }

    public enum ProductionHandlerColumn{
        ID("id"),
        COMPANY_ID("companyid"),
        APPROVAL_STATUS("approvalstatus"),
        NAME("name"),
        COMMENTS("comments");

        final String label;

        ProductionHandlerColumn(String label){
            this.label = label;
        }
    }

}
