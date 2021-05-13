package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Commercant;
import fr.shoppo.mainmsinterface.domain.bo.commerce.ProductWithCommercant;
import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageProductService;
import fr.shoppo.mainmsinterface.infrastructure.json.JsonManagerComponent;
import fr.shoppo.mainmsinterface.infrastructure.service.exception.ProductRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/commercant-auth/commerce/product")
public class ManageProductController {

    private static final Logger logger = LoggerFactory.getLogger(ManageProductController.class);

    ManageProductService manageProductService;
    ManageCommercantService manageCommercantService;
    JsonManagerComponent jsonManagerComponent;

    @PostMapping(value = {"","/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createProduct(
            @RequestBody String jsonRequest
    ) throws Exception{
        var requestInformation = jsonManagerComponent
                .tryToGet((Void)->new JSONObject(jsonRequest),
                        new ProductRequestException("Input was not JSON format"));

        var name = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getString("name"),
                        new ProductRequestException("name not found"));

        var description = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getString("description"),
                        new ProductRequestException("description not found"));

        var prix = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getDouble("prix"),
                        new ProductRequestException("prix not found"));

        var stock = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getInt("stock"),
                        new ProductRequestException("stock not found"));

        var image = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getString("image"),
                        new ProductRequestException("image not found"));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String result = manageProductService.createProduit(name, description, prix.floatValue(), stock, image, user.getUsername());
        if(result.equals("OK")){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping(value = "/edit-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editProduct(
            @RequestBody String jsonRequest
    ) throws Exception{
        var requestInformation = jsonManagerComponent
                .tryToGet((Void)->new JSONObject(jsonRequest),
                        new ProductRequestException("Input was not JSON format"));

        var id = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getInt("id"),
                        new ProductRequestException("id not found"));

        var name = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getString("name"),
                        new ProductRequestException("name not found"));

        var description = "";

        if(requestInformation.getString("description") != null) {
            description = requestInformation.getString("description");
        }

        var prix = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getDouble("prix"),
                        new ProductRequestException("prix not found"));

        var stock = 0;

        if(requestInformation.getString("stock") != null) {
            stock = requestInformation.getInt("stock");
        }

        var image = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getString("image"),
                        new ProductRequestException("image not found"));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String result =  manageProductService.editProduct(id, name, description, prix.floatValue(), stock, image, user.getUsername());

        if(result.equals("OK")){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping(value = "/delete-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProduct(
            @RequestBody String jsonRequest
    ) throws Exception{
        var requestInformation = jsonManagerComponent
                .tryToGet((Void)->new JSONObject(jsonRequest),
                        new ProductRequestException("Input was not JSON format"));

        var id = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getInt("id"),
                        new ProductRequestException("id not found"));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String result = manageProductService.deleteProduct(id, user.getUsername());

        System.out.print("result : " + result);

        if(result.equals("OK")){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping(value = "/get-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProduct(
            @RequestBody String jsonRequest
    ) throws Exception {
        var requestInformation = jsonManagerComponent
                .tryToGet((Void) -> new JSONObject(jsonRequest),
                        new ProductRequestException("Input was not JSON format"));

        var id = jsonManagerComponent
                .tryToGet((Void) -> requestInformation.getInt("id"),
                        new ProductRequestException("id not found"));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            String result = manageProductService.getProduct(id, user.getUsername());
            ObjectMapper objectMapper = new ObjectMapper();
            ProductWithCommercant p = objectMapper.readValue(result, ProductWithCommercant.class);

            if(p != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
        } catch (JsonProcessingException jsonProcessingException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produit non récupéré");
        }


    }

    @PostMapping("/update-quantity/{idProduit}")
    public ResponseEntity<String> updateQuantityProduit(@PathVariable int idProduit, @RequestParam int quantity, Principal principal){
        try {
            var mapper = new ObjectMapper();
            var commercant = mapper.readValue(manageCommercantService.getCommercant(principal.getName()), Commercant.class);

            var commerce = commercant.getCommerce();
            var response = manageProductService.updateQuantityProduit(commerce.getSiretCode(), idProduit,quantity);
            if(MessageConstantEnum.OK.toString().equals(response))
                return ResponseEntity.ok("");
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @GetMapping("/get-products-not-vfp")
    public ResponseEntity<String> getProductNotVfp(Principal principal){
        var response = manageProductService.getProductsNotVfp(principal.getName());
        if("null".equals(response))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstantEnum.ERREUR.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-products-commerce")
    public ResponseEntity<String> getProductsCommerce(Principal principal){
        return ResponseEntity.ok(manageProductService.getProductsCommerce(principal.getName()));
    }

    @Autowired
    public void setManageProductService(ManageProductService manageProductService) {
        this.manageProductService = manageProductService;
    }

    @Autowired
    public void setJsonManagerComponent(JsonManagerComponent jsonManagerComponent) {
        this.jsonManagerComponent = jsonManagerComponent;
    }

    @Autowired
    public void setManageCommercantService(ManageCommercantService manageCommercantService) {
        this.manageCommercantService = manageCommercantService;
    }
}
