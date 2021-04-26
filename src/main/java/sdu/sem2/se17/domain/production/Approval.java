package sdu.sem2.se17.domain.production;

/* Casper Fenger Jensen */
public enum Approval {
    NOT_SEEN(false),
    NOT_APPROVED(false),
    APPROVED(true);

    private final Boolean approved;

    Approval(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isApproved() {
        return approved;
    }
}
