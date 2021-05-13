package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.AddDeleteVfpPanier;
import fr.shoppo.mainmsinterface.domain.bo.UpdatePanierProductOutputExchange;
import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeByCommerceOutput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.TypeCommandeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PanierServiceImplTest {

    @InjectMocks
    private PanierServiceImpl panierService;

    @Mock
    private RabbitTemplate template;

    @Mock
    private DirectExchange exchangeCommerce;

    private final String exchangeCommerceName = "exchangeCommerceName";
    private final String routingKeyGetPanier = "routingKeyGetPanier";
    private final String routingKeyUpdatePanier = "routingKeyUpdatePanier";
    private final String routingKeyGetPanierByCommerce = "routingKeyGetPanierByCommerce";
    private final String routingKeyAddOrDeleteVfpPanier = "routingKeyAddOrDeleteVfpPanier";


    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        panierService.setRoutingKeyGetPanier(routingKeyGetPanier);
        panierService.setRoutingKeyUpdatePanier(routingKeyUpdatePanier);
        panierService.setRoutingKeyGetPanierByCommerce(routingKeyGetPanierByCommerce);
        panierService.setRoutingKeyAddOrDeleteVfpPanier(routingKeyAddOrDeleteVfpPanier);
        when(exchangeCommerce.getName()).thenReturn(exchangeCommerceName);
    }

    @Test
    void getPanier(){
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyGetPanier,
                10
        )).thenReturn("toto");
        assertEquals("toto",panierService.getPanier(10));
    }

    @Test
    void updatePanier(){
        var message = new UpdatePanierProductOutputExchange(1, 2,3);
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyUpdatePanier,
                message
        )).thenReturn("toto");
        assertEquals("toto",panierService.updatePanierProduct(1,2,3));
    }

    @Test
    void getPanierByCommerce(){
        var message = new CreateCommandeByCommerceOutput("email", 1, new ArrayList<>(), TypeCommandeEnum.ONLINE, null);
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyGetPanierByCommerce,
                message
        )).thenReturn("toto");
        assertEquals("toto",panierService.getPanierByCommerce(message));
    }

    @Test
    void addOrDeleteVfpPanier(){
        var idClient = 1;
        var idVfp = 2;
        var addOrDelete = false;
        var message = new AddDeleteVfpPanier(idClient,idVfp, addOrDelete);
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyAddOrDeleteVfpPanier,
                message
        )).thenReturn("toto");
        assertEquals("toto",panierService.addOrDeleteVfpPanier(idClient, idVfp, addOrDelete));
    }
}
