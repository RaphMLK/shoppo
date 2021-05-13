package fr.shoppo.mainmsinterface.domain.bo.commerce;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductAttribute {
    String name;
    String description;
    String image;
    float prix;
    int stock;

    public static ProductAttribute of(
            String name,
            String description,
            float prix,
            int stock,
            String image){
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductAttribute() {
        /*binding*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAttribute)) return false;
        ProductAttribute that = (ProductAttribute) o;
        return Float.compare(that.getPrix(), getPrix()) == 0 &&
                getStock() == that.getStock() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getImage(), that.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getImage(), getPrix(), getStock());
    }
}
