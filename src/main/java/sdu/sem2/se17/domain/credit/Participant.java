package sdu.sem2.se17.domain.credit;

import com.google.gson.annotations.Expose;

/*
Nicolas Heeks
Casper Andresen
Hampus Fink
 */
public class Participant {
    @Expose
    private String name;
    private long id;

    public Participant(String name){
        this.setName(name);
    }
    public Participant(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
