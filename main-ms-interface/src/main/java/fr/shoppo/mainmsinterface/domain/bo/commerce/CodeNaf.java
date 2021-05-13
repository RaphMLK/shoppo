package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.io.Serializable;

public class CodeNaf implements Serializable {
    String code;
    String intitule;


    public CodeNaf() {
        /*binding*/
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

}
