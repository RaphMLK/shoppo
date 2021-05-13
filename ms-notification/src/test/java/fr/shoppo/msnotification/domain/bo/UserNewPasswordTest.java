package fr.shoppo.msnotification.domain.bo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserNewPasswordTest {

    @Test
    void should_have_accessors(){
        var unp = new UserNewPassword();

        unp.setEmail("test");
        unp.setPassword("test");

        assertEquals("test",unp.getEmail());
        assertEquals("test",unp.getPassword());
    }

}