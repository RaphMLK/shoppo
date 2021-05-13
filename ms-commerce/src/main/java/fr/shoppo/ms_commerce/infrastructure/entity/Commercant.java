package fr.shoppo.ms_commerce.infrastructure.entity;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
public class Commercant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Integer id;

    @Column(unique = true)
    @NotNull
    String email;

    @Column
    @NotNull
    String password;

    @ManyToOne
    @JoinColumn
    @Cascade({SAVE_UPDATE})
    Commerce commerce;

    @Column
    String nom;

    @Column
    String prenom;

    @Column
    @NotNull
    boolean owner;

    @Column(name = "changepassword")
    @NotNull
    boolean changePassword;

    public static Commercant of(String email, String password){
        var c = new Commercant();
        c.setEmail(email);
        c.setPassword(password);
        return c;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Commerce getCommerce() {
        return commerce;
    }

    public void setCommerce(Commerce commerce) {
        this.commerce = commerce;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public Commercant() {
        /*for binding*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commercant)) return false;
        Commercant that = (Commercant) o;
        return getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
