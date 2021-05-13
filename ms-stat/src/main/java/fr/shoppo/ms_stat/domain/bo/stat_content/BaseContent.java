package fr.shoppo.ms_stat.domain.bo.stat_content;

public class BaseContent implements Content{

    String name;
    Object value;

    public BaseContent() {
        /*data binding*/
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object value() {
        return this.value;
    }

    @Override
    public String name(){
        return this.name;
    }

    @Override
    public Content value(Object value){
        this.value = value;
        return this;
    }

    @Override
    public Content name(String name) {
        this.name = name;
        return this;
    }

    public static BaseContent of(String name, Object value){
        var content = new BaseContent();
        content.setName(name);
        content.setValue(value);
        return content;
    }

    public static BaseContent of(Object value){
        return of("_",value);
    }

    public Object getValue() { /*for binding*/
        return value;
    }
}
