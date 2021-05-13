package fr.shoppo.mainmsinterface.infrastructure.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "connexion")
public class Connexion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "token", unique = true)
    @NotNull
    private String token;

    @Column(name = "role")
    @NotNull
    private String role;

    public Connexion(String email, String token, String role) {
        this.email = email;
        this.token = token;
        this.role = role;
    }

    public Connexion(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public Connexion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
