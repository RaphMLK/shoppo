package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.commerce.Commercant;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commerce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ManageCommerceServiceImplTest {

    private Class<ManageCommerceServiceImpl> manageCommerceServiceImplClass;
    private ManageCommerceServiceImpl manageCommerceServiceImplMock;
    private ManageCommerceServiceImpl manageCommerceServiceImpl;
    private DirectExchange exchangeCommerce;
    private RabbitTemplate template;

    @BeforeEach
    void setUp() {
        manageCommerceServiceImplClass = ManageCommerceServiceImpl.class;
        manageCommerceServiceImplMock = mock(ManageCommerceServiceImpl.class);
        exchangeCommerce = mock(DirectExchange.class);

        doCallRealMethod().when(manageCommerceServiceImplMock).setExchangeCommerce(any(DirectExchange.class));
        manageCommerceServiceImplMock.setExchangeCommerce(exchangeCommerce);

        doCallRealMethod().when(manageCommerceServiceImplMock).setRoutingKeyCreateCommerce(any(String.class));
        manageCommerceServiceImplMock.setRoutingKeyCreateCommerce("routing_key_create_commerce");

        doCallRealMethod().when(manageCommerceServiceImplMock).setRoutingKeyAddCommercant(any(String.class));
        manageCommerceServiceImplMock.setRoutingKeyAddCommercant("routing_add_commercant_commerce");


        doCallRealMethod().when(manageCommerceServiceImplMock).setRoutingKeyDeleteCommercant(any(String.class));
        manageCommerceServiceImplMock.setRoutingKeyDeleteCommercant("routing_delete_commercant_commerce");

        manageCommerceServiceImpl = new ManageCommerceServiceImpl();
        template = mock(RabbitTemplate.class);

        manageCommerceServiceImpl.setTemplate(template);
    }

    @Test
    void ManageCommerceServiceImplAnnotation() {
        assertNotNull(manageCommerceServiceImplClass.getAnnotation(Service.class));
    }

    @Test
    void createCommerceReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageCommerceServiceImplClass.getDeclaredMethod("createCommerce", String.class, String.class).getReturnType());
    }

    @Test
    void createCommerce() {
        when(exchangeCommerce.getName()).thenReturn("exchange_create_commerce");
        when(manageCommerceServiceImplMock.commerceInteraction("123456",
                "toto@gmail.com",
                "routing_key_create_commerce",
                "exchange_create_commerce")).thenReturn("ok");
        when(manageCommerceServiceImplMock.createCommerce("123456",
                "toto@gmail.com")).thenCallRealMethod();
        assertEquals("ok", manageCommerceServiceImplMock.createCommerce("123456",
                "toto@gmail.com"));
    }

    @Test
    void addCommercantReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageCommerceServiceImplClass.getDeclaredMethod("addCommercant", String.class, String.class).getReturnType());
    }

    @Test
    void addCommercant() {
        when(exchangeCommerce.getName()).thenReturn("exchange_add_commercant_commerce");
        when(manageCommerceServiceImplMock.commerceInteraction("123456",
                "toto@gmail.com",
                "routing_add_commercant_commerce",
                "exchange_add_commercant_commerce")).thenReturn("ok");
        when(manageCommerceServiceImplMock.addCommercant("123456",
                "toto@gmail.com")).thenCallRealMethod();
        assertEquals("ok", manageCommerceServiceImplMock.addCommercant("123456",
                "toto@gmail.com"));
    }

    @Test
    void rmCommercantReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageCommerceServiceImplClass.getDeclaredMethod("rmCommercant", String.class, String.class).getReturnType());
    }

    @Test
    void rmCommercant() {
        when(exchangeCommerce.getName()).thenReturn("exchange_delete_commercant_commerce");
        when(manageCommerceServiceImplMock.commerceInteraction("123456",
                "toto@gmail.com",
                "routing_delete_commercant_commerce",
                "exchange_delete_commercant_commerce")).thenReturn("ok");
        when(manageCommerceServiceImplMock.rmCommercant("123456",
                "toto@gmail.com")).thenCallRealMethod();
        assertEquals("ok", manageCommerceServiceImplMock.rmCommercant("123456",
                "toto@gmail.com"));
    }

    @Test
    void commerceInteraction() {
        String siret = "123456";
        String mail = "toto@gmail.com";
        var commerce = new Commerce();
        var commercant = new Commercant();

        commerce.setSiretCode(siret);
        commercant.setEmail(mail);
        commercant.setCommerce(commerce);
        when(template.convertSendAndReceive(
                eq("exchange"),
                eq("routing"),
                eq(commercant))).thenReturn("ok");

        assertEquals("ok", manageCommerceServiceImpl.commerceInteraction(
                siret, mail, "routing","exchange"
        ));
    }

    @Test
    void commerceInteractionReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageCommerceServiceImplClass.getDeclaredMethod("commerceInteraction", String.class, String.class, String.class, String.class).getReturnType());
    }
}
