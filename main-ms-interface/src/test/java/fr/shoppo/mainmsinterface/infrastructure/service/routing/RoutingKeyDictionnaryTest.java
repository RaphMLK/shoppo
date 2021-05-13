package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestPropertySource("classpath:application.yml")
@SpringBootTest(classes = RoutingKeyDictionnary.class)
class RoutingKeyDictionnaryTest {
    
    RoutingKeyDictionnary routingKeyDictionnary;

    @Test
    void should_routingKeyLoginAdmin(){
        assertEquals("admin_login",routingKeyDictionnary.routingKeyLoginAdmin);
    }
    @Test
    void should_routingKeyLoginClient(){
        assertEquals("client_login",routingKeyDictionnary.routingKeyLoginClient);
    }
    @Test
    void should_routingKeyLoginCommercant(){
        assertEquals("commercant_login",routingKeyDictionnary.routingKeyLoginCommercant);
    }
    @Test
    void should_routingKeyCreateClient(){
        assertEquals("create_client",routingKeyDictionnary.routingKeyCreateClient);
    }
    @Test
    void should_routingKeyCreateCommerce(){
        assertEquals("create_commerce",routingKeyDictionnary.routingKeyCreateCommerce);
    }

    @Test
    void should_routingKeyUpdateCommerce(){
        assertEquals("update_commerce",routingKeyDictionnary.routingKeyUpdateCommerce);
    }

    @Test
    void should_routingKeyAddCommercant(){
        assertEquals("add_commercant",routingKeyDictionnary.routingKeyAddCommercant);
    }

    @Test
    void should_routingKeyDeleteCommercant(){
        assertEquals("delete_commercant",routingKeyDictionnary.routingKeyDeleteCommercant);
    }

    @Test
    void should_routingKeyAddProduct(){
        assertEquals("add_product",routingKeyDictionnary.routingKeyAddProduct);
    }
    @Test
    void should_routingKeyResetPasswordAdmin(){
        assertEquals("admin_reset_password",routingKeyDictionnary.routingKeyResetPasswordAdmin);
    }
    @Test
    void should_routingKeyResetPasswordClient(){
        assertEquals("client_reset_password",routingKeyDictionnary.routingKeyResetPasswordClient);
    }
    @Test
    void should_routingKeyResetPasswordCommercant(){
        assertEquals("commercant_reset_password",routingKeyDictionnary.routingKeyResetPasswordCommercant);
    }
    @Test
    void should_routingKeyChangePasswordNeedClient(){
        assertEquals("client_change_password_need",routingKeyDictionnary.routingKeyChangePasswordNeedClient);
    }
    @Test
    void should_routingKeyChangePasswordNeedCommercant(){
        assertEquals("commercant_change_password_need",routingKeyDictionnary.routingKeyChangePasswordNeedCommercant);
    }

    @Test
    void should_routingKeyFindCommercant(){
        assertEquals("find_commercant",routingKeyDictionnary.routingKeyFindCommercant);
    }

    @Autowired
    public void setRoutingKeyDictionnary(RoutingKeyDictionnary routingKeyDictionnary) {
        this.routingKeyDictionnary = routingKeyDictionnary;
    }
}