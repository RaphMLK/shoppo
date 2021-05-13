package fr.shoppo.msclient.infrastructure.service;

import fr.shoppo.msclient.domain.service.CheckerClient;
import fr.shoppo.msclient.domain.service.VFPStateManager;
import fr.shoppo.msclient.infrastructure.dao.ClientDao;
import fr.shoppo.msclient.infrastructure.entity.Client;
import fr.shoppo.msclient.infrastructure.exception.ClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static fr.shoppo.msclient.domain.constant.MessageConstantEnum.ERREUR_INVALID_INPUT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManageClientServiceImplTest {
    ClientDao clientDao;
    PasswordManager passwordManager;
    CheckerClient checkerClient;

    ManageClientServiceImpl manageClientService;
    VFPStateManager stateManager;

    Client henry;

    @BeforeEach
    void setup(){
        clientDao = mock(ClientDao.class);
        passwordManager = mock(PasswordManager.class);
        checkerClient = mock(CheckerClient.class);
        stateManager = mock(VFPStateManager.class);

        manageClientService= new ManageClientServiceImpl(clientDao);
        manageClientService.setCheckerClient(checkerClient);
        manageClientService.setPasswordManager(passwordManager);
        manageClientService.setStateManager(stateManager);

        henry = new Client();
        henry.setId(0);
        henry.setEmail("test");
        henry.setPassword("test");
        henry.setChangePassword(false);
    }

    @Test
    void should_reset_password() throws ClientException {
        when(clientDao.save(henry)).thenReturn(henry);
        when(clientDao.findByEmail("test")).thenReturn(Optional.of(henry));
        when(passwordManager.generateSecureRandomPassword()).thenReturn("secured");

        assertEquals(henry,manageClientService.resetPassword(henry.getEmail()));
    }


    @Test
    void should_reset_password_not_found() {
        when(clientDao.findByEmail("test")).thenReturn(Optional.empty());
        assertThrows(ClientException.class, () -> manageClientService.resetPassword(henry.getEmail()));
    }

    @Test
    void should_createClient() throws ClientException {
        when(checkerClient.checkClient(henry)).thenReturn(true);
        when(clientDao.findByEmail("test")).thenReturn(Optional.empty());
        when(clientDao.save(henry)).thenReturn(henry);

        assertEquals(henry,manageClientService.createClient(henry));
    }

    @Test
    void should_createClient_but_already_exist() throws ClientException {
        when(checkerClient.checkClient(henry)).thenReturn(true);
        when(clientDao.findByEmail("test")).thenReturn(Optional.of(henry));

        assertThrows(ClientException.class,() -> manageClientService.createClient(henry));
    }


    @Test
    void should_createClient_with_not_validating_data() throws ClientException {
        when(checkerClient.checkClient(henry)).thenReturn(false);
        when(clientDao.findByEmail("test")).thenReturn(Optional.empty());
        when(clientDao.save(henry)).thenReturn(henry);

        assertThrows(ClientException.class,() -> manageClientService.createClient(henry));
    }

    @Test
    void should_createClient_with_parser_isnt_ok() throws ClientException {
        when(checkerClient.checkClient(henry)).thenThrow(ClientException.class);

        assertThrows(ClientException.class,() -> manageClientService.createClient(henry));
    }

    @Test
    void should_login() throws ClientException {
        henry.setPassword("secured");
        when(clientDao.findByEmail("test")).thenReturn(Optional.of(henry));
        when(checkerClient.passwordInputParser("test")).thenReturn("secured");

        assertEquals(henry,manageClientService.login("test", "test"));
    }

    @Test
    void should_login_with_not_found() {
        when(clientDao.findByEmail("test")).thenReturn(Optional.empty());

        assertThrows(ClientException.class,()->manageClientService.login("test", "test"));
    }


    @Test
    void should_login_with_invalid_input_password() throws ClientException {
        henry.setPassword("secured");
        when(clientDao.findByEmail("test")).thenReturn(Optional.of(henry));
        when(checkerClient.passwordInputParser("test")).thenThrow(new ClientException(ERREUR_INVALID_INPUT,"Input is malformed"));

        assertThrows(ClientException.class,()->manageClientService.login("test", "test"));
    }

    @Test
    void should_login_with_invalid_password() throws ClientException {
        henry.setPassword("another_secured");
        when(clientDao.findByEmail("test")).thenReturn(Optional.of(henry));
        when(checkerClient.passwordInputParser("test")).thenReturn("secured");

        assertThrows(ClientException.class,()->manageClientService.login("test", "test"));
    }

    @Test
    void changePasswordNeedTest() throws ClientException{
        henry.setPassword("secured");
        when(clientDao.findByEmail("test")).thenReturn(Optional.empty());
        assertThrows(ClientException.class,()->manageClientService.changePasswordNeed("test", "test"));

        when(clientDao.findByEmail("test")).thenReturn(Optional.of(henry));
        when(checkerClient.passwordInputParser("test")).thenThrow(new ClientException(ERREUR_INVALID_INPUT,"Input is malformed"));
        assertThrows(ClientException.class,()->manageClientService.changePasswordNeed("test", "test"));

        when(checkerClient.passwordInputParser("mdp")).thenReturn("secured");
        assertEquals(henry,manageClientService.changePasswordNeed("test", "mdp"));
    }

    @Test
    void updateSolde_should_be_ok_if_client_is_found_and_amount_with_solde_is_correvt(){
        //given
        henry.setSolde(10);
        //when
        when(clientDao.save(any())).thenReturn(henry);
        when(clientDao.findByEmail(anyString())).thenReturn(Optional.of(henry));
        //then
        assertTrue(manageClientService.updateSolde("",1,true));
        assertTrue(manageClientService.updateSolde("",-10,true));
    }

    @Test
    void updateSolde_should_be_false_if_client_is_not_found(){
        //given
        //when
        when(clientDao.findByEmail(anyString())).thenReturn(Optional.empty());
        //then
        assertFalse(manageClientService.updateSolde("",1,true));
    }

    @Test
    void updateSolde_should_be_false_if_client_is_found_and_amount_with_solde_is_incorrect(){
        //given
        henry.setSolde(10);
        //when
        when(clientDao.save(any())).thenReturn(henry);
        when(clientDao.findByEmail(anyString())).thenReturn(Optional.of(henry));
        //then
        assertFalse(manageClientService.updateSolde("",-(henry.getSolde()+1),true));
    }

    @Test
    void getClientByEmail_exception(){
        var email = "email";
        when(clientDao.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(ClientException.class,()->manageClientService.getClientByEmail(email));
    }

    @Test
    void getClientByEmail_success() throws ClientException {
        var email = "email";
        when(clientDao.findByEmail(email)).thenReturn(Optional.of(new Client()));
        assertEquals(new Client(),manageClientService.getClientByEmail(email));
    }
    @Test

    void getClientByQrCode_exception(){
        var qrCode = UUID.randomUUID();
        when(clientDao.findByQrCode(qrCode)).thenReturn(Optional.empty());
        assertThrows(ClientException.class,()->manageClientService.getClientByQrCode(qrCode.toString()));
    }

    @Test
    void getClientByQrCode_success() throws ClientException {
        var qrCode = UUID.randomUUID();
        when(clientDao.findByQrCode(qrCode)).thenReturn(Optional.of(new Client()));
        assertEquals(new Client(),manageClientService.getClientByQrCode(qrCode.toString()));
    }
}