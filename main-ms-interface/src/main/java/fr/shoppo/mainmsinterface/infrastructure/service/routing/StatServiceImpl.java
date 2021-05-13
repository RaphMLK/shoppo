package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.stat.ReadingInput;
import fr.shoppo.mainmsinterface.domain.service.StatService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatServiceImpl extends RoutingKeyDictionnary implements StatService  {
    RabbitTemplate template;
    DirectExchange exchangeStat;


    @Override
    public String readStat(ReadingInput input) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeStat.getName(),
                        routingKeyReadStat,
                        input)
        );
    }

    @Autowired
    public void setExchangeStat(DirectExchange exchangeStat) {
        this.exchangeStat = exchangeStat;
    }

    @Autowired
    public void setTemplate(RabbitTemplate template) {
        this.template = template;
    }
}
