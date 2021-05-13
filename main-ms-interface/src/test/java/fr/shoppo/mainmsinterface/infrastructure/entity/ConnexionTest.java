package fr.shoppo.mainmsinterface.infrastructure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConnexionTest {

    private Integer id;
    private String email;
    private String token;
    private String role;
    private Connexion connexionObject;

    @BeforeEach
    void setUp(){
        email = "toto@gmail.com";
        id = 1;
        token = "1234567";
        role = "ADMIN";
        connexionObject = new Connexion(email, token,role);
        connexionObject.setId(id);
    }

    @Test
    void getId(){
        assertEquals(id,connexionObject.getId());
    }

    @Test
    void setId(){
        var idTest = 2;
        connexionObject.setId(idTest);
        assertEquals(idTest,connexionObject.getId());
    }

    @Test
    void getEmail(){
        assertEquals(email,connexionObject.getEmail());
    }

    @Test
    void setEmail(){
        var emailTest = "titi@gmail.com";
        connexionObject.setEmail(emailTest);
        assertEquals(emailTest,connexionObject.getEmail());
    }

    @Test
    void getToken(){
        assertEquals(token,connexionObject.getToken());
    }

    @Test
    void setToken(){
        var tokenTest = "azerty";
        connexionObject.setToken(tokenTest);
        assertEquals(tokenTest,connexionObject.getToken());
    }

    @Test
    void getRole(){
        assertEquals(role,connexionObject.getRole());
    }

    @Test
    void setRole(){
        var roleTest = "CLIENT";
        connexionObject.setRole(roleTest);
        assertEquals(roleTest,connexionObject.getRole());
    }

    @Test
    void constructeurVide(){
        var connexionTest = new Connexion();
        connexionTest.setId(2);
        assertEquals(2,connexionTest.getId());
    }

    @Test
    void constructeurEmailToken(){
        var connexionTest = new Connexion("toto@gmail.com", "123456");
        assertEquals("toto@gmail.com",connexionTest.getEmail());
        assertEquals("123456",connexionTest.getToken());
    }

}
