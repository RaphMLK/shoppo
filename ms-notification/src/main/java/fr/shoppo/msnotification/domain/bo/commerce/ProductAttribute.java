package fr.shoppo.msnotification.domain.bo.commerce;

import java.util.Arrays;
import java.util.Objects;

public class ProductAttribute {
    String name;
    String description;
    byte[] image;
    float prix;
    int stock;

    public static ProductAttribute of(
            String name,
            String description,
            float prix,
            int stock,
            byte[] image){
        var pa = new ProductAttribute();
        pa.setStock(stock);
        pa.setDescription(description);
        pa.setName(name);
        pa.setPrix(prix);
        pa.setImage(image);
        return pa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ProductAttribute() {
        /*binding*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttribute that = (ProductAttribute) o;
        return Float.compare(that.prix, prix) == 0 &&
                stock == that.stock &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Arrays.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, prix, stock);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
