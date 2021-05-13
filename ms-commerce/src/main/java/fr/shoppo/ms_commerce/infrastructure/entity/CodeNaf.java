package fr.shoppo.ms_commerce.infrastructure.entity;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class CodeNaf implements Serializable {
    @Id
    @Column
    String code;

    @Column
    @NotNull
    String intitule;

    public static CodeNaf of(String code, String intitule){
        var cn = new CodeNaf();
        cn.code = code;
        cn.intitule = intitule;
        return cn;
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

    public CodeNaf() {
        /*this is for data jpa and binding*/
    }
}
