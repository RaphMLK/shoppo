package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.Objects;

public class Product {

    Integer id;
    ProductAttribute attribute = new ProductAttribute();
    Commerce commerce;
    String name;
    String description;
    String image;
    float prix;
    int stock;


    public Product() {
        /*binding*/
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(ProductAttribute attribute) {
        this.attribute = attribute;
    }

    public Commerce getCommerce() {
        return commerce;
    }

    public void setCommerce(Commerce commerce) {
        this.commerce = commerce;
    }

    public String getName() {
        return attribute.name;
    }

    public void setName(String name) {
        this.name = name;
        this.attribute.name = name;
    }

    public String getDescription() {
        return attribute.description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.attribute.description = description;
    }

    public float getPrix() {
        return attribute.prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
        this.attribute.prix = prix;
    }

    public int getStock() {
        return attribute.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        this.attribute.stock = stock;
    }

    public String getImage() {
        return attribute.image;
    }

    public void setImage(String image) {
        this.image=image;
        this.attribute.image = image;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) &&
                Objects.equals(getAttribute(), product.getAttribute()) &&
                Objects.equals(getCommerce(), product.getCommerce());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAttribute(), getCommerce());
    }
}
