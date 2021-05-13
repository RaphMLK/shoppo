package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.List;
import java.util.Objects;

public class CreateCommandeByCommerceOutput {

    String emailCommerce;

    int idClient;

    List<PanierIdQuantity> panierIdQuantityList;

    private TypeCommandeEnum typeCommandeEnum;

    private Integer advantage;

    public CreateCommandeByCommerceOutput(String emailCommerce, int idClient, List<PanierIdQuantity> panierIdQuantityList, TypeCommandeEnum typeCommandeEnum, Integer advantage) {
        this.emailCommerce = emailCommerce;
        this.idClient = idClient;
        this.panierIdQuantityList = panierIdQuantityList;
        this.typeCommandeEnum = typeCommandeEnum;
        this.advantage = advantage;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateCommandeByCommerceOutput that = (CreateCommandeByCommerceOutput) o;
        return idClient == that.idClient && Objects.equals(emailCommerce, that.emailCommerce) && Objects.equals(panierIdQuantityList, that.panierIdQuantityList) && typeCommandeEnum == that.typeCommandeEnum && Objects.equals(advantage, that.advantage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailCommerce, idClient, panierIdQuantityList, typeCommandeEnum, advantage);
    }
}
