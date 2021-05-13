package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.service.ManageAdminService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageAdminServiceImpl
        extends RoutingKeyDictionnary
        implements ManageAdminService {

    RabbitTemplate template;

    DirectExchange exchangeAdmin;

    @Override
    public String resetPassword(String email) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeAdmin.getName(),
                        routingKeyResetPasswordAdmin,
                        email)
        );
    }

    @Override
    public String login(User user) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeAdmin.getName(),
                        routingKeyLoginAdmin,
                        user)
        );
    }

    @Autowired
    public void setTemplate(RabbitTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setExchangeAdmin(DirectExchange exchangeAdmin) {
        this.exchangeAdmin = exchangeAdmin;
    }
}
