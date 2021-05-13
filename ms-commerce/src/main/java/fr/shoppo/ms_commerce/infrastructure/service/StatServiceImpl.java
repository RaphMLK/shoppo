package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.bo.stat.StatInput;
import fr.shoppo.ms_commerce.domain.service.StatService;
import fr.shoppo.ms_commerce.infrastructure.entity.Commande;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatServiceImpl implements StatService {

    private final RabbitTemplate template;

    DirectExchange exchangeStat;
    String routingKeyCreateCommande;

    public StatServiceImpl(
            RabbitTemplate template
    ) {
        this.template = template;
    }
    @Override
    public void createCommande(List<Commande> commandeList) {

        this.template.convertAndSend(exchangeStat.getName(),
                routingKeyCreateCommande, StatInput.of(commandeList));
    }

    @Value("${mq.routing-key.stat.create-commande}")
    public void setRoutingKeyCreateCommande(String routingKeyCreateCommande) {
        this.routingKeyCreateCommande = routingKeyCreateCommande;
    }

    @Autowired
    public void setExchangeStat(DirectExchange exchangeStat) {
        this.exchangeStat = exchangeStat;
    }
}
