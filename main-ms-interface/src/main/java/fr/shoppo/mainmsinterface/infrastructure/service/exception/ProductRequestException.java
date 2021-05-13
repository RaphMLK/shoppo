package fr.shoppo.mainmsinterface.infrastructure.service.exception;

import static java.lang.String.format;

public class ProductRequestException extends Exception {

    public ProductRequestException(String msg){
        super(msg);
    }

    public ProductRequestException(String msg, String args){
        super(format("%s : %s", msg, args));
    }
}
