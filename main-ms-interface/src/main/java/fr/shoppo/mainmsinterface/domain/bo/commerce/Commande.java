package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Commande {

    Integer id;
    Date dateCreation = new Date();
    Date dateRepriseClient;
    Boolean traitee = false;
    int clientId;
    BigDecimal prix = BigDecimal.ZERO;
    Set<ProductQuantite> products = new HashSet<>();
    Commerce commerce;
    TypeCommande typeCommande;

    public Commande() {
        /*binding*/
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

    public Set<ProductQuantite> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductQuantite> products) {
        this.products = products;
    }

    public Commerce getCommerce() {
        return commerce;
    }

    public void setCommerce(Commerce commerce) {
        this.commerce = commerce;
    }

    public TypeCommande getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(TypeCommande typeCommande) {
        this.typeCommande = typeCommande;
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
                Objects.equals(prix, commande.prix) &&
                Objects.equals(products, commande.products) &&
                Objects.equals(commerce, commande.commerce) &&
                Objects.equals(typeCommande, commande.typeCommande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreation, dateRepriseClient, traitee, clientId, prix, products, commerce, typeCommande);
    }
}
