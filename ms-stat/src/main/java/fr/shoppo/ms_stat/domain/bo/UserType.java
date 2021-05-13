package fr.shoppo.ms_stat.domain.bo;

import fr.shoppo.ms_stat.domain.bo.stat_content.Content;
import fr.shoppo.ms_stat.domain.bo.stat_id.BaseId;

public enum UserType {
    CLIENT,
    COMMERCANT,
    ADMINISTRATEUR;


    public Stat from(Content content){
        return Stat
                .of()
                .content(content)
                .id(
                        BaseId.of(this,content.name())
                );
    }
}
