package sdu.sem2.se17.domain.credit;

import com.google.gson.annotations.Expose;

public class Participant {
    @Expose
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
