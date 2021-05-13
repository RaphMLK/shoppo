package fr.shoppo.mainmsinterface.presentation;

import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verif-vf-globaux")
public class VerifVfpGlobaux {

    private ManageClientService manageClientService;

    @GetMapping("/is-park/{plaque}")
    public ResponseEntity<Boolean> isPark(@PathVariable String plaque){
        return ResponseEntity.ok(manageClientService.isPark(plaque));
    }

    @GetMapping("/is-transport/{qrCode}")
    public ResponseEntity<Boolean> isTransport(@PathVariable String qrCode){
        return ResponseEntity.ok(manageClientService.isTransport(qrCode));
    }

    @Autowired
    public void setManageClientService(ManageClientService manageClientService) {
        this.manageClientService = manageClientService;
    }
}
