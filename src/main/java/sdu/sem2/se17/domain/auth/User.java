package sdu.sem2.se17.domain.auth;

/* Casper Fenger Jensen */
public abstract class User {
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;

    public User(boolean isAdmin, String username, String password, String email) {
        this.isAdmin = isAdmin;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
