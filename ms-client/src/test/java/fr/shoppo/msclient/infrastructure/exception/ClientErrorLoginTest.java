package fr.shoppo.msclient.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientErrorLoginTest {

    @Test
    void should_throw_error_login_children_of_client_Exception(){
        assertThrows(ClientException.class,() -> {throw new ClientErrorLogin();});
    }

}