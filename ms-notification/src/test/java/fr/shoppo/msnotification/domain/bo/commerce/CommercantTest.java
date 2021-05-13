package fr.shoppo.msnotification.domain.bo.commerce;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommercantTest{

    @Test
    void should_have_accessors(){
        var commercant = new Commercant();
        commercant.setId(123);
        commercant.setEmail("test");
        commercant.setPassword("test");
        commercant.setOwner(false);


        var commerce = new Commerce();
        commercant.setCommerce(commerce);

        assertEquals("test",commercant.getEmail());
        assertEquals("test",commercant.getPassword());

        assertEquals(123,commercant.getId());
        assertSame(String.class,commercant.toString().getClass());
    }
}