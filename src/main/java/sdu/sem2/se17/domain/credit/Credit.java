package sdu.sem2.se17.domain.credit;

import com.google.gson.annotations.Expose;

/* Casper Fenger Jensen */
/* + Casper Andresen */
public class Credit {

    @Expose
    private Participant participant;
    @Expose
    private Role role;

    private long id;

    public Credit(Participant participant, Role role){
        this.participant = participant;
        this.role = role;
    }
    public Credit(){}

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = Role.getRole(role);
    }

    public String toString(){
        return this.participant.getName() + ", " + this.role.label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
