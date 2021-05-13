package fr.shoppo.msclient.infrastructure.exception;

import fr.shoppo.msclient.domain.constant.MessageConstantEnum;

import static java.lang.String.format;

public class ClientException extends Exception{
    public ClientException(MessageConstantEnum messageConstantEnum) {
        super(messageConstantEnum.toString());
    }

    public ClientException(MessageConstantEnum messageConstantEnum,String args) {
        super(format("%s : %s",messageConstantEnum.toString(),args));
    }
}
