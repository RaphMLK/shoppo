package fr.shoppo.ms_commerce.domain.bo;

import java.util.Set;

public class AddVfpByProductGroup {

    private String emailCommerce;

    private Set<AddVfpByProduct> addVfpByProducts;

    public AddVfpByProductGroup() {
    }

    public String getEmailCommerce() {
        return emailCommerce;
    }

    public void setEmailCommerce(String emailCommerce) {
        this.emailCommerce = emailCommerce;
    }

    public Set<AddVfpByProduct> getAddVfpByProducts() {
        return addVfpByProducts;
    }

    public void setAddVfpByProducts(Set<AddVfpByProduct> addVfpByProducts) {
        this.addVfpByProducts = addVfpByProducts;
    }
}
