package fr.shoppo.ms_commerce.domain.bo;

import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Information {
    String message;
    Commercant commercant;
    Commerce commerce;
    Set<Product> productList = new HashSet<>();
    Set<ProductVfpDTO> advantages = new HashSet<>();

    public Information() {
    /*data binding*/
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Commercant getCommercant() {
        return commercant;
    }

    public void setCommercant(Commercant commercant) {
        this.commercant = commercant;
    }

    public Set<Product> getProductList() {
        return productList;
    }

    public void setProductList(Set<Product> productList) {
        this.productList = productList;
    }

    public Commerce getCommerce() {
        return commerce;
    }

    public void setCommerce(Commerce commerce) {
        this.commerce = commerce;
    }

    public Set<ProductVfpDTO> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(Set<ProductVfpDTO> advantages) {
        this.advantages = advantages;
    }
}
