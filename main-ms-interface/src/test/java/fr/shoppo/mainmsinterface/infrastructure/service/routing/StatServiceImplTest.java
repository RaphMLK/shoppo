package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.stat.ReadingInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StatServiceImplTest {

    @InjectMocks
    private StatServiceImpl statService;

    @Mock
    private RabbitTemplate template;

    @Mock
    private DirectExchange exchangeStat;

    private final String exchangeStatName = "exchangeStatName";
    private final String routingKeyReadStat = "routingKeyReadStat";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        statService.setRoutingKeyReadStat(routingKeyReadStat);
        when(exchangeStat.getName()).thenReturn(exchangeStatName);
    }

    @Test
    void readStat(){
        var input = new ReadingInput();
        when(template.convertSendAndReceive(exchangeStatName, routingKeyReadStat, input)).thenReturn("ok");
        assertEquals("ok", statService.readStat(input));
    }

}