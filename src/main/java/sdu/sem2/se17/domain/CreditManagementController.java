package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.*;

import java.util.ArrayList;

/* Casper Fenger Jensen */
public class CreditManagementController {
    private User sessionUser;
    private ArrayList<User> users;
    private ArrayList<Production> productions;
    private ArrayList<ProductionCompany> companies;

    public boolean login(String username, String password) {
        for (User user: users) {
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
        return this.sessionUser.isAdmin();
    }
    //User
    public void createProducer(String username, String password, long companyId){

    }
    //Production
    public ArrayList<Production> getProductions() {
        return null;
    }
    public Production createProduction(String name) {
        return null;
    }
    public ArrayList<Production> findProduction(String name) {
        return null;
    }
    public Production findProduction(long productionId) {
        return null;
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
