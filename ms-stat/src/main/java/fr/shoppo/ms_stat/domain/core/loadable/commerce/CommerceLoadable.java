package fr.shoppo.ms_stat.domain.core.loadable.commerce;

import fr.shoppo.ms_stat.domain.StatService;
import fr.shoppo.ms_stat.infrastructure.entity.Commande;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;


public enum CommerceLoadable {
    CMD(Commande.class, List.of(FunctionCommerceLoadable::addCAConsumer,
            FunctionCommerceLoadable::addNBClientConsummer)),
    NONE(Void.class, List.of())
    ;

    Class<?> clazz;
    List<BiConsumer<Object,StatService>> calls;

    <T>
    CommerceLoadable(Class<T> clazz, List<BiConsumer<Object,StatService>> calls) {
        this.clazz = clazz;
        this.calls = calls;
    }

    public void  consume(Object p1,StatService statService){
        this.calls.forEach(call -> call.accept(p1,statService));
    }

    public static CommerceLoadable fromClass(Class<?> clazz){
        return Arrays.stream(values())
                .filter(v -> v.clazz.equals(clazz))
                .findFirst()
                .orElse(NONE);
    }
}
