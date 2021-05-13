package fr.shoppo.mainmsinterface.domain.bo.client;

import java.util.Objects;

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

    public static SoldeInput of(String email, float amount){
        var input = new SoldeInput();
        input.setEmail(email);
        input.setAmount(amount);
        return input;
    }

    public static SoldeInput of(String email, float amount, boolean effective){
        var input = of(email,amount);
        input.effective = effective;
        return input;
    }

    public SoldeInput() {
        /*data-binding*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoldeInput that = (SoldeInput) o;
        return Float.compare(that.amount, amount) == 0 && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, amount);
    }
}
