package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.ms_commerce.domain.bo.ProductWithCommercant;
import fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum;
import fr.shoppo.ms_commerce.domain.service.CommercantService;
import fr.shoppo.ms_commerce.domain.service.ProductService;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantNotFoundException;
import fr.shoppo.ms_commerce.infrastructure.exception.ProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManageProductControllerTest {

    ManageProductController manageProductController;

    ProductService productService;
    CommercantService commercantService;

    Commerce commerce;
    Commercant commercant;
    Product product;
    ProductWithCommercant productWithCommercant;
    ProductWithCommercant productWithCommercant1;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        commercantService = mock(CommercantService.class);

        manageProductController = new ManageProductController(productService, commercantService);


        this.commercant = new Commercant();
        this.commerce = new Commerce();
        this.commercant.setCommerce(commerce);

        this.product = Product.of(1,"name","description",2f,3,commerce,"a".getBytes());

        this.productWithCommercant = initProductWithCommercant(product);

        this.productWithCommercant1 = initProductWithCommercant(product);
    }

    @Test
    void createProduct_commercant_not_found() throws CommercantNotFoundException {
        when(commercantService.getCommercant("email")).thenReturn(Optional.empty());
        assertEquals(MessageConstantEnum.COMMERCANT_NOT_FOUND.toString() ,manageProductController.createProduct(productWithCommercant));
    }

    @Test
    void createProduct_product_not_created() throws CommercantNotFoundException {
        when(commercantService.getCommercant("email")).thenReturn(Optional.of(commercant));
        when(productService.createProduit(product)).thenReturn(Optional.empty());
        assertEquals(MessageConstantEnum.ERREUR_PRODUCT_NOT_CREATED.toString(), manageProductController.createProduct(productWithCommercant));
    }

    @Test
    void getProduct_commercant_not_found() throws CommercantNotFoundException {
        when(commercantService.getCommercant("email")).thenReturn(Optional.empty());
        assertEquals(MessageConstantEnum.COMMERCANT_NOT_FOUND.toString(), manageProductController.createProduct(productWithCommercant));
    }

    @Test
    void getProduct_product_not_found() throws CommercantNotFoundException {
        when(commercantService.getCommercant("email")).thenReturn(Optional.of(commercant));
        when(productService.getProduct(product)).thenReturn(Optional.empty());
        assertEquals(MessageConstantEnum.ERREUR_PRODUCT_NOT_FOUND.toString() ,manageProductController.getProduct(productWithCommercant));
    }

    @Test
    void getProduct_ok() throws CommercantNotFoundException, JsonProcessingException {
        when(commercantService.getCommercant("email")).thenReturn(Optional.of(commercant));
        ObjectMapper objectMapper = new ObjectMapper();
        productWithCommercant1.setCommercant("");
        // TODO enlever le any() par le product
        when(productService.getProduct(any())).thenReturn(Optional.of(product));
        assertEquals(objectMapper.writeValueAsString(productWithCommercant1), manageProductController.getProduct(productWithCommercant));
    }

    @Test
    void deleteProduct_product_not_found() throws CommercantNotFoundException {
        when(commercantService.getCommercant("email")).thenReturn(Optional.of(commercant));
        when(productService.getProduct(product)).thenReturn(Optional.empty());
        assertEquals(MessageConstantEnum.ERREUR_DELETE_PRODUCT.toString() ,manageProductController.deleteProduct(productWithCommercant));
    }

    @Test
    void deleteProduct_commercant_not_found() throws CommercantNotFoundException {
        when(commercantService.getCommercant("email")).thenReturn(Optional.empty());
        assertEquals(MessageConstantEnum.COMMERCANT_NOT_FOUND.toString(), manageProductController.deleteProduct(productWithCommercant));
    }

    @Test
    void deleteProduct_ok() throws CommercantNotFoundException {
        when(commercantService.getCommercant("email")).thenReturn(Optional.of(commercant));
        // TODO enlever le any() par le product
        when(productService.deleteProduct(any())).thenReturn(Optional.of(product));
        assertEquals("OK", manageProductController.deleteProduct(productWithCommercant));
    }

    ProductWithCommercant initProductWithCommercant(Product product) {
        ProductWithCommercant productWithCommercant = new ProductWithCommercant();
        productWithCommercant.setId(product.getId());
        productWithCommercant.setName(product.getAttribute().getName());
        productWithCommercant.setDescription(product.getAttribute().getDescription());
        productWithCommercant.setImage(Base64.getEncoder().encodeToString(product.getAttribute().getImage()));
        productWithCommercant.setStock(product.getAttribute().getStock());
        productWithCommercant.setPrix(product.getAttribute().getPrix());
        productWithCommercant.setCommercant("email");
        return productWithCommercant;
    }

    @Test
    void updateQuantity_OK(){
        var product = new Product();
        product.setId(1);

        when(productService.updateQuantityProduit(product)).thenReturn(Optional.of(product));

        assertEquals(OK.toString(), manageProductController.updateQuantity(product));
    }

    @Test
    void updateQuantity_ERROR(){
        var product = new Product();
        product.setId(1);

        when(productService.updateQuantityProduit(product)).thenReturn(Optional.empty());
        var expected = new ProductException(MessageConstantEnum.ERREUR_PRODUCT_NOT_UPDATE).getMessage();
        assertEquals(expected, manageProductController.updateQuantity(product));
    }

    @Test
    void getProductsCommerce() throws JsonProcessingException {
        var email = "toto@gmail.com";
        var product = new Product();
        product.setCommerce(new Commerce());
        product.setId(1);
        var productListParam = new ArrayList<Product>();
        productListParam.add(product);

        when(productService.getProductsCommerce(email)).thenReturn(productListParam);

        product.setCommerce(null);
        var productListExpected = new ArrayList<Product>();
        productListExpected.add(product);
        ObjectMapper objectMapper = new ObjectMapper();
        var expected = objectMapper.writeValueAsString(productListExpected);

        assertEquals(expected, manageProductController.getProductsCommerce(email));
    }

    @Test
    void getProductsNotVfpCommerce() throws JsonProcessingException {
        var email = "email@gmail.com";
        when(productService.getProductsNotVfpCommerce(email)).thenReturn(List.of());
        var expected = "[]";
        assertEquals(expected, manageProductController.getProductsNotVfpCommerce(email));
    }

}