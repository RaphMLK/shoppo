package fr.shoppo.msadmin.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static fr.shoppo.msadmin.domain.constant.MessageConstantEnum.OK;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdminExceptionTest {
    @Test
    void should_throw_exception_with_enum_and_message(){
        assertThrows(AdminException.class,() ->{throw new AdminException(OK,"test");});
    }

    @Test
    void should_throw_exception_with_enum(){
        assertThrows(AdminException.class,() ->{throw new AdminException(OK);});
    }
}