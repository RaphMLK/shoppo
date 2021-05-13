package fr.shoppo.msadmin.infrastructure.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AdminTest {
    @Test
    void should_have_accessors(){
        var admin = new Admin();

        admin.setEmail("test");
        admin.setPassword("test");
        admin.setId(0);

        assertEquals("test",admin.getEmail());
        assertEquals("test",admin.getPassword());
        assertEquals(0,admin.getId());
    }

    @Test
    void should_have_an_equals_on_mail(){
        var admin = new Admin();

        admin.setEmail("test");
        admin.setPassword("test");


        var admin1 = new Admin();

        admin1.setEmail("test");
        admin1.setPassword("test1");


        assertEquals(admin,admin1);
        assertEquals(admin.hashCode(),admin1.hashCode());

        assertNotEquals(admin.getPassword(),admin1.getPassword());

        assertEquals(admin,admin);

        assertNotEquals(admin,new Object());
    }
}