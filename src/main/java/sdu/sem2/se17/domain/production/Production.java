package sdu.sem2.se17.domain.production;

import com.google.gson.annotations.Expose;
import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.credit.Role;

import java.util.ArrayList;

/* Casper Fenger Jensen */
public class Production {
    private long companyId;
    @Expose
    private String name;
    @Expose
    private ArrayList<Credit> credits = new ArrayList<>();
    private Approval approval = Approval.NOT_SEEN;
    private long id;
    private String comments;

    public Production(){

    }

    public Production(long companyId, String name) {
        this.name = name;
        this.companyId = companyId;
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
        this.createCredit(new Credit(participant, role, this.id));
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

    public Approval getApproval() {
        return approval;
    }

    public void setApproval(Approval aproval) {
        this.approval = aproval;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setCredits(ArrayList<Credit> credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
