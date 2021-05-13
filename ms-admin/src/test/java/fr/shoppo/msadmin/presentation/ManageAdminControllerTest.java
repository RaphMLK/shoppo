package fr.shoppo.msadmin.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.msadmin.domain.bo.User;
import fr.shoppo.msadmin.domain.service.AdminService;
import fr.shoppo.msadmin.domain.service.NotificationService;
import fr.shoppo.msadmin.infrastructure.entity.Admin;
import fr.shoppo.msadmin.infrastructure.exception.AdminException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.shoppo.msadmin.domain.constant.MessageConstantEnum.ERREUR;
import static fr.shoppo.msadmin.domain.constant.MessageConstantEnum.OK;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ManageAdminControllerTest {

    AdminService adminService;
    NotificationService notificationService;

    ManageAdminController manageAdminController;

    Admin roger;

    @BeforeEach
    void setup(){
       adminService = mock(AdminService.class);
       notificationService = mock(NotificationService.class);

       manageAdminController = new ManageAdminController(adminService,notificationService);

       doNothing().when(notificationService).resetPassword(any());

        roger = new Admin();
        roger.setPassword("test");
        roger.setEmail("test");
    }

    @Test
    void should_resetpassword() throws AdminException {
        when(adminService.resetPassword("test")).thenReturn(roger);
        assertEquals(OK.toString(),manageAdminController.resetPassword("test"));
    }

    @Test
    void should_resetpassword_but_not_find() throws AdminException {
        when(adminService.resetPassword("test")).thenReturn(null);
        assertEquals(ERREUR.toString(),manageAdminController.resetPassword("test"));
    }


    @Test
    void should_resetpassword_but_thrown_exception() throws AdminException {
        when(adminService.resetPassword("test")).thenThrow(new AdminException(OK,"test"));
        assertEquals(format("%s : %s",OK.toString(),"test"),manageAdminController.resetPassword("test"));
    }

    @Test
    void should_login() throws AdminException, JsonProcessingException {
        var user = new User();
        user.setEmail(roger.getEmail());
        user.setPassword(roger.getPassword());

        when(adminService.login("test","test")).thenReturn(roger);

        ObjectMapper objectMapper = new ObjectMapper();
        assertEquals("OK#"+objectMapper.writeValueAsString(roger),manageAdminController.login(user));
    }

    @Test
    void should_login_but_not_find() throws AdminException {
        var user = new User();
        user.setEmail(roger.getEmail());
        user.setPassword(roger.getPassword());

        when(adminService.login("test","test")).thenReturn(null);

        assertEquals(ERREUR.toString(),manageAdminController.login(user));
    }

    @Test
    void should_login_but_thrown_exception() throws AdminException {
        var user = new User();
        user.setEmail(roger.getEmail());
        user.setPassword(roger.getPassword());

        when(adminService.login("test","test")).thenThrow(new AdminException(OK,"test"));

        assertEquals(format("%s : %s",OK.toString(),"test"),manageAdminController.login(user));
    }


}