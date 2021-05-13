package fr.shoppo.msnotification.domain.bo.commerce;



import java.util.HashSet;
import java.util.Set;

public class Information {
    String message;
    Commercant commercant;
    Commerce commerce;
    Set<Product> productList = new HashSet<>();

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
}
