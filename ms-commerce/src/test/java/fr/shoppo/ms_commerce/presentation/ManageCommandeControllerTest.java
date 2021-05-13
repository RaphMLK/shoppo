package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.shoppo.ms_commerce.domain.bo.CreateCommandeInput;
import fr.shoppo.ms_commerce.domain.bo.TypeCommandeEnum;
import fr.shoppo.ms_commerce.domain.bo.panier.CreateCommandeByCommerceInput;
import fr.shoppo.ms_commerce.domain.service.*;
import fr.shoppo.ms_commerce.infrastructure.entity.*;
import fr.shoppo.ms_commerce.infrastructure.mapper.CommandeToCommandeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManageCommandeControllerTest {

    CommandeService commandeService;
    PanierService panierService;

    ManageCommandeController controller;
    Commercant commercant;

    List<Commande> listCommandes;

    @BeforeEach
    void setup() {
        commandeService = mock(CommandeService.class);
        panierService = mock(PanierService.class);

        listCommandes = new ArrayList<>();
        listCommandes.add(new Commande());
        listCommandes.add(new Commande());

        commercant = new Commercant();

        doNothing().when(panierService).deletePanier(any());

        controller = new ManageCommandeController(commandeService);
        controller.setPanierService(panierService);

        when(commandeService.save(any())).thenReturn(Optional.empty());
    }

    @Test
    void should_return_commandes_of_client() throws JsonProcessingException {
        when(commandeService.getCommandesOfClient(1)).thenReturn(this.listCommandes);

        this.listCommandes.forEach( e -> e.setCommerce((null)));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        assertEquals(objectMapper.writeValueAsString(this.listCommandes), controller.getCommandesClient(1));
    }

    @Test
    void should_return_commandes_of_commerce() throws JsonProcessingException {
        when(commandeService.getCommandesOfCommerce(this.commercant.getEmail())).thenReturn(this.listCommandes);

        this.listCommandes.forEach( e -> e.setCommerce((null)));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        assertEquals(objectMapper.writeValueAsString(this.listCommandes), controller.getCommandesCommerce(this.commercant.getEmail()));
    }

    @Test
    void should_return_one_commande() throws JsonProcessingException {
        Commande c = new Commande();
        when(commandeService.getCommandeById(1)).thenReturn(Optional.of(c));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        assertEquals(objectMapper.writeValueAsString(CommandeToCommandeDTO.commandeToCommandeDTO(c)), controller.getCommande(1));
    }

    @Test
    void should_return_one_commande_not_found() {
        Commande c = new Commande();
        when(commandeService.getCommandeById(1)).thenReturn(Optional.empty());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        assertEquals("Commande non trouvée", controller.getCommande(1));
    }

    @Test
    void should_update_commande_traitee() {
        Commande c = new Commande();
        when(commandeService.updateCommandeTraitee(1)).thenReturn("OK");

        assertEquals("OK", controller.updateCommandeTraitee(1));
    }

    @Test
    void shouldnt_update_commande_traitee_not_found() {
        Commande c = new Commande();
        when(commandeService.updateCommandeTraitee(1)).thenReturn("Commande non trouvée");

        assertEquals("Commande non trouvée", controller.updateCommandeTraitee(1));
    }

    @Test
    void createCommande(){
        when(panierService.getPanierCommande(1)).thenReturn(List.of());
        when(commandeService.createCommandeByPanier(List.of(),Optional.empty(), TypeCommandeEnum.ONPLACE, false)).thenReturn("ok");
        var createCommandeInput = new CreateCommandeInput();
        createCommandeInput.setIdClient(1);
        createCommandeInput.setTypeCommandeEnum(TypeCommandeEnum.ONPLACE);
        assertEquals("ok", controller.createCommande(createCommandeInput));
    }

    @Test
    void createCommandeByCommerce(){
        var idClient = 1;
        var emailCommerce = "toto@gmail.com";
        when(panierService.createPanierByCommerce(emailCommerce,
                idClient,
                List.of())).thenReturn(List.of());
        when(commandeService.createCommandeByPanier(List.of(),Optional.empty(), TypeCommandeEnum.ONPLACE, true)).thenReturn("ok");
        var createCommandeByCommerceInput = new CreateCommandeByCommerceInput();
        createCommandeByCommerceInput.setIdClient(idClient);
        createCommandeByCommerceInput.setTypeCommandeEnum(TypeCommandeEnum.ONPLACE);
        createCommandeByCommerceInput.setEmailCommerce(emailCommerce);
        createCommandeByCommerceInput.setPanierIdQuantityList(List.of());
        assertEquals("ok", controller.createCommandeByCommerce(createCommandeByCommerceInput));
    }
}

