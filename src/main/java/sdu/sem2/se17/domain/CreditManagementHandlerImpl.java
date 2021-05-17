package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.auth.Admin;
import sdu.sem2.se17.domain.auth.Producer;
import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.persistenceinterface.*;
import sdu.sem2.se17.domain.production.Approval;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.domain.production.ProductionCompany;
import sdu.sem2.se17.persistence.data.*;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/*
Hampus Fink
Nicolas Heeks
Casper Andresen
Casper Jensen
 */
public class CreditManagementHandlerImpl implements CreditManagementHandler {

    private User sessionUser;

    private final CreditHandler creditHandler;
    private final ParticipantHandler participantHandler;
    private final ProductionCompanyHandler productionCompanyHandler;
    private final ProductionHandler productionHandler;
    private final UserHandler userHandler;

    public CreditManagementHandlerImpl(DataSource dataSource) {
        participantHandler = new ParticipantHandlerImpl(dataSource);
        creditHandler = new CreditHandlerImpl(dataSource, this.participantHandler);
        productionCompanyHandler = new ProductionCompanyHandlerImpl(dataSource);
        productionHandler = new ProductionHandlerImpl(dataSource);
        userHandler = new UserHandlerImpl(dataSource);
    }

    public CreditManagementHandlerImpl() {
        this(new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "postgres"));
    }

    @Override
    public boolean login(String username, String password) {
         Optional<User> foundUser = userHandler.findByUsername(username);
         if (foundUser.isPresent()){
             Boolean passwordCorrect = foundUser.map(value -> value.getPassword().equals(password)).get();

             if (passwordCorrect){
                 sessionUser = foundUser.get();
                 return true;
             }
         }
         return false;
    }

    @Override
    public boolean isAdmin() {
        return this.sessionUser instanceof Admin;
    }

    @Override
    public void deleteUser(long userId) {
        this.userHandler.delete(userId);
    }

    @Override
    public ArrayList<User> getUsers(){
        return this.userHandler.readAll();
    }

    @Override
    public void createUser(String username, String password, String email) {
        this.userHandler.create(new Admin(username, password, email));
    }

    @Override
    public void createUser(String username, String password, String email, long companyId) {
        this.userHandler.create(new Producer(username, password, email, companyId));
    }

    @Override
    public ArrayList<Production> getProductions() {
        var productions  = productionHandler.readAll();

        if (isAdmin()){
            return productions;
        } else {
            return (ArrayList<Production>) productions.stream()
                    .filter(p -> p.getCompanyId() == ((Producer)sessionUser).getCompanyId())
                    .filter(p -> p.getApproval() != Approval.APPROVED)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Production findProduction(long productionId) {
        return this.productionHandler
                .read(productionId)
                .orElse(null);
    }

    @Override
    public Production createProduction(String name) {
        return this.productionHandler
                .create(new Production(((Producer)(sessionUser)).getCompanyId(), name))
                .orElse(null);
    }

    @Override
    public void updateProduction(Production production) {
        this.productionHandler.update(production);
    }

    @Override
    public void deleteProduction(long productionId) {
        this.productionHandler.delete(productionId);
    }

    @Override
    public void createParticipant(String name) {
        this.participantHandler.create(new Participant(name));
    }

    @Override
    public ArrayList<Participant> findParticipant(String name) {
        return this.participantHandler.findByName(name);
    }

    @Override
    public Participant findParticipant(long id) {
        return this.participantHandler.read(id).orElse(null);
    }

    @Override
    public void deleteParticipant(long id) {
        this.participantHandler.delete(id);
    }

    @Override
    public ArrayList<ProductionCompany> getCompanies() {
        return this.productionCompanyHandler.readAll();
    }

    @Override
    public void createCompany(String name) {
        this.productionCompanyHandler.create(new ProductionCompany(name));
    }
}
