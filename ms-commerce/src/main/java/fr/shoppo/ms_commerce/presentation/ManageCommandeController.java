package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.shoppo.ms_commerce.domain.bo.CreateCommandeInput;
import fr.shoppo.ms_commerce.domain.bo.panier.CreateCommandeByCommerceInput;
import fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum;
import fr.shoppo.ms_commerce.domain.service.*;
import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfp;
import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfpPK;
import fr.shoppo.ms_commerce.infrastructure.mapper.CommandeToCommandeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.*;

@Component
public class ManageCommandeController {

    private static final Logger logger = LoggerFactory.getLogger(ManageCommandeController.class);

    private final CommandeService commandeService;
    PanierService panierService;
    private VfpService vfpService;

    public ManageCommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @RabbitListener(queues = "${mq.queue.get-commandes-commerce}")
    public String getCommandesCommerce(String email) throws JsonProcessingException {
        var commandesList = commandeService.getCommandesOfCommerce(email);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.writeValueAsString(commandesList);
    }

    @RabbitListener(queues = "${mq.queue.get-commandes-client}")
    public String getCommandesClient(int id) throws JsonProcessingException {
        var commandesList = commandeService.getCommandesOfClient(id);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.writeValueAsString(commandesList);
    }

    @RabbitListener(queues = "${mq.queue.create-commande}")
    public String createCommande(CreateCommandeInput createCommandeInput){
        var idClient = createCommandeInput.getIdClient();
        var panier = panierService.getPanierCommande(idClient);

        return commandeService.createCommandeByPanier(panier,
                panierService.getPanierVfp(idClient),
                createCommandeInput.getTypeCommandeEnum(),
                false);
    }

    @RabbitListener(queues = "${mq.queue.create-commande-by-commerce}")
    public String createCommandeByCommerce(CreateCommandeByCommerceInput getPanierByCommerceInput){
        var panier = panierService.createPanierByCommerce(getPanierByCommerceInput.getEmailCommerce(),
                getPanierByCommerceInput.getIdClient(),
                getPanierByCommerceInput.getPanierIdQuantityList());
        AtomicReference<Optional<PanierVfp>> advantage = new AtomicReference<>(Optional.empty());
        AtomicReference<Optional<Object>> responseOptionnalAtomic = new AtomicReference<>(Optional.empty());
        if(getPanierByCommerceInput.getAdvantage() != null) {
            vfpService.getVfpById(getPanierByCommerceInput.getAdvantage()).ifPresentOrElse(
                    productVfp -> advantage.set(Optional.of(new PanierVfp(new PanierVfpPK(productVfp, getPanierByCommerceInput.getIdClient())))),
                    () -> responseOptionnalAtomic.set(Optional.of(MessageConstantEnum.ERREUR_TYPE.toString()))
            );
        }
        if(responseOptionnalAtomic.get().isPresent())
            return MessageConstantEnum.ERREUR_TYPE.toString();

        return commandeService.createCommandeByPanier(
                panier,
                advantage.get(),
                getPanierByCommerceInput.getTypeCommandeEnum(),
                true);

    }


    @RabbitListener(queues = "${mq.queue.get-commande}")
    public String getCommande(int id) {
        var containerString = new AtomicReference<>(COMMANDE_NOT_FOUND.toString());
        this.commandeService
                .getCommandeById(id)
                .ifPresent(
                    commande ->
                    {
                        var commandeDTO = CommandeToCommandeDTO.commandeToCommandeDTO(commande);
                        var objectMapper = new ObjectMapper();
                        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                        try {
                            containerString.set(objectMapper.writeValueAsString(commandeDTO));
                        } catch (JsonProcessingException e) {
                            containerString.set(ERREUR.toString());
                        }
                    }
                );
        return containerString.get();
    }

    @RabbitListener(queues = "${mq.queue.update-commande-traitee}")
    public String updateCommandeTraitee(int id) {
            return this.commandeService.updateCommandeTraitee(id);
    }

    @Autowired
    public void setPanierService(PanierService panierService) {
        this.panierService = panierService;
    }

    @Autowired
    public void setVfpService(VfpService vfpService) {
        this.vfpService = vfpService;
    }
}
