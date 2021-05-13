package fr.shoppo.msadmin.domain.bo;

public class User {

    String email;
    String password;

    public User() {
        /*data binding*/
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
