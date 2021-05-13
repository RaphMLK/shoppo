package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commercant;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commerce;
import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageProductService;
import fr.shoppo.mainmsinterface.infrastructure.json.JsonManagerComponent;
import fr.shoppo.mainmsinterface.infrastructure.service.exception.ProductRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManageProductControllerTest {

    ManageProductController manageProductController;

    ManageProductService manageProductService;

    ManageCommercantService manageCommercantService;

    JsonManagerComponent jsonManagerComponent;

    Principal principal;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        manageProductService = mock(ManageProductService.class);
        manageCommercantService = mock(ManageCommercantService.class);
        jsonManagerComponent = mock(JsonManagerComponent.class);
        principal = mock(Principal.class);

        manageProductController = new ManageProductController();
        manageProductController.setManageProductService(manageProductService);
        manageProductController.setJsonManagerComponent(jsonManagerComponent);
        manageProductController.setManageCommercantService(manageCommercantService);
        var mapper = new ObjectMapper();
        var henry = new Commercant();
        var shoploc = new Commerce();
        shoploc.setSiretCode("123");
        henry.setCommerce(shoploc);
        when(manageCommercantService.getCommercant(any())).thenReturn(mapper.writeValueAsString(henry));
    }

    @Test
    void should_receive_valid_json_createProduct() throws Exception {
        when(jsonManagerComponent.tryToGet(any(), any())).thenCallRealMethod();
        assertThrows(ProductRequestException.class, () -> manageProductController.createProduct("not a json"));
    }

    @Test
    void should_receive_valid_data_createProduct() throws Exception {
        when(jsonManagerComponent.tryToGet(any(), any())).thenCallRealMethod();
        when(manageProductService.createProduit("name", "description", 1, 2, "img", "yoooo")).thenReturn("OK");

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("yoooo");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(ProductRequestException.class, () -> manageProductController.createProduct("{email:\"123\"}"));
        assertThrows(ProductRequestException.class, () -> manageProductController.createProduct("{siret:\"123\"}"));
        assertThrows(ProductRequestException.class, () -> manageProductController.createProduct("{}"));

        assertEquals(ResponseEntity.ok().body("OK"), manageProductController.createProduct("{name:\"name\",description:\"description\",prix:1,stock:2,image:\"img\"}"));

        when(manageProductService.createProduit("name", "description", 1, 2, "img", "yoooo")).thenReturn("error");
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error"), manageProductController.createProduct("{name:\"name\",description:\"description\",prix:1,stock:2,image:\"img\"}"));
    }

    @Test
    void getProductsCommerceTestOk() {
        when(principal.getName()).thenReturn("name");
        when(manageProductService.getProductsCommerce("name")).thenReturn("ok");

        var expected = ResponseEntity.ok("ok");

        assertEquals(expected, manageProductController.getProductsCommerce(principal));
    }

    @Test
    void updateQuantityProduitInternalError() {
        when(principal.getName()).thenReturn("name");
        int idProduit = 1;
        int quantity = 50;
        when(manageProductService.updateQuantityProduit("123",idProduit, quantity)).thenReturn("error");

        var expected = new ResponseEntity<>("error",HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(expected, manageProductController.updateQuantityProduit(idProduit, quantity, principal));
    }

    @Test
    void updateQuantityProduitOk() {
        when(principal.getName()).thenReturn("name");
        int idProduit = 1;
        int quantity = 50;
        when(manageProductService.updateQuantityProduit("123",idProduit, quantity)).thenReturn("OK");

        var expected = ResponseEntity.ok("");

        assertEquals(expected, manageProductController.updateQuantityProduit(idProduit, quantity, principal));
    }

    @Test
    void getProductNotVfp_error() {
        when(principal.getName()).thenReturn("name");
        when(manageProductService.getProductsNotVfp("name")).thenReturn("null");
        var expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstantEnum.ERREUR.toString());
        assertEquals(expected, manageProductController.getProductNotVfp(principal));
    }

    @Test
    void getProductNotVfp_ok() {
        when(principal.getName()).thenReturn("name");
        when(manageProductService.getProductsNotVfp("name")).thenReturn("ok");
        var expected = ResponseEntity.ok("ok");
        assertEquals(expected, manageProductController.getProductNotVfp(principal));
    }
}