package fr.shoppo.mainmsinterface.presentation;

import fr.shoppo.mainmsinterface.domain.bo.Email;
import fr.shoppo.mainmsinterface.domain.bo.Token;
import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.bo.UserConnected;
import fr.shoppo.mainmsinterface.domain.service.ManageAdminService;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import fr.shoppo.mainmsinterface.domain.service.ManageConnexionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum.*;
import static fr.shoppo.mainmsinterface.infrastructure.config.securite.Role.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManageUserControllerTest {

    private final String email = "toto@gmail.com";
    private final Email emailObject = new Email(email);
    
    final User user = new User();
    final UserConnected userConnected = new UserConnected();
    
    private ManageClientService manageClientService;
    private ManageCommercantService manageCommercantService;
    private ManageAdminService manageAdminService;
    private Class<ManageUserController> manageUserControllerClass;
    private ManageUserController manageUserController;
    private ManageConnexionService manageConnexionService;

    @BeforeEach
    void setUp() {
        manageClientService = mock(ManageClientService.class);
        manageCommercantService = mock(ManageCommercantService.class);
        manageAdminService = mock(ManageAdminService.class);
        manageUserControllerClass = ManageUserController.class;
        manageConnexionService = mock(ManageConnexionService.class);
        manageUserController = new ManageUserController(manageClientService, manageCommercantService, manageAdminService, manageConnexionService);
        user.setEmail(email);
        user.setPassword("test");
    }

    @Test
    void controllerAnnotation() {
        assertNotNull(manageUserControllerClass.getAnnotation(Controller.class));
        assertArrayEquals(new String[]{"/manageUser"}, ManageUserController.class.getAnnotation(RequestMapping.class).value());
    }

    @Test
    void resetPasswordUserAnnotation() throws NoSuchMethodException {
        var method = manageUserControllerClass.getDeclaredMethod("resetPasswordUser", Email.class);
        assertArrayEquals(new String[]{"/resetPassword"}, method.getAnnotation(PostMapping.class).value());
    }

    @Test
    void resetPasswordUserNotFound() {

        when(manageClientService.resetPassword(email)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.resetPassword(email)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.resetPassword(email)).thenReturn(ADMIN_NOT_FOUND.toString());

        var response = manageUserController.resetPasswordUser(emailObject);

        var responseExpected = new ResponseEntity<String>(USER_NOT_FOUND.toString(), USER_NOT_FOUND.httpStatus());

        assertEquals(responseExpected, response);
    }

    @Test
    void resetPasswordUserClientFound() {

        when(manageClientService.resetPassword(email)).thenReturn(OK.toString());

        when(manageCommercantService.resetPassword(email)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.resetPassword(email)).thenReturn(ADMIN_NOT_FOUND.toString());

        var response = manageUserController.resetPasswordUser(emailObject);

        var responseExpected = new ResponseEntity<String>("",HttpStatus.OK);

        assertEquals(responseExpected, response);
    }

    @Test
    void resetPasswordUserCommercantFound() {

        when(manageClientService.resetPassword(email)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.resetPassword(email)).thenReturn(OK.toString());

        when(manageAdminService.resetPassword(email)).thenReturn(ADMIN_NOT_FOUND.toString());

        var response = manageUserController.resetPasswordUser(emailObject);

        var responseExpected = new ResponseEntity<String>("",HttpStatus.OK);

        assertEquals(responseExpected, response);
    }

    @Test
    void resetPasswordUserAdminFound() {

        when(manageClientService.resetPassword(email)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.resetPassword(email)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.resetPassword(email)).thenReturn(OK.toString());

        var response = manageUserController.resetPasswordUser(emailObject);

        var responseExpected = new ResponseEntity<String>("",HttpStatus.OK);

        assertEquals(responseExpected, response);
    }

    @Test
    void resetPasswordUserClientError() {

        when(manageClientService.resetPassword(email)).thenReturn(ERREUR.toString());

        when(manageCommercantService.resetPassword(email)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.resetPassword(email)).thenReturn(ADMIN_NOT_FOUND.toString());

        var response = manageUserController.resetPasswordUser(emailObject);

        var responseExpected = new ResponseEntity<String>(ERREUR.toString(), ERREUR.httpStatus());

        assertEquals(responseExpected, response);
    }

    @Test
    void resetPasswordUserCommercantError() {

        when(manageClientService.resetPassword(email)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.resetPassword(email)).thenReturn(ERREUR.toString());

        when(manageAdminService.resetPassword(email)).thenReturn(ADMIN_NOT_FOUND.toString());

        var response = manageUserController.resetPasswordUser(emailObject);

        var responseExpected = new ResponseEntity<String>(ERREUR.toString(), ERREUR.httpStatus());

        assertEquals(responseExpected, response);
    }

    @Test
    void resetPasswordUserAdminError() {

        when(manageClientService.resetPassword(email)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.resetPassword(email)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.resetPassword(email)).thenReturn(ERREUR.toString());

        var response = manageUserController.resetPasswordUser(emailObject);

        var responseExpected = new ResponseEntity<String>(ERREUR.toString(), ERREUR.httpStatus());

        assertEquals(responseExpected, response);
    }
    @Test
    void resetPasswordUserGenericError() {

        when(manageClientService.resetPassword(email)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.resetPassword(email)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.resetPassword(email)).thenReturn(ERROR_LOGIN.toString());

        var response = manageUserController.resetPasswordUser(emailObject);

        var responseExpected = new ResponseEntity<String>(ERROR_LOGIN.toString(), ERROR_LOGIN.httpStatus());

        assertEquals(responseExpected, response);
    }

    @Test
    void loginNotFound() {

        when(manageClientService.login(user)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.login(user)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.login(user)).thenReturn(ADMIN_NOT_FOUND.toString());

        var response = manageUserController.login(user);

        var responseExpected = new ResponseEntity<String>(USER_NOT_FOUND.toString(), USER_NOT_FOUND.httpStatus());

        assertEquals(responseExpected, response);
    }

    @Test
    void loginClientFound() {
        userConnected.setRole(CLIENT.libelle());

        when(manageClientService.login(user)).thenReturn(OK.toString());

        when(manageCommercantService.login(user)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.login(user)).thenReturn(ADMIN_NOT_FOUND.toString());

        var response = manageUserController.login(user);

        var responseExpected = ResponseEntity.ok(OK.toString());

        assertEquals(responseExpected, response);
    }

    @Test
    void loginCommercantFound() {
        userConnected.setRole(COMMERCANT.libelle());

        when(manageClientService.login(user)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.login(user)).thenReturn(OK.toString());

        when(manageAdminService.login(user)).thenReturn(ADMIN_NOT_FOUND.toString());

        var response = manageUserController.login(user);

        var responseExpected = ResponseEntity.ok(OK.toString());

        assertEquals(responseExpected, response);
    }

    @Test
    void loginAdminFound() {

        userConnected.setRole(ADMIN.libelle());

        when(manageClientService.login(user)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.login(user)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.login(user)).thenReturn(OK.toString());

        var response = manageUserController.login(user);

        var responseExpected = ResponseEntity.ok(OK.toString());

        assertEquals(responseExpected, response);
    }

    @Test
    void loginClientError() {

        when(manageClientService.login(user)).thenReturn(ERREUR.toString());

        when(manageCommercantService.login(user)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.login(user)).thenReturn(ADMIN_NOT_FOUND.toString());

        var response = manageUserController.login(user);

        var responseExpected = new ResponseEntity<String>(ERREUR.toString(), ERREUR.httpStatus());

        assertEquals(responseExpected, response);
    }

    @Test
    void loginCommercantError() {

        when(manageClientService.login(user)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.login(user)).thenReturn(ERREUR.toString());

        when(manageAdminService.login(user)).thenReturn(ADMIN_NOT_FOUND.toString());

        var response = manageUserController.login(user);

        var responseExpected = new ResponseEntity<String>(ERREUR.toString(), ERREUR.httpStatus());

        assertEquals(responseExpected, response);
    }

    @Test
    void loginAdminError() {

        when(manageClientService.login(user)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.login(user)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.login(user)).thenReturn(ERREUR.toString());

        var response = manageUserController.login(user);

        var responseExpected = new ResponseEntity<String>(ERREUR.toString(), ERREUR.httpStatus());

        assertEquals(responseExpected, response);
    }
    @Test
    void loginGenericError() {

        when(manageClientService.login(user)).thenReturn(CLIENT_NOT_FOUND.toString());

        when(manageCommercantService.login(user)).thenReturn(COMMERCANT_NOT_FOUND.toString());

        when(manageAdminService.login(user)).thenReturn(ERROR_LOGIN.toString());

        var response = manageUserController.login(user);

        var responseExpected = new ResponseEntity<String>(ERROR_LOGIN.toString(), ERROR_LOGIN.httpStatus());

        assertEquals(responseExpected, response);
    }

    @Test
    void disconnect(){
        var token = new Token();
        token.setValue("test");
        when(manageConnexionService.removeFromToken("test")).thenReturn(OK.toString());
        var respExpect = new ResponseEntity<>(OK.toString(), HttpStatus.OK);
        assertEquals(respExpect,manageUserController.disconnect(token));
    }

    @Test
    void disconnect_but_CONNEXION_NOT_FOUND(){
        var token = new Token();
        token.setValue("test");
        when(manageConnexionService.removeFromToken("test")).thenReturn(CONNEXION_NOT_FOUND.toString());
        var respExpect = new ResponseEntity<>(CONNEXION_NOT_FOUND.toString(), HttpStatus.NOT_FOUND);
        assertEquals(respExpect,manageUserController.disconnect(token));
    }

    @Test
    void disconnect_but_INTERNAL_SERVER_ERROR(){
        var token = new Token();
        token.setValue("test");
        when(manageConnexionService.removeFromToken("test")).thenReturn(ERREUR.toString());
        var respExpect = new ResponseEntity<>(ERREUR.toString(), ERREUR.httpStatus());

        assertEquals(respExpect,manageUserController.disconnect(token));
    }


}
