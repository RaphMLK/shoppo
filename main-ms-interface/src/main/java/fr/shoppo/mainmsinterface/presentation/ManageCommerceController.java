package fr.shoppo.mainmsinterface.presentation;


import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageCommerceService;
import fr.shoppo.mainmsinterface.infrastructure.json.JsonManagerComponent;
import fr.shoppo.mainmsinterface.infrastructure.service.exception.CommerceRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.BiFunction;

import static org.springframework.util.StringUtils.trimAllWhitespace;

@Controller
@RestController
@RequestMapping("/admin/commerce")
public class ManageCommerceController {
    private static final Logger logger = LoggerFactory.getLogger(ManageCommerceController.class);

    ManageCommerceService manageCommerceService;
    JsonManagerComponent jsonManagerComponent;

    @PostMapping(value = {"","/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCommerce(
            @RequestBody String jsonRequest
    ) throws Exception {

        return commerceInteraction(jsonRequest, "/admin/commerce",(s, m) ->manageCommerceService.createCommerce(s,m) );
    }

    @PostMapping(value = {"/addCommercant","/addCommercant/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCommercant(
            @RequestBody String jsonRequest
    ) throws Exception {
        return commerceInteraction(jsonRequest, "/admin/commerce/addCommercant", (s, m) ->manageCommerceService.addCommercant(s,m));
    }

    @PostMapping(value = {"/rmCommercant","/rmCommercant/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> rmCommercant(
            @RequestBody String jsonRequest
    ) throws Exception {
        return commerceInteraction(jsonRequest, "/admin/commerce/rmCommercant", (s, m) ->manageCommerceService.rmCommercant(s,m));
    }

    ResponseEntity<String> commerceInteraction(String jsonRequest, String url, BiFunction<String, String, String> fn) throws Exception {
        var requestInformation = jsonManagerComponent
                .tryToGet((Void)->new JSONObject(jsonRequest),
                        new CommerceRequestException("Input was not JSON format", "POST",url,null));

        var siret = jsonManagerComponent
                .tryToGet((Void) -> trimAllWhitespace(requestInformation.getString("siret")) ,
                        new CommerceRequestException("SIRET not found", "POST",url,requestInformation));

        var email = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getString("email"),
                        new CommerceRequestException("EMAIL not found", "POST",url,requestInformation));

        var response = MessageConstantEnum.fromMessage(fn.apply(siret,email));
        return ResponseEntity
                .status(response.httpStatus())
                .body(response.toString());
    }



    @Autowired
    public void setManageCommerceService(ManageCommerceService manageCommerceService) {
        this.manageCommerceService = manageCommerceService;
    }

    @Autowired
    public void setJsonManagerComponent(JsonManagerComponent jsonManagerComponent) {
        this.jsonManagerComponent = jsonManagerComponent;
    }
}
