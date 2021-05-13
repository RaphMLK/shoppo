package fr.shoppo.mainmsinterface.infrastructure.function;

import fr.shoppo.mainmsinterface.infrastructure.service.exception.function.FunctionThrowerJSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class FunctionThrowerJSONExceptionTest {

    JSONExceptionExtendsTestMocking mockExtends;

    @Test
    public void should_handle_every_function_that_thrown_jsonException()  {
        mockExtends = mock(JSONExceptionExtendsTestMocking.class);

        FunctionThrowerJSONException<Void,String> toStringBadWay =
                (Void) -> new JSONObject("1243").toString();
        FunctionThrowerJSONException<Void,String> throwExtends =
                (Void) -> {throw new JSONExceptionExtendsTestMocking("something");};

        assertThrows(JSONException.class,() -> toStringBadWay.apply(null));
        assertThrows(JSONException.class,() -> toStringBadWay.apply(null));
    }

    private static class JSONExceptionExtendsTestMocking extends JSONException {
        public JSONExceptionExtendsTestMocking(String s) {
            super(s);
        }
    }

}