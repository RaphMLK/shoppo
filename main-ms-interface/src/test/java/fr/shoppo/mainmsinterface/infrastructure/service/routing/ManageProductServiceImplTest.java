package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.commerce.Commercant;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commerce;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Product;
import fr.shoppo.mainmsinterface.domain.bo.commerce.ProductWithCommercant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManageProductServiceImplTest {

    private Class<ManageProductServiceImpl> manageProductServiceClass;
    private ManageProductServiceImpl manageProductServiceImpl;
    private RabbitTemplate template;
    private final String exchangeCommerceName = "exchange_commerce";
    private final String routingKeyGetProductsCommerce = "get_products_commerce";
    private final String routingKeyAddProduct = "add_product_commerce";
    private final String routingKeyUpdateQuantity = "update_quantity_commerce";
    private final String routingKeyGetProduct = "routingKeyGetProduct";
    private final String routingKeyEditProduct = "routingKeyEditProduct";
    private final String routingKeyDeleteProduct = "routingKeyDeleteProduct";
    private final String routingKeyGetProductsCommerceNotVfp = "routingKeyGetProductsCommerceNotVfp";

    @BeforeEach
    void setUp() {
        manageProductServiceClass = ManageProductServiceImpl.class;
        manageProductServiceImpl = new ManageProductServiceImpl();
        template = mock(RabbitTemplate.class);
        DirectExchange exchangeCommerce = mock(DirectExchange.class);
        manageProductServiceImpl.setExchangeCommerce(exchangeCommerce);
        manageProductServiceImpl.setTemplate(template);
        manageProductServiceImpl.setRoutingKeyAddProduct(routingKeyAddProduct);
        manageProductServiceImpl.setRoutingKeyUpdateQuantity(routingKeyUpdateQuantity);
        manageProductServiceImpl.setRoutingKeyGetProductsCommerce(routingKeyGetProductsCommerce);
        manageProductServiceImpl.setRoutingKeyGetProduct(routingKeyGetProduct);
        manageProductServiceImpl.setRoutingKeyEditProduct(routingKeyEditProduct);
        manageProductServiceImpl.setRoutingKeyDeleteProduct(routingKeyDeleteProduct);
        manageProductServiceImpl.setRoutingKeyGetProductsCommerceNotVfp(routingKeyGetProductsCommerceNotVfp);

        when(exchangeCommerce.getName()).thenReturn(exchangeCommerceName);
    }

    @Test
    void manageProductServiceImplAnnotation() {
        assertNotNull(manageProductServiceClass.getAnnotation(Service.class));
    }

    @Test
    void createProduitReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageProductServiceClass.getDeclaredMethod("createProduit", String.class, String.class, float.class, int.class, String.class, String.class).getReturnType());
    }

    @Test
    void createProduit() {
        String name = "name";
        String description = "description";
        float prix = 2F;
        int stock = 5;
        String image = "image";
        String commercant = "commercant";
        ProductWithCommercant product = new ProductWithCommercant();
        product.setName(name);
        product.setDescription(description);
        product.setPrix(prix);
        product.setStock(stock);
        product.setImage(image);
        product.setCommercant(commercant);
        when(template.convertSendAndReceive(
                eq(exchangeCommerceName),
                eq(routingKeyAddProduct),
                eq(product)
        )).thenReturn("ok");
        assertEquals("ok", manageProductServiceImpl.createProduit(name, description, prix, stock, image, commercant));
    }

    @Test
    void updateQuantityProduitReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageProductServiceClass.getDeclaredMethod("updateQuantityProduit", String.class, int.class, int.class).getReturnType());
    }

    @Test
    void updateQuantityProduit() {
        var email = "toto@gmail.com";
        var id = 1;
        var quantity = 2;
        Commerce commerce = new Commerce();
        commerce.setSiretCode("123");
        Commercant commercant = new Commercant();
        commercant.setEmail(email);


        Product product = new Product();
        product.setId(id);
        product.setStock(quantity);

        product.setCommerce(commerce);
        when(template.convertSendAndReceive(
                eq(exchangeCommerceName),
                eq(routingKeyUpdateQuantity),
                eq(product)
        )).thenReturn("ok");
        assertEquals("ok", manageProductServiceImpl.updateQuantityProduit(commerce.getSiretCode(), id, quantity));
    }

    @Test
    void getProductsCommerceReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageProductServiceClass.getDeclaredMethod("getProductsCommerce", String.class).getReturnType());
    }

    @Test
    void getProductsCommerce() {
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyGetProductsCommerce,
                "toto@gmail.com"
        )).thenReturn("ok");
        assertEquals("ok", manageProductServiceImpl.getProductsCommerce("toto@gmail.com"));
    }

    @Test
    void getProductReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageProductServiceClass.getDeclaredMethod("getProduct", Integer.class, String.class).getReturnType());
    }

    @Test
    void getProduct() {
        var id = 1;
        var commercant = "commercant";
        ProductWithCommercant product = new ProductWithCommercant();
        product.setId(id);
        product.setCommercant(commercant);

        when(template.convertSendAndReceive(exchangeCommerceName,
                routingKeyGetProduct,
                product)).thenReturn("ok");

        assertEquals("ok", manageProductServiceImpl.getProduct(id, commercant));
    }

    @Test
    void editProductReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageProductServiceClass.getDeclaredMethod("editProduct", int.class, String.class, String.class, float.class, int.class, String.class, String.class).getReturnType());
    }

    @Test
    void editProduct()  {
        var id = 1;
        var name = "name";
        var description = "description";
        var prix = 5F;
        var stock = 10;
        var image = "image";
        var commercant = "commercant";
        ProductWithCommercant product = new ProductWithCommercant();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrix(prix);
        product.setStock(stock);
        product.setCommercant(commercant);
        product.setImage(image);
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyEditProduct,
                product
        )).thenReturn("ok");

        assertEquals("ok", manageProductServiceImpl.editProduct(
                id,
                name,
                description,
                prix,
                stock,
                image,
                commercant
        ));
    }

    @Test
    void deleteProductReturnType() throws NoSuchMethodException {
        assertEquals(String.class, manageProductServiceClass.getDeclaredMethod("deleteProduct", int.class, String.class).getReturnType());
    }

    @Test
    void deleteProduct() {
        var id = 1;
        var commercant = "commercant";
        ProductWithCommercant product = new ProductWithCommercant();
        product.setId(id);
        product.setCommercant(commercant);
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyDeleteProduct,
                product
        )).thenReturn("ok");

        assertEquals("ok", manageProductServiceImpl.deleteProduct(id, commercant));
    }

    @Test
    void getProductsNotVfp() {
        var email = "email@gmail.com";
        when(template.convertSendAndReceive(
                exchangeCommerceName,
                routingKeyGetProductsCommerceNotVfp,
                email
        )).thenReturn("ok");

        assertEquals("ok", manageProductServiceImpl.getProductsNotVfp(email));
    }

}