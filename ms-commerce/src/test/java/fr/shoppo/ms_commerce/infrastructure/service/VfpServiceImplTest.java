package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.bo.AddVfpByProduct;
import fr.shoppo.ms_commerce.domain.bo.AddVfpByProductGroup;
import fr.shoppo.ms_commerce.domain.bo.ProductVfpDTO;
import fr.shoppo.ms_commerce.domain.bo.ProductVfpProductDTO;
import fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum;
import fr.shoppo.ms_commerce.domain.service.CommercantService;
import fr.shoppo.ms_commerce.domain.service.ProductService;
import fr.shoppo.ms_commerce.infrastructure.dao.ProductDao;
import fr.shoppo.ms_commerce.infrastructure.dao.ProductVfpDao;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import fr.shoppo.ms_commerce.infrastructure.entity.ProductVfp;
import fr.shoppo.ms_commerce.infrastructure.entity.embeddable.ProductAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VfpServiceImplTest {

    @InjectMocks
    private VfpServiceImpl vfpService;

    @Mock
    private CommercantService commercantService;

    @Mock
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductVfpDao productVfpDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addVfpByProduct_commercant_not_found() {
        var email = "email@gmail.com";
        var addVfpByProductGroup = new AddVfpByProductGroup();
        addVfpByProductGroup.setEmailCommerce(email);
        addVfpByProductGroup.setAddVfpByProducts(new HashSet<>());
        when(commercantService.getCommercant(email)).thenReturn(Optional.empty());
        var expected = MessageConstantEnum.ERREUR.toString();
        assertEquals(expected, vfpService.addVfpByProduct(addVfpByProductGroup));
    }

    @Test
    void addVfpByProduct_product_not_found() {
        var email = "email@gmail.com";
        var addVfpByProductGroup = new AddVfpByProductGroup();
        addVfpByProductGroup.setEmailCommerce(email);
        var hashSet = new HashSet<AddVfpByProduct>();
        var product = new AddVfpByProduct();
        product.setId(2);
        hashSet.add(product);
        addVfpByProductGroup.setAddVfpByProducts(hashSet);
        var commercant = new Commercant();
        commercant.setCommerce(new Commerce());
        when(commercantService.getCommercant(email)).thenReturn(Optional.of(commercant));
        when(productService.productInCommerce(2, new Commerce())).thenReturn(false);
        var expected = MessageConstantEnum.ERREUR_PRODUCT_NOT_FOUND.toString();
        assertEquals(expected, vfpService.addVfpByProduct(addVfpByProductGroup));
    }

    @Test
    void addVfpByProduct_ok() {
        var email = "email@gmail.com";
        var addVfpByProductGroup = new AddVfpByProductGroup();
        addVfpByProductGroup.setEmailCommerce(email);
        var hashSet = new HashSet<AddVfpByProduct>();
        var product = new AddVfpByProduct();
        product.setId(2);
        product.setLibelle("test");
        hashSet.add(product);
        addVfpByProductGroup.setAddVfpByProducts(hashSet);
        var commercant = new Commercant();
        commercant.setCommerce(new Commerce());
        when(commercantService.getCommercant(email)).thenReturn(Optional.of(commercant));
        when(productService.productInCommerce(2, new Commerce())).thenReturn(true);
        when(productDao.findById(2)).thenReturn(Optional.of(new Product()));
        var expected = MessageConstantEnum.OK.toString();
        assertEquals(expected, vfpService.addVfpByProduct(addVfpByProductGroup));
    }

    @Test
    void deleteVfp_not_exist(){
        var id = 1;
        when(productVfpDao.findById(id)).thenReturn(Optional.empty());
        var expected = MessageConstantEnum.ERREUR_PRODUCT_NOT_FOUND.toString();
        assertEquals(expected, vfpService.deleteVfp(id));
    }

    @Test
    void deleteVfp_ok(){
        var id = 1;
        when(productVfpDao.findById(id)).thenReturn(Optional.of(new ProductVfp()));
        var expected = MessageConstantEnum.OK.toString();
        assertEquals(expected, vfpService.deleteVfp(id));
    }

    @Test
    void getVfp_commercant_not_found(){
        var email = "email@gmail.com";
        when(commercantService.getCommercant(email)).thenReturn(Optional.empty());
        var expected = new ArrayList<ProductVfpDTO>();
        assertEquals(expected, vfpService.getVfp(email));
    }

    @Test
    void getVfp_commercant_product_null(){
        var email = "email@gmail.com";
        var commercant = new Commercant();
        var commerce = new Commerce();
        commerce.setSiretCode("123");
        commercant.setCommerce(commerce);
        when(commercantService.getCommercant(email)).thenReturn(Optional.of(commercant));

        var productVfp = new ProductVfp();
        productVfp.setId(1);
        productVfp.setLibelle("libelle");
        productVfp.setStock(2);
        productVfp.setCommerce(commerce);
        when(productVfpDao.findProductVfpByCommerce(commercant.getCommerce()))
                .thenReturn(List.of(productVfp));

        var expected = List.of(new ProductVfpDTO(
                productVfp.getId(),
                productVfp.getLibelle(),
                null,
                productVfp.getStock(),
                "123"
        ));
        assertEquals(expected, vfpService.getVfp(email));
    }

    @Test
    void getVfp_commercant_product_not_null(){
        var email = "email@gmail.com";
        var commercant = new Commercant();
        var commerce = new Commerce();
        commerce.setSiretCode("123");
        commercant.setCommerce(commerce);
        when(commercantService.getCommercant(email)).thenReturn(Optional.of(commercant));

        var productVfp = new ProductVfp();
        productVfp.setId(1);
        productVfp.setLibelle("libelle");
        productVfp.setCommerce(commerce);
        var product = new Product();
        product.setId(3);
        product.setCommerce(commerce);
        var productAttribute = new ProductAttribute();
        productAttribute.setName("name");
        productAttribute.setStock(10);
        product.setAttribute(productAttribute);
        productVfp.setProduct(product);
        when(productVfpDao.findProductVfpByCommerce(commercant.getCommerce()))
                .thenReturn(List.of(productVfp));

        var expected = List.of(new ProductVfpDTO(
                productVfp.getId(),
                productVfp.getLibelle(),
                new ProductVfpProductDTO(productVfp.getProduct().getId(), productVfp.getProduct().getAttribute().getName()),
                productVfp.getProduct().getAttribute().getStock(),
                "123"
        ));
        assertEquals(expected, vfpService.getVfp(email));
    }
}