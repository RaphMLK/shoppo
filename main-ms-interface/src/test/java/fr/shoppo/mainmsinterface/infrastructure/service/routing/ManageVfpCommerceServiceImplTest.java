package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.commerce.AddVfpByProductGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ManageVfpCommerceServiceImplTest {

    @InjectMocks
    private ManageVfpCommerceServiceImpl manageVfpCommerceService;

    @Mock
    private RabbitTemplate template;

    @Mock
    private DirectExchange exchangeCommerce;

    private final String routingAddVfpByProductAndCommerce = "routingAddVfpByProductAndCommerce";
    private final String routingdeleteVfpByCommerce = "routingdeleteVfpByCommerce";
    private final String routingKeyGetVfpCommerce = "routingKeyGetVfpCommerce";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        manageVfpCommerceService.setRoutingAddVfpByProductAndCommerce(routingAddVfpByProductAndCommerce);
        manageVfpCommerceService.setRoutingdeleteVfpByCommerce(routingdeleteVfpByCommerce);
        manageVfpCommerceService.setRoutingKeyGetVfpCommerce(routingKeyGetVfpCommerce);
        when(exchangeCommerce.getName()).thenReturn("exchangeCommerce");
    }

    @Test
    void addVfpByProduct() {
        var addVfpByProductGroup = new AddVfpByProductGroup();
        addVfpByProductGroup.setEmailCommerce("email@gmail.com");
        addVfpByProductGroup.setAddVfpByProducts(new HashSet<>());

        when(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingAddVfpByProductAndCommerce,
                        addVfpByProductGroup
                )
        ).thenReturn("ok");

        assertEquals("ok", manageVfpCommerceService.addVfpByProduct(addVfpByProductGroup));
    }

    @Test
    void deleteVfp(){
        when(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingdeleteVfpByCommerce,
                        1
                )
        ).thenReturn("ok");

        assertEquals("ok", manageVfpCommerceService.deleteVfp(1));
    }

    @Test
    void getVfp(){
        var email = "email@gmail.com";

        when(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetVfpCommerce,
                        email
                )
        ).thenReturn("ok");

        assertEquals("ok", manageVfpCommerceService.getVfp(email));
    }

}