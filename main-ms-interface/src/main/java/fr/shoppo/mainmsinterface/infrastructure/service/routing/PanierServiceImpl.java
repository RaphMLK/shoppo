package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.AddDeleteVfpPanier;
import fr.shoppo.mainmsinterface.domain.bo.UpdatePanierProductOutputExchange;
import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeByCommerceOutput;
import fr.shoppo.mainmsinterface.domain.service.PanierService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanierServiceImpl extends RoutingKeyDictionnary implements PanierService {

    RabbitTemplate template;
    DirectExchange exchangeCommerce;

    @Override
    public String getPanier(int idClient) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetPanier,
                        idClient)
        );
    }

    @Override
    public String updatePanierProduct(int idClient, int idProduit, int quantite) {
        var message = new UpdatePanierProductOutputExchange(idClient, idProduit,quantite);
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyUpdatePanier,
                        message)
        );
    }

    @Override
    public String getPanierByCommerce(CreateCommandeByCommerceOutput getPanierByCommerceOutput)  {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetPanierByCommerce,
                        getPanierByCommerceOutput)
        );
    }

    @Override
    public String addOrDeleteVfpPanier(int idClient, int idVfp, boolean addOrDelete) {
        var message = new AddDeleteVfpPanier(idClient,idVfp, addOrDelete);
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyAddOrDeleteVfpPanier,
                        message)
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
