package fr.shoppo.msclient.domain.bo;

import java.util.Arrays;

public enum VFPStateFormat {
    NOT_COMMAND("_"),
    COMMAND("#")
    ;
    String value;
    public static final int LENGTH = 7;
    VFPStateFormat(String value) {
        this.value = value;
    }

    public String value(){
        return this.value;
    }

    public String regex(int len){
        return this.value + "{" + len + "}";/*unoptimal for StringBuilder*/
    }

    public static String regex(boolean inclusive){
        return regex(inclusive, LENGTH);
    }

    public static String regex(boolean inclusive,int len){
        var builder = new StringBuilder("[");
        if(!inclusive) builder.append("^");
        Arrays
                .stream(values())
                .forEach(e -> builder.append(e.value));
        builder.append("]");

        return (len > 0 ? builder.append("{").append(len).append("}") : builder).toString();
    }
}
