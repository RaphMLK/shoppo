package fr.shoppo.mainmsinterface.presentation.exception;

import fr.shoppo.mainmsinterface.infrastructure.service.exception.CommerceRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ManageEachExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ CommerceRequestException.class })
    public ResponseEntity<String> handleAccessDeniedException(
            Exception ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.toString());
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<String> handleIllegalArgumentException(
            Exception ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
