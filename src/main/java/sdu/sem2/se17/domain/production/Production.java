package sdu.sem2.se17.domain.production;

import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;

import java.util.ArrayList;

public class Production {
    private long id;
    private long companyId;
    private String name;
    private ArrayList<Credit> credits;

    public void createCredit(Participant participant, Role role){

    }
    public ArrayList<Credit> getCredits(){
        return null;
    }
    public void removeCredit(long id){

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
