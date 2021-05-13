package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeByCommerceOutput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeInput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.TypeCommandeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManageCommandeServiceImplTest {

    private Class<ManageCommandeServiceImpl> manageCommandeServiceClass;
    private ManageCommandeServiceImpl manageCommandeService;
    private RabbitTemplate template;

    private final String exchangeCommerceName = "exchange_commerce";
    private final String routingKeyGetCommandesCommerce = "routingKeyGetCommandesCommerce";

    private final String routingKeyGetCommandesClient = "routingKeyGetCommandesClient";

    private final String routingKeyCreateCommande = "routingKeyCreateCommande";

    private final String routingKeyGetCommande = "routingKeyGetCommande";

    private final String routingKeyUpdateCommandeTraitee = "routingKeyUpdateCommandeTraitee";

    private final String routingKeyCreateCommandeByCommerce ="routingKeyCreateCommandeByCommerce";

    @BeforeEach
    void setUp(){
        manageCommandeServiceClass = ManageCommandeServiceImpl.class;
        manageCommandeService = new ManageCommandeServiceImpl();
        template = mock(RabbitTemplate.class);
        DirectExchange exchangeCommerce = mock(DirectExchange.class);
        manageCommandeService.setTemplate(template);

        manageCommandeService.setExchangeCommerce(exchangeCommerce);
        manageCommandeService.setRoutingKeyGetCommandesCommerce(routingKeyGetCommandesCommerce);
        when(exchangeCommerce.getName()).thenReturn(exchangeCommerceName);

        manageCommandeService.setRoutingKeyGetCommandesClient(routingKeyGetCommandesClient);

        manageCommandeService.setRoutingKeyGetCommande(routingKeyGetCommande);

        manageCommandeService.setRoutingKeyUpdateCommandeTraitee(routingKeyUpdateCommandeTraitee);
        manageCommandeService.setRoutingKeyCreateCommandeByCommerce(routingKeyCreateCommandeByCommerce);
        manageCommandeService.setRoutingKeyCreateCommande(routingKeyCreateCommande);
    }

    @Test
    void manageCommandeServiceImplAnnotation(){
        assertNotNull(manageCommandeServiceClass.getAnnotation(Service.class));
    }

    @Test
    void getCommandesCommerceReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageCommandeServiceClass.getDeclaredMethod("getCommandesCommerce", String.class).getReturnType());
    }

    @Test
    void getCommandesCommerce() {
        var email = "toto@gmail.com";
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyGetCommandesCommerce,
                email
        )).thenReturn("ok");

        assertEquals("ok", manageCommandeService.getCommandesCommerce(email));
    }

    @Test
    void getCommandesClient() {
        int id = 1;
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyGetCommandesClient,
                id
        )).thenReturn("ok");

        assertEquals("ok", manageCommandeService.getCommandesClient(id));
    }

    @Test
    void getCommande() {
        int id = 1;
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyGetCommande,
                id
        )).thenReturn("ok");

        assertEquals("ok", manageCommandeService.getCommande(id));
    }

    @Test
    void updateCommande() {
        int id = 1;
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyUpdateCommandeTraitee,
                id
        )).thenReturn("ok");

        assertEquals("ok", manageCommandeService.updateCommande(id));
    }

    @Test
    void createCommande(){
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyCreateCommande,
                CreateCommandeInput.of(1,TypeCommandeEnum.ONPLACE)
                )
        ).thenReturn("ok");
        assertEquals("ok", manageCommandeService.createCommande(1, TypeCommandeEnum.ONPLACE));
    }

    @Test
    void createCommandeByCommerce(){
        var createCommandeByCommerceOutput = new CreateCommandeByCommerceOutput("toto@gmail.com",1, List.of(), TypeCommandeEnum.ONPLACE, null);
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyCreateCommandeByCommerce,
                createCommandeByCommerceOutput
        )).thenReturn("ok");
        assertEquals("ok", manageCommandeService.createCommandeByCommerce(createCommandeByCommerceOutput));
    }

}
