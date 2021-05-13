package fr.shoppo.mainmsinterface.infrastructure.service.exception;

import static java.lang.String.format;

public class ClientRequestException extends Exception{

    public ClientRequestException(String msg){
        super(msg);
    }

    public ClientRequestException(String msg, String args){
        super(format("%s : %s", msg, args));
    }
}
