package fr.shoppo.msclient.infrastructure.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    @Test
    void should_have_accessors(){
        var client = new Client();

        client.setPassword("test");
        client.setEmail("test");
        client.setId(0);
        client.setChangePassword(false);

        assertEquals("test",client.getPassword());
        assertEquals("test",client.getEmail());
        assertEquals(0,client.getId());
        assertEquals(false,client.getChangePassword());
    }

}