package fr.shoppo.mainmsinterface.presentation;

import fr.shoppo.mainmsinterface.domain.service.commerce.ManageCommerceService;
import fr.shoppo.mainmsinterface.infrastructure.json.JsonManagerComponent;
import fr.shoppo.mainmsinterface.infrastructure.service.exception.CommerceRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManageCommerceControllerTest {

    ManageCommerceController manageCommerceController;

    ManageCommerceService manageCommerceService;

    JsonManagerComponent jsonManagerComponent;

    @BeforeEach
    public void setup(){
        manageCommerceService = mock(ManageCommerceService.class);
        jsonManagerComponent = mock(JsonManagerComponent.class);

        manageCommerceController = new ManageCommerceController();
        manageCommerceController.setJsonManagerComponent(jsonManagerComponent);
        manageCommerceController.setManageCommerceService(manageCommerceService);

    }

    @Test
    public void should_receive_valid_json() throws Exception {
        when(jsonManagerComponent.tryToGet(any(),any())).thenCallRealMethod();
        assertThrows(CommerceRequestException.class,() -> manageCommerceController.createCommerce("not a json"));
    }

    @Test
    public void should_receive_valid_data() throws Exception {
        when(jsonManagerComponent.tryToGet(any(),any())).thenCallRealMethod();
        when(manageCommerceService.createCommerce("123","123")).thenReturn("OK");

        assertThrows(CommerceRequestException.class,() -> manageCommerceController.createCommerce("{email:\"123\"}"));
        assertThrows(CommerceRequestException.class,() -> manageCommerceController.createCommerce("{siret:\"123\"}"));
        assertThrows(CommerceRequestException.class,() -> manageCommerceController.createCommerce("{}"));

        assertEquals(ResponseEntity.ok().body("OK"),manageCommerceController.createCommerce("{siret:\"123\",email:\"123\"}"));
    }

    @Test
    public void should_receive_valid_data_addCommercant() throws Exception {
        when(jsonManagerComponent.tryToGet(any(),any())).thenCallRealMethod();
        when(manageCommerceService.addCommercant("123","123")).thenReturn("OK");

        assertThrows(CommerceRequestException.class,() -> manageCommerceController.addCommercant("{email:\"123\"}"));
        assertThrows(CommerceRequestException.class,() -> manageCommerceController.addCommercant("{siret:\"123\"}"));
        assertThrows(CommerceRequestException.class,() -> manageCommerceController.addCommercant("{}"));

        assertEquals(ResponseEntity.ok().body("OK"),manageCommerceController.addCommercant("{siret:\"123\",email:\"123\"}"));
    }

    @Test
    public void should_receive_valid_data_rmCommercant() throws Exception {
        when(jsonManagerComponent.tryToGet(any(),any())).thenCallRealMethod();
        when(manageCommerceService.rmCommercant("123","123")).thenReturn("OK");

        assertThrows(CommerceRequestException.class,() -> manageCommerceController.rmCommercant("{email:\"123\"}"));
        assertThrows(CommerceRequestException.class,() -> manageCommerceController.rmCommercant("{siret:\"123\"}"));
        assertThrows(CommerceRequestException.class,() -> manageCommerceController.rmCommercant("{}"));

        assertEquals(ResponseEntity.ok().body("OK"),manageCommerceController.rmCommercant("{siret:\"123\",email:\"123\"}"));
    }
}