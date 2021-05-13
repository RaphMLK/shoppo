package fr.shoppo.ms_commerce.infrastructure.mapper;

import fr.shoppo.ms_commerce.domain.bo.CommandeDTO;
import fr.shoppo.ms_commerce.domain.bo.ProductVfpDTO;
import fr.shoppo.ms_commerce.infrastructure.entity.Commande;
import fr.shoppo.ms_commerce.infrastructure.entity.CommandeVfp;

import java.util.Optional;

public class CommandeToCommandeDTO {

    public static CommandeDTO commandeToCommandeDTO (Commande commande) {
        Optional<ProductVfpDTO> productVfpDTOOptionnal = Optional.empty();
        if(commande.getCommandeVfp()!=null)
            productVfpDTOOptionnal = Optional.of(commandeVfpToProductVfpDto(commande.getCommandeVfp()));
        var commandeDTO = new CommandeDTO(commande.getId(),
                commande.getDateCreation(),
                commande.getDateRepriseClient(),
                commande.getTraitee(),
                commande.getClientId(),
                commande.getPrix(),
                commande.getProducts(),
                commande.getCommerce(),
                commande.getTypeCommande(),
                null);
        productVfpDTOOptionnal.ifPresent(commandeDTO::setProductVfpDTO);
        return commandeDTO;
    }

    private static ProductVfpDTO commandeVfpToProductVfpDto(CommandeVfp commandeVfp) {
        var productVfp = commandeVfp.getProductVfp();
        return ProductVfpToProductVfpDTO.productVfpToProductVfpDTO(productVfp);
    }
}
