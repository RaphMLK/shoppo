package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.infrastructure.dao.CommercantDao;
import fr.shoppo.ms_commerce.infrastructure.dao.CommerceDao;
import fr.shoppo.ms_commerce.infrastructure.dao.ProductDao;
import fr.shoppo.ms_commerce.infrastructure.dao.ProductVfpDao;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import fr.shoppo.ms_commerce.infrastructure.entity.ProductVfp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    ProductDao productDao;
    CommerceDao commerceDao;
    CommercantDao commercantDao;
    ProductVfpDao productVfpDao;

    ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productDao = mock(ProductDao.class);
        commerceDao = mock(CommerceDao.class);
        commercantDao = mock(CommercantDao.class);
        productVfpDao = mock(ProductVfpDao.class);

        productService = new ProductServiceImpl(productDao, commerceDao,commercantDao, productVfpDao);
    }

    @Test
    void createProduit() {
        Product product = Product.of(0,"","",0f,0,Commerce.of(),null).image(new byte[]{});


        when(productDao.save(Mockito.any())).thenReturn(null);
        assertEquals(Optional.empty(), productService.createProduit(product));

        when(productDao.save(Mockito.any())).thenReturn(product);
        when(commerceDao.findBySiretCode(Mockito.any())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), productService.createProduit(product));

        when(commerceDao.findBySiretCode(Mockito.any())).thenReturn(Optional.of(new Commerce()));
        assertEquals(Optional.of(product), productService.createProduit(product));
    }

    @Test
    void getProduct() {
        Commerce commerce = new Commerce();
        commerce.setSiretCode("5165");

        Product product = new Product();
        product.setId(1);
        product.setCommerce(commerce);


        when(commerceDao.findBySiretCode(any())).thenReturn(Optional.of(commerce));


        when(productDao.findById(1)).thenReturn(null);
        assertEquals(Optional.empty(), productService.getProduct(product));

        when(productDao.findById(1)).thenReturn(Optional.of(product));
        assertEquals(Optional.of(product), productService.getProduct(product));
    }

    @Test
    void deleteProduct() {
        Commerce commerce = new Commerce();
        commerce.setSiretCode("5165");

        Product product = new Product();
        product.setId(1);
        product.setCommerce(commerce);

        when(commerceDao.findBySiretCode(any())).thenReturn(Optional.of(commerce));

        when(productDao.findById(1)).thenReturn(null);
        assertEquals(Optional.empty(), productService.deleteProduct(product));

        when(productDao.findById(1)).thenReturn(Optional.of(product));
        assertEquals(Optional.of(product), productService.deleteProduct(product));

    }

    @Test
    void getProductsCommerce_CommercantNull(){
        var email = "toto@gmail.com";
        when(commercantDao.findByEmail(email)).thenReturn(null);

        assertEquals(new ArrayList<>(), productService.getProductsCommerce(email));
    }

    @Test
    void getProductsCommerce_CommercantNotNull(){
        var email = "toto@gmail.com";
        var commercant = new Commercant();
        commercant.setId(1);
        when(commercantDao.findByEmail(email)).thenReturn(commercant);

        var productList = new ArrayList<Product>();
        var product = new Product();
        product.setId(2);
        productList.add(product);

        when(productDao.findByCommerce(commercant.getCommerce())).thenReturn(productList);

        assertEquals(productList, productService.getProductsCommerce(email));
    }

    @Test
    void productInCommerce_product_not_exists(){
        var commerce = new Commerce();
        var id = 1;
        when(productDao.findById(id)).thenReturn(Optional.empty());
        assertFalse(productService.productInCommerce(id, commerce));
    }

    @Test
    void productInCommerce_product_not_commerce(){
        var commerce = new Commerce();
        commerce.setSiretCode("1234");
        var id = 1;
        var product = new Product();
        product.setCommerce(new Commerce());
        when(productDao.findById(id)).thenReturn(Optional.of(product));
        assertFalse(productService.productInCommerce(id, commerce));
    }

    @Test
    void productInCommerce_true(){
        var commerce = new Commerce();
        commerce.setSiretCode("1234");
        var id = 1;
        var product = new Product();
        product.setCommerce(commerce);
        when(productDao.findById(id)).thenReturn(Optional.of(product));
        assertTrue(productService.productInCommerce(id, commerce));
    }

    @Test
    void getProductsNotVfpCommerce_commercant_null(){
        var email = "emaoil@gmail.Com";
        when(commercantDao.findByEmail(email)).thenReturn(null);
        assertEquals(new ArrayList<>(), productService.getProductsNotVfpCommerce(email));
    }

    @Test
    void getProductsNotVfpCommerce_list_empty(){
        var email = "emaoil@gmail.Com";
        var commercant = new Commercant();
        commercant.setCommerce(new Commerce());
        when(commercantDao.findByEmail(email)).thenReturn(commercant);
        when(productDao.findByCommerce(commercant.getCommerce())).thenReturn(List.of());
        assertEquals(new ArrayList<>(), productService.getProductsNotVfpCommerce(email));
    }

    @Test
    void getProductsNotVfpCommerce_not_product_not_vfp(){
        var email = "emaoil@gmail.Com";
        var commercant = new Commercant();
        commercant.setCommerce(new Commerce());
        var product = new Product();
        when(commercantDao.findByEmail(email)).thenReturn(commercant);
        when(productDao.findByCommerce(commercant.getCommerce())).thenReturn(List.of(product));
        when(productVfpDao.findProductVfpByProduct(product)).thenReturn(Optional.of(new ProductVfp()));
        assertEquals(new ArrayList<>(), productService.getProductsNotVfpCommerce(email));
    }

    @Test
    void getProductsNotVfpCommerce_with_product_not_vfp(){
        var email = "emaoil@gmail.Com";
        var commercant = new Commercant();
        commercant.setCommerce(new Commerce());
        var product = new Product();
        when(commercantDao.findByEmail(email)).thenReturn(commercant);
        when(productDao.findByCommerce(commercant.getCommerce())).thenReturn(List.of(product));
        when(productVfpDao.findProductVfpByProduct(product)).thenReturn(Optional.empty());
        assertEquals(List.of(product), productService.getProductsNotVfpCommerce(email));
    }
}