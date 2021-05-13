package fr.shoppo.ms_commerce.infrastructure.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.ms_commerce.domain.bo.ProductVfpDTO;
import fr.shoppo.ms_commerce.domain.bo.ProductVfpProductDTO;
import fr.shoppo.ms_commerce.domain.bo.TypeCommandeEnum;
import fr.shoppo.ms_commerce.domain.bo.panier.CommandePanierBo;
import fr.shoppo.ms_commerce.domain.bo.panier.PanierDTO;
import fr.shoppo.ms_commerce.domain.bo.panier.ProductPanier;
import fr.shoppo.ms_commerce.infrastructure.entity.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PanierMapper {

    public static Optional<String> panierObjectToJson(List<Panier> panierList, Optional<PanierVfp> panierVfpOptional) {
        var commandes = new ArrayList<CommandePanierBo>();
        var commerceList = new ArrayList<Commerce>();
        panierList.forEach(e -> {
            var commerce = e.getProduct().getCommerce();
            if (!commerceList.contains(commerce))
                commerceList.add(commerce);
        });
        commerceList
                .stream()
                .distinct()
                .forEach(c -> {
                    var productPanierList = new ArrayList<ProductPanier>();
                    panierList
                            .stream()
                            .filter(p -> p.getProduct().getCommerce().equals(c))
                            .forEach(p -> {
                                var product = p.getProduct();
                                var productAttr = product.getAttribute();
                                productPanierList.add(
                                        new ProductPanier(product.getId(),productAttr.getName(),
                                                productAttr.getPrix(),p.getQuantite(),productAttr.getStock())
                                );
                            });
                    commandes.add(new CommandePanierBo(c.getSiretCode(),c.getEnseigne(),productPanierList));
                });


        AtomicReference<ProductVfpDTO> advantage = new AtomicReference<>(null);
        panierVfpOptional.ifPresent( a -> {
                    var productVfp = a.getPanierVfpPK().getProductVfp();
                    advantage.set(ProductVfpToProductVfpDTO.productVfpToProductVfpDTO(productVfp));
                }
        );

        var panier = new PanierDTO(advantage.get(),commandes);
        var objectMapper = new ObjectMapper();
        try {
            return Optional.of(objectMapper.writeValueAsString(panier));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static Optional<List<Commande>>
    toCommande(List<Panier> panier, Optional<PanierVfp> panierVfpOptional,TypeCommandeEnum typeCommandeEnum){
        Map<Commerce,Commande> commerceCommandeMap = new HashMap<>();
        AtomicBoolean addVfpInCommande = new AtomicBoolean(false);
        panier
                .forEach(p -> {
                    var product = p.getProduct();
                    var qte = p.getQuantite();
                    var commerce = product.getCommerce();

                    var cmd = commerceCommandeMap
                            .getOrDefault(commerce,
                                Commande.of(p.getIdClient(),commerce,typeCommandeEnum));

                    cmd.addProductQuantite(ProductMapper.toProductWithoutCommerce(product), cmd,qte);
                    panierVfpOptional.ifPresent(panierVfp -> {
                        if(panierVfp.getPanierVfpPK().getProductVfp().getCommerce().getSiretCode().equals(cmd.getCommerce().getSiretCode())) {
                            cmd.setCommandeVfp(new CommandeVfp(cmd, panierVfp.getPanierVfpPK().getProductVfp()));
                            addVfpInCommande.set(true);
                        }
                    });
                    commerceCommandeMap.put(commerce,cmd);
                });
        panierVfpOptional.ifPresent(panierVfp -> {
            if(!addVfpInCommande.get()){
                var commerce = panierVfp.getPanierVfpPK().getProductVfp().getCommerce();
                var cmd =  commerceCommandeMap
                        .getOrDefault(commerce,
                                Commande.of(panierVfp.getPanierVfpPK().getIdClient(),commerce,typeCommandeEnum));
                cmd.setCommandeVfp(new CommandeVfp(cmd,panierVfp.getPanierVfpPK().getProductVfp()));
                commerceCommandeMap.put(commerce,cmd);
            }
        });
        return Optional.of(new ArrayList<>(commerceCommandeMap.values()));
    }
}
