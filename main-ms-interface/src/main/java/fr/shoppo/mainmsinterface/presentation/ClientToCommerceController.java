package fr.shoppo.mainmsinterface.presentation;

import fr.shoppo.mainmsinterface.domain.service.ClientToCommerceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-auth/client/clientToCommerce")
public class ClientToCommerceController {

    private ClientToCommerceService clientToCommerceService;

    private static final Logger logger = LoggerFactory.getLogger(ClientToCommerceController.class);

    @GetMapping("/getAllCommerces")
    public ResponseEntity<String> getAllCommerces() {
        var getAllCommerce = clientToCommerceService.getAllCommerces();
        if(!getAllCommerce.equals("null"))
            return ResponseEntity.ok(getAllCommerce);
        logger.error("Erreur getAllCommerces, Service KO, methode getAllCommerces");
        return new ResponseEntity<>("",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Autowired
    public void setClientToCommerceService(ClientToCommerceService clientToCommerceService) {
        this.clientToCommerceService = clientToCommerceService;
    }
}
