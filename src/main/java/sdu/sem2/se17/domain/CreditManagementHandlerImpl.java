package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.persistenceinterface.*;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.domain.production.ProductionCompany;
import sdu.sem2.se17.persistence.data.*;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.ArrayList;

public class CreditManagementHandlerImpl implements CreditManagementHandler {

    private User sessionUser;

    private final CreditHandler creditHandler;
    private final ParticipantHandler participantHandler;
    private final ProductionCompanyHandler productionCompanyHandler;
    private final ProductionHandler productionHandler;
    private final UserHandler userHandler;

    public CreditManagementHandlerImpl() {
        var dataSource = new DataSource(null, null, null);

        creditHandler = new CreditHandlerImpl(dataSource);
        participantHandler = new ParticipantHandlerImpl(dataSource);
        productionCompanyHandler = new ProductionCompanyHandlerImpl(dataSource);
        productionHandler = new ProductionHandlerImpl(dataSource);
        userHandler = new UserHandlerImpl(dataSource);
    }



    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public void deleteUser(long userId) {

    }

    @Override
    public void createUser(String username, String password, String email, long companyId) {

    }

    @Override
    public ArrayList<Production> getProductions() {
        return null;
    }

    @Override
    public ArrayList<Production> findProduction(String name) {
        return null;
    }

    @Override
    public Production findProduction(long productionId) {
        return null;
    }

    @Override
    public Production createProduction(String name) {
        return null;
    }

    @Override
    public void updateProduction(Production production) {

    }

    @Override
    public void deleteProduction(long productionId) {

    }

    @Override
    public void createParticipant(String name) {

    }

    @Override
    public ArrayList<Participant> findParticipant(String name) {
        return null;
    }

    @Override
    public void findParticipant(long id) {

    }

    @Override
    public void deleteParticipant(long id) {

    }

    @Override
    public ArrayList<ProductionCompany> getCompanies() {
        return null;
    }

    @Override
    public void createCompany(String name) {

    }
}
