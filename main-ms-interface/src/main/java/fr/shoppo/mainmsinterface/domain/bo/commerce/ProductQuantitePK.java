package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.Objects;

public class ProductQuantitePK{

    private int idCommande;

    private ProductWithoutCommerce product;

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public ProductWithoutCommerce getProduct() {
        return product;
    }

    public void setProduct(ProductWithoutCommerce product) {
        this.product = product;
    }

    public ProductQuantitePK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductQuantitePK that = (ProductQuantitePK) o;
        return idCommande == that.idCommande && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommande, product);
    }
}
