package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.ms_commerce.domain.bo.Information;
import fr.shoppo.ms_commerce.domain.service.CommerceService;
import fr.shoppo.ms_commerce.domain.service.ManageCommerce;
import fr.shoppo.ms_commerce.domain.service.NotificationService;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.exception.CommerceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.*;

@Component
public class ManageCommerceController {
    private static final Logger logger = LoggerFactory.getLogger(ManageCommerceController.class);

    private NotificationService notificationService;
    private ManageCommerce manageCommerce;
    private CommerceService commerceService;

    @RabbitListener(queues = "${mq.queue.delete-commercant}")
    public String deleteCommercant(Commercant commercant){
        var loggingMessage = "DELETE COMMERCANT";

        try {
            checkInput(commercant,loggingMessage);
            var commerce = commercant.getCommerce();
            var optionalCommerce = manageCommerce.deleteCommercant(commerce.getSiretCode(),commercant);
            if(optionalCommerce.isPresent()) {
                return OK.toString();
            }
            throw new CommerceException(ERREUR);
        } catch (CommerceException commerceException) {
            return logEnding(commerceException.getMessage(),loggingMessage);
        }
    }

    @RabbitListener(queues = "${mq.queue.create-commerce}")
    public String createCommerce(Commercant commercant) {
        return commerceInteraction(commercant,true,"CREATE COMMERCE");
    }

    @RabbitListener(queues = "${mq.queue.add-commercant}")
    public String addCommercant(Commercant commercant) {
        return commerceInteraction(commercant,false,"ADDING COMMERCANT TO COMMERCE");
    }

    @RabbitListener(queues = "${mq.queue.update-commerce}")
    public String updateCommerce(Commerce commerce){
        var commerceOption = manageCommerce.updateCommerce(commerce);
        return (commerceOption.isPresent() ? OK : ERREUR_COMMERCE_NOT_UPDATED).toString();
    }

    /*
    * It's the same schema to create or updating a commerce so we can facto
    * */
    String commerceInteraction(Commercant commercant, boolean isCreation, String loggingMessage){
        try{
            checkInput(commercant,loggingMessage);

            var commerce = commercant.getCommerce();

            var optionalCommerce = manageCommerce.buildCommerce(commerce.getSiretCode(),commercant, isCreation);

            if (optionalCommerce.isPresent()) {
                var information = new Information();
                information.setCommercant(commercant);
                information.setCommerce(optionalCommerce.get());
                if (isCreation) {
                    notificationService.createCommerce(information);
                } else {
                    notificationService.addCommercant(information);
                }
                return logEnding(OK.toString(), loggingMessage);
            }
            throw new CommerceException(isCreation?ERREUR_COMMERCE_NOT_CREATED:ERREUR_COMMERCANT_NOT_ADDED);
        }catch (CommerceException commerceException) {
            return logEnding(commerceException.getMessage(),loggingMessage);
        }
    }

    void checkInput(Commercant commercant,String loggingMessage) throws CommerceException {
        if ( manageCommerce.isValidInputForCreatingCommerce(commercant) )
            throw new CommerceException(ERREUR_INVALID_INPUT);
        logger.info("BEGIN {} : {}",loggingMessage,commercant);
    }

    String logEnding(String response, String loggingMessage){
        logger.info("END {} : {}",loggingMessage,response);
        return response;
    }

    @RabbitListener(queues = "${mq.queue.get-all-commerces}")
    public String getAllCommerces() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(commerceService.getAllCommercesForClient());
    }

    @Autowired
    public void setCommerceService(CommerceService commerceService) {
        this.commerceService = commerceService;
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    public void setManageCommerce(ManageCommerce manageCommerce) {
        this.manageCommerce = manageCommerce;
    }
}
