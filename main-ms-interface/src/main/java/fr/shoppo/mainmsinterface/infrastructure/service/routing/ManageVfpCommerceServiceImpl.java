package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.commerce.AddVfpByProductGroup;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageVfpCommerceService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageVfpCommerceServiceImpl extends RoutingKeyDictionnary implements ManageVfpCommerceService {

    private RabbitTemplate template;
    DirectExchange exchangeCommerce;

    @Override
    public String addVfpByProduct(AddVfpByProductGroup addVfpByProductGroup) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingAddVfpByProductAndCommerce,
                        addVfpByProductGroup
                )
        );
    }

    @Override
    public String deleteVfp(int idVfp) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingdeleteVfpByCommerce,
                        idVfp
                )
        );
    }

    @Override
    public String getVfp(String email) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetVfpCommerce,
                        email
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
