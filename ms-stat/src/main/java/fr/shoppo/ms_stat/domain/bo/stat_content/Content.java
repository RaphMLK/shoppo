package fr.shoppo.ms_stat.domain.bo.stat_content;

public interface Content {
    Object value();
    String name();
    Content value(Object value);
    Content name(String name);
}
