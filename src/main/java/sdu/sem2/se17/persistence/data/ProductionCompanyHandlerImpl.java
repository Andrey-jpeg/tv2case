package sdu.sem2.se17.persistence.data;

import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.persistenceinterface.ProductionCompanyHandler;
import sdu.sem2.se17.domain.production.ProductionCompany;
import sdu.sem2.se17.persistence.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ProductionCompanyHandlerImpl implements ProductionCompanyHandler {

    private final DataSource dataSource;

    public ProductionCompanyHandlerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Optional<ProductionCompany> create(ProductionCompany productionCompany) {
        Optional<ProductionCompany> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                            INSERT INTO ProductionCompany (customer_id)
                            VALUES (?)
                            RETURNING *
                        """)
        ) {
            configureStatement(productionCompany, statement);

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

    @Override
    public Optional<ProductionCompany> read(long id) {
        Optional<ProductionCompany> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                            SELECT * FROM ProductionCompany
                            WHERE id = ?
                            RETURNING *
                        """)
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

    @Override
    public void update(ProductionCompany productionCompany) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(""" 
                            UPDATE Participant
                            SET (name) =
                            (?)
                            WHERE id = ?
                        """
                )
        ) {
            configureStatement(productionCompany, statement);
            statement.setLong(2, productionCompany.getId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM ProductionCompany WHERE id = ?")
        ) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ArrayList<ProductionCompany> readAll() {
        ArrayList<ProductionCompany> productionCompany = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM productionCompany");
        ) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    productionCompany.add(map(resultSet));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return productionCompany;
    }

    private void configureStatement(ProductionCompany productionCompany, PreparedStatement statement) throws SQLException {
        statement.setString(1, productionCompany.getName());
    }

    private ProductionCompany map(ResultSet resultSet) throws SQLException {
        var productionCompany = new ProductionCompany();

        productionCompany.setId(resultSet.getLong(ParticipantHandlerImpl.ParticipantHandlerColumn.ID.label));
        productionCompany.setName(resultSet.getString(ParticipantHandlerImpl.ParticipantHandlerColumn.NAME.label));

        return productionCompany;
    }
        }


