package sdu.sem2.se17.domain.credit;

/* Casper Fenger Jensen */
public class Credit {

    private Participant participant;
    private Role role;

    public Credit(Participant participant, Role role){
        this.participant = participant;
        this.role = role;
    }

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

    public String toString(){
        return this.participant.getName() + ", " + this.role.label;
    }
}
