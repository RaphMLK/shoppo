package fr.shoppo.msclient.infrastructure.exception;

import static fr.shoppo.msclient.domain.constant.MessageConstantEnum.CLIENT_ERROR_LOGIN;

public class ClientErrorLogin extends ClientException {

    public ClientErrorLogin() { super(CLIENT_ERROR_LOGIN); }
}
