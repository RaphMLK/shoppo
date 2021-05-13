package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.Client;
import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.bo.client.AvantageInput;
import fr.shoppo.mainmsinterface.domain.bo.client.AvantageInputPost;
import fr.shoppo.mainmsinterface.domain.bo.client.SoldeInput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Information;
import fr.shoppo.mainmsinterface.domain.bo.commerce.TypeCommandeEnum;
import fr.shoppo.mainmsinterface.domain.bo.stat.ReadingInputController;
import fr.shoppo.mainmsinterface.domain.bo.stat.UserType;
import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import fr.shoppo.mainmsinterface.domain.service.PanierService;
import fr.shoppo.mainmsinterface.domain.service.StatService;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageCommandeService;
import fr.shoppo.mainmsinterface.infrastructure.action.ReadStatAction;
import fr.shoppo.mainmsinterface.infrastructure.action.ValidationPanierAction;
import fr.shoppo.mainmsinterface.infrastructure.json.JsonManagerComponent;
import fr.shoppo.mainmsinterface.infrastructure.service.exception.ClientRequestException;
import fr.shoppo.mainmsinterface.infrastructure.service.exception.CommercantRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum.ERREUR;

@RestController
@RequestMapping("/client-auth/client")
public class ManageConnectedClientController {

    private static final Logger logger = LoggerFactory.getLogger(ManageConnectedClientController.class);

    ManageClientService manageClientService;
    ManageCommercantService manageCommercantService;
    JsonManagerComponent jsonManagerComponent;
    ManageCommandeService manageCommandeService;
    PanierService panierService;
    StatService statService;

    @PostMapping(value = {"/stat/read","/stat/read/"})
    public ResponseEntity<String> readStat(
            @RequestBody ReadingInputController input
    ){
        return ReadStatAction.read(input,UserType.CLIENT,statService);
    }

    @PostMapping(value = {"/password/change","/password/change/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePasswordNeed(@RequestBody String jsonRequest) throws Exception {
        var requestInformation = jsonManagerComponent
                .tryToGet((Void)->new JSONObject(jsonRequest),
                        new ClientRequestException("Input was not JSON format"));

        var password = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getString("password"),
                        new ClientRequestException("password not found"));
        org.springframework.security.core.userdetails.User connected = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        user.setEmail(connected.getUsername());
        user.setPassword(password);
        return ResponseEntity.ok(manageClientService.changePassword(user));
    }

    @GetMapping(
            value = {"/commerce/{siret}","/commerce/{siret}/"}
    )
    public ResponseEntity<String> informationAndCommerce(
            @PathVariable("siret") String siret
    ) throws CommercantRequestException, JsonProcessingException {
        if(siret == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        var response = manageCommercantService.findCommercant(siret,true);
        ObjectMapper mapper = new ObjectMapper();
        if(response.trim().length() <= 0)
            throw new CommercantRequestException(ERREUR.toString());
        var info = mapper.readValue(response, Information.class);

        if(info == null || info.getCommercant() == null)
            throw new CommercantRequestException(info != null ? info.getMessage() : ERREUR.toString());

        return ResponseEntity.ok(mapper.writeValueAsString(info));
    }

    @PostMapping(
            value = {"/panier/valider","/panier/valider/"}
    )
    public ResponseEntity<String> validerPanier(
            @RequestParam String type
            ) throws JsonProcessingException, JSONException {
        var principal = (org.springframework.security.core.userdetails.User)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ValidationPanierAction.paiement(manageCommandeService,
                manageClientService,
                panierService,
                principal.getUsername(),
                TypeCommandeEnum.valueOf(type), this::updateSolde);
    }

    @PostMapping(
            value = {"/solde/update","/solde/update/"}
    )
    public ResponseEntity<String> updateSolde(
            @RequestParam float amount
    ){
        var principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var input = SoldeInput.of(principal.getUsername(),amount);

        var response = MessageConstantEnum
                .fromMessage(manageClientService.updateSolde(input));
        return ResponseEntity
                .status(response.httpStatus())
                .body(response.toString());
    }

    @GetMapping(
            value = {"/solde","/solde/"}
    )
    public ResponseEntity<Float> getSolde() throws JsonProcessingException {
        var principal = (org.springframework.security.core.userdetails.User)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var result = manageClientService.getClient(principal.getUsername());
        var objectMapper = new ObjectMapper();
        var client = objectMapper.readValue(result, Client.class);
        return ResponseEntity.ok(client.getSolde());
    }

    @GetMapping("/get-qrcode")
    public ResponseEntity<String> getQrCode(Principal principal) throws JsonProcessingException {
        var clientString = manageClientService.getClient(principal.getName());
        var objectMapper = new ObjectMapper();
        var client = objectMapper.readValue(clientString, Client.class);
        return ResponseEntity.ok(client.getQrCode().toString());
    }

    @GetMapping("/avantage")
    public ResponseEntity<String> listAvantage() {
        return ResponseEntity.ok(manageClientService.listAvantage());
    }

    @PostMapping("/avantage")
    public ResponseEntity<String> useAvantage(
            @RequestBody AvantageInputPost post
    )
    {
        var input = new AvantageInput();
        input.setAvantage(post.getAvantage());

        var principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        input.setEmail(principal.getUsername());

        var response = MessageConstantEnum
                .fromMessage(manageClientService.updateAvantage(input));
        return ResponseEntity
                .status(response.httpStatus())
                .body(response.toString());
    }

    @GetMapping("/infos")
    public ResponseEntity<Client> getInformationsClient() throws JsonProcessingException {
        var principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var clientString = manageClientService.getClient(principal.getUsername());
        var objectMapper = new ObjectMapper();
        var client = objectMapper.readValue(clientString, Client.class);

        client.setPassword(null);
        client.setEmail(null);
        client.setChangePassword(null);
        client.setId(null);
        return ResponseEntity.ok(client);
    }

    @ExceptionHandler({JsonProcessingException.class})
    public ResponseEntity<String> caughtJSONProcessingException(
            Exception ex
    ){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
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
    public void setManageCommandeService(ManageCommandeService manageCommandeService) {
        this.manageCommandeService = manageCommandeService;
    }

    @Autowired
    public void setPanierService(PanierService panierService) {
        this.panierService = panierService;
    }

    @Autowired
    public void setStatService(StatService statService) {
        this.statService = statService;
    }
}
