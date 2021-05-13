package fr.shoppo.mainmsinterface.domain.bo.stat;

import java.util.Arrays;

public enum StatLabel {
    CLICK_AND_COLLECT_ATTR("CnC %s"),
    CA_ON_SIRET("CA %s"),
    NB_CLIENT("NB CLIENT %s"),
    CMD_H("Commande(H)"),
    NB_VFP("NB VFP"),
    CLIENT_H("Client(H) %s"),
    NB_CMD("NB CMD %s"),


    ;
    String label;

    StatLabel(String label) {
        this.label = label;
    }

    public String labelFormatting(){
        return this.label.replaceAll("%s","");
    }

    public static StatLabel fromLabelFormating(String label){
        return Arrays.stream(values())
                .filter(e -> label != null  && e.labelFormatting().trim().equalsIgnoreCase(label.trim()))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException(String.format("Got %s but not found in stat label",label)));
    }

    public boolean needParameter(){
        return this.label.contains("%s");
    }

    public String format(Object... args){
        return String.format(this.label,args);
    }
}
