package fr.shoppo.ms_stat.domain.bo.stat_id;

public class StringId extends StatId {
    String value;

    public static StringId of(String name){
        var id = new StringId();

        id.setValue(name);

        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
