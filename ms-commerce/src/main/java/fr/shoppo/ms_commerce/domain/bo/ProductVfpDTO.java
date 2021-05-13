package fr.shoppo.ms_commerce.domain.bo;

import java.util.Objects;

public class ProductVfpDTO {

    private int id;

    private String libelle;

    private ProductVfpProductDTO product;

    private int stock;

    private String commerce;

    public ProductVfpDTO(int id, String libelle, ProductVfpProductDTO product, int stock, String commerce) {
        this.id = id;
        this.libelle = libelle;
        this.product = product;
        this.stock = stock;
        this.commerce = commerce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public ProductVfpProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductVfpProductDTO product) {
        this.product = product;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCommerce() {
        return commerce;
    }

    public void setCommerce(String commerce) {
        this.commerce = commerce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVfpDTO that = (ProductVfpDTO) o;
        return id == that.id && stock == that.stock && Objects.equals(libelle, that.libelle) && Objects.equals(product, that.product) && Objects.equals(commerce, that.commerce);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle, product, stock, commerce);
    }
}
