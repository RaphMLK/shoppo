package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.bo.client.AvantageInput;
import fr.shoppo.mainmsinterface.domain.bo.client.SoldeInput;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageClientServiceImpl
        extends RoutingKeyDictionnary
        implements ManageClientService {

    RabbitTemplate template;

    DirectExchange exchangeClient;

    @Override
    public String resetPassword(String email) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeClient.getName(),
                        routingKeyResetPasswordClient,
                        email)
        );
    }

    @Override
    public String login(User user) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeClient.getName(),
                        routingKeyLoginClient,
                        user)
        );
    }

    @Override
    public String createClient(User user) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeClient.getName(),
                routingKeyCreateClient,
                user)
        );
    }

    @Override
    public String changePassword(User user) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeClient.getName(),
                        routingKeyChangePasswordNeedClient,
                        user)
        );
    }

    @Override
    public String getClient(String email) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeClient.getName(),
                        routingKeyGetClient,
                        email)
        );
    }

    @Override
    public String getClientByQrCode(String qrCode) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeClient.getName(),
                        routingKeyGetClientByQrCode,
                        qrCode)
        );
    }

    @Override
    public String updateSolde(SoldeInput soldeInput){
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeClient.getName(),
                        routingKeyUpdateSolde,
                        soldeInput)
        );
    }

    @Override
    public String listAvantage()  {
        return String.valueOf(template
                .convertSendAndReceive(exchangeClient.getName(),routingKeyGetAllAvantage,""));
    }

    @Override
    public String updateAvantage(AvantageInput avantageInput){
        return String.valueOf(template
                .convertSendAndReceive(exchangeClient.getName(),routingKeyUpdateAvantage,avantageInput));
    }

    @Override
    public boolean isPark(String plaque) {
        return Boolean.parseBoolean(String.valueOf(template
                .convertSendAndReceive(exchangeClient.getName(),routingKeyIsPark,plaque)));
    }

    @Override
    public boolean isTransport(String qrCode) {
        return Boolean.parseBoolean(String.valueOf(template
                .convertSendAndReceive(exchangeClient.getName(),routingKeyIsTransport,qrCode)));
    }

    @Autowired
    public void setTemplate(RabbitTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setExchangeClient(DirectExchange exchangeClient) {
        this.exchangeClient = exchangeClient;
    }
}
