package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.bo.commerce.CodeNaf;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commercant;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commerce;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Information;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import fr.shoppo.mainmsinterface.infrastructure.json.JsonManagerComponent;
import fr.shoppo.mainmsinterface.infrastructure.service.exception.CommercantRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManageCommercantControllerTest {

    ManageCommercantController manageCommercantController;

    ManageCommercantService manageCommercantService;
    JsonManagerComponent jsonManagerComponent;

    SecurityContext securityContext = mock(SecurityContext.class);
    Authentication authentication = mock(Authentication.class);
    org.springframework.security.core.userdetails.User connected = mock(org.springframework.security.core.userdetails.User.class);


    @BeforeEach
    void setUp() {
        manageCommercantService = mock(ManageCommercantService.class);
        jsonManagerComponent = mock(JsonManagerComponent.class);

        manageCommercantController = new ManageCommercantController();
        manageCommercantController.setManageCommercantService(manageCommercantService);
        manageCommercantController.setJsonManagerComponent(jsonManagerComponent);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void should_receive_valid_json_changePasswordNeed() throws Exception {
        when(jsonManagerComponent.tryToGet(any(),any())).thenCallRealMethod();
        assertThrows(CommercantRequestException.class,() -> manageCommercantController.changePasswordNeed("not a json"));
    }

    @Test
    void changePasswordNeed() throws Exception {
        when(jsonManagerComponent.tryToGet(any(),any())).thenCallRealMethod();
        User user = new User();
        user.setEmail("test");
        user.setPassword("password");
        when(manageCommercantService.changePassword(user)).thenReturn("test");

        when(connected.getUsername()).thenReturn("test");
        when(authentication.getPrincipal()).thenReturn(connected);

        assertEquals(HttpStatus.OK, manageCommercantController.changePasswordNeed("{password:\"password\"}").getStatusCode());
        assertEquals("test", manageCommercantController.changePasswordNeed("{password:\"password\"}").getBody());
    }

    @Test
    void findCommercant_authNull() throws CommercantRequestException, JsonProcessingException {
        when(securityContext.getAuthentication()).thenReturn(null);
        assertEquals(HttpStatus.FORBIDDEN, manageCommercantController.informationAndCommerce("false").getStatusCode());
    }
    @Test
    void findCommercant_principalNull() throws CommercantRequestException, JsonProcessingException {
        when(authentication.getPrincipal()).thenReturn(null);
        assertEquals(HttpStatus.FORBIDDEN, manageCommercantController.informationAndCommerce("false").getStatusCode());
    }
    @Test
    void findCommercant() throws JsonProcessingException, CommercantRequestException {
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(connected);
        when(manageCommercantService.findCommercant(any(),anyBoolean())).thenReturn("");
        assertThrows(CommercantRequestException.class,() ->  manageCommercantController.informationAndCommerce("false").getStatusCode());

        var info = new Information();
        var commercant = new Commercant();
        var commerce = new Commerce();
        var codeNaf = new CodeNaf();

        commerce.setCodeNaf(codeNaf);
        info.setCommerce(commerce);
        info.setCommercant(commercant);
        ObjectMapper mapper = new ObjectMapper();
        when(manageCommercantService.findCommercant(any(),anyBoolean())).thenReturn(mapper.writeValueAsString(info));
        assertEquals(HttpStatus.OK, manageCommercantController.informationAndCommerce("false").getStatusCode());
        assertEquals(mapper.writeValueAsString(info), manageCommercantController.informationAndCommerce("false").getBody());

        when(authentication.isAuthenticated()).thenReturn(false);
        assertEquals(HttpStatus.FORBIDDEN, manageCommercantController.informationAndCommerce("false").getStatusCode());

        SecurityContextHolder.clearContext();
        assertEquals(HttpStatus.FORBIDDEN, manageCommercantController.informationAndCommerce("false").getStatusCode());
    }

}