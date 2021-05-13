package fr.shoppo.ms_commerce.infrastructure.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.shoppo.ms_commerce.domain.bo.TypeCommandeEnum;
import fr.shoppo.ms_commerce.domain.service.*;
import fr.shoppo.ms_commerce.infrastructure.dao.CommandeDao;
import fr.shoppo.ms_commerce.infrastructure.dao.CommercantDao;
import fr.shoppo.ms_commerce.infrastructure.dao.PanierVfpDao;
import fr.shoppo.ms_commerce.infrastructure.dao.ProductVfpDao;
import fr.shoppo.ms_commerce.infrastructure.entity.Commande;
import fr.shoppo.ms_commerce.infrastructure.entity.Panier;
import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfp;
import fr.shoppo.ms_commerce.infrastructure.mapper.PanierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.*;

@Service
public class CommandeServiceImpl implements CommandeService {

    private static final Logger logger = LoggerFactory.getLogger(CommandeServiceImpl.class);

    private final CommandeDao commandeDao;
    private final CommercantDao commercantDao;
    private final PanierService panierService;
    private final ProductService productService;
    private final NotificationService notificationService;
    private final StatService statService;
    private final PanierVfpDao panierVfpDao;
    private final ProductVfpDao productVfpDao;

    public CommandeServiceImpl(CommandeDao commandeDao, CommercantDao commercantDao, PanierService panierService, ProductService productService, NotificationService notificationService, StatService statService, PanierVfpDao panierVfpDao, ProductVfpDao productVfpDao) {
        this.commandeDao = commandeDao;
        this.commercantDao = commercantDao;
        this.panierService = panierService;
        this.productService = productService;
        this.notificationService = notificationService;
        this.statService = statService;
        this.panierVfpDao = panierVfpDao;
        this.productVfpDao = productVfpDao;
    }

    @Override
    public List<Commande> getCommandesOfCommerce(String email) {
        var commercant = commercantDao.findByEmail(email);
        if(commercant == null)
            return new ArrayList<>();
        return commandeDao.findByCommerce(commercant.getCommerce());
    }

    @Override
    public List<Commande> getCommandesOfClient(int id) {
        return commandeDao.findByClientId(id);
    }

    @Override
    public Optional<Commande> save(Commande commande){
        return Optional.of(commandeDao.save(commande));
    }

    @Override
    public Optional<Commande> getCommandeById(int id) {
        return commandeDao.findById(id);
    }

    @Override
    public String updateCommandeTraitee(int id) {
        AtomicReference<String> response = new AtomicReference<>(COMMANDE_NOT_FOUND.toString());
        commandeDao
                .findById(id)
                .ifPresent( commandeUpdate -> {
                    commandeUpdate.setTraitee(true);
                    this.commandeDao.save(commandeUpdate);
                    response.set(OK.toString());
                });
        logger.info("Update commande traitee run with {}",response.get());
        return response.get();
    }

    @Override
    public String createCommandeByPanier(List<Panier> panier, Optional<PanierVfp> panierVfpOptional, TypeCommandeEnum typeCommandeEnum, boolean istraiter) {
        var panierVfpIsOk = new AtomicBoolean(true);
        panierVfpOptional.ifPresent(panierVfp -> panierVfpIsOk.set(panierService.validatePanierVfp(panierVfp) && !panier.isEmpty()));
        if(panier.stream().allMatch(panierService::validatePanierProduct) && panierVfpIsOk.get()) {
            var commandes = PanierMapper.toCommande(panier, panierVfpOptional, typeCommandeEnum);
            commandes.ifPresent(c ->{
                c.forEach(commande -> {
                    commande.setTraitee(istraiter);
                    this.save(commande);
                });
                panier.forEach(p -> {
                    panierService.deletePanier(p);
                    var prod = p.getProduct();
                    prod.changeStock(-(p.getQuantite()));
                    productService.updateProduct(prod);
                });
                panierVfpOptional.ifPresent(panierVfp -> {
                    panierVfpDao.delete(panierVfp);
                    var productVfp = panierVfp.getPanierVfpPK().getProductVfp();
                    Optional.ofNullable(productVfp.getProduct()).ifPresentOrElse(
                            product -> {
                                product.changeStock((-1));
                                productService.updateProduct(product);
                            },
                            () -> {
                                productVfp.setStock(productVfp.getStock()-1);
                                productVfpDao.save(productVfp);
                            }
                    );
                });
                try {
                    /*notificationService.createCommande(c); //ajout en potentiel back log */
                    statService.createCommande(c);
                } catch (JsonProcessingException e) {
                    logger.warn("Failed to send asynchronized data ! [Notification & Stat] : {}",e.getMessage());
                }
            });

            return OK.toString();
        }
        return ERREUR_INVALID_PANIER.toString();
    }
}
