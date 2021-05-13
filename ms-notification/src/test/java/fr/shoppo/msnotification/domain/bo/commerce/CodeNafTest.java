package fr.shoppo.msnotification.domain.bo.commerce;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CodeNafTest {
    @Test
    void should_have_intitule_accessor(){
        var codeNafEntity = new CodeNaf();
        codeNafEntity.setIntitule("test");
        codeNafEntity.setCode("test");
        var commerce = new ArrayList<Commerce>();
        codeNafEntity.setCommerce(commerce);
        assertEquals(commerce,codeNafEntity.getCommerce());
        assertEquals("test",codeNafEntity.getCode());
        assertEquals("test",codeNafEntity.getIntitule());
        assertSame(String.class,codeNafEntity.toString().getClass());

    }
}