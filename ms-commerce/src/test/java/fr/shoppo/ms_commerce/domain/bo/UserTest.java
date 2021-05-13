package fr.shoppo.ms_commerce.domain.bo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void should_have_accessors(){
        var user = new User();
        user.setEmail("test");
        user.setPassword("test");

        assertEquals("test",user.getEmail());
        assertEquals("test",user.getPassword());
    }
}