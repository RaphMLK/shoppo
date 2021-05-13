package fr.shoppo.mainmsinterface.presentation;

import fr.shoppo.mainmsinterface.domain.bo.stat.ReadingInputController;
import fr.shoppo.mainmsinterface.domain.bo.stat.UserType;
import fr.shoppo.mainmsinterface.domain.service.StatService;
import fr.shoppo.mainmsinterface.infrastructure.action.ReadStatAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-auth/admin")
public class ManageAdminController {

    StatService statService;

    @PostMapping(value = {"/stat/read","/stat/read/"})
    public ResponseEntity<String> readStat(
            @RequestBody ReadingInputController input
    ){
        return ReadStatAction.read(input, UserType.ADMINISTRATEUR,statService);
    }

    @Autowired
    public void setStatService(StatService statService) {
        this.statService = statService;
    }

}
