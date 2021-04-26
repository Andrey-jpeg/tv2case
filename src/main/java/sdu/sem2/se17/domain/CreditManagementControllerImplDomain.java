package sdu.sem2.se17.domain;
import sdu.sem2.se17.domain.auth.*;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.Approval;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.domain.production.ProductionCompany;

import java.util.ArrayList;

/* Casper Fenger Jensen */
public class CreditManagementControllerImplDomain implements CreditManagementController  {
    private User sessionUser;
    private ArrayList<User> users;
    private ArrayList<Production> productions;
    private ArrayList<ProductionCompany> companies;

    public CreditManagementControllerImplDomain(ArrayList<User> users, ArrayList<Production> productions, ArrayList<ProductionCompany> companies) {
        this.users = users;
        this.productions = productions;
        this.companies = companies;
    }

    public CreditManagementControllerImplDomain() {
        this.users = new ArrayList<>() {{
            add(new Admin("SysAdmin", "password", "john@sdu.student.dk"));
            add(new Admin("Lorem", "", "test@sdu.student.dk"));


            add(new Producer("John", "password", "john@sdu.student.dk", 0));
            add(new Producer("tester 123", "", "test@sdu.student.dk", 1));

            add(new Admin("Ipsum", "aaaa2231", "1133@sdu.student.dk"));
            add(new Admin("Dolem", "551231", "55123@sdu.student.dk"));

            add(new Producer("AnotherUser11", "tihi123", "aa@sdu.student.dk", 2));
            add(new Producer("Producer4", "uhyes", "ddd@sdu.student.dk", 3));
        }};

        this.productions = new ArrayList<>();
        this.productions.add(new Production(1, "First movie made"));

        this.companies = new ArrayList<>();
        this.companies.add(new ProductionCompany(1, "We make movies"));
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
        if(this.sessionUser == null){
            return null;
        }
        else if (this.isAdmin()) {
            return this.productions;
        } else {
            ArrayList<Production> tempArray = new ArrayList<>();
            for (Production i: this.productions) {
                if (((Producer)sessionUser).getCompanyId() == i.getCompanyId()) {
                    tempArray.add(i);
                }
            }
            return tempArray;
        }

    }

    @Override
    public void createProduction(String name) {
        if(this.sessionUser != null && !isAdmin()){
            createProduction(((Producer) sessionUser).getCompanyId(), name);
        }
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
        if (sessionUser == null) {
            return null;
        } else if (index >= this.productions.size()) {
            return null;
        }
        return this.productions.get((int)(index));
    }

    public void validateProduction(long productionIndex, Approval approval) {
       getProductions().get((int) productionIndex).setApproval(approval);
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

    @Override
    public ArrayList<String> getRoleTitles() {
        return new ArrayList<>() {{
            for (Role roletitle: Role.values()) {
                add(String.valueOf(roletitle));
            }
        }};
    }
}
