package fr.shoppo.ms_commerce.infrastructure.exception;

import fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum;

import static java.lang.String.format;

public class CommerceException extends Exception{
    public CommerceException(MessageConstantEnum messageConstantEnum) {
        super(messageConstantEnum.toString());
    }

    public CommerceException(MessageConstantEnum messageConstantEnum, String args) {
        super(format("%s : %s",messageConstantEnum.toString(),args));
    }
}
