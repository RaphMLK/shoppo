package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commerce;
import fr.shoppo.mainmsinterface.domain.bo.commerce.FindCommercantInput;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageCommercantServiceImpl
        extends RoutingKeyDictionnary
        implements ManageCommercantService  {

    RabbitTemplate template;
    DirectExchange exchangeCommerce;

    @Override
    public String resetPassword(String email) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyResetPasswordCommercant,
                        email)
        );
    }

    @Override
    public String login(User user) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyLoginCommercant,
                        user)
        );
    }

    @Override
    public String changePassword(User user) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyChangePasswordNeedCommercant,
                        user)
        );
    }

    @Override
    public String findCommercant(String emailOrSiret,boolean withProducts) {
        return String.valueOf(template.convertSendAndReceive(
                exchangeCommerce.getName(),
                routingKeyFindCommercant,
                FindCommercantInput.of(emailOrSiret,withProducts))
        );
    }

    @Override
    public String update(Commerce commerce){
        return String.valueOf(
          template.convertSendAndReceive(
                  exchangeCommerce.getName(),
                  routingKeyUpdateCommerce,
                  commerce
          )
        );
    }

    @Override
    public String getCommercant(String email) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetCommercant,
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
