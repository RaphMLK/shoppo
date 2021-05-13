package fr.shoppo.mainmsinterface.domain.bo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTest {

    private String token;
    private Token tokenObject;

    @BeforeEach
    void setUp(){
        token = "1234567890";
        tokenObject = new Token();
        tokenObject.setValue(token);
    }

    @Test
    void getToken(){
        assertEquals(token, tokenObject.getValue());
    }

    @Test
    void setToken(){
        var tokenTest = "abcdefgf";
        tokenObject.setValue(tokenTest);
        assertEquals(tokenTest, tokenObject.getValue());
    }
}
