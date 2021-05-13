package fr.shoppo.ms_commerce.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class CommandeVfp implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "id_commande")
    @JsonBackReference
    private Commande commande;

    @OneToOne
    @JoinColumn(name = "id_vfp", nullable = false)
    private ProductVfp productVfp;

    public CommandeVfp() {
    }

    public CommandeVfp(Commande commande, ProductVfp productVfp) {
        this.commande = commande;
        this.productVfp = productVfp;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public ProductVfp getProductVfp() {
        return productVfp;
    }

    public void setProductVfp(ProductVfp productVfp) {
        this.productVfp = productVfp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandeVfp that = (CommandeVfp) o;
        return Objects.equals(commande, that.commande) && Objects.equals(productVfp, that.productVfp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commande, productVfp);
    }
}
