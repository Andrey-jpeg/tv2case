package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.auth.Admin;
import sdu.sem2.se17.domain.auth.Producer;
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

    public CreditManagementHandlerImpl(DataSource dataSource) {
        participantHandler = new ParticipantHandlerImpl(dataSource);
        creditHandler = new CreditHandlerImpl(dataSource, (ParticipantHandlerImpl)participantHandler);
        productionCompanyHandler = new ProductionCompanyHandlerImpl(dataSource);
        productionHandler = new ProductionHandlerImpl(dataSource);
        userHandler = new UserHandlerImpl(dataSource);
    }



    @Override
    public boolean login(String username, String password) {
        return userHandler
                .findByUsername(username)
                .map(value -> value.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public boolean isAdmin() {
        return this.sessionUser instanceof Admin;
    }

    @Override
    public void deleteUser(long userId) {
        userHandler.delete(userId);
    }

    @Override
    public void createUser(String username, String password, String email) {
        userHandler.create(new Admin(username, password, email));
    }

    @Override
    public void createUser(String username, String password, String email, long companyId) {
        userHandler.create(new Producer(username, password, email, companyId));
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
