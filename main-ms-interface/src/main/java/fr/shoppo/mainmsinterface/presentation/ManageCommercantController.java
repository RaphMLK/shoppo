package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commerce;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Information;
import fr.shoppo.mainmsinterface.domain.bo.stat.ReadingInputController;
import fr.shoppo.mainmsinterface.domain.bo.stat.UserType;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import fr.shoppo.mainmsinterface.domain.service.StatService;
import fr.shoppo.mainmsinterface.infrastructure.action.ReadStatAction;
import fr.shoppo.mainmsinterface.infrastructure.json.JsonManagerComponent;
import fr.shoppo.mainmsinterface.infrastructure.mapper.UserDetailsMapper;
import fr.shoppo.mainmsinterface.infrastructure.service.exception.CommercantRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum.ERREUR;

@RestController
@RequestMapping("/commercant-auth/commerce/commercant")
public class ManageCommercantController {

    private static final Logger logger = LoggerFactory.getLogger(ManageCommercantController.class);

    ManageCommercantService manageCommercantService;
    JsonManagerComponent jsonManagerComponent;
    StatService statService;

    @PostMapping(value = {"/stat/read","/stat/read/"})
    public ResponseEntity<String> readStat(
            @RequestBody ReadingInputController input
    ){
        return ReadStatAction.read(input,UserType.COMMERCANT,statService);
    }

    @PostMapping(value = {"/password/change","/password/change/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePasswordNeed(@RequestBody String jsonRequest) throws Exception {
        var requestInformation = jsonManagerComponent
                .tryToGet((Void)->new JSONObject(jsonRequest),
                        new CommercantRequestException("Input was not JSON format"));

        var password = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getString("password"),
                        new CommercantRequestException("password not found"));
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = UserDetailsMapper.toUserFromPrincipal(principal);
        user.setPassword(password);
        return ResponseEntity.ok(manageCommercantService.changePassword(user));
    }

    @GetMapping(
            value = {"/",""}
    )
    public ResponseEntity<String> informationAndCommerce(
            @RequestParam(required = false) String withProduct
    ) throws CommercantRequestException, JsonProcessingException {
        var context = SecurityContextHolder.getContext();

        if(context == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        var auth = context.getAuthentication();
        if(auth == null || !auth.isAuthenticated())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        var principal = auth.getPrincipal();
        if(principal == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        var user = UserDetailsMapper.toUserFromPrincipal(principal);
        var response = manageCommercantService.findCommercant(user.getEmail(),Boolean.parseBoolean(withProduct));

        ObjectMapper mapper = new ObjectMapper();
        if(response.trim().length() <= 0)
            throw new CommercantRequestException(ERREUR.toString());

        var info = mapper.readValue(response,Information.class);

        if(info == null || info.getCommercant() == null)
            throw new CommercantRequestException(info != null ? info.getMessage() : ERREUR.toString());

        var commerce = info.getCommerce();
        if(commerce!=null)
            commerce.setName(commerce.getEnseigne());

        return ResponseEntity.ok(mapper.writeValueAsString(info));
    }

    @PostMapping(value= {"/",""})
    public ResponseEntity<String> updateCommerce(
        @RequestBody Commerce commerce
    ){
        System.out.println(commerce);
        var response = manageCommercantService.update(commerce);
        return ResponseEntity.ok(response);
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
    public void setStatService(StatService statService) {
        this.statService = statService;
    }
}
