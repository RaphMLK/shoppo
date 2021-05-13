package fr.shoppo.mainmsinterface.presentation;

import fr.shoppo.mainmsinterface.infrastructure.service.routing.ClientToCommerceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientToCommerceControllerTest {

    private ClientToCommerceController clientToCommerceController;
    private ClientToCommerceServiceImpl clientToCommerceService;

    @BeforeEach
    void setUp(){
        clientToCommerceController = new ClientToCommerceController();
        clientToCommerceService = mock(ClientToCommerceServiceImpl.class);
        clientToCommerceController.setClientToCommerceService(clientToCommerceService);
    }

    @Test
    void getAllCommerces_Error_queue(){
        when(clientToCommerceService.getAllCommerces()).thenReturn("null");
        var expected = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(expected, clientToCommerceController.getAllCommerces());
    }

    @Test
    void getAllCommerces_OK(){
        when(clientToCommerceService.getAllCommerces()).thenReturn("toto");
        var expected = ResponseEntity.ok("toto");
        assertEquals(expected, clientToCommerceController.getAllCommerces());
    }
}
