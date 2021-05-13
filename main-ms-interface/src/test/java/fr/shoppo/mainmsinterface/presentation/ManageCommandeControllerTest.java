package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.shoppo.mainmsinterface.domain.bo.Client;
import fr.shoppo.mainmsinterface.domain.bo.client.SoldeInput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.*;
import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import fr.shoppo.mainmsinterface.domain.service.PanierService;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageCommandeService;
import fr.shoppo.mainmsinterface.infrastructure.json.JsonManagerComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManageCommandeControllerTest {

    ManageCommandeController manageCommandeController;

    ManageCommandeService manageCommandeService;
    ManageClientService manageClientService;
    ManageCommercantService manageCommercantService;
    PanierService panierService;

    JsonManagerComponent jsonManagerComponent;

    Principal principal;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        manageCommandeService = mock(ManageCommandeService.class);
        manageClientService = mock(ManageClientService.class);
        manageCommercantService = mock(ManageCommercantService.class);
        panierService = mock(PanierService.class);
        jsonManagerComponent = mock(JsonManagerComponent.class);
        principal = mock(Principal.class);

        manageCommandeController = new ManageCommandeController();
        manageCommandeController.setManageCommercantService(manageCommercantService);
        manageCommandeController.setManageClientService(manageClientService);
        manageCommandeController.setManageCommandeService(manageCommandeService);
        manageCommandeController.setJsonManagerComponent(jsonManagerComponent);
        manageCommandeController.setPanierService(panierService);
        var mapper = new ObjectMapper();
        var henry = new Commercant();
        var paul = new Client();
        var shoploc = new Commerce();
        shoploc.setSiretCode("123");
        henry.setCommerce(shoploc);
        when(manageCommercantService.getCommercant(any())).thenReturn(mapper.writeValueAsString(henry));
        when(manageClientService.getClient(any())).thenReturn(mapper.writeValueAsString(paul));
    }

    @Test
    void getCommandeByCommerceTestOk() throws JsonProcessingException {
        Commande commande = new Commande();

        Commercant commercant = new Commercant();
        Commerce commerce = new Commerce();
        commerce.setSiretCode("a");
        commande.setCommerce(commerce);
        commercant.setCommerce(commerce);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var c = objectMapper.writeValueAsString(commande);
        var expected = ResponseEntity.ok(c);
        var commercant2 = objectMapper.writeValueAsString(commercant);

        when(principal.getName()).thenReturn("name");
        when(manageCommercantService.getCommercant("name")).thenReturn(commercant2);
        when(manageCommandeService.getCommande(1)).thenReturn(c);

        assertEquals(expected, manageCommandeController.getCommandeByCommerce(principal, 1));
    }

    @Test
    void getCommandeByCommerceTestWrongCommerce() throws JsonProcessingException {
        Commande commande = new Commande();

        Commercant commercant = new Commercant();
        Commerce commerce = new Commerce();
        commerce.setSiretCode("a");
        Commerce commerce2 = new Commerce();
        commerce2.setSiretCode("b");
        commande.setCommerce(commerce);
        commercant.setCommerce(commerce2);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var c = objectMapper.writeValueAsString(commande);
        var expected = new ResponseEntity<String>(MessageConstantEnum.WRONG_COMMERCE_COMMANDE.toString(), HttpStatus.BAD_REQUEST);
        var commercant2 = objectMapper.writeValueAsString(commercant);

        when(principal.getName()).thenReturn("name");
        when(manageCommercantService.getCommercant("name")).thenReturn(commercant2);
        when(manageCommandeService.getCommande(1)).thenReturn(c);

        assertEquals(expected, manageCommandeController.getCommandeByCommerce(principal, 1));
    }

    @Test
    void getCommandeByClientTestOk() throws JsonProcessingException {
        Commande commande = new Commande();

        Client client = new Client();
        client.setId(1);
        Commerce commerce = new Commerce();
        commerce.setSiretCode("a");
        commande.setClientId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var c = objectMapper.writeValueAsString(commande);
        var expected = ResponseEntity.ok(c);
        var client2 = objectMapper.writeValueAsString(client);

        when(principal.getName()).thenReturn("name");
        when(manageClientService.getClient("name")).thenReturn(client2);
        when(manageCommandeService.getCommande(1)).thenReturn(c);

        assertEquals(expected, manageCommandeController.getCommandeByClient(principal, 1));
    }

    @Test
    void getCommandeByClientTestWrongClient() throws JsonProcessingException {
        Commande commande = new Commande();

        Client client = new Client();
        client.setId(2);
        Commerce commerce = new Commerce();
        commerce.setSiretCode("a");
        commande.setClientId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var c = objectMapper.writeValueAsString(commande);
        var expected = new ResponseEntity<String>(MessageConstantEnum.WRONG_CLIENT_COMMANDE.toString(), HttpStatus.BAD_REQUEST);
        var client2 = objectMapper.writeValueAsString(client);

        when(principal.getName()).thenReturn("name");
        when(manageClientService.getClient("name")).thenReturn(client2);
        when(manageCommandeService.getCommande(1)).thenReturn(c);

        assertEquals(expected, manageCommandeController.getCommandeByClient(principal, 1));
    }

    @Test
    void getCommandesCommerceTestOk() throws JsonProcessingException {
        List<Commande> commandes = new ArrayList<>();
        Commande commande = new Commande();
        Commande commande2 = new Commande();
        commandes.add(commande);
        commandes.add(commande2);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var c = objectMapper.writeValueAsString(commandes);
        var expected = ResponseEntity.ok(c);

        when(principal.getName()).thenReturn("name");
        when(manageCommandeService.getCommandesCommerce(principal.getName())).thenReturn(c);

        assertEquals(expected, manageCommandeController.getCommandesCommerce(principal));
    }

    @Test
    void getCommandesClientTestOk() throws JsonProcessingException {
        List<Commande> commandes = new ArrayList<>();
        Commande commande = new Commande();
        Commande commande2 = new Commande();
        commandes.add(commande);
        commandes.add(commande2);
        Client client = new Client();
        client.setId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var c = objectMapper.writeValueAsString(commandes);
        var cli = objectMapper.writeValueAsString(client);
        var expected = ResponseEntity.ok(c);

        when(principal.getName()).thenReturn("name");
        when(manageCommandeService.getCommandesClient(1)).thenReturn(c);
        when(manageClientService.getClient(principal.getName())).thenReturn(cli);



        assertEquals(expected, manageCommandeController.getCommandesClient(principal));
    }

    @Test
    void getCommandesClientTestError() throws JsonProcessingException {
        List<Commande> commandes = new ArrayList<>();
        Commande commande = new Commande();
        Commande commande2 = new Commande();
        commandes.add(commande);
        commandes.add(commande2);
        Client client = new Client();
        client.setId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var c = objectMapper.writeValueAsString(commandes);
        var cli = objectMapper.writeValueAsString(client);
        var expected = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        when(principal.getName()).thenReturn("name");
        when(manageCommandeService.getCommandesClient(1)).thenReturn(c);
        when(manageClientService.getClient(principal.getName())).thenReturn("a");



        assertEquals(expected, manageCommandeController.getCommandesClient(principal));
    }

    @Test
    void updateCommandeTraiteeTestOk() throws JsonProcessingException {
        Commande commande = new Commande();
        commande.setId(1);

        Commercant commercant = new Commercant();
        Commerce commerce = new Commerce();
        commerce.setSiretCode("a");
        commande.setCommerce(commerce);
        commercant.setCommerce(commerce);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var c = objectMapper.writeValueAsString(commande);
        var expected = ResponseEntity.ok(MessageConstantEnum.COMMANDE_UPDATED.toString());
        var commercant2 = objectMapper.writeValueAsString(commercant);

        when(principal.getName()).thenReturn("name");
        when(manageCommercantService.getCommercant("name")).thenReturn(commercant2);
        when(manageCommandeService.getCommande(1)).thenReturn(c);
        when(manageCommandeService.updateCommande(commande.getId())).thenReturn("OK");

        assertEquals(expected, manageCommandeController.updateCommandeeTraitee(principal, commande));
    }

    @Test
    void updateCommandeTraiteeNotUpdatedTestOk() throws JsonProcessingException {
        Commande commande = new Commande();
        commande.setId(1);

        Commercant commercant = new Commercant();
        Commerce commerce = new Commerce();
        commerce.setSiretCode("a");
        commande.setCommerce(commerce);
        commercant.setCommerce(commerce);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var c = objectMapper.writeValueAsString(commande);
        var expected = new ResponseEntity<String>("aaaa", HttpStatus.BAD_REQUEST);
        var commercant2 = objectMapper.writeValueAsString(commercant);

        when(principal.getName()).thenReturn("name");
        when(manageCommercantService.getCommercant("name")).thenReturn(commercant2);
        when(manageCommandeService.getCommande(1)).thenReturn(c);
        when(manageCommandeService.updateCommande(commande.getId())).thenReturn("aaaa");

        assertEquals(expected, manageCommandeController.updateCommandeeTraitee(principal, commande));
    }

    @Test
    void commandeByCommerce_OK() throws JsonProcessingException, JSONException {
        when(principal.getName()).thenReturn("toto@gmail.com");
        var createCommandeByCommerceInput = new CreateCommandeByCommerceInput();
        var uuid = UUID.randomUUID();
        createCommandeByCommerceInput.setClient(uuid.toString());
        createCommandeByCommerceInput.setOnlinePayment(true);
        createCommandeByCommerceInput.setCommand(List.of());
        var client = new Client();
        client.setId(1);
        client.setSolde(10);
        client.setQrCode(uuid);
        client.setEmail("toto@gmail.com");
        var mapper = new ObjectMapper();
        when(manageClientService.getClientByQrCode(uuid.toString()))
                .thenReturn(mapper.writeValueAsString(client));
        when(manageClientService.updateSolde(SoldeInput.of("toto@gmail.com",-0F)))
                .thenReturn(MessageConstantEnum.OK.toString());
        var createCommandeByCommerceOutput = new CreateCommandeByCommerceOutput("toto@gmail.com", 1,
                createCommandeByCommerceInput.getCommand(), TypeCommandeEnum.ONLINE, null);
        when(panierService.getPanierByCommerce(createCommandeByCommerceOutput)).thenReturn("{advantage: null, panier: []}");
        when(manageCommandeService.createCommandeByCommerce(createCommandeByCommerceOutput))
                .thenReturn(MessageConstantEnum.OK.toString());
        assertEquals(ResponseEntity.status(HttpStatus.OK).body(MessageConstantEnum.OK.toString())
                ,manageCommandeController.commandeByCommerce(principal, createCommandeByCommerceInput));
    }

}
