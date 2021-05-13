package fr.shoppo.mainmsinterface.domain.bo.commerce;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void should_have_accessors() {
        Product product = new Product();
        ProductAttribute productAttribute = new ProductAttribute();
        product.setId(1);


        productAttribute.setName("name");
        productAttribute.setDescription("description");
        productAttribute.setPrix(2f);
        productAttribute.setStock(3);
        product.setCommerce(new Commerce());
        productAttribute.setImage("img");

        assertEquals(1, product.getId());
        assertEquals("name",productAttribute.getName());
        assertEquals("description",productAttribute.getDescription());
        assertEquals(2f,productAttribute.getPrix());
        assertEquals(3,productAttribute.getStock());
        assertEquals(new Commerce(), product.getCommerce());
        assertEquals("img", productAttribute.getImage());
    }

}