package fr.shoppo.ms_commerce.domain.bo;

import java.util.Objects;

public class ProductWithCommercant {
    Integer id;
    String name;
    String description;
    float prix;
    int stock;
    String image;
    String commercant = "";

    public ProductWithCommercant() {
        /*data binding*/
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommercant() {
        return commercant;
    }

    public void setCommercant(String commercant) {
        this.commercant = commercant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductWithCommercant that = (ProductWithCommercant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(prix, that.prix) &&
                Objects.equals(stock, that.stock) &&
                Objects.equals(image, that.image) &&
                Objects.equals(commercant, that.commercant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, prix, stock, image, commercant);
    }
}
