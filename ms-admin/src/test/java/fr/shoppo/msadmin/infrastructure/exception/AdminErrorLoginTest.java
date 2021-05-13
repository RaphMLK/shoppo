package fr.shoppo.msadmin.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AdminErrorLoginTest {

    @Test
    void should_throw_error_login_children_of_admin_Exception(){
        assertThrows(AdminException.class,() -> {throw new AdminErrorLogin();});
    }

}