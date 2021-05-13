package fr.shoppo.mainmsinterface.presentation;

import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/client")
public class ManageClientController {

    private static final Logger logger = LoggerFactory.getLogger(ManageClientController.class);

    ManageClientService manageClientService;


    @PostMapping("/")
    public ResponseEntity<String> createClient(
            @RequestBody
                    User user
    ){
        return ResponseEntity.ok(manageClientService.createClient(user));
    }


    @Autowired
    public void setManageClientService(ManageClientService manageClientService) {
        this.manageClientService = manageClientService;
    }
}
