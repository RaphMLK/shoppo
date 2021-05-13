package fr.shoppo.msnotification.domain.bo.commerce;

import fr.shoppo.msnotification.domain.bo.Adresse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommerceTest {

    @Test
    void should_have_accessors(){
        var commerce = new Commerce();
        commerce.setSiretCode("test");
        commerce.setSirenCode("test");

        commerce.setEnseigne("test");
        commerce.setLienPhoto("test");
        commerce.setDescription("test");

        var addr = new Adresse();
        addr.setVille("test");
        addr.setNumeroRue("test");
        addr.setLibelleRue("test");
        addr.setCodePostal("test");

        commerce.setAdresse(addr);

        var codeNaf = new CodeNaf();
        var commercants = new ArrayList<Commercant>();

        commerce.setCodeNaf(codeNaf);
        commerce.setCommercants(commercants);

        assertEquals("test",commerce.getSiretCode());
        assertEquals("test",commerce.getSirenCode());
        assertEquals("test",commerce.getDescription());
        assertEquals("test",commerce.getEnseigne());
        assertEquals("test",commerce.getLienPhoto());

        assertEquals(addr,commerce.getAdresse());
        assertEquals(codeNaf,commerce.getCodeNaf());
        assertEquals(commercants,commerce.getCommercants());

        assertEquals("test",addr.getCodePostal());
        assertEquals("test",addr.getLibelleRue());
        assertEquals("test",addr.getNumeroRue());
        assertEquals("test",addr.getVille());

        assertSame(String.class,commerce.toString().getClass());
    }

}