package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.Objects;

public class ProductQuantite {

    ProductQuantitePK productQuantitePK;
    int quantite;


    public ProductQuantite() {
        /*binding*/
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
