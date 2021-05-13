package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManageAdminServiceImplTest {

    private Class<ManageAdminServiceImpl> manageAdminServiceClass;
    private ManageAdminServiceImpl manageAdminService;
    private RabbitTemplate template;

    private final String exchangeAdminName = "exchange_admin";
    private final String routingKeyResetPasswordAdmin = "routingKeyResetPasswordAdmin";

    private final String routingKeyLoginAdmin = "routingKeyLoginAdmin";

    @BeforeEach
    void setUp(){
        manageAdminServiceClass = ManageAdminServiceImpl.class;
        manageAdminService = new ManageAdminServiceImpl();
        template = mock(RabbitTemplate.class);
        manageAdminService.setTemplate(template);
        DirectExchange exchangeAdmin = mock(DirectExchange.class);

        manageAdminService.setExchangeAdmin(exchangeAdmin);
        manageAdminService.setRoutingKeyResetPasswordAdmin(routingKeyResetPasswordAdmin);
        when(exchangeAdmin.getName()).thenReturn(exchangeAdminName);

        manageAdminService.setRoutingKeyLoginAdmin(routingKeyLoginAdmin);
    }

    @Test
    void manageAdminServiceImplAnnotation(){
        assertNotNull(manageAdminServiceClass.getAnnotation(Service.class));
    }

    @Test
    void resetPasswordReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageAdminServiceClass.getDeclaredMethod("resetPassword", String.class).getReturnType());
    }

    @Test
    void resetPassword(){
        var email = "toto@gmail.com";
        when(template.convertSendAndReceive(
                exchangeAdminName,
                routingKeyResetPasswordAdmin,
                email)).thenReturn("ok");
        assertEquals("ok", manageAdminService.resetPassword(email));
    }

    @Test
    void loginReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageAdminServiceClass.getDeclaredMethod("login", User.class).getReturnType());
    }

    @Test
    void login() {
        var user = new User();
        user.setEmail("toto@gmail.com");
        when(template.convertSendAndReceive(exchangeAdminName,
                routingKeyLoginAdmin,
                user)).thenReturn("ok");
        assertEquals("ok", manageAdminService.login(user));
    }
}
