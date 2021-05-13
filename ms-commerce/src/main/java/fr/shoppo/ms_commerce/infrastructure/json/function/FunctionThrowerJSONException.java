package fr.shoppo.ms_commerce.infrastructure.json.function;

import org.springframework.boot.configurationprocessor.json.JSONException;

@FunctionalInterface
public interface FunctionThrowerJSONException<T,R> {
    R apply(T t) throws JSONException;

}
