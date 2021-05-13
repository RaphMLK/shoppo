package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.Objects;

public class FindCommercantInput {

    String mailOrSiret;

    boolean withProduct;

    public static FindCommercantInput of(String first, boolean second){
        var input = new FindCommercantInput();
        input.setMailOrSiret(first);
        input.setWithProduct(second);
        return input;
    }

    public String getMailOrSiret() {
        return mailOrSiret;
    }

    public void setMailOrSiret(String mailOrSiret) {
        this.mailOrSiret = mailOrSiret;
    }

    public boolean isWithProduct() {
        return withProduct;
    }

    public void setWithProduct(boolean withProduct) {
        this.withProduct = withProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FindCommercantInput that = (FindCommercantInput) o;
        return withProduct == that.withProduct && Objects.equals(mailOrSiret, that.mailOrSiret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailOrSiret, withProduct);
    }
}
