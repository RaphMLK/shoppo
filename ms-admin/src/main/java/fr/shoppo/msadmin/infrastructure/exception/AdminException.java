package fr.shoppo.msadmin.infrastructure.exception;

import fr.shoppo.msadmin.domain.constant.MessageConstantEnum;

import static java.lang.String.format;

public class AdminException extends Exception{

    public AdminException(MessageConstantEnum messageConstantEnum) {
        super(messageConstantEnum.toString());
    }

    public AdminException(MessageConstantEnum messageConstantEnum, String args) {
        super(format("%s : %s",messageConstantEnum.toString(),args));
    }
}
