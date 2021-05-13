package fr.shoppo.ms_commerce.domain.bo;

import java.io.Serializable;

public class User implements Serializable {


    private String email;

    private String password;

    public User() {
        /* empty constructor for rabbit */
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
