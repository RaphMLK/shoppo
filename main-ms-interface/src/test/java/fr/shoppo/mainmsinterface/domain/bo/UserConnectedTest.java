package fr.shoppo.mainmsinterface.domain.bo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserConnectedTest {

    private String token;
    private String role;
    private UserConnected userObject;

    @BeforeEach
    void setUp(){
        token = "1234567890";
        role = "ADMIN";
        userObject = new UserConnected();
        userObject.setToken(token);
        userObject.setRole(role);
    }

    @Test
    void getToken(){
        assertEquals(token, userObject.getToken());
    }

    @Test
    void setToken(){
        var tokenTest = "azerrty";
        userObject.setToken(tokenTest);
        assertEquals(tokenTest, userObject.getToken());
    }

    @Test
    void getRole(){
        assertEquals(role, userObject.getRole());
    }

    @Test
    void setRole(){
        var roleTest = "CLIENT";
        userObject.setRole(roleTest);
        assertEquals(roleTest, userObject.getRole());
    }

    @Test
    void should_have_an_equals(){
        var unp = new UserConnected();
        var unp1 = new UserConnected();

        unp.setRole("test");
        unp.setToken("test");

        unp1.setRole("test");
        unp1.setToken("test");

        assertEquals(unp,unp1);
        assertEquals(unp.hashCode(),unp1.hashCode());

        assertEquals(unp,unp);

        assertNotEquals(unp,new Object());
    }
}
