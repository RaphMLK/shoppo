package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.Client;
import fr.shoppo.mainmsinterface.domain.bo.ReturnBasicExchange;
import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import fr.shoppo.mainmsinterface.domain.service.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/client-auth/panier")
public class PanierController {

    private ManageClientService manageClientService;
    private PanierService panierService;
    private final ObjectMapper objectMapper;

    public PanierController() {
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("/getPanier")
    public ResponseEntity<String> getPanier(Principal principal) {
        var clientString = manageClientService.getClient(principal.getName());
        try {
            Client client = objectMapper.readValue(clientString, Client.class);
            var panier = panierService.getPanier(client.getId());
            if ("null".equals(panier) || panier.equals(MessageConstantEnum.ERREUR_TYPE.toString()))
                return ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("");
            return ResponseEntity.ok(panier);
        } catch (JsonProcessingException jsonProcessingException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client non trouvé");
        }
    }

    @PostMapping("/updatePanier")
    public ResponseEntity<String> updatePanierProduct(Principal principal, @RequestParam int idProduit, @RequestParam int quantite) {
        var clientString = manageClientService.getClient(principal.getName());
        try {
            Client client = objectMapper.readValue(clientString, Client.class);
            var updateString = panierService.updatePanierProduct(client.getId(), idProduit, quantite);
            if ("null".equals(updateString))
                return ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("");
            var update = objectMapper.readValue(updateString, ReturnBasicExchange.class);
            if (MessageConstantEnum.ERREUR_TYPE.toString().equals(update.getStatus()))
                return ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body(update.getContent());
            return ResponseEntity.ok("");
        } catch (JsonProcessingException jsonProcessingException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client non trouvé");
        }
    }

    @PostMapping("/add-vfp")
    public ResponseEntity<String> addVfpPanier(Principal principal, @RequestParam int idVfp) {
        var clientString = manageClientService.getClient(principal.getName());
        try {
            Client client = objectMapper.readValue(clientString, Client.class);
            if (!client.isStatusVFP() || client.getAvantage().equals("USED"))
                return ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("Vous ne pouvez pas ajouter de VFP au panier");
            return addOrDeleteVfpPanier(client.getId(), idVfp, true);
        } catch (JsonProcessingException jsonProcessingException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client non trouvé");
        }
    }

    @PostMapping("/delete-vfp")
    public ResponseEntity<String> deleteVfpPanier(Principal principal, @RequestParam int idVfp) {
        var clientString = manageClientService.getClient(principal.getName());
        try {
            Client client = objectMapper.readValue(clientString, Client.class);
            return addOrDeleteVfpPanier(client.getId(), idVfp, false);
        } catch (JsonProcessingException jsonProcessingException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client non trouvé");
        }
    }

    private ResponseEntity<String> addOrDeleteVfpPanier(int idClient, int idVfp, boolean addOrDelete) throws JsonProcessingException {
        var updateString = panierService.addOrDeleteVfpPanier(idClient, idVfp, addOrDelete);
        if ("null".equals(updateString))
            return ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body("");
        var add = objectMapper.readValue(updateString, ReturnBasicExchange.class);
        if (MessageConstantEnum.ERREUR_TYPE.toString().equals(add.getStatus()))
            return ResponseEntity.status(MessageConstantEnum.ERREUR_TYPE.httpStatus()).body(add.getContent());
        return ResponseEntity.ok("");
    }

    @Autowired
    public void setManageClientService(ManageClientService manageClientService) {
        this.manageClientService = manageClientService;
    }

    @Autowired
    public void setPanierService(PanierService panierService) {
        this.panierService = panierService;
    }
}
