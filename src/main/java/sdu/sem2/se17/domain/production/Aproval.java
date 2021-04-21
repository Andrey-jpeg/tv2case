package sdu.sem2.se17.domain.production;

/* Casper Fenger Jensen */
public enum Aproval {
    NOT_SEEN(null),
    NOT_APROVED(false),
    APROVED(true);

    private final Boolean aproved;

    Aproval(Boolean aproved) {
        this.aproved = aproved;
    }

    public Boolean isAproved() {
        return aproved;
    }
}
