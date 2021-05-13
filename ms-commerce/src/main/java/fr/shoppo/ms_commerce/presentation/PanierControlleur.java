package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.ms_commerce.domain.bo.panier.AddDeleteVfpPanier;
import fr.shoppo.ms_commerce.domain.bo.panier.CreateCommandeByCommerceInput;
import fr.shoppo.ms_commerce.domain.bo.panier.UpdatePanierProductInput;
import fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum;
import fr.shoppo.ms_commerce.domain.service.PanierService;
import fr.shoppo.ms_commerce.domain.service.VfpService;
import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfp;
import fr.shoppo.ms_commerce.infrastructure.entity.PanierVfpPK;
import fr.shoppo.ms_commerce.infrastructure.mapper.PanierMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PanierControlleur {

    private PanierService panierService;
    private VfpService vfpService;

    @RabbitListener(queues = "${mq.queue.get-panier}")
    public String getPanier(int idCLient) {
        var responseOptionnal = PanierMapper.panierObjectToJson(panierService.getPanierCommande(idCLient), panierService.getPanierVfp(idCLient));
        return responseOptionnal.orElseGet(MessageConstantEnum.ERREUR_TYPE::toString);
    }

    @RabbitListener(queues = "${mq.queue.update-panier}")
    public String updatePanier(UpdatePanierProductInput updatePanierProductInput) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(
                panierService.updatePanierCommande(
                        updatePanierProductInput.getIdCLient(),
                        updatePanierProductInput.getIdProduit(),
                        updatePanierProductInput.getQuantite()
                )
        );
    }

    @RabbitListener(queues = "${mq.queue.add-delete-vfp-panier}")
    public String addOrDeleteVfpPanier(AddDeleteVfpPanier addDeleteVfpPanier) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        var response = addDeleteVfpPanier.isAddOrDelete()
                ? panierService.addVfp(addDeleteVfpPanier.getIdClient(), addDeleteVfpPanier.getIdVfp())
                : panierService.deleteVfp(addDeleteVfpPanier.getIdClient());
        return objectMapper.writeValueAsString(
                response
        );
    }

    @RabbitListener(queues = "${mq.queue.get-panier-by-commerce}")
    public String getPanierByCommerce(CreateCommandeByCommerceInput getPanierByCommerceInput) {
        var panier =panierService.createPanierByCommerce(getPanierByCommerceInput.getEmailCommerce(),
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

        var responseOptionnal = PanierMapper.panierObjectToJson(panier, advantage.get());
        return responseOptionnal.orElseGet(MessageConstantEnum.ERREUR_TYPE::toString);
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
