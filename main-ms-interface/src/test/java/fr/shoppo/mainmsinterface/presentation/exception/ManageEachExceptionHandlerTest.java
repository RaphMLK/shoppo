package fr.shoppo.mainmsinterface.presentation.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManageEachExceptionHandlerTest {

    private ManageEachExceptionHandler manageEachExceptionHandler;


    @BeforeEach
    public void setup() {
        manageEachExceptionHandler = new ManageEachExceptionHandler();
    }

    @Test
    void handleAccessDeniedException(){
        var exception = new Exception("test");
        var response = ResponseEntity
                .badRequest()
                .body(exception.toString());
        assertEquals(response,manageEachExceptionHandler.handleAccessDeniedException(exception));
    }

    @Test
    void handleIllegalArgumentException(){
        var exception = new Exception("test");
        var response = ResponseEntity
                .badRequest()
                .body(exception.getMessage());
        assertEquals(response,manageEachExceptionHandler.handleIllegalArgumentException(exception));
    }
}
