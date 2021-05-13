package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.Arrays;
import java.util.Objects;

public class ProductWithoutCommerce {

    Integer id;
    ProductAttribute attribute;

    public ProductWithoutCommerce() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductWithoutCommerce that = (ProductWithoutCommerce) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attribute);
    }
}
