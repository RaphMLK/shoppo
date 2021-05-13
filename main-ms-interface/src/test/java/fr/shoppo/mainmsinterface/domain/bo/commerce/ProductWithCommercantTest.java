package fr.shoppo.mainmsinterface.domain.bo.commerce;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductWithCommercantTest {

    @Test
    void should_have_accessors() {
        ProductWithCommercant product = new ProductWithCommercant();
        product.setId(1);
        product.setName("name");
        product.setDescription("description");
        product.setPrix(2f);
        product.setStock(3);
        product.setCommercant("commercant");
        product.setImage("img");

        assertEquals(1, product.getId());
        assertEquals("name",product.getName());
        assertEquals("description",product.getDescription());
        assertEquals(2f,product.getPrix());
        assertEquals(3,product.getStock());
        assertEquals("commercant", product.getCommercant());
        assertEquals("img", product.getImage());
    }

}