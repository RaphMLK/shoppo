package fr.shoppo.mainmsinterface.infrastructure.service.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommerceRequestExceptionTest {

    private String message;
    private String method;
    private String uri;
    private JSONObject parameters;
    private CommerceRequestException commerceRequestExceptionObject;

    @BeforeEach
    void setUp() throws JSONException {
        message = "message";
        method = "method";
        uri = "uri";
        parameters = new JSONObject("{" +
                "\"test\":\"test\"" +
                "}");
        commerceRequestExceptionObject = new CommerceRequestException(message,method,uri,parameters);
    }

    @Test
    void getMessage(){
        assertEquals(message, commerceRequestExceptionObject.getMessage());
    }

    @Test
    void setMessage(){
        var messageTest = "message test";
        commerceRequestExceptionObject.setMessage(messageTest);
        assertEquals(messageTest, commerceRequestExceptionObject.getMessage());
    }

    @Test
    void getMethod(){
        assertEquals(method, commerceRequestExceptionObject.getMethod());
    }

    @Test
    void setMethod(){
        var methodeTest = "methode test";
        commerceRequestExceptionObject.setMethod(methodeTest);
        assertEquals(methodeTest, commerceRequestExceptionObject.getMethod());
    }

    @Test
    void getUri(){
        assertEquals(uri, commerceRequestExceptionObject.getUri());
    }

    @Test
    void setUri(){
        var uriTest = "uri test";
        commerceRequestExceptionObject.setUri(uriTest);
        assertEquals(uriTest, commerceRequestExceptionObject.getUri());
    }

    @Test
    void getParameters(){
        assertEquals(parameters, commerceRequestExceptionObject.getParameters());
    }

    @Test
    void setParameters() throws JSONException {
        var parametersTest = new JSONObject("{" +
                "\"test\":\"toto\"" +
                "}");
        commerceRequestExceptionObject.setParameters(parametersTest);
        assertEquals(parametersTest, commerceRequestExceptionObject.getParameters());
    }

    @Test
    void getCause(){
        var throwTest = commerceRequestExceptionObject.getCause();
        assertNull(throwTest);
    }

    @Test
    void toStringTest(){
        var response = "message method uri {\"test\":\"test\"}";
        assertEquals(response,commerceRequestExceptionObject.toString());
    }
}
