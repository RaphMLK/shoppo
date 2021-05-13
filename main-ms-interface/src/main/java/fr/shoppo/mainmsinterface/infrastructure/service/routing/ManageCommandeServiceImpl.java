package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeByCommerceOutput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeInput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.TypeCommandeEnum;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageCommandeService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageCommandeServiceImpl extends RoutingKeyDictionnary implements ManageCommandeService {

    RabbitTemplate template;
    DirectExchange exchangeCommerce;

    @Override
    public String getCommandesCommerce(String email) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetCommandesCommerce,
                        email
                )
        );
    }

    @Override
    public String getCommandesClient(int id) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetCommandesClient,
                        id
                )
        );
    }

    @Override
    public String createCommande(int id, TypeCommandeEnum typeCommandeEnum){
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyCreateCommande,
                        CreateCommandeInput.of(id,typeCommandeEnum)
                )
        );
    }

    @Override
    public String createCommandeByCommerce(CreateCommandeByCommerceOutput createCommandeByCommerceOutput) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyCreateCommandeByCommerce,
                        createCommandeByCommerceOutput
                )
        );
    }

    @Override
    public String getCommande(int id) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetCommande,
                        id
                )
        );
    }

    @Override
    public String updateCommande(int id) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyUpdateCommandeTraitee,
                        id
                )
        );
    }

    @Autowired
    public void setTemplate(RabbitTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setExchangeCommerce(DirectExchange exchangeCommerce) {
        this.exchangeCommerce = exchangeCommerce;
    }
}
