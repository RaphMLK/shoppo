package fr.shoppo.mainmsinterface.domain.bo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdresseTest {

    private String codePostal;
    private String numeroRue;
    private String libelleRue;
    private String ville;
    private Adresse adresseObject;

    @BeforeEach
    void setUp(){
        codePostal = "59000";
        numeroRue = "18";
        libelleRue = "jean jaurès";
        ville = "Lille";
        adresseObject = new Adresse();
        adresseObject.setCodePostal(codePostal);
        adresseObject.setLibelleRue(libelleRue);
        adresseObject.setNumeroRue(numeroRue);
        adresseObject.setVille(ville);
    }

    @Test
    void setCodePostal(){
        var cp = "62000";
        adresseObject.setCodePostal(cp);
        assertEquals(cp, adresseObject.getCodePostal());
    }

    @Test
    void setNumeroRue(){
        var num = "20";
        adresseObject.setNumeroRue(num);
        assertEquals(num, adresseObject.getNumeroRue());
    }

    @Test
    void setLibelleRue(){
        var adr = "de béthune";
        adresseObject.setLibelleRue(adr);
        assertEquals(adr, adresseObject.getLibelleRue());
    }

    @Test
    void setVille(){
        var villeT = "Arras";
        adresseObject.setVille(villeT);
        assertEquals(villeT, adresseObject.getVille());
    }

    @Test
    void getCodePostal(){
        assertEquals(codePostal, adresseObject.getCodePostal());
    }

    @Test
    void getNumeroRue(){
        assertEquals(numeroRue, adresseObject.getNumeroRue());
    }

    @Test
    void getLibelleRue(){
        assertEquals(libelleRue, adresseObject.getLibelleRue());
    }

    @Test
    void getVille(){
        assertEquals(ville, adresseObject.getVille());
    }

}
