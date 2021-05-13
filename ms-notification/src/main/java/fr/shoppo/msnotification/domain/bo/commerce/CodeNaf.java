package fr.shoppo.msnotification.domain.bo.commerce;

import java.util.List;

public class CodeNaf {
    String code;
    String intitule;
    List<Commerce> commerce;


    public CodeNaf() {
        /*data binding*/
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public List<Commerce> getCommerce() {
        return commerce;
    }

    public void setCommerce(List<Commerce> commerce) {
        this.commerce = commerce;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", intitule='" + intitule + '\'' +
                ", commerce=" + commerce +
                '}';
    }
}
