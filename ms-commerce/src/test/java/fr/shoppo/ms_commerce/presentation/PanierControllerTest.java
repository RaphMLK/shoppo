package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.shoppo.ms_commerce.domain.bo.panier.AddDeleteVfpPanier;
import fr.shoppo.ms_commerce.domain.bo.panier.CreateCommandeByCommerceInput;
import fr.shoppo.ms_commerce.domain.bo.panier.PanierIdQuantity;
import fr.shoppo.ms_commerce.domain.bo.panier.UpdatePanierProductInput;
import fr.shoppo.ms_commerce.domain.bo.ReturnBasicExchange;
import fr.shoppo.ms_commerce.domain.service.PanierService;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.Panier;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import fr.shoppo.ms_commerce.infrastructure.mapper.PanierMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PanierControllerTest {

    private PanierControlleur panierControlleur;
    private PanierService panierService;

    @BeforeEach
    void setUp(){
        panierControlleur = new PanierControlleur();
        panierService = mock(PanierService.class);
        panierControlleur.setPanierService(panierService);
    }

    @Test
    void getPanier_Panier_Empty(){
        var idClient = 1;
        var panier = new ArrayList<Panier>();
        when(panierService.getPanierCommande(idClient)).thenReturn(panier);
        assertEquals("{\"advantage\":null,\"panier\":[]}",panierControlleur.getPanier(idClient));
    }

    @Test
    void getPanier_OneCommerce_OneProduct(){
        var idClient = 1;
        var panier = new ArrayList<Panier>();
        panier.add(Panier.of(idClient, Product.of(1,"p1","d1", 8.99F, 50, Commerce.of("123","520","enseigne"),null), 10));
        when(panierService.getPanierCommande(idClient)).thenReturn(panier);
        assertEquals("{\"advantage\":null,\"panier\":[{\"id\":\"520\",\"enseigne\":\"enseigne\",\"products\":[{\"id\":1,\"name\":\"p1\",\"prix\":8.99,\"quantity\":10,\"stock\":50}],\"prixTotal\":8.99}]}",panierControlleur.getPanier(idClient));
    }

    @Test
    void getPanier_OneCommerce_MultipleProduct(){
        var idClient = 1;
        var panier = new ArrayList<Panier>();
        panier.add(Panier.of(idClient, Product.of(1,"p1","d1", 8.99F, 50, Commerce.of("123","520","enseigne"),null), 10));
        panier.add(Panier.of(idClient, Product.of(2,"p2","d2", 5.99F, 50, Commerce.of("123","520","enseigne"),null), 8));
        when(panierService.getPanierCommande(idClient)).thenReturn(panier);
        assertEquals("{\"advantage\":null,\"panier\":[{\"id\":\"520\",\"enseigne\":\"enseigne\",\"products\":[{\"id\":1,\"name\":\"p1\",\"prix\":8.99,\"quantity\":10,\"stock\":50},{\"id\":2,\"name\":\"p2\",\"prix\":5.99,\"quantity\":8,\"stock\":50}],\"prixTotal\":14.98}]}",panierControlleur.getPanier(idClient));
    }

    @Test
    void getPanier_TwoCommerce_MultipleProduct(){
        var idClient = 1;
        var panier = new ArrayList<Panier>();
        panier.add(Panier.of(idClient, Product.of(1,"p1","d1", 8.99F, 50, Commerce.of("123","520","enseigne"),null), 10));
        panier.add(Panier.of(idClient, Product.of(2,"p2","d2", 5.99F, 50, Commerce.of("123","520","enseigne"),null), 8));
        panier.add(Panier.of(idClient, Product.of(3,"p3","d3", 4.99F, 40, Commerce.of("789","720","enseigne2"),null), 40));
        when(panierService.getPanierCommande(idClient)).thenReturn(panier);
        assertEquals("{\"advantage\":null,\"panier\":[{\"id\":\"520\",\"enseigne\":\"enseigne\",\"products\":[{\"id\":1,\"name\":\"p1\",\"prix\":8.99,\"quantity\":10,\"stock\":50},{\"id\":2,\"name\":\"p2\",\"prix\":5.99,\"quantity\":8,\"stock\":50}],\"prixTotal\":14.98},{\"id\":\"720\",\"enseigne\":\"enseigne2\",\"products\":[{\"id\":3,\"name\":\"p3\",\"prix\":4.99,\"quantity\":40,\"stock\":40}],\"prixTotal\":4.99}]}",panierControlleur.getPanier(idClient));
    }

    @Test
    void getPanier_Error(){
        var idClient = 1;
        var panier = new ArrayList<Panier>();
        when(panierService.getPanierCommande(idClient)).thenReturn(panier);
        MockedStatic<PanierMapper> mocked = mockStatic(PanierMapper.class);
        mocked.when(() -> PanierMapper.panierObjectToJson(panier, Optional.empty())).thenReturn(Optional.empty());
        assertEquals("ERROR",panierControlleur.getPanier(idClient));
        mocked.close();
    }

    @Test
    void updatePanier() throws JsonProcessingException {
        var updatePanierInput = new UpdatePanierProductInput();
        updatePanierInput.setIdCLient(1);
        updatePanierInput.setIdProduit(2);
        updatePanierInput.setQuantite(3);
        var updatePanierOutput = new ReturnBasicExchange();
        updatePanierOutput.setStatus("ok");
        updatePanierOutput.setContent("content");
        when(panierService.updatePanierCommande(1,2,3)).thenReturn(updatePanierOutput);
        assertEquals("{\"status\":\"ok\",\"content\":\"content\"}", panierControlleur.updatePanier(updatePanierInput));
    }

    @Test
    void getPanierByCommerce_Optional_Empty() {
        var emailCommerce = "toto@gmail.com";
        var idCLient = 1;
        List<PanierIdQuantity> panierIdQuantityList = List.of();
        when(panierService.createPanierByCommerce(emailCommerce,idCLient,panierIdQuantityList)).thenReturn(List.of());
        MockedStatic<PanierMapper> mocked = mockStatic(PanierMapper.class);
        mocked.when(() -> PanierMapper.panierObjectToJson(List.of(), Optional.empty())).thenReturn(Optional.empty());
        var input = new CreateCommandeByCommerceInput();
        input.setEmailCommerce(emailCommerce);
        input.setIdClient(idCLient);
        input.setPanierIdQuantityList(panierIdQuantityList);
        assertEquals("ERROR",panierControlleur.getPanierByCommerce(input));
        mocked.close();
    }

    @Test
    void getPanierByCommerce_ok() {
        var emailCommerce = "toto@gmail.com";
        var idClient = 1;
        List<PanierIdQuantity> panierIdQuantityList = List.of();
        when(panierService.createPanierByCommerce(emailCommerce,idClient,panierIdQuantityList))
                .thenReturn(List.of(Panier.of(idClient, Product.of(1,"p1","d1", 8.99F, 50, Commerce.of("123","520","enseigne"),null), 10)));
        var input = new CreateCommandeByCommerceInput();
        input.setEmailCommerce(emailCommerce);
        input.setIdClient(idClient);
        input.setPanierIdQuantityList(panierIdQuantityList);
        assertEquals("{\"advantage\":null,\"panier\":[{\"id\":\"520\",\"enseigne\":\"enseigne\",\"products\":[{\"id\":1,\"name\":\"p1\",\"prix\":8.99,\"quantity\":10,\"stock\":50}],\"prixTotal\":8.99}]}",panierControlleur.getPanierByCommerce(input));
    }

    @Test
    void addOrDeleteVfpPanier_add_VFP() throws JsonProcessingException {
        var idClient = 1;
        var idVfp = 2;
        var addOrDelete = true;
        var addDeleteVfpPanier = new AddDeleteVfpPanier(idClient,idVfp,addOrDelete);
        var response = new ReturnBasicExchange("ok", "toto");
        when(panierService.addVfp(idClient,idVfp)).thenReturn(response);
        assertEquals("{\"status\":\"ok\",\"content\":\"toto\"}", panierControlleur.addOrDeleteVfpPanier(addDeleteVfpPanier));
    }

    @Test
    void addOrDeleteVfpPanier_delete_VFP() throws JsonProcessingException {
        var idClient = 1;
        var idVfp = 2;
        var addOrDelete = false;
        var addDeleteVfpPanier = new AddDeleteVfpPanier(idClient,idVfp,addOrDelete);
        var response = new ReturnBasicExchange("ok", "titi");
        when(panierService.deleteVfp(idClient)).thenReturn(response);
        assertEquals("{\"status\":\"ok\",\"content\":\"titi\"}", panierControlleur.addOrDeleteVfpPanier(addDeleteVfpPanier));
    }
}
