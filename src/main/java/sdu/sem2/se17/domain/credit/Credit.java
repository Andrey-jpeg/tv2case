package sdu.sem2.se17.domain.credit;

/* Casper Fenger Jensen */
public class Credit {
    private long id;
    private Participant participant;
    private Role role;

    Credit(Participant participant, Role role){
        this.participant = participant;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
