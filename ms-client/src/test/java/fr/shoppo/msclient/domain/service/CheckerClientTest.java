package fr.shoppo.msclient.domain.service;

import fr.shoppo.msclient.infrastructure.entity.Client;
import fr.shoppo.msclient.infrastructure.exception.ClientException;
import fr.shoppo.msclient.infrastructure.service.PasswordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckerClientTest {
    PasswordManager passwordManager;

    CheckerClient checkerClient;
    Client henry;
    @BeforeEach
    void setup(){
        passwordManager = mock(PasswordManager.class);
        checkerClient = new CheckerClient();

        checkerClient.setPasswordManager(passwordManager);

        when(passwordManager.hash(any())).thenReturn("secured");
        henry = new Client();
    }

    @Test
    void should_checkClient() throws ClientException {
        henry.setEmail("unmailvalide@mail.fr");
        henry.setPassword("test");
        assertTrue(checkerClient.checkClient(henry));
    }

    @Test
    void should_checkClient_null_throw() {
        assertThrows(ClientException.class,() -> checkerClient.checkClient(null));
        henry.setEmail("unmail");
        assertThrows(ClientException.class,() -> checkerClient.checkClient(henry));
        henry.setEmail(null);
        henry.setPassword("pass");
        assertThrows(ClientException.class,() -> checkerClient.checkClient(henry));
    }

    @Test
    void should_checkClient_have_bad_mail() throws ClientException {
        henry.setEmail("unmailinvalid");
        henry.setPassword("test");
        assertFalse(checkerClient.checkClient(henry));
    }
}