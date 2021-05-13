package fr.shoppo.ms_commerce.infrastructure.exception;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.COMMERCANT_ERROR_LOGIN;

public class CommercantErrorLogin extends CommerceException {

    public CommercantErrorLogin() { super(COMMERCANT_ERROR_LOGIN); }


}
