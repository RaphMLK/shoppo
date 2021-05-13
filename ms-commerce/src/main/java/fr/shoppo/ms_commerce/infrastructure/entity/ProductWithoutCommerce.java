package fr.shoppo.ms_commerce.infrastructure.entity;

import fr.shoppo.ms_commerce.infrastructure.entity.embeddable.ProductAttribute;
import fr.shoppo.ms_commerce.infrastructure.entity.listener.PreventAnyUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@EntityListeners(PreventAnyUpdate.class)/*Read-only*/
@Table(name = "product")
@Inheritance
public class ProductWithoutCommerce implements Serializable {

    @Id
    @Column
    Integer id;

    @Embedded
    ProductAttribute attribute;

    public ProductWithoutCommerce() {
        /*data binding*/
    }

    public ProductAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(ProductAttribute attribute) {
        this.attribute = attribute;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductWithoutCommerce{" +
                "id=" + id +
                ", attribute=" + attribute +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductWithoutCommerce product = (ProductWithoutCommerce) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
