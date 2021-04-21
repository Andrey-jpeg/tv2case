package sdu.sem2.se17.domain;
import sdu.sem2.se17.domain.auth.*;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.domain.production.ProductionCompany;

import java.util.ArrayList;

/* Casper Fenger Jensen */
public class CreditManagementController {
    private User sessionUser;
    private ArrayList<User> users;
    private ArrayList<Production> productions;
    private ArrayList<ProductionCompany> companies;

    public boolean login(String username, String password) {
        for (User user: this.users) {
            if(!username.equals(user.getUsername())) {
                continue;
            }

            if (password.equals(user.getPassword())) {
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

    public Production createProduction(String name) {
        return null;
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
        if (index > this.productions.size()) {
            return null;
        }
        return this.productions.get((int)(index));
    }

    public void validateProduction(long productionId) {
    }

    public void addCreditToProduction(long productionId, String name, Role role) {
    }

    public void deleteCredit(long productionId, long creditId) {

    }
    //Companies
    public ArrayList<ProductionCompany> getCompanies(){
        return null;
    }



}
