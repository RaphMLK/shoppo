package fr.shoppo.mainmsinterface.infrastructure.json;

import fr.shoppo.mainmsinterface.infrastructure.service.exception.function.FunctionThrowerJSONException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Component;

@Component
public class JsonManagerComponent {

    public <T> T tryToGet(FunctionThrowerJSONException<Void, T> converter, Exception exceptionToThrow)
            throws Exception {
        try{
            return converter.apply(null);
        }catch(JSONException jsonException){
            throw exceptionToThrow;
        }
    }

}
