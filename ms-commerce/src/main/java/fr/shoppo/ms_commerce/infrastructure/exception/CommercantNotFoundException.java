package fr.shoppo.ms_commerce.infrastructure.exception;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.COMMERCANT_NOT_FOUND;

public class CommercantNotFoundException extends CommerceException {

    public CommercantNotFoundException() {
        super(COMMERCANT_NOT_FOUND);
    }
}
