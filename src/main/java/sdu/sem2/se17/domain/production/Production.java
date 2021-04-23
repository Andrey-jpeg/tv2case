package sdu.sem2.se17.domain.production;

import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;

import java.util.ArrayList;

/* Casper Fenger Jensen */
public class Production {
    private long companyId;
    private String name;
    private ArrayList<Credit> credits;
    private Approval approval = Approval.NOT_SEEN;

    public Production(long companyId, String name) {
        this.companyId = companyId;
        this.name = name;
        this.credits = new ArrayList<>();
    }

    public Production(long companyId, String name, ArrayList<Credit> credits) {
        this(companyId, name);
        for (Credit credit: credits) {
            this.createCredit(credit);
        }
    }

    public void createCredit(Credit credit) {
        this.credits.add(credit);
    }

    public void createCredit(Participant participant, Role role) {
        this.createCredit(new Credit(participant, role));
    }

    public ArrayList<Credit> getCredits(){
        return this.credits;
    }

    public void removeCredit(long index){
        this.credits.remove((int)(index));
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

    public Approval getAproval() {
        return approval;
    }

    public void setAproval(Approval aproval) {
        this.approval = aproval;
    }
}
