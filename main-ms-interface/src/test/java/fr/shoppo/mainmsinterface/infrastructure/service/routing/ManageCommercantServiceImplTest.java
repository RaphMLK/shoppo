package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commerce;
import fr.shoppo.mainmsinterface.domain.bo.commerce.FindCommercantInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManageCommercantServiceImplTest {

    private Class<ManageCommercantServiceImpl> manageCommercantServiceClass;
    private ManageCommercantServiceImpl manageCommercantService;
    private RabbitTemplate template;

    private final String exchangeCommerceName = "exchange_commerce";
    private final String routingKeyResetPasswordCommercant= "routingKeyResetPasswordCommercant";

    private final String routingKeyChangePasswordNeedCommercant= "routingKeyChangePasswordNeedCommercant";

    private final String routingKeyLoginCommercant= "routingKeyLoginCommercant";
    private final String routingKeyFindCommercant = "routingKeyFindCommercant";
    private final String routingKeyUpdateCommerce = "routingKeyUpdateCommerce";

    @BeforeEach
    void setUp(){
        manageCommercantServiceClass = ManageCommercantServiceImpl.class;
        manageCommercantService = new ManageCommercantServiceImpl();
        template = mock(RabbitTemplate.class);
        DirectExchange exchangeCommerce = mock(DirectExchange.class);
        manageCommercantService.setTemplate(template);

        manageCommercantService.setExchangeCommerce(exchangeCommerce);
        manageCommercantService.setRoutingKeyResetPasswordCommercant(routingKeyResetPasswordCommercant);
        when(exchangeCommerce.getName()).thenReturn(exchangeCommerceName);

        manageCommercantService.setRoutingKeyChangePasswordNeedCommercant(routingKeyChangePasswordNeedCommercant);

        manageCommercantService.setRoutingKeyLoginCommercant(routingKeyLoginCommercant);
        manageCommercantService.setRoutingKeyFindCommercant(routingKeyFindCommercant);
        manageCommercantService.setRoutingKeyUpdateCommerce(routingKeyUpdateCommerce);
    }

    @Test
    void manageCommercantServiceImplAnnotation(){
        assertNotNull(manageCommercantServiceClass.getAnnotation(Service.class));
    }

    @Test
    void resetPasswordReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageCommercantServiceClass.getDeclaredMethod("resetPassword", String.class).getReturnType());
    }

    @Test
    void resetPassword() {
        var email = "toto@gmail.com";
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyResetPasswordCommercant,
                email
        )).thenReturn("ok");

        assertEquals("ok", manageCommercantService.resetPassword(email));
    }

    @Test
    void changePasswordReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageCommercantServiceClass.getDeclaredMethod("changePassword", User.class).getReturnType());
    }

    @Test
    void changePassword() {
        var user = new User();
        user.setEmail("toto@gmail.com");

        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyChangePasswordNeedCommercant,
                user
        )).thenReturn("ok");

        assertEquals("ok", manageCommercantService.changePassword(user));
    }

    @Test
    void loginReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageCommercantServiceClass.getDeclaredMethod("login", User.class).getReturnType());
    }

    @Test
    void login() {
        var user = new User();
        user.setEmail("toto@gmail.com");

        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyLoginCommercant,
                user
        )).thenReturn("ok");

        assertEquals("ok", manageCommercantService.login(user));
    }

    @Test
    void findCommercant(){
        var emailOrSiret = "toto@gmail.com";
        var withProducts = false;
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyFindCommercant,
                FindCommercantInput.of(emailOrSiret,withProducts)
        )).thenReturn("ok");
        assertEquals("ok", manageCommercantService.findCommercant(emailOrSiret, withProducts));
    }

    @Test
    void update(){
        var commerce = new Commerce();
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyUpdateCommerce,
               commerce
        )).thenReturn("ok");
        assertEquals("ok", manageCommercantService.update(commerce));
    }

    @Test
    void findCommercantReturnType() throws NoSuchMethodException{
        assertEquals(String.class, manageCommercantServiceClass.getDeclaredMethod("findCommercant", String.class, boolean.class).getReturnType());
    }

    @Test
    void updateReturnType() throws NoSuchMethodException{
        assertEquals(String.class, manageCommercantServiceClass.getDeclaredMethod("update", Commerce.class).getReturnType());
    }
}
