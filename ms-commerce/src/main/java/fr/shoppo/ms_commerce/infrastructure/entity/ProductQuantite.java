package fr.shoppo.ms_commerce.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class ProductQuantite implements Serializable {

    @EmbeddedId
    ProductQuantitePK productQuantitePK;

    @Column
    int quantite;

    public static ProductQuantite of(ProductWithoutCommerce p, Commande c, int q){
        var pq = new ProductQuantite();
        var pqPK = new ProductQuantitePK();
        pqPK.setProduct(p);
        pqPK.setCommande(c);
        pq.setProductQuantitePK(pqPK);
        pq.setQuantite(q);
        return pq;
    }

    public ProductQuantitePK getProductQuantitePK() {
        return productQuantitePK;
    }

    public void setProductQuantitePK(ProductQuantitePK productQuantitePK) {
        this.productQuantitePK = productQuantitePK;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductQuantite that = (ProductQuantite) o;
        return quantite == that.quantite && Objects.equals(productQuantitePK, that.productQuantitePK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productQuantitePK, quantite);
    }
}
