package fr.shoppo.mainmsinterface.presentation;

import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageVfpCommerceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class ManageVfpCommerceTest {

    @InjectMocks
    private ManageVfpCommerce manageVfpCommerce;

    @Mock
    private ManageVfpCommerceService manageVfpCommerceService;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(principal.getName()).thenReturn("name");
    }

    @Test
    void addVfpByProduct_anyError() {
        when(manageVfpCommerceService.addVfpByProduct(any())).thenReturn("null");
        var expected = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstantEnum.ERREUR.toString());
        assertEquals(expected, manageVfpCommerce.addVfpByProduct(principal,new HashSet<>()));
    }

    @Test
    void addVfpByProduct_errorSpecifique() {
        when(manageVfpCommerceService.addVfpByProduct(any())).thenReturn("toto");
        var expected = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("toto");
        assertEquals(expected, manageVfpCommerce.addVfpByProduct(principal,new HashSet<>()));
    }

    @Test
    void addVfpByProduct_ok() {
        when(manageVfpCommerceService.addVfpByProduct(any())).thenReturn("OK");
        var expected = ResponseEntity.ok("OK");
        assertEquals(expected, manageVfpCommerce.addVfpByProduct(principal,new HashSet<>()));
    }

    @Test
    void deleteVfp_anyError() {
        when(manageVfpCommerceService.deleteVfp(anyInt())).thenReturn("null");
        var expected = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstantEnum.ERREUR.toString());
        assertEquals(expected, manageVfpCommerce.deleteVfp(1));
    }

    @Test
    void deleteVfp_errorSpecifique() {
        when(manageVfpCommerceService.deleteVfp(anyInt())).thenReturn("toto");
        var expected = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("toto");
        assertEquals(expected, manageVfpCommerce.deleteVfp(1));
    }

    @Test
    void deleteVfp_ok() {
        when(manageVfpCommerceService.deleteVfp(anyInt())).thenReturn("OK");
        var expected = ResponseEntity.ok("OK");
        assertEquals(expected, manageVfpCommerce.deleteVfp(1));
    }

    @Test
    void getVfp_anyError() {
        when(manageVfpCommerceService.getVfp(principal.getName())).thenReturn("null");
        var expected = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstantEnum.ERREUR.toString());
        assertEquals(expected, manageVfpCommerce.getVfp(principal));
    }


    @Test
    void getVfp_ok() {
        when(manageVfpCommerceService.getVfp(principal.getName())).thenReturn("OK");
        var expected = ResponseEntity.ok("OK");
        assertEquals(expected, manageVfpCommerce.getVfp(principal));
    }
}