package fr.shoppo.mainmsinterface.domain.bo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailTest {

    private String email;
    private Email emailObject;

    @BeforeEach
    void setUp(){
        email = "toto@gmail.com";
        emailObject = new Email(email);
    }

    @Test
    void getEmail(){
        assertEquals(email, emailObject.getValue());
    }

    @Test
    void setEmail(){
        var emailTest = "titi@gmail.com";
        emailObject.setValue(emailTest);
        assertEquals(emailTest, emailObject.getValue());
    }

    @Test
    void constructeurVide(){
        var email = new Email();
        var value = "toto@gmail.com";
        email.setValue(value);
        assertEquals(value, email.getValue());
    }
}
