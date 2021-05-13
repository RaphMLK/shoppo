package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.commerce.Commercant;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commerce;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageCommerceService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageCommerceServiceImpl
        extends RoutingKeyDictionnary
        implements ManageCommerceService {

    RabbitTemplate template;
    DirectExchange exchangeCommerce;

    @Override
    public String createCommerce(String siret, String mail) {
        return commerceInteraction(siret,mail,routingKeyCreateCommerce,exchangeCommerce.getName());
    }

    @Override
    public String addCommercant(String siret, String mail)  {
        return commerceInteraction(siret,mail,routingKeyAddCommercant,exchangeCommerce.getName());
    }

    @Override
    public String rmCommercant(String siret, String mail) {
        return commerceInteraction(siret,mail,routingKeyDeleteCommercant,exchangeCommerce.getName());
    }

    public String commerceInteraction(
            String siret,
            String mail,
            String routingKey,
            String exchangerName
    ){
        var commerce = new Commerce();
        var commercant = new Commercant();

        commerce.setSiretCode(siret);
        commercant.setEmail(mail);
        commercant.setCommerce(commerce);

        return String.valueOf(
                template.convertSendAndReceive(exchangerName,
                        routingKey,
                        commercant)
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

