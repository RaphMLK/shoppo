package fr.shoppo.msclient.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.msclient.domain.bo.SoldeInput;
import fr.shoppo.msclient.domain.bo.UserNewPassword;
import fr.shoppo.msclient.domain.service.ManageClientService;
import fr.shoppo.msclient.domain.service.NotificationService;
import fr.shoppo.msclient.infrastructure.entity.Client;
import fr.shoppo.msclient.infrastructure.exception.ClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.shoppo.msclient.domain.constant.MessageConstantEnum.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManageClientControllerTest {

    ManageClientService clientService;

    NotificationService notificationService;

    ManageClientController manageClientController;

    Client roger;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup(){
        clientService = mock(ManageClientService.class);
        notificationService = mock(NotificationService.class);

        manageClientController = new ManageClientController(clientService,notificationService, null);

        doNothing().when(notificationService).createClient(any());
        doNothing().when(notificationService).resetPassword(any());
        roger = new Client();
        roger.setEmail("test");
        roger.setPassword("test");
        roger.setId(0);
        roger.setChangePassword(false);
    }

    @Test
    void should_create_client() throws ClientException {
        when(clientService.createClient(roger)).thenReturn(roger);

        assertEquals(OK.toString(),manageClientController.createClient(roger));
    }

    @Test
    void should_create_client_but_return_client_exception() throws ClientException {
        when(clientService.createClient(roger)).thenThrow(new ClientException(ERREUR,"test"));

        assertEquals(format("%s : %s",ERREUR.toString(),"test"),manageClientController.createClient(roger));
    }

    @Test
    void should_reset_password() throws ClientException {
        when(clientService.resetPassword("test")).thenReturn(roger);

        assertEquals(OK.toString(),manageClientController.resetPassword("test"));
    }

    @Test
    void should_reset_password_with_error_null_client() throws ClientException {
        when(clientService.resetPassword("test")).thenReturn(null);

        assertEquals(ERREUR.toString(),manageClientController.resetPassword("test"));
    }


    @Test
    void should_reset_password_send_message_from_client_exception() throws ClientException {
        when(clientService.resetPassword("test")).thenThrow(new ClientException(ERREUR,"test"));

        assertEquals(format("%s : %s",ERREUR.toString(),"test"),manageClientController.resetPassword("test"));
    }

    @Test
    void should_login() throws ClientException, JsonProcessingException {
        when(clientService.login("test","test")).thenReturn(roger);

        ObjectMapper objectMapper = new ObjectMapper();
        assertEquals("OK#"+objectMapper.writeValueAsString(roger),manageClientController.login(roger));
    }

    @Test
    void should_login_but_return_client_exception() throws ClientException {
        when(clientService.login("test","test")).thenThrow(new ClientException(ERREUR,"test"));

        assertEquals(format("%s : %s",ERREUR.toString(),"test"),manageClientController.login(roger));
    }

    @Test
    void changePasswordNeedTest() throws ClientException{
        UserNewPassword userNewPassword = new UserNewPassword();
        userNewPassword.setEmail("test");
        userNewPassword.setPassword("test");

        when(clientService.changePasswordNeed("test", "test")).thenReturn(roger);

        assertEquals(OK.toString(), manageClientController.changePasswordNeed(userNewPassword));

        when(clientService.changePasswordNeed("test", "test")).thenThrow(new ClientException(ERREUR,"test"));

        assertEquals(format("%s : %s",ERREUR.toString(),"test"), manageClientController.changePasswordNeed(userNewPassword));
    }

    @Test
    void getClientByQrCode_ClientException() throws ClientException {
        var qrcode ="qrcode";
        when(clientService.getClientByQrCode(qrcode)).thenThrow(new ClientException(ERREUR,"test"));
        assertEquals(format("%s : %s",ERREUR.toString(),"test"), manageClientController.getClientByQrCode(qrcode));
    }

    @Test
    void getClientByQrCode_Succes() throws ClientException, JsonProcessingException {
        var qrcode ="qrcode";
        when(clientService.getClientByQrCode(qrcode)).thenReturn(new Client());
        assertEquals(mapper.writeValueAsString(new Client()), manageClientController.getClientByQrCode(qrcode));
    }

    @Test
    void updateSolde_error() {
        var soldeInput = new SoldeInput();
        soldeInput.setEmail("email");
        soldeInput.setAmount(2);
        when(clientService.updateSolde(soldeInput.getEmail(),soldeInput.getAmount(),true)).thenReturn(false);
        assertEquals(ERREUR_SOLDE_A_ZERO.toString(), manageClientController.updateSolde(soldeInput));
    }

    @Test
    void updateSolde_ok() {
        var soldeInput = new SoldeInput();
        soldeInput.setEmail("email");
        soldeInput.setAmount(2);
        when(clientService.updateSolde(soldeInput.getEmail(),soldeInput.getAmount(),true)).thenReturn(true);
        assertEquals(OK.toString(), manageClientController.updateSolde(soldeInput));
    }


    @Test
    void getClient_ClientException() throws ClientException {
        var email ="email";
        when(clientService.getClientByEmail(email)).thenThrow(new ClientException(ERREUR,"test"));
        assertEquals(format("%s : %s",ERREUR.toString(),"test"), manageClientController.getClient(email));
    }

    @Test
    void getClient_Succes() throws ClientException, JsonProcessingException {
        var email ="email";
        when(clientService.getClientByEmail(email)).thenReturn(new Client());
        assertEquals(mapper.writeValueAsString(new Client()), manageClientController.getClient(email));
    }


}