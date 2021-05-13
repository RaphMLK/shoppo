package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.service.ClientToCommerceService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientToCommerceServiceImpl extends RoutingKeyDictionnary implements ClientToCommerceService {

    RabbitTemplate template;

    DirectExchange exchangeCommerce;


    @Override
    public String getAllCommerces() {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetAllCommerces,"")
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
