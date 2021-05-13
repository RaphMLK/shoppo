package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClientToCommerceServiceImplTest {

    @InjectMocks
    private ClientToCommerceServiceImpl clientToCommerceService;

    @Mock
    private RabbitTemplate template;

    @Mock
    private DirectExchange exchangeCommerce;

    private final String routingKeyGetAllCommerces = "routingKeyGetAllCommerces";
    private final String exchangeCommerceName = "exchangeCommerceName";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        clientToCommerceService.setRoutingKeyGetAllCommerces(routingKeyGetAllCommerces);
        when(exchangeCommerce.getName()).thenReturn(exchangeCommerceName);
    }

    @Test
    void getAllCommerces(){
        when(template.convertSendAndReceive(exchangeCommerceName,
                routingKeyGetAllCommerces,
                "")).thenReturn("toto");

        assertEquals("toto",clientToCommerceService.getAllCommerces());
    }
}
