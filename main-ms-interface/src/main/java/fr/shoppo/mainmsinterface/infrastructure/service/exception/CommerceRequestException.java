package fr.shoppo.mainmsinterface.infrastructure.service.exception;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import static java.lang.String.format;

public class CommerceRequestException extends Exception{

    String message;
    String method;
    String uri;
    JSONObject parameters;

    public CommerceRequestException(String message, String method, String uri, JSONObject parameters) {
        this.message = message;
        this.method = method;
        this.uri = uri;
        this.parameters = parameters;
    }

    /*public CommerceRequestException() {
    }*/

    public CommerceRequestException(String message) {
        super(message);
    }

    /*public CommerceRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommerceRequestException(Throwable cause) {
        super(cause);
    }

    public CommerceRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }*/

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public JSONObject getParameters() {
        return parameters;
    }

    public void setParameters(JSONObject parameters) {
        this.parameters = parameters;
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    public String toString() {
        return format("%s %s %s %s",
                message,
                method,
                uri,
                parameters);
    }
}
