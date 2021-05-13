package fr.shoppo.mainmsinterface.domain.bo;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Client implements Serializable {

    private Integer id;
    private String email;
    private String password;
    private Boolean changePassword;
    float solde;
    private UUID qrCode;
    String pointsVFP = "";
    boolean statusVFP = false;
    String avantage = "NONE";
    private String plaque;


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

    public UUID getQrCode() {
        return qrCode;
    }

    public void setQrCode(UUID qrCode) {
        this.qrCode = qrCode;
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

    public String getAvantage() {
        return avantage;
    }

    public void setAvantage(String avantage) {
        this.avantage = avantage;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public Client() {
        /*data bindiing*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Float.compare(client.getSolde(), getSolde()) == 0 &&
                isStatusVFP() == client.isStatusVFP() &&
                Objects.equals(getId(), client.getId()) &&
                Objects.equals(getEmail(), client.getEmail()) &&
                Objects.equals(getPassword(), client.getPassword()) &&
                Objects.equals(getChangePassword(), client.getChangePassword()) &&
                Objects.equals(getQrCode(), client.getQrCode()) &&
                Objects.equals(getPointsVFP(), client.getPointsVFP()) &&
                Objects.equals(getAvantage(), client.getAvantage()) &&
                Objects.equals(getPlaque(), client.getPlaque());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getChangePassword(), getSolde(), getQrCode(), getPointsVFP(), isStatusVFP(), getAvantage(), getPlaque());
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", changePassword=" + changePassword +
                ", solde=" + solde +
                ", qrCode=" + qrCode +
                ", pointsVFP='" + pointsVFP + '\'' +
                ", statusVFP=" + statusVFP +
                ", avantage='" + avantage + '\'' +
                ", plaque='" + plaque + '\'' +
                '}';
    }
}
