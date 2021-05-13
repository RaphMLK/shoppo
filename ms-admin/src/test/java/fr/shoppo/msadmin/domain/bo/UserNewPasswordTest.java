package fr.shoppo.msadmin.domain.bo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserNewPasswordTest {

    @Test
    void should_have_accessors(){
        var unp = new UserNewPassword();

        unp.setEmail("test");
        unp.setPassword("test");

        assertEquals("test",unp.getEmail());
        assertEquals("test",unp.getPassword());
    }

    @Test
    void should_have_an_equals_on_mail(){
        var unp = new UserNewPassword("test","sdqdqdsq");
        var unp1 = new UserNewPassword("test","dsqds");


        assertEquals(unp,unp1);
        assertEquals(unp.hashCode(),unp1.hashCode());

        assertNotEquals(unp.getPassword(),unp1.getPassword());

        assertEquals(unp,unp);

        assertNotEquals(unp,new Object());
    }
}