package fr.shoppo.msadmin.infrastructure.exception;

import static fr.shoppo.msadmin.domain.constant.MessageConstantEnum.ADMIN_ERROR_LOGIN;

public class AdminErrorLogin extends AdminException {

    public AdminErrorLogin() {
        super(ADMIN_ERROR_LOGIN);
    }


}
