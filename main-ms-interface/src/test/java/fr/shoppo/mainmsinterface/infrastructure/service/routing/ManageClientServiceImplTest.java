package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.bo.client.SoldeInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManageClientServiceImplTest {

    private Class<ManageClientServiceImpl> manageClientServiceClass;
    private ManageClientServiceImpl manageClientService;
    private RabbitTemplate template;

    private final String exchangeClientName = "exchange_client";
    private final String routingKeyResetPasswordClient = "routingKeyResetPasswordClient";

    private final String routingKeyChangePasswordNeedClient = "routingKeyChangePasswordNeedClient";

    private final String routingKeyLoginClient = "routingKeyLoginClient";

    private final String routingKeyCreateClient = "routingKeyCreateClient";

    private final String routingKeyGetClient = "routingKeyGetClient";
    private final String routingKeyGetClientByQrCode = "routingKeyGetClientByQrCode";
    private final String routingKeyUpdateSolde = "routingKeyUpdateSolde";

    @BeforeEach
    void setUp(){
        manageClientServiceClass = ManageClientServiceImpl.class;
        manageClientService = new ManageClientServiceImpl();
        template = mock(RabbitTemplate.class);
        DirectExchange exchangeClient = mock(DirectExchange.class);
        manageClientService.setTemplate(template);

        manageClientService.setExchangeClient(exchangeClient);
        manageClientService.setRoutingKeyResetPasswordClient(routingKeyResetPasswordClient);
        when(exchangeClient.getName()).thenReturn(exchangeClientName);

        manageClientService.setRoutingKeyChangePasswordNeedClient(routingKeyChangePasswordNeedClient);

        manageClientService.setRoutingKeyLoginClient(routingKeyLoginClient);

        manageClientService.setRoutingKeyCreateClient(routingKeyCreateClient);
        manageClientService.setRoutingKeyGetClient(routingKeyGetClient);
        manageClientService.setRoutingKeyGetClientByQrCode(routingKeyGetClientByQrCode);
        manageClientService.setRoutingKeyUpdateSolde(routingKeyUpdateSolde);
    }

    @Test
    void manageClientServiceImplAnnotation(){
        assertNotNull(manageClientServiceClass.getAnnotation(Service.class));
    }

    @Test
    void resetPasswordReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageClientServiceClass.getDeclaredMethod("resetPassword", String.class).getReturnType());
    }

    @Test
    void resetPassword() {
        var email = "toto@gmail.com";
        when(template.convertSendAndReceive(
                exchangeClientName,
                routingKeyResetPasswordClient,
                email
        )).thenReturn("ok");

        assertEquals("ok", manageClientService.resetPassword(email));
    }

    @Test
    void changePasswordReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageClientServiceClass.getDeclaredMethod("changePassword", User.class).getReturnType());
    }

    @Test
    void changePassword() {
        var user = new User();
        user.setEmail("toto@gmail.com");

        when(template.convertSendAndReceive(
                exchangeClientName,
                routingKeyChangePasswordNeedClient,
                user
        )).thenReturn("ok");

        assertEquals("ok", manageClientService.changePassword(user));
    }

    @Test
    void loginReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageClientServiceClass.getDeclaredMethod("login", User.class).getReturnType());
    }

    @Test
    void login() {
        var user = new User();
        user.setEmail("toto@gmail.com");

        when(template.convertSendAndReceive(
                exchangeClientName,
                routingKeyLoginClient,
                user
        )).thenReturn("ok");

        assertEquals("ok", manageClientService.login(user));
    }

    @Test
    void createClientReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageClientServiceClass.getDeclaredMethod("createClient", User.class).getReturnType());
    }

    @Test
    void createClient() {
        var user = new User();
        user.setEmail("toto@gmail.com");

        when(template.convertSendAndReceive(
                exchangeClientName,
                routingKeyCreateClient,
                user
        )).thenReturn("ok");

        assertEquals("ok", manageClientService.createClient(user));
    }

    @Test
    void getClientByQrCode() {
        when(template.convertSendAndReceive(
                exchangeClientName,
                routingKeyGetClientByQrCode,
                "qrcode")).thenReturn("ok");
        assertEquals("ok", manageClientService.getClientByQrCode("qrcode"));
    }

    @Test
    void getClient() {
        when(template.convertSendAndReceive(
                exchangeClientName,
                routingKeyGetClient,
                "toto@gmail.com")).thenReturn("ok");
        assertEquals("ok", manageClientService.getClient("toto@gmail.com"));
    }

    @Test
    void updateSolde() {
        var soldeInput = SoldeInput.of("email", 1);
        when(template.convertSendAndReceive(
                exchangeClientName,
                routingKeyUpdateSolde,
                soldeInput)).thenReturn("ok");
        assertEquals("ok", manageClientService.updateSolde(soldeInput));
    }
}
