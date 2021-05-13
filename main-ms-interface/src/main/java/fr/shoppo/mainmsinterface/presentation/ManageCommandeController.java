package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.Client;
import fr.shoppo.mainmsinterface.domain.bo.client.SoldeInput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commande;
import fr.shoppo.mainmsinterface.domain.bo.commerce.CommandeDTO;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commercant;
import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeByCommerceInput;
import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import fr.shoppo.mainmsinterface.domain.service.PanierService;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageCommandeService;
import fr.shoppo.mainmsinterface.infrastructure.action.ValidationPanierAction;
import fr.shoppo.mainmsinterface.infrastructure.json.JsonManagerComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static fr.shoppo.mainmsinterface.domain.bo.commerce.TypeCommandeEnum.ONPLACE;

@Controller
@RestController
public class ManageCommandeController {

    private static final Logger logger = LoggerFactory.getLogger(ManageCommandeController.class);

    ManageCommandeService manageCommandeService;
    JsonManagerComponent jsonManagerComponent;
    ManageClientService manageClientService;
    ManageCommercantService manageCommercantService;
    PanierService panierService;


    @GetMapping("/commercant-auth/commerce/commande/get-commandes-commerce")
    public ResponseEntity<String> getCommandesCommerce(Principal principal){
        return ResponseEntity.ok(manageCommandeService.getCommandesCommerce(principal.getName()));
    }

    @GetMapping("/client-auth/client/get-commande/{id}")
    public ResponseEntity<String> getCommandeByClient(Principal principal, @PathVariable int id){
        try {
            String client = manageClientService.getClient(principal.getName());
            ObjectMapper objectMapper = new ObjectMapper();
            Client c = objectMapper.readValue(client, Client.class);
            String commande = manageCommandeService.getCommande(id);
            ObjectMapper objectMapperClient = new ObjectMapper();
            CommandeDTO commandeObj = objectMapper.readValue(commande, CommandeDTO.class);
            if(commandeObj.getClientId() == c.getId()) {
                return ResponseEntity.ok(commande);
            } else {
                return new ResponseEntity<>(MessageConstantEnum.WRONG_CLIENT_COMMANDE.toString(), HttpStatus.BAD_REQUEST);
            }
        } catch (JsonProcessingException jsonProcessingException) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/commercant-auth/commerce/commande/get-commande/{id}")
    public ResponseEntity<String> getCommandeByCommerce(Principal principal, @PathVariable int id){
        try {
            String commercant = manageCommercantService.getCommercant(principal.getName());
            ObjectMapper objectMapper = new ObjectMapper();
            Commercant c = objectMapper.readValue(commercant, Commercant.class);
            String commande = manageCommandeService.getCommande(id);
            ObjectMapper objectMapperClient = new ObjectMapper();
            CommandeDTO commandeObj = objectMapper.readValue(commande, CommandeDTO.class);
            if(commandeObj.getCommerce().getSiretCode().equals(c.getCommerce().getSiretCode())) {
                return ResponseEntity.ok(commande);
            } else {
                return new ResponseEntity<>(MessageConstantEnum.WRONG_COMMERCE_COMMANDE.toString(), HttpStatus.BAD_REQUEST);
            }
        } catch (JsonProcessingException jsonProcessingException) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/client-auth/client/get-commandes-client")
    public ResponseEntity<String> getCommandesClient(Principal principal){
        try {
            String result = manageClientService.getClient(principal.getName());
            ObjectMapper objectMapper = new ObjectMapper();
            Client c = objectMapper.readValue(result, Client.class);
            return ResponseEntity.ok(manageCommandeService.getCommandesClient(c.getId()));
        } catch (JsonProcessingException jsonProcessingException) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/commercant-auth/commerce/commande/update-commande-traitee")
    public ResponseEntity<String> updateCommandeeTraitee(Principal principal, @RequestBody Commande com){
        String res = this.manageCommandeService.updateCommande(com.getId());
        if(res.equals("OK")) {
            return ResponseEntity.ok(MessageConstantEnum.COMMANDE_UPDATED.toString());
        }

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @PostMapping( value = {"/commercant-auth/commerce/commande/valider","/commercant-auth/commerce/commande/valider/"})
    public ResponseEntity<String> validerPanier(
            @RequestParam String email
    ) throws JsonProcessingException, JSONException {
        return ValidationPanierAction.paiement(manageCommandeService,
                manageClientService,
                panierService,
                email,
                ONPLACE,
                aFloat -> {
                    var input = SoldeInput.of(email,aFloat,false);

                    var response = MessageConstantEnum
                            .fromMessage(manageClientService.updateSolde(input));
                    return ResponseEntity
                            .status(response.httpStatus())
                            .body(response.toString());
                });
    }

    @PostMapping(value = "/commercant-auth/commandeByCommerce")
    public ResponseEntity<String> commandeByCommerce(Principal principal,@RequestBody CreateCommandeByCommerceInput createCommandeByCommerceInput) throws JsonProcessingException, JSONException {
        var clientString = manageClientService.getClientByQrCode(createCommandeByCommerceInput.getClient());
        ObjectMapper objectMapper = new ObjectMapper();
        var client = objectMapper.readValue(clientString, Client.class);
        return ValidationPanierAction.createCommandeByCommerce(manageCommandeService,
                        panierService,
                        principal.getName(),
                manageClientService,
                createCommandeByCommerceInput,
                        aFloat -> {
                            var input = SoldeInput.of(client.getEmail(), aFloat, createCommandeByCommerceInput.isOnlinePayment());

                            var response = MessageConstantEnum
                                    .fromMessage(manageClientService.updateSolde(input));
                            return ResponseEntity
                                    .status(response.httpStatus())
                                    .body(response.toString());
                        },
                 client);
    }

    @Autowired
    public void setManageCommandeService(ManageCommandeService manageCommandeService) {
        this.manageCommandeService = manageCommandeService;
    }

    @Autowired
    public void setManageClientService(ManageClientService manageClientService) {
        this.manageClientService = manageClientService;
    }

    @Autowired
    public void setManageCommercantService(ManageCommercantService manageCommercantService) {
        this.manageCommercantService = manageCommercantService;
    }

    @Autowired
    public void setJsonManagerComponent(JsonManagerComponent jsonManagerComponent) {
        this.jsonManagerComponent = jsonManagerComponent;
    }

    @Autowired
    public void setPanierService(PanierService panierService) {
        this.panierService = panierService;
    }
}
