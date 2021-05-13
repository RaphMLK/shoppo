package fr.shoppo.msadmin.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AdminNotFoundExceptionTest {
    @Test
    void should_extendsAdminException(){
        assertThrows(AdminException.class,() -> {throw new AdminNotFoundException();});
    }

}