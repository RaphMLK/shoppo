package fr.shoppo.ms_commerce.domain.bo.panier;

import fr.shoppo.ms_commerce.domain.bo.TypeCommandeEnum;

import java.util.List;

public class CreateCommandeByCommerceInput {

    String emailCommerce;

    int idClient;

    List<PanierIdQuantity> panierIdQuantityList;

    private TypeCommandeEnum typeCommandeEnum;

    private Integer advantage;

    public CreateCommandeByCommerceInput() {
    }

    public String getEmailCommerce() {
        return emailCommerce;
    }

    public void setEmailCommerce(String emailCommerce) {
        this.emailCommerce = emailCommerce;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public List<PanierIdQuantity> getPanierIdQuantityList() {
        return panierIdQuantityList;
    }

    public void setPanierIdQuantityList(List<PanierIdQuantity> panierIdQuantityList) {
        this.panierIdQuantityList = panierIdQuantityList;
    }

    public TypeCommandeEnum getTypeCommandeEnum() {
        return typeCommandeEnum;
    }

    public void setTypeCommandeEnum(TypeCommandeEnum typeCommandeEnum) {
        this.typeCommandeEnum = typeCommandeEnum;
    }

    public Integer getAdvantage() {
        return advantage;
    }

    public void setAdvantage(Integer advantage) {
        this.advantage = advantage;
    }
}
