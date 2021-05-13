package fr.shoppo.msnotification.domain.bo.commerce;


import java.util.Base64;
import java.util.Objects;

public class Product {


    Integer id;
    ProductAttribute attribute;
    Commerce commerce;

    public static Product of(
            Integer id,
            String name,
            String description,
            float prix,
            int stock,
            Commerce commerce,
            byte[] image) {
        var p = new Product();
        p.id = id;
        p.commerce = commerce;
        p.attribute = ProductAttribute.of(name,description,prix,stock,image);
        return p;
    }

    public Product image(byte[] imageBin){
        this.attribute.setImage(Base64.getDecoder().decode(imageBin));
        return this;
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

    public Product changeStock(int toAdd){
        this.attribute.setStock(this.attribute.getStock() + toAdd);
        return this;
    }

    public Product() {
        /*data binding*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(attribute, product.attribute) &&
                Objects.equals(commerce, product.commerce);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attribute, commerce);
    }
}
