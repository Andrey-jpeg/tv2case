package sdu.sem2.se17.domain.auth;

public class Producer extends User {
    private long companyId;

    public Producer(String username, String password, String email, long companyId) {
        super(username, password, email);
        this.companyId = companyId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
