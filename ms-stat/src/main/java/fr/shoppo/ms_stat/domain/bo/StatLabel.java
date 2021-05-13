package fr.shoppo.ms_stat.domain.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum StatLabel {
    LAST_SAVE("Last Save"),

    CA_ON_SIRET("CA %s"),
    NB_CLIENT("NB CLIENT %s"),
    NB_CMD("NB CMD %s"),
    CLICK_AND_COLLECT_ATTR("CnC %s"),

    CMD_H("Commande(H)"),
    NB_VFP("NB VFP"),
    CLIENT_H("Client(H) %s"),
    ;
    String label;

    StatLabel(String label) {
        this.label = label;
    }

    public String labelFormatting(){
        return this.label.replaceAll("%s","").trim();
    }

    public boolean needParameter(){
        return this.label.contains("%s");
    }

    public String format(Object... args){

        var format = new AtomicReference<>(this.label);
        var realArgs = new ArrayList<>();
        Arrays.stream(args)
                .forEach(e ->{
                        var isStatLabel = new AtomicReference<>(false);
                        Arrays.stream(values())
                        .filter(v -> e!= null && v.labelFormatting().equalsIgnoreCase(e.toString().trim()))
                        .forEach( v ->{
                            format.set(String.format(format.get(), v.label));
                            isStatLabel.set(true);
                        });
                    if(!isStatLabel.get())realArgs.add(e);
                });

        if(format.get().contains("%s") && realArgs.size() == 0) return "undefined args";
        return String.format(format.get(),realArgs.toArray());
    }
}
