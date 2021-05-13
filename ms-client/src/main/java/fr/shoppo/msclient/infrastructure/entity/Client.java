package fr.shoppo.msclient.infrastructure.entity;

import com.sun.istack.NotNull;
import fr.shoppo.msclient.domain.bo.AvantageVFP;
import fr.shoppo.msclient.infrastructure.entity.listener.VFPStateChecker;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

import static fr.shoppo.msclient.domain.bo.VFPStateFormat.NOT_COMMAND;

@Entity
@EntityListeners(VFPStateChecker.class)/*vfpstate will be parsed before insert or update and after selection*/
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
    @NotNull
    Boolean changePassword = false;

    @Column
    @NotNull
    float solde;

    @Column(length = 7, nullable = false)
    @NotNull
    String pointsVFP = StringUtils.repeat(NOT_COMMAND.value(),7);

    @Column
    @NotNull
    boolean statusVFP = false;

    @Column(nullable = false, unique = true)
    private UUID qrCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    AvantageVFP avantage = AvantageVFP.NONE;

    @Column(unique = true, length = 9)
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

    public UUID getQrCode() {
        return qrCode;
    }

    public void setQrCode(UUID qrCode) {
        this.qrCode = qrCode;
    }

    public AvantageVFP getAvantage() {
        return avantage;
    }

    public void setAvantage(AvantageVFP avantage) {
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
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Float.compare(client.solde, solde) == 0 && statusVFP == client.statusVFP && Objects.equals(id, client.id) && Objects.equals(email, client.email) && Objects.equals(password, client.password) && Objects.equals(changePassword, client.changePassword) && Objects.equals(pointsVFP, client.pointsVFP) && Objects.equals(qrCode, client.qrCode) && Objects.equals(plaque, client.plaque) && avantage == client.avantage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, changePassword, solde, pointsVFP, statusVFP, qrCode, plaque, avantage);
    }
}
