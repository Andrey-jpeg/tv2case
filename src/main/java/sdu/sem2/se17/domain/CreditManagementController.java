package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.auth.Admin;
import sdu.sem2.se17.domain.auth.Producer;
import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;
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
    public boolean login(String username, String password);
    public boolean isAdmin();

    //User
    public void createProducer(String username, String password, String email, long companyId);

    //Production
    public ArrayList<Production> getProductions();
    public void createProduction(String name);
    public void createProduction(long companyId, String name);
    public Production findProduction(String name);
    public Production findProduction(long index);
    public void validateProduction(long productionIndex, Approval approval);
    public void addCreditToProduction(long productionIndex, String name, Role role);
    public void deleteCredit(long productionIndex, long creditIndex);

    //Companies
    public ArrayList<ProductionCompany> getCompanies();

    //Role
    public ArrayList<String> getRoleTitles();
}
