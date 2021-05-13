package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.bo.panier.PanierIdQuantity;
import fr.shoppo.ms_commerce.domain.bo.ReturnBasicExchange;
import fr.shoppo.ms_commerce.infrastructure.dao.*;
import fr.shoppo.ms_commerce.infrastructure.entity.*;
import fr.shoppo.ms_commerce.infrastructure.entity.embeddable.ProductAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PanierServiceImplTest {

    PanierServiceImpl panierService;
    Panier panier;
    Product product;
    PanierDao panierDao;
    ProductDao productDao;
    CommercantDao commercantDao;
    ProductVfpDao productVfpDao;
    PanierVfpDao panierVfpDao;
    CommandeDao commandeDao;

    @BeforeEach
    void setup() {
        panierService = new PanierServiceImpl();
        panierDao = mock(PanierDao.class);
        productDao = mock(ProductDao.class);
        commercantDao = mock(CommercantDao.class);
        productVfpDao = mock(ProductVfpDao.class);
        panierVfpDao = mock(PanierVfpDao.class);
        commandeDao = mock(CommandeDao.class);
        panierService.setProductDao(productDao);
        panierService.setPanierDao(panierDao);
        panierService.setCommercantDao(commercantDao);
        panierService.setProductVfpDao(productVfpDao);
        panierService.setPanierVfpDao(panierVfpDao);
        panierService.setCommandeDao(commandeDao);
        panier = new Panier();

        product = Product.of(null,"Baba au rhum","",1.5f,10,null,null);

        panier.setProduct(product);
    }

    @Test
    void should_validate_panier_if_quantities_and_stock_are_correct(){
        panier.setQuantite(product.getAttribute().getStock());
        assertTrue(panierService.validatePanierProduct(panier));
    }

    @Test
    void should_reject_if_quantities_are_out_of_stock(){
        panier.setQuantite(product.getAttribute().getStock()+1);
        assertFalse(panierService.validatePanierProduct(panier));
    }

    @Test
    void should_reject_if_quantities_are_under_zero() {
        panier.setQuantite(-1);
        assertFalse(panierService.validatePanierProduct(panier));
    }

    @Test
    void should_reject_if_product_in_panier_is_malformed_ie_dont_have_stock_value_default_at_0(){
        product.getAttribute().setStock(0);
        assertFalse(panierService.validatePanierProduct(panier));
    }

    @Test
    void should_reject_if_panier_is_malformed_ie_dont_have_product() {
        panier.setProduct(null);
        assertFalse(panierService.validatePanierProduct(panier));
    }

    @Test
    void getPanier_ok() {
        var panier = List.of(new Panier());
        when(panierDao.findByIdClient(1)).thenReturn(panier);

        assertEquals(panier, panierService.getPanierCommande(1));
    }

    @Test
    void updatePanier_product_not_exist() {
        var idClient = 1;
        var idProduit = 2;
        var quantite = 3;
        when(productDao.findById(idProduit)).thenReturn(Optional.empty());
        assertEquals(new ReturnBasicExchange(ERREUR_TYPE.toString(), ERREUR_PRODUCT_NOT_FOUND.toString()),
                panierService.updatePanierCommande(idClient, idProduit,quantite));
    }

    @Test
    void updatePanier_addProduct_quantite_negatif_or_0() {
        var idClient = 1;
        var idProduit = 2;
        var quantite = 0;
        var product = Product.of(2, "name", "description", 10.99F,
                10, new Commerce(), null);
        when(productDao.findById(idProduit)).thenReturn(Optional.of(product));
        when(panierDao.findByIdClientAndProduct(idClient, product)).thenReturn(Optional.empty());
        assertEquals(new ReturnBasicExchange(ERREUR_TYPE.toString(),ERREUR_ADD_PANIER_QUANTITE_NULL.toString()),
                panierService.updatePanierCommande(idClient, idProduit,quantite));
    }

    @Test
    void updatePanier_addProduct_quantite_more_stock() {
        var idClient = 1;
        var idProduit = 2;
        var quantite = 50;
        var product = Product.of(2, "name", "description", 10.99F,
                10, new Commerce(), null);
        when(productDao.findById(idProduit)).thenReturn(Optional.of(product));
        when(panierDao.findByIdClientAndProduct(idClient, product)).thenReturn(Optional.empty());
        assertEquals(new ReturnBasicExchange(ERREUR_TYPE.toString(),ERREUR_ADD_PANIER_STOCK_TROP_PETIT.toString()),
                panierService.updatePanierCommande(idClient, idProduit,quantite));
    }

    @Test
    void updatePanier_addProduct_ok() {
        var idClient = 1;
        var idProduit = 2;
        var quantite = 4;
        var product = Product.of(2, "name", "description", 10.99F,
                10, new Commerce(), null);
        when(productDao.findById(idProduit)).thenReturn(Optional.of(product));
        when(panierDao.findByIdClientAndProduct(idClient, product)).thenReturn(Optional.empty());
        when(panierDao.save(panier)).thenReturn(panier);
        assertEquals(new ReturnBasicExchange(OK.toString(),null),
                panierService.updatePanierCommande(idClient, idProduit,quantite));
    }

    @Test
    void updatePanier_deleteProduct_ok() {
        var idClient = 1;
        var idProduit = 2;
        var quantite = 0;
        var product = Product.of(2, "name", "description", 10.99F,
                10, new Commerce(), null);
        var panier = Panier.of(idClient, product, 4);
        when(productDao.findById(idProduit)).thenReturn(Optional.of(product));
        when(panierDao.findByIdClientAndProduct(idClient, product)).thenReturn(Optional.of(panier));
        assertEquals(new ReturnBasicExchange(OK.toString(),null),
                panierService.updatePanierCommande(idClient, idProduit,quantite));
    }

    @Test
    void updatePanier_updateProduct_quantite_more_stock() {
        var idClient = 1;
        var idProduit = 2;
        var quantite = 11;
        var product = Product.of(2, "name", "description", 10.99F,
                10, new Commerce(), null);
        var panier = Panier.of(idClient, product, 4);
        when(productDao.findById(idProduit)).thenReturn(Optional.of(product));
        when(panierDao.findByIdClientAndProduct(idClient, product)).thenReturn(Optional.of(panier));
        assertEquals(new ReturnBasicExchange(ERREUR_TYPE.toString(), ERREUR_ADD_PANIER_STOCK_TROP_PETIT.toString()),
                panierService.updatePanierCommande(idClient, idProduit,quantite));
    }

    @Test
    void updatePanier_updateProduct_ok() {
        var idClient = 1;
        var idProduit = 2;
        var quantite = 8;
        var product = Product.of(2, "name", "description", 10.99F,
                10, new Commerce(), null);
        var panier = Panier.of(idClient, product, 4);
        when(productDao.findById(idProduit)).thenReturn(Optional.of(product));
        when(panierDao.findByIdClientAndProduct(idClient, product)).thenReturn(Optional.of(panier));
        var panierAfterUpdate = Panier.of(idClient, product, 8);
        when(panierDao.save(panierAfterUpdate)).thenReturn(panierAfterUpdate);
        assertEquals(new ReturnBasicExchange(OK.toString(),null),
                panierService.updatePanierCommande(idClient, idProduit,quantite));
    }

    @Test
    void createPanierByCommerce_list_empty(){
        var email =  "toto@gmail.com";
        var idClient = 1;
        List<PanierIdQuantity> panierIdQuantityList = List.of();
        assertEquals(new ArrayList<Panier>(), panierService.createPanierByCommerce(email,idClient,panierIdQuantityList));
    }

    @Test
    void createPanierByCommerce_list_product_not_found(){
        var email =  "toto@gmail.com";
        var idClient = 1;
        List<PanierIdQuantity> panierIdQuantityList = List.of(new PanierIdQuantity(1,1));
        when(productDao.findById(1)).thenReturn(Optional.empty());
        assertEquals(new ArrayList<Panier>(), panierService.createPanierByCommerce(email,idClient,panierIdQuantityList));
    }

    @Test
    void createPanierByCommerce_list_product_not_commerce(){
        var email =  "toto@gmail.com";
        var idClient = 1;
        List<PanierIdQuantity> panierIdQuantityList = List.of(new PanierIdQuantity(1,1));
        when(productDao.findById(1))
                .thenReturn(Optional.of(
                        Product.of(1,null,null,1,1,
                                Commerce.of(null, "123",null,null,null,null,null,null),null)));
        var commercant = Commercant.of(email,"toto");
        commercant.setCommerce(Commerce.of(null, "789",null,null,null,null,null,null));
        when(commercantDao.findByEmail(email)).thenReturn(commercant);
        assertEquals(new ArrayList<Panier>(), panierService.createPanierByCommerce(email,idClient,panierIdQuantityList));
    }

    @Test
    void createPanierByCommerce_one_product(){
        var email =  "toto@gmail.com";
        var idClient = 1;
        List<PanierIdQuantity> panierIdQuantityList = List.of(new PanierIdQuantity(1,1));
        var product = Product.of(1,null,null,1,1,
                Commerce.of(null, "123",null,null,null,null,null,null),null);
        when(productDao.findById(1))
                .thenReturn(Optional.of(product));
        var commercant = Commercant.of(email,"toto");
        commercant.setCommerce(Commerce.of(null, "123",null,null,null,null,null,null));
        when(commercantDao.findByEmail(email)).thenReturn(commercant);
        assertEquals(List.of(Panier.of(idClient, product, 1)),
                panierService.createPanierByCommerce(email,idClient,panierIdQuantityList));
    }

    @Test
    void addVfp_not_found_vfp() {
        var idClient = 1;
        var idVfp = 2;
        when(productVfpDao.findById(idVfp)).thenReturn(Optional.empty());
        var expected = new ReturnBasicExchange(ERREUR_TYPE.toString(), "VFP non trouvé");
        assertEquals(expected, panierService.addVfp(idClient,idVfp));
    }

    @Test
    void addVfp_client_deja_produit_vfp_panier() {
        var idClient = 1;
        var idVfp = 2;
        when(productVfpDao.findById(idVfp)).thenReturn(Optional.of(new ProductVfp()));
        when(panierVfpDao.findPanierVfpByPanierVfpPK_IdClient(idClient)).thenReturn(Optional.of(new PanierVfp()));
        var expected = new ReturnBasicExchange(ERREUR_TYPE.toString(), "Vous avez déjà un VFP dans le panier");
        assertEquals(expected, panierService.addVfp(idClient,idVfp));
    }

    @Test
    void addVfp_client_pas_commande_commerce() {
        var idClient = 1;
        var idVfp = 2;
        var productVfp = new ProductVfp();
        productVfp.setCommerce(new Commerce());
        when(productVfpDao.findById(idVfp)).thenReturn(Optional.of(productVfp));
        when(panierVfpDao.findPanierVfpByPanierVfpPK_IdClient(idClient)).thenReturn(Optional.empty());
        when(commandeDao.findCommandeByCommerceAndClientId(new Commerce(), idClient)).thenReturn(List.of());
        var expected = new ReturnBasicExchange(ERREUR_TYPE.toString(), "Vous devez avoir effectué une commande dans ce commerce pour ajouter un VFP");
        assertEquals(expected, panierService.addVfp(idClient,idVfp));
    }

    @Test
    void addVfp_product_in_vfp_not_stock() {
        var idClient = 1;
        var idVfp = 2;
        var productVfp = new ProductVfp();
        productVfp.setCommerce(new Commerce());
        var product = new Product();
        var attribute = new ProductAttribute();
        attribute.setStock(0);
        product.setAttribute(attribute);
        productVfp.setProduct(product);
        when(productVfpDao.findById(idVfp)).thenReturn(Optional.of(productVfp));
        when(panierVfpDao.findPanierVfpByPanierVfpPK_IdClient(idClient)).thenReturn(Optional.empty());
        when(commandeDao.findCommandeByCommerceAndClientId(new Commerce(), idClient)).thenReturn(List.of(new Commande()));
        var expected = new ReturnBasicExchange(ERREUR_TYPE.toString(), "Le VFP n'est pas disponible");
        assertEquals(expected, panierService.addVfp(idClient,idVfp));
    }

    @Test
    void addVfp_vfp_not_stock() {
        var idClient = 1;
        var idVfp = 2;
        var productVfp = new ProductVfp();
        productVfp.setCommerce(new Commerce());
        productVfp.setStock(0);
        when(productVfpDao.findById(idVfp)).thenReturn(Optional.of(productVfp));
        when(panierVfpDao.findPanierVfpByPanierVfpPK_IdClient(idClient)).thenReturn(Optional.empty());
        when(commandeDao.findCommandeByCommerceAndClientId(new Commerce(), idClient)).thenReturn(List.of(new Commande()));
        var expected = new ReturnBasicExchange(ERREUR_TYPE.toString(), "Le VFP n'est pas disponible");
        assertEquals(expected, panierService.addVfp(idClient,idVfp));
    }

    @Test
    void addVfp_vfp_ok() {
        var idClient = 1;
        var idVfp = 2;
        var productVfp = new ProductVfp();
        productVfp.setCommerce(new Commerce());
        productVfp.setStock(1);
        when(productVfpDao.findById(idVfp)).thenReturn(Optional.of(productVfp));
        when(panierVfpDao.findPanierVfpByPanierVfpPK_IdClient(idClient)).thenReturn(Optional.empty());
        when(commandeDao.findCommandeByCommerceAndClientId(new Commerce(), idClient)).thenReturn(List.of(new Commande()));
        var expected = new ReturnBasicExchange(OK.toString(), null);
        assertEquals(expected, panierService.addVfp(idClient,idVfp));
    }

    @Test
    void deleteVfp_not_vfp_panier(){
        var idClient = 1;
        when(panierVfpDao.findPanierVfpByPanierVfpPK_IdClient(idClient)).thenReturn(Optional.empty());
        var expected = new ReturnBasicExchange(ERREUR_TYPE.toString(), "Vous n'avez pas de VFP dans le panier");
        assertEquals(expected, panierService.deleteVfp(idClient));
    }

    @Test
    void deleteVfp_ok(){
        var idClient = 1;
        when(panierVfpDao.findPanierVfpByPanierVfpPK_IdClient(idClient)).thenReturn(Optional.of(new PanierVfp()));
        var expected = new ReturnBasicExchange(OK.toString(), null);
        assertEquals(expected, panierService.deleteVfp(idClient));
    }

}