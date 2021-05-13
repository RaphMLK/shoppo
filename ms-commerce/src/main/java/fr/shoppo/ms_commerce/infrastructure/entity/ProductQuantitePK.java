package fr.shoppo.ms_commerce.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductQuantitePK implements Serializable {

    @JoinColumn(name = "id_commande")
    @ManyToOne
    @JsonBackReference
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private ProductWithoutCommerce product;

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public ProductWithoutCommerce getProduct() {
        return product;
    }

    public void setProduct(ProductWithoutCommerce product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductQuantitePK that = (ProductQuantitePK) o;
        return Objects.equals(commande, that.commande) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commande, product);
    }
}
