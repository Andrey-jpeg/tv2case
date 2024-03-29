package sdu.sem2.se17.domain.production;

public class ProductionCompany {
    private long id;
    private String name;


    public ProductionCompany() {

    }

    public ProductionCompany(String name) {
        this.name = name;
    }

    public ProductionCompany(long id, String name) {
        this(name);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
