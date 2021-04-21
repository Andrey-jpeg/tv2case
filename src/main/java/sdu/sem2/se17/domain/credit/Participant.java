package sdu.sem2.se17.domain.credit;

public class Participant {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Participant(String name){
        this.setName(name);
    }
}
