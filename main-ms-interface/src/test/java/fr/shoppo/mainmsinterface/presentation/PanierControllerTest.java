package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.Client;
import fr.shoppo.mainmsinterface.domain.bo.ReturnBasicExchange;
import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.infrastructure.service.routing.ManageClientServiceImpl;
import fr.shoppo.mainmsinterface.infrastructure.service.routing.PanierServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PanierControllerTest {


    @InjectMocks
    private PanierController panierController;

    @Mock
    private PanierServiceImpl panierService;
    @Mock
    private ManageClientServiceImpl manageClientService;
    @Mock
    private Principal principal;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void getPanier_error_client_parse(){
        when(principal.getName()).thenReturn("toto");
        when(manageClientService.getClient("toto")).thenReturn("toto");
        var expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client non trouvé");
        assertEquals(expected, panierController.getPanier(principal));
    }

    @Test
    void getPanier_error_panier_null() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        var clientString = objectMapper.writeValueAsString(clientObject);
        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.getPanier(clientObject.getId())).thenReturn("null");
        var expected = ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("");
        assertEquals(expected, panierController.getPanier(principal));
    }

    @Test
    void getPanier_error_panier_error() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        var clientString = objectMapper.writeValueAsString(clientObject);
        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.getPanier(clientObject.getId())).thenReturn(MessageConstantEnum.ERREUR_TYPE.toString());
        var expected = ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("");
        assertEquals(expected, panierController.getPanier(principal));
    }

    @Test
    void getPanier_ok() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        var clientString = objectMapper.writeValueAsString(clientObject);
        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.getPanier(clientObject.getId())).thenReturn("toto");
        var expected = ResponseEntity.ok("toto");
        assertEquals(expected, panierController.getPanier(principal));
    }

    @Test
    void updatePanier_error_client_parse(){
        when(principal.getName()).thenReturn("toto");
        when(manageClientService.getClient("toto")).thenReturn("toto");
        var expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client non trouvé");
        assertEquals(expected, panierController.updatePanierProduct(principal,2,3));
    }

    @Test
    void updatePanier_error_panier_null() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        var clientString = objectMapper.writeValueAsString(clientObject);
        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.updatePanierProduct(clientObject.getId(),2,3)).thenReturn("null");
        var expected = ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("");
        assertEquals(expected, panierController.updatePanierProduct(principal,2,3));
    }

    @Test
    void updatePanier_error_panier_error() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        var clientString = objectMapper.writeValueAsString(clientObject);

        var updatePanierInputExchangeObject = new ReturnBasicExchange(MessageConstantEnum.ERREUR_TYPE.toString(),"error");
        var updatePanierInputExchangeString = objectMapper.writeValueAsString(updatePanierInputExchangeObject);

        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.updatePanierProduct(clientObject.getId(),2,3)).thenReturn(updatePanierInputExchangeString);

        var expected = ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("error");
        assertEquals(expected, panierController.updatePanierProduct(principal,2,3));
    }

    @Test
    void updatePanier_ok() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        var clientString = objectMapper.writeValueAsString(clientObject);

        var updatePanierInputExchangeObject = new ReturnBasicExchange(MessageConstantEnum.OK.toString(),"");
        var updatePanierInputExchangeString = objectMapper.writeValueAsString(updatePanierInputExchangeObject);

        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.updatePanierProduct(clientObject.getId(),2,3)).thenReturn(updatePanierInputExchangeString);

        var expected = ResponseEntity.ok("");
        assertEquals(expected, panierController.updatePanierProduct(principal,2,3));
    }

    @Test
    void addVfpPanier_error_client_parse(){
        when(principal.getName()).thenReturn("toto");
        when(manageClientService.getClient("toto")).thenReturn("toto");
        var expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client non trouvé");
        assertEquals(expected, panierController.addVfpPanier(principal,2));
    }

    @Test
    void addVfpPanier_error_exchange_null() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        clientObject.setStatusVFP(true);
        var clientString = objectMapper.writeValueAsString(clientObject);
        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.addOrDeleteVfpPanier(clientObject.getId(),2,true)).thenReturn("null");
        var expected = ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("");
        assertEquals(expected, panierController.addVfpPanier(principal,2));
    }

    @Test
    void addVfpPanier_error_return_error() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        clientObject.setStatusVFP(true);
        var clientString = objectMapper.writeValueAsString(clientObject);

        var updatePanierInputExchangeObject = new ReturnBasicExchange(MessageConstantEnum.ERREUR_TYPE.toString(),"error");
        var updatePanierInputExchangeString = objectMapper.writeValueAsString(updatePanierInputExchangeObject);

        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.addOrDeleteVfpPanier(clientObject.getId(),2,true)).thenReturn(updatePanierInputExchangeString);

        var expected = ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("error");
        assertEquals(expected, panierController.addVfpPanier(principal,2));
    }

    @Test
    void addVfpPanier_ok() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        clientObject.setStatusVFP(true);
        var clientString = objectMapper.writeValueAsString(clientObject);

        var updatePanierInputExchangeObject = new ReturnBasicExchange(MessageConstantEnum.OK.toString(),"");
        var updatePanierInputExchangeString = objectMapper.writeValueAsString(updatePanierInputExchangeObject);

        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.addOrDeleteVfpPanier(clientObject.getId(),2,true)).thenReturn(updatePanierInputExchangeString);

        var expected = ResponseEntity.ok("");
        assertEquals(expected, panierController.addVfpPanier(principal,2));
    }

    @Test
    void deleteVfpPanier_error_client_parse(){
        when(principal.getName()).thenReturn("toto");
        when(manageClientService.getClient("toto")).thenReturn("toto");
        var expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client non trouvé");
        assertEquals(expected, panierController.deleteVfpPanier(principal,2));
    }

    @Test
    void deleteVfpPanier_error_exchange_null() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        var clientString = objectMapper.writeValueAsString(clientObject);
        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.addOrDeleteVfpPanier(clientObject.getId(),2,false)).thenReturn("null");
        var expected = ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("");
        assertEquals(expected, panierController.deleteVfpPanier(principal,2));
    }

    @Test
    void deleteVfpPanier_error_return_error() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        var clientString = objectMapper.writeValueAsString(clientObject);

        var updatePanierInputExchangeObject = new ReturnBasicExchange(MessageConstantEnum.ERREUR_TYPE.toString(),"error");
        var updatePanierInputExchangeString = objectMapper.writeValueAsString(updatePanierInputExchangeObject);

        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.addOrDeleteVfpPanier(clientObject.getId(),2,false)).thenReturn(updatePanierInputExchangeString);

        var expected = ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("error");
        assertEquals(expected, panierController.deleteVfpPanier(principal,2));
    }

    @Test
    void deleteVfpPanier_ok() throws JsonProcessingException {
        when(principal.getName()).thenReturn("toto");
        var clientObject = new Client();
        clientObject.setId(10);
        clientObject.setEmail("toto@gmail.com");
        clientObject.setPassword("password");
        clientObject.setChangePassword(false);
        var clientString = objectMapper.writeValueAsString(clientObject);

        var updatePanierInputExchangeObject = new ReturnBasicExchange(MessageConstantEnum.OK.toString(),"");
        var updatePanierInputExchangeString = objectMapper.writeValueAsString(updatePanierInputExchangeObject);

        when(manageClientService.getClient("toto")).thenReturn(clientString);
        when(panierService.addOrDeleteVfpPanier(clientObject.getId(),2,false)).thenReturn(updatePanierInputExchangeString);

        var expected = ResponseEntity.ok("");
        assertEquals(expected, panierController.deleteVfpPanier(principal,2));
    }

}
