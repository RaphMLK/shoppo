package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.Client;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commercant-auth")
public class CommerceToClientController {

    private ManageClientService manageClientService;

    @GetMapping("/get-client-by-qrcode")
    public ResponseEntity<String> getClientByQrCode (@RequestParam String qrCode) throws JsonProcessingException {
        var clientString = manageClientService.getClientByQrCode(qrCode);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readValue(clientString, Client.class);
        return ResponseEntity.ok(clientString);
    }

    @Autowired
    public void setManageClientService(ManageClientService manageClientService) {
        this.manageClientService = manageClientService;
    }
}
