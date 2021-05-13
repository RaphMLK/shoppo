package fr.shoppo.ms_stat.presentation;

import fr.shoppo.ms_stat.domain.bo.commande.StatInput;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommerceRequest {

    RealTimeCalculator commerceCalculator;

    @RabbitListener(queues = "${mq.queue.stat.create-commande}")
    public void calculateCreateCommandeStatistics(StatInput input) {
        var commandes = input.getCommandes();
        commandes.forEach( cmd ->
                commerceCalculator
                    .load(cmd)
                    .run() );
    }

    @Autowired
    public void setCommerceCalculator(RealTimeCalculator commerceCalculator) {
        this.commerceCalculator = commerceCalculator;
    }
}
