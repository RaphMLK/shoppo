package fr.shoppo.mainmsinterface.infrastructure.service.exception;

import static java.lang.String.format;

public class CommercantRequestException extends Exception{

    public CommercantRequestException(String msg){
        super(msg);
    }

    public CommercantRequestException(String msg, String args){
        super(format("%s : %s", msg, args));
    }
}
