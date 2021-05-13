package fr.shoppo.ms_stat.infrastructure.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Client {

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

    @Column(name = "changepassword")
    Boolean changePassword;

    @Column
    float solde;

    @Column(length = 7, nullable = false)
    String pointsVFP;

    @Column
    boolean statusVFP;

    @Column
    private String qrCode;

    @Column
    String avantage;

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

    public Boolean getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        this.changePassword = changePassword;
    }

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public String getPointsVFP() {
        return pointsVFP;
    }

    public void setPointsVFP(String pointsVFP) {
        this.pointsVFP = pointsVFP;
    }

    public boolean isStatusVFP() {
        return statusVFP;
    }

    public void setStatusVFP(boolean statusVFP) {
        this.statusVFP = statusVFP;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getAvantage() {
        return avantage;
    }

    public void setAvantage(String avantage) {
        this.avantage = avantage;
    }

    public Client() {
        /*data bindiing*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(email, client.email) && Objects.equals(password, client.password) && Objects.equals(changePassword, client.changePassword) && Objects.equals(qrCode, client.qrCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, changePassword, qrCode);
    }
}
