package fr.shoppo.ms_stat.infrastructure.entity;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
public class Commande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Integer id;

    @Column(name = "date_creation")
    @NotNull
    Date dateCreation;

    @Column(name = "date_reprise_client")
    @NotNull
    Date dateRepriseClient;

    @Column(name = "traitee")
    @NotNull
    Boolean traitee;

    @Column
    BigDecimal prix = BigDecimal.ZERO;

    @Column(name = "client_id")
    int clientId;

    @Column(name = "commerce_siret_code")
    String commerceSiretCode;

    @ManyToOne
    @JoinColumn
    @Cascade({ALL})
    TypeCommande typeCommande;

    public Commande() {
        /*data binding*/
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateRepriseClient() {
        return dateRepriseClient;
    }

    public void setDateRepriseClient(Date dateRepriseClient) {
        this.dateRepriseClient = dateRepriseClient;
    }

    public Boolean getTraitee() {
        return traitee;
    }

    public void setTraitee(Boolean traitee) {
        this.traitee = traitee;
    }

    public String getCommerceSiretCode() {
        return commerceSiretCode;
    }

    public void setCommerceSiretCode(String commerceSiretCode) {
        this.commerceSiretCode = commerceSiretCode;
    }

    public TypeCommande getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(TypeCommande typeCommande) {
        this.typeCommande = typeCommande;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return clientId == commande.clientId &&
                Objects.equals(id, commande.id) &&
                Objects.equals(dateCreation, commande.dateCreation) &&
                Objects.equals(dateRepriseClient, commande.dateRepriseClient) &&
                Objects.equals(traitee, commande.traitee) &&
                Objects.equals(commerceSiretCode, commande.commerceSiretCode) &&
                Objects.equals(typeCommande, commande.typeCommande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreation, dateRepriseClient, traitee, clientId, commerceSiretCode, typeCommande);
    }
}
