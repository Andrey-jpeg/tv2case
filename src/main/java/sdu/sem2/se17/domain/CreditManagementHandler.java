package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.domain.production.ProductionCompany;

import java.util.ArrayList;

/*
Hampus Fink
Casper Jensen
Casper Andresen
*/
public interface CreditManagementHandler {

    boolean login(String username, String password);
    boolean isAdmin();

    void deleteUser(long userId);
    void createUser(String username, String password, String email);
    void createUser(String username, String password, String email, long companyId);
    ArrayList<User> getUsers();

    ArrayList<Production> getProductions();
    Production findProduction(long productionId);
    Production createProduction(String name);
    void updateProduction(Production production);
    void deleteProduction(long productionId);

    void createParticipant(String name);
    ArrayList<Participant> findParticipant(String name);
    ArrayList<Production> recentlyParticipatedIn(Participant participant);
    Participant findParticipant(long id);
    void deleteParticipant(long id);

    ArrayList<ProductionCompany> getCompanies();
    void createCompany(String name);

}
