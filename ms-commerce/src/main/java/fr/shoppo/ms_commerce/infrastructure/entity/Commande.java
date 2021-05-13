package fr.shoppo.ms_commerce.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import fr.shoppo.ms_commerce.domain.bo.TypeCommandeEnum;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.ALL;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
public class Commande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Integer id;

    @Column
    @NotNull
    Date dateCreation = new Date();

    @Column
    @NotNull
    Date dateRepriseClient;

    @Column
    @NotNull
    Boolean traitee = false;


    @Column
    int clientId;

    @Column
    BigDecimal prix = BigDecimal.ZERO;


    @OneToMany(mappedBy = "productQuantitePK.commande")
    @Cascade({ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    Set<ProductQuantite> products = new HashSet<>();

    @ManyToOne
    @JoinColumn
    @Cascade({SAVE_UPDATE})
    Commerce commerce;

    @ManyToOne
    @JoinColumn
    @Cascade({ALL})
    TypeCommande typeCommande;

    @OneToOne(mappedBy = "commande")
    @Cascade({ALL})
    @JsonManagedReference
    CommandeVfp commandeVfp;

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

    public CommandeVfp getCommandeVfp() {
        return commandeVfp;
    }

    public void setCommandeVfp(CommandeVfp commandeVfp) {
        this.commandeVfp = commandeVfp;
    }

    public void addProductQuantite(ProductWithoutCommerce product, Commande commande, int quantite){
        var prodAttr = product.attribute;
        this.products.add(ProductQuantite.of(product, commande,quantite));
        this.prix = this.prix.add(BigDecimal.valueOf(prodAttr.getPrix() * quantite));
    }

    public static Commande of(int idC, Commerce c, TypeCommandeEnum t){
        var cmd = new Commande();
        cmd.setClientId(idC);
        cmd.setCommerce(c);
        cmd.setTypeCommande(TypeCommande.of(t));
        return cmd;
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
                Objects.equals(commerce, commande.commerce) &&
                Objects.equals(typeCommande, commande.typeCommande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreation, dateRepriseClient, traitee, clientId, commerce, typeCommande);
    }
}
