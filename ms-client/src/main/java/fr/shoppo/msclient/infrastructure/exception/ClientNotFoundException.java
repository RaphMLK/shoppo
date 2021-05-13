package fr.shoppo.msclient.infrastructure.exception;

import static fr.shoppo.msclient.domain.constant.MessageConstantEnum.CLIENT_NOT_FOUND;

public class ClientNotFoundException extends ClientException{
    public ClientNotFoundException() {
        super(CLIENT_NOT_FOUND);
    }

}
