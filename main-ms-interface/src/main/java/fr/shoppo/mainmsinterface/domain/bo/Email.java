package fr.shoppo.mainmsinterface.domain.bo;

public class Email {
    private String value;

    public Email(String value) {
        this.value = value;
    }

    public Email() {
        /*binding*/
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
