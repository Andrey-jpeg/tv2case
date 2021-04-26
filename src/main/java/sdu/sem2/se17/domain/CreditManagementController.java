package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.production.Approval;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.domain.production.ProductionCompany;
import java.util.ArrayList;

/*
* Hampus
* Casper Fenger jensen
* */
public interface CreditManagementController {

    //Auth
    boolean login(String username, String password);
    boolean isAdmin();

    //User
    void createProducer(String username, String password, String email, long companyId);
    //Production
    ArrayList<Production> getProductions();
    void createProduction(String name);
    void createProduction(long companyId, String name);
    Production findProduction(String name);
    Production findProduction(long index);
    void validateProduction(long productionIndex, Approval approval);
    void addCreditToProduction(long productionIndex, String name, String role);
    void deleteCredit(long productionIndex, long creditIndex);

    //Companies
    ArrayList<ProductionCompany> getCompanies();

    //Role
    ArrayList<String> getRoleTitles();
}
