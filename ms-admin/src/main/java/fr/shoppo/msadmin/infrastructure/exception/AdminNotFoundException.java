package fr.shoppo.msadmin.infrastructure.exception;


import static fr.shoppo.msadmin.domain.constant.MessageConstantEnum.ADMIN_NOT_FOUND;

public class AdminNotFoundException extends AdminException{

    public AdminNotFoundException() {
        super(ADMIN_NOT_FOUND);
    }
}
