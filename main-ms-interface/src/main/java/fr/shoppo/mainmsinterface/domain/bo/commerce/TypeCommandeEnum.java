package fr.shoppo.mainmsinterface.domain.bo.commerce;

import java.util.Arrays;

public enum TypeCommandeEnum {
    ONLINE,
    ONPLACE,
    ;

    static TypeCommandeEnum of(String name){
        return Arrays.stream(values()).filter(e -> e.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(ONLINE);
    }

}
