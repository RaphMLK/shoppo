package fr.shoppo.mainmsinterface.domain.bo.commerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommercantTest {

    private Integer id;
    private String email;
    private String password;
    private Commerce commerce;
    private Commercant commercantObject;

    @BeforeEach
    void setUp(){
        id = 1;
        email = "toto@gmail.com";
        password = "password";
        commerce = new Commerce();
        commercantObject = new Commercant();
        commercantObject.setId(id);
        commercantObject.setEmail(email);
        commercantObject.setPassword(password);
        commercantObject.setCommerce(commerce);
    }

    @Test
    void getId(){
        assertEquals(id, commercantObject.getId());
    }

    @Test
    void setId(){
        var idTest = 2;
        commercantObject.setId(idTest);
        assertEquals(idTest, commercantObject.getId());
    }

    @Test
    void getEmail(){
        assertEquals(email, commercantObject.getEmail());
    }

    @Test
    void setEmail(){
        var emailTest = "test@gmail.com";
        commercantObject.setEmail(emailTest);
        assertEquals(emailTest, commercantObject.getEmail());
    }

    @Test
    void getPassword(){
        assertEquals(password, commercantObject.getPassword());
    }

    @Test
    void setPassword(){
        var passwordTest = "passwordTest";
        commercantObject.setPassword(passwordTest);
        assertEquals(passwordTest, commercantObject.getPassword());
    }

    @Test
    void getCommerce(){
        assertEquals(commerce, commercantObject.getCommerce());
    }

    @Test
    void setCommerce(){
        var commerceTest = new Commerce();
        commerceTest.setDescription("description");
        commercantObject.setCommerce(commerceTest);
        assertEquals(commerceTest, commercantObject.getCommerce());
    }
}
