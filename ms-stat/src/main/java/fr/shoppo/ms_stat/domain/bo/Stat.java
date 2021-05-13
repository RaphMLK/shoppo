package fr.shoppo.ms_stat.domain.bo;

import fr.shoppo.ms_stat.domain.bo.stat_content.Content;
import fr.shoppo.ms_stat.domain.bo.stat_id.StatId;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Id;

public class Stat {

    @Id
    String id;

    @Indexed(unique = true)
    StatId customId;

    Content content;

    public Object contentValue(){
        return content.value();
    }

    public <T> T contentValue(Class<T> clazz){
        return clazz.cast(contentValue());
    }

    public Stat contentValue(Object value){
        content.value(value);
        return this;
    }

    public Stat contentName(String name){
        content.name(name);
        return this;
    }


    public static Stat of(){
        return new Stat();
    }

    public Stat id(StatId id){
        setCustomId(id);
        return this;
    }

    public Stat content(Content content){
        setContent(content);
        return this;
    }


    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatId getCustomId() {
        return customId;
    }

    public void setCustomId(StatId customId) {
        this.customId = customId;
    }

}
