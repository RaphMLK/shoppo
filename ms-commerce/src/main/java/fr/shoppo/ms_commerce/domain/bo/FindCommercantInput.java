package fr.shoppo.ms_commerce.domain.bo;

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
}
