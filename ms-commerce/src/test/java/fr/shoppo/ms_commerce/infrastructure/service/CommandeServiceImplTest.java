package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.bo.CreateCommandeInput;
import fr.shoppo.ms_commerce.domain.bo.TypeCommandeEnum;
import fr.shoppo.ms_commerce.domain.service.*;
import fr.shoppo.ms_commerce.infrastructure.dao.CommandeDao;
import fr.shoppo.ms_commerce.infrastructure.dao.CommercantDao;
import fr.shoppo.ms_commerce.infrastructure.entity.*;
import fr.shoppo.ms_commerce.infrastructure.entity.embeddable.ProductAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.ERREUR_INVALID_PANIER;
import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommandeServiceImplTest {

    private CommandeService commandeService;
    private CommandeDao commandeDao;
    private CommercantDao commercantDao;
    private PanierService panierService;
    private ProductService productService;
    private NotificationService notificationService;
    private StatService statService;


    @BeforeEach
    void setup(){
        commandeDao = mock(CommandeDao.class);
        commercantDao = mock(CommercantDao.class);
        panierService = mock(PanierService.class);
        productService = mock(ProductService.class);
        notificationService = mock(NotificationService.class);
        statService = mock(StatService.class);

        commandeService = spy(new CommandeServiceImpl(commandeDao, commercantDao, panierService,
                productService, notificationService, statService, null, null));
    }

    @Test
    void should_return_commandes_of_client() {
        //Given
        var listCommandes = new ArrayList<Commande>();
        listCommandes.add(new Commande());
        listCommandes.add(new Commande());

        when(commandeDao.findByClientId(1)).thenReturn(listCommandes);

        assertEquals(listCommandes, commandeService.getCommandesOfClient(1));
    }

    @Test
    void should_return_commandes_of_commerce() {
        //Given
        var commercant = new Commercant();
        commercant.setEmail("test");

        var listCommandes = new ArrayList<Commande>();
        listCommandes.add(new Commande());
        listCommandes.add(new Commande());

        when(commercantDao.findByEmail("test")).thenReturn(commercant);
        when(commandeDao.findByCommerce(commercant.getCommerce())).thenReturn(listCommandes);

        assertEquals(listCommandes, commandeService.getCommandesOfCommerce(commercant.getEmail()));
    }

    @Test
    void get_one_commande() {
        Commande commande = new Commande();
        when(commandeDao.findById(1)).thenReturn(java.util.Optional.of(commande));

        assertEquals(java.util.Optional.of(commande), commandeService.getCommandeById(1));
    }

    @Test
    void get_one_commande_not_found() {
        Commande commande = new Commande();
        when(commandeDao.findById(1)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), commandeService.getCommandeById(1));
    }

    @Test
    void update_commande_traitee() {
        Optional<Commande> commande = Optional.of(new Commande());
        when(commandeDao.findById(1)).thenReturn(commande);
        when(commandeDao.save(commande.get())).thenReturn(commande.get());

        assertEquals("OK", commandeService.updateCommandeTraitee(1));
    }

    @Test
    void update_commande_not_found() {
        Optional<Commande> commande = Optional.of(new Commande());
        when(commandeDao.findById(1)).thenReturn(Optional.empty());
        when(commandeDao.save(commande.get())).thenReturn(null);

        assertEquals("Commande non trouvée", commandeService.updateCommandeTraitee(1));
    }

    @Test
    void should_create_cmd_by_commerce_if_everything_ok(){
        var createInput = new CreateCommandeInput();
        createInput.setIdClient(0);
        createInput.setTypeCommandeEnum(TypeCommandeEnum.ONLINE);
        var panier = generatePanier();
        when(panierService.validatePanierProduct(any(Panier.class))).thenReturn(true);
        doReturn(Optional.empty()).when(commandeService).save(any());

        assertEquals(
                OK.toString(),
                commandeService.createCommandeByPanier(panier,Optional.empty(), TypeCommandeEnum.ONPLACE, false));
    }

    @Test
    void shouldnt_create_cmd_by_commerce_if_any_panier_is_malformed(){
        var panier = generatePanier();
        when(panierService.getPanierCommande(0)).thenReturn(panier);
        when(panierService.validatePanierProduct(any(Panier.class))).thenReturn(false);

        assertEquals(ERREUR_INVALID_PANIER.toString(),commandeService.createCommandeByPanier(panier,Optional.empty(), TypeCommandeEnum.ONPLACE, false));
    }

    List<Panier> generatePanier(){
        return generateProduct().stream().map( pr -> {
            var p = new Panier();
            var attribute = pr.getAttribute();
            p.setQuantite(attribute.getStock());
            p.setProduct(pr);
            p.setIdClient(0);
            p.setId(0);
            return p;
        }).collect(Collectors.toList());
    }

    List<Product> generateProduct(){
        var commerce1 = new Commerce();
        var commerce2 = new Commerce();


        commerce1.setSiretCode("123");
        commerce2.setSiretCode("456");

        return IntStream.range(1,10).mapToObj(
                i ->{
                    var p = new Product();
                    p.setAttribute(ProductAttribute.of(
                            "Pain",
                            "",
                            (float) i,
                            i,
                            "null".getBytes()
                    ));

                    p.setCommerce(i%2==0?commerce1:commerce2);
                    return p;
                }
        ).collect(Collectors.toList());
    }
}
