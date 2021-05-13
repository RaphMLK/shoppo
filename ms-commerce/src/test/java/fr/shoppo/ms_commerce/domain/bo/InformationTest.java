package fr.shoppo.ms_commerce.domain.bo;

import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InformationTest {

    @Test
    void should_have_accessors() {
        Information information = new Information();
        information.setMessage("message");
        var commercant = new Commercant();
        commercant.setEmail("toto@gmail.com");
        information.setCommercant(commercant);
        var productList = new HashSet<Product>();
        information.setProductList(productList);

        assertEquals("message", information.getMessage());
        assertEquals(commercant, information.getCommercant());
        assertEquals(productList, information.getProductList());
    }
}
