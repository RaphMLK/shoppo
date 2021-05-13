package fr.shoppo.mainmsinterface.domain.bo.client;

public class AvantageInput {

    String email;
    String avantage;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvantage() {
        return avantage;
    }

    public void setAvantage(String avantage) {
        this.avantage = avantage;
    }

    public AvantageInput() {
        /*binding*/
    }

    public AvantageInput(String email, String avantage) {
        this.email = email;
        this.avantage = avantage;
    }
}
