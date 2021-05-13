package fr.shoppo.msclient.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientNotFoundExceptionTest {

    @Test
    void should_extendsClientException(){
        assertThrows(ClientException.class,() -> {throw new ClientNotFoundException();});
    }

}