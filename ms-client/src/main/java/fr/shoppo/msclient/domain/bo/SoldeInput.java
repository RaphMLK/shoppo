package fr.shoppo.msclient.domain.bo;

public class SoldeInput {
    String email;
    float amount;
    boolean effective = true;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean isEffective() {
        return effective;
    }

    public void setEffective(boolean effective) {
        this.effective = effective;
    }

    public SoldeInput() {
        /*data-binding*/
    }
}
