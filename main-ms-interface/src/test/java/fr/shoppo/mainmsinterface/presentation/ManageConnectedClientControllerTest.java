package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.Client;
import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.bo.commerce.*;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import fr.shoppo.mainmsinterface.domain.service.PanierService;
import fr.shoppo.mainmsinterface.domain.service.StatService;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageCommandeService;
import fr.shoppo.mainmsinterface.infrastructure.json.JsonManagerComponent;
import fr.shoppo.mainmsinterface.infrastructure.service.exception.ClientRequestException;
import fr.shoppo.mainmsinterface.infrastructure.service.exception.CommercantRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.UUID;

import static fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManageConnectedClientControllerTest {

    ManageConnectedClientController manageConnectedClientController;
    ManageCommercantService manageCommercantService;
    ManageClientService manageClientService;
    ManageCommandeService manageCommandeService;
    JsonManagerComponent jsonManagerComponent;
    PanierService panierService;
    StatService statService;

    @BeforeEach
    void setUp() {
        manageClientService = mock(ManageClientService.class);
        jsonManagerComponent = mock(JsonManagerComponent.class);
        manageCommercantService = mock(ManageCommercantService.class);
        manageCommandeService = mock(ManageCommandeService.class);
        panierService = mock(PanierService.class);
        statService = mock(StatService.class);

        manageConnectedClientController = new ManageConnectedClientController();
        manageConnectedClientController.setManageClientService(manageClientService);
        manageConnectedClientController.setJsonManagerComponent(jsonManagerComponent);
        manageConnectedClientController.setManageCommercantService(manageCommercantService);
        manageConnectedClientController.setManageCommandeService(manageCommandeService);
        manageConnectedClientController.setPanierService(panierService);
        manageConnectedClientController.setStatService(statService);
        when(panierService.getPanier(0)).thenReturn("{advantage:null, panier :[{prixTotal:100}]}");
    }

    @Test
    void getInformationsClient_test() throws JsonProcessingException {
        var connected = mock(org.springframework.security.core.userdetails.User.class);
        var authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(connected);
        var securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        var henry = new Client();
        henry.setId(0);
        henry.setEmail("test");
        henry.setPassword("");
        henry.setAvantage("");
        henry.setPlaque("");
        henry.setSolde(0f);
        when(connected.getUsername()).thenReturn(henry.getEmail());
        var mapper = new ObjectMapper();
        when(manageClientService.getClient(anyString())).thenReturn(mapper.writeValueAsString(henry));
        henry.setPassword(null);
        henry.setEmail(null);
        henry.setChangePassword(null);
        henry.setId(null);
        assertEquals(ResponseEntity.ok(henry),manageConnectedClientController.getInformationsClient());
        /*
            @GetMapping("/infos")
    public ResponseEntity<Client> getInformationsClient() throws JsonProcessingException {
        var principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var clientString = manageClientService.getClient(principal.getUsername());
        var objectMapper = new ObjectMapper();
        var client = objectMapper.readValue(clientString, Client.class);
        client.setPassword(null);
        client.setEmail(null);
        client.setChangePassword(null);
        client.setId(null);
        return ResponseEntity.ok(client);
    }
        * */
    }

    @Test
    void should_receive_valid_json_changePasswordNeed() throws Exception {
        when(jsonManagerComponent.tryToGet(any(),any())).thenCallRealMethod();
        assertThrows(ClientRequestException.class,() -> manageConnectedClientController.changePasswordNeed("not a json"));
    }

    @Test
    void changePasswordNeed() throws Exception {
        when(jsonManagerComponent.tryToGet(any(),any())).thenCallRealMethod();
        User user = new User();
        user.setEmail("test");
        user.setPassword("password");
        when(manageClientService.changePassword(user)).thenReturn("test");

        org.springframework.security.core.userdetails.User connected = mock(org.springframework.security.core.userdetails.User.class);
        when(connected.getUsername()).thenReturn("test");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(connected);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertEquals(HttpStatus.OK, manageConnectedClientController.changePasswordNeed("{password:\"password\"}").getStatusCode());
        assertEquals("test", manageConnectedClientController.changePasswordNeed("{password:\"password\"}").getBody());
    }

    @Test
    void findCommerce() throws CommercantRequestException, JsonProcessingException {

        var info = new Information();
        var commercant = new Commercant();
        var commerce = new Commerce();
        var codeNaf = new CodeNaf();

        commerce.setCodeNaf(codeNaf);
        info.setCommerce(commerce);
        info.setCommercant(commercant);
        ObjectMapper mapper = new ObjectMapper();

        when(manageCommercantService.findCommercant(any(),anyBoolean())).thenReturn(mapper.writeValueAsString(info));
        assertEquals(HttpStatus.OK, manageConnectedClientController.informationAndCommerce("blabla").getStatusCode());
        assertEquals(mapper.writeValueAsString(info), manageConnectedClientController.informationAndCommerce("blabla").getBody());
    }

    @Test
    void createCommandeSuccessfull() throws JsonProcessingException, JSONException {
        var connected = mock(org.springframework.security.core.userdetails.User.class);
        var authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(connected);
        var securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        var mapper = new ObjectMapper();
        var client = new Client();
        client.setId(0);

        when(manageClientService.getClient(any())).thenReturn(mapper.writeValueAsString(client));
        when(manageClientService.updateSolde(any())).thenReturn(OK.toString());
        when(manageCommandeService.createCommande(0,TypeCommandeEnum.ONLINE)).thenReturn(OK.toString());

        var resp = manageConnectedClientController.validerPanier(TypeCommandeEnum.ONLINE.toString());
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(OK.toString(), resp.getBody());
    }

    @Test
    void createCommandeUnSuccessfull() throws JsonProcessingException, JSONException {
        var connected = mock(org.springframework.security.core.userdetails.User.class);
        var authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(connected);
        var securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        var mapper = new ObjectMapper();
        var client = new Client();
        client.setId(0);

        when(manageClientService.getClient(any())).thenReturn(mapper.writeValueAsString(client));
        when(manageClientService.updateSolde(any())).thenReturn(OK.toString());
        when(manageCommandeService.createCommande(0,TypeCommandeEnum.ONLINE)).thenReturn(ERREUR_INVALID_PANIER.toString());

        var resp = manageConnectedClientController.validerPanier(TypeCommandeEnum.ONLINE.toString());
        assertEquals(ERREUR_INVALID_PANIER.httpStatus(), resp.getStatusCode());
        assertEquals(ERREUR_INVALID_PANIER.toString(), resp.getBody());
    }

    @Test
    void updateSolde(){
        var connected = mock(org.springframework.security.core.userdetails.User.class);
        var authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(connected);
        var securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(manageClientService.updateSolde(any())).thenReturn(OK.toString());
        var resp = manageConnectedClientController.updateSolde(10);
        assertEquals(HttpStatus.OK,resp.getStatusCode());
        assertEquals(OK.toString(),resp.getBody());
    }


    @Test
    void updateSolde_unsuccess(){
        var connected = mock(org.springframework.security.core.userdetails.User.class);
        var authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(connected);
        var securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(manageClientService.updateSolde(any())).thenReturn(ERREUR.toString());
        var resp = manageConnectedClientController.updateSolde(10);
        assertEquals(ERREUR.httpStatus(),resp.getStatusCode());
        assertEquals(ERREUR.toString(),resp.getBody());
    }

    @Test
    void getSolde() throws JsonProcessingException {
        var connected = mock(org.springframework.security.core.userdetails.User.class);
        var authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(connected);
        var securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        var mapper = new ObjectMapper();
        var client = new Client();
        client.setId(0);
        client.setSolde(10);

        when(manageClientService.getClient(any())).thenReturn(mapper.writeValueAsString(client));

        var resp = manageConnectedClientController.getSolde();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(10, resp.getBody());
    }

    @Test
    void getQrCode_errorParsing() throws JsonProcessingException {
        var principal = mock(Principal.class);
        when(principal.getName()).thenReturn("toto");
        var mapper = new ObjectMapper();
        var client = new Client();
        client.setId(0);
        client.setQrCode(UUID.randomUUID());
        when(manageClientService.getClient("toto")).thenReturn(mapper.writeValueAsString(client));
        var expected = ResponseEntity.ok(client.getQrCode().toString());
        assertEquals(expected, manageConnectedClientController.getQrCode(principal));
    }
}