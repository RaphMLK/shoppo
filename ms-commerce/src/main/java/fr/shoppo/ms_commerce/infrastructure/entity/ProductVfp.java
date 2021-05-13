package fr.shoppo.ms_commerce.infrastructure.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class ProductVfp implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "id_commerce")
    private Commerce commerce;

    @OneToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Column
    private String libelle;

    @Column
    private Integer stock;

    public ProductVfp() {

    }

    public ProductVfp(Commerce commerce, Product product, String libelle) {
        this.commerce = commerce;
        this.product = product;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Commerce getCommerce() {
        return commerce;
    }

    public void setCommerce(Commerce commerce) {
        this.commerce = commerce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVfp that = (ProductVfp) o;
        return id == that.id && Objects.equals(commerce, that.commerce) && Objects.equals(product, that.product) && Objects.equals(libelle, that.libelle) && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commerce, product, libelle, stock);
    }
}
