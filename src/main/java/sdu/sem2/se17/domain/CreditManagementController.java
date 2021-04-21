package sdu.sem2.se17.domain;
import sdu.sem2.se17.domain.auth.*;
import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.Aproval;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.domain.production.ProductionCompany;

import java.util.ArrayList;

/* Casper Fenger Jensen */
public class CreditManagementController {
    private User sessionUser;
    private ArrayList<User> users;
    private ArrayList<Production> productions;
    private ArrayList<ProductionCompany> companies;

    public CreditManagementController(ArrayList<User> users, ArrayList<Production> productions, ArrayList<ProductionCompany> companies) {
        this.users = users;
        this.productions = productions;
        this.companies = companies;
    }

    public boolean login(String username, String password) {
        for (User user: this.users) {
            if(!username.equals(user.getUsername())) {
                continue;
            }

            if (password.equals(user.getPassword())) {
                this.sessionUser = user;
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin(){
        return this.sessionUser instanceof Admin;
    }

    //User
    public void createProducer(String username, String password, String email, long companyId){
        users.add(new Producer(username, password, email, companyId));
    }

    //Production
    public ArrayList<Production> getProductions() {
        return this.productions;
    }

    public void createProduction(long companyId, String name) {
        productions.add(new Production(companyId, name));
    }

    public Production findProduction(String name) {
        for (Production production: this.productions) {
            if (name.equalsIgnoreCase(production.getName())) {
                return production;
            }
        }
        return null;
    }

    public Production findProduction(long index) {
        if (index >= this.productions.size()) {
            return null;
        }
        return this.productions.get((int)(index));
    }

    public void validateProduction(long productionIndex, Aproval aproval) {
        findProduction(productionIndex).setAproval(aproval);
    }

    public void addCreditToProduction(long productionIndex, String name, Role role) {
        findProduction(productionIndex).createCredit(new Participant(name), role);
    }

    public void deleteCredit(long productionIndex, long creditIndex) {
        findProduction(productionIndex).removeCredit(creditIndex);
    }

    //Companies
    public ArrayList<ProductionCompany> getCompanies(){
        return this.companies;
    }
}
