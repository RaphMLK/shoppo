package fr.shoppo.mainmsinterface.presentation;

import fr.shoppo.mainmsinterface.domain.bo.commerce.AddVfpByProduct;
import fr.shoppo.mainmsinterface.domain.bo.commerce.AddVfpByProductGroup;
import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageVfpCommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/commercant-auth/manage-vfp")
public class ManageVfpCommerce {

    private ManageVfpCommerceService manageVfpCommerceService;

    @PostMapping("/add-by-product")
    public ResponseEntity<String> addVfpByProduct(Principal principal, @RequestBody Set<AddVfpByProduct> addVfpByProduct){
        var addVfpByProductGroup = new AddVfpByProductGroup();
        addVfpByProductGroup.setAddVfpByProducts(addVfpByProduct);
        addVfpByProductGroup.setEmailCommerce(principal.getName());
        var response = manageVfpCommerceService.addVfpByProduct(addVfpByProductGroup);
        if(MessageConstantEnum.OK.toString().equals(response))
            return ResponseEntity.ok(response);
        else if(!"null".equals(response))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstantEnum.ERREUR.toString());

    }

    @PostMapping("/delete-vfp")
    public ResponseEntity<String> deleteVfp(@RequestParam int idVfp){
        var response = manageVfpCommerceService.deleteVfp(idVfp);
        if(MessageConstantEnum.OK.toString().equals(response))
            return ResponseEntity.ok(response);
        else if(!"null".equals(response))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstantEnum.ERREUR.toString());
    }

    @GetMapping("/get-vfp")
    public ResponseEntity<String> getVfp(Principal principal){
        var response = manageVfpCommerceService.getVfp(principal.getName());
        if(!"null".equals(response))
            return ResponseEntity.ok(response);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstantEnum.ERREUR.toString());
    }

    @Autowired
    public void setManageVfpCommerceService(ManageVfpCommerceService manageVfpCommerceService) {
        this.manageVfpCommerceService = manageVfpCommerceService;
    }
}
