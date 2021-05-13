package fr.shoppo.mainmsinterface.domain.bo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private String email;
    private String password;
    private User userObject;

    @BeforeEach
    void setUp(){
        email = "toto@gmail.com";
        password = "azerty";
        userObject = new User();
        userObject.setEmail(email);
        userObject.setPassword(password);
    }

    @Test
    void getEmail(){
        assertEquals(email, userObject.getEmail());
    }

    @Test
    void setEmail(){
        var emailTest = "titi@gmail.com";
        userObject.setEmail(emailTest);
        assertEquals(emailTest, userObject.getEmail());
    }

    @Test
    void getPassword(){
        assertEquals(password, userObject.getPassword());
    }

    @Test
    void setPassword(){
        var passwordTest = "1234567890";
        userObject.setPassword(passwordTest);
        assertEquals(passwordTest, userObject.getPassword());
    }
}
