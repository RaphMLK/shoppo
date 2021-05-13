package fr.shoppo.ms_commerce.infrastructure.json;

import fr.shoppo.ms_commerce.infrastructure.json.function.FunctionThrowerJSONException;
import org.springframework.boot.configurationprocessor.json.JSONException;

public class JsonManagerComponent {
    /*
     * Usage : tryToGet((Void) -> jsonObject.get<T>("name"))
     * Replace T by a type as Integer or String it could gave you that
     * UsageForString : tryToGet((Void) -> jsonObject.getString("name"))
     * UsageForCustomType : tryToGet((Void) -> (CustomType)jsonObject.get("name"))
     * I've don't verify but it could be parsed and so don't even needed to be casted
     *
     * Goal : if you know that the object behind a name could be null and you wanna get null if it's the case, use that !
     * */
    public static <T> T tryToGet(FunctionThrowerJSONException<Void, T> converter) {
        try{
            return converter.apply(null);
        }catch(JSONException jsonException){
            return null;
        }
    }

    public static <T> T tryToGet(
            FunctionThrowerJSONException<Void, T> converter,
            Class<T> defaultType
    )  {
        try{
            return converter.apply(null);
        }catch(JSONException jsonException){
            try {
                return defaultType.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    public static <T> T tryToGet(FunctionThrowerJSONException<Void, T> converter, Exception exceptionToThrow)
            throws Exception {
        try{
            return converter.apply(null);
        }catch(JSONException jsonException){
            throw exceptionToThrow;
        }
    }

    JsonManagerComponent() throws IllegalAccessException {
        /*to disable code smell*/
        throw new IllegalAccessException("This is a static class");
    }
}
