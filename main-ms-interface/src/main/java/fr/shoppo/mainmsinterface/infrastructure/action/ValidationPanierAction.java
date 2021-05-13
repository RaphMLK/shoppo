package fr.shoppo.mainmsinterface.infrastructure.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.Client;
import fr.shoppo.mainmsinterface.domain.bo.client.AvantageInput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeByCommerceInput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.CreateCommandeByCommerceOutput;
import fr.shoppo.mainmsinterface.domain.bo.commerce.TypeCommandeEnum;
import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import fr.shoppo.mainmsinterface.domain.service.PanierService;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageCommandeService;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;
import java.util.stream.IntStream;

import static fr.shoppo.mainmsinterface.domain.bo.commerce.TypeCommandeEnum.ONLINE;
import static fr.shoppo.mainmsinterface.domain.bo.commerce.TypeCommandeEnum.ONPLACE;
import static fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum.OK;

public class ValidationPanierAction {

    public static ResponseEntity<String> paiement(
            ManageCommandeService manageCommandeService,
            ManageClientService manageClientService,
            PanierService panierService,
            String email,
            TypeCommandeEnum typeCommandeEnum,
            Function<Float,ResponseEntity<String>> updateSolde
    ) throws JsonProcessingException, JSONException {
        var result = manageClientService.getClient(email);
        var objectMapper = new ObjectMapper();
        var client = objectMapper.readValue(result, Client.class);

        var idClient = client.getId();

        var getPanier = panierService.getPanier(idClient);
        var panier = new JSONArray(new JSONObject(getPanier).getString("panier"));

        float amountToPay = pricePanier(panier);

        var entity = updateSolde.apply(-amountToPay);
        if(!HttpStatus.OK.equals(entity.getStatusCode()))
            return entity;

        entity = useAdvantage(new JSONObject(getPanier).getString("advantage"), client.getEmail(), manageClientService);
        if(!HttpStatus.OK.equals(entity.getStatusCode())) {
            updateSolde.apply(amountToPay);
            return entity;
        }

        var response = MessageConstantEnum.fromMessage(manageCommandeService.createCommande(idClient,typeCommandeEnum));
        if(!OK.equals(response) && !ONPLACE.equals(typeCommandeEnum))
            updateSolde.apply(amountToPay);

        return ResponseEntity.status(response.httpStatus()).body(response.toString());
    }

    public static ResponseEntity<String> createCommandeByCommerce(ManageCommandeService manageCommandeService,
                                                           PanierService panierService,
                                                           String emailCommerce,
                                                           ManageClientService manageClientService,
                                                           CreateCommandeByCommerceInput createCommandeByCommerceInput,
                                                                  Function<Float,ResponseEntity<String>> updateSolde,
                                                                  Client client) throws JSONException {

        var typeCommandeEnum = createCommandeByCommerceInput.isOnlinePayment() ? ONLINE : ONPLACE;

        var createCommandeByCommerceOutput = new CreateCommandeByCommerceOutput(emailCommerce, client.getId(), createCommandeByCommerceInput.getCommand(), typeCommandeEnum, createCommandeByCommerceInput.getAdvantage());

        var getPanier = panierService.getPanierByCommerce(createCommandeByCommerceOutput);
        var panier = new JSONArray(new JSONObject(getPanier).getString("panier"));

        float amountToPay = pricePanier(panier);

        var entity = updateSolde.apply(-amountToPay);
        if(!HttpStatus.OK.equals(entity.getStatusCode()))
            return entity;

        entity = useAdvantage(new JSONObject(getPanier).getString("advantage"), client.getEmail(), manageClientService);
        if(!HttpStatus.OK.equals(entity.getStatusCode())) {
            updateSolde.apply(amountToPay);
            return entity;
        }

        var response = MessageConstantEnum.fromMessage(manageCommandeService.createCommandeByCommerce(createCommandeByCommerceOutput));
        if(!OK.equals(response) && !ONPLACE.equals(typeCommandeEnum))
            updateSolde.apply(amountToPay);

        return ResponseEntity.status(response.httpStatus()).body(response.toString());
    }

    private static float pricePanier(JSONArray panier) {
        return IntStream.range(0,panier.length())
                .mapToObj( idx ->{
                    try {
                        var p = panier.getJSONObject(idx);
                        return Float.valueOf( p.get("prixTotal").toString());
                    } catch (JSONException e) {
                        return 0f;
                    }
                }).reduce(Float::sum).orElse(0f);
    }

    private static ResponseEntity<String> useAdvantage(String advantage, String email, ManageClientService manageClientService){
        if(!"null".equals(advantage)) {
            var input = new AvantageInput(email,"USED");
            var response = MessageConstantEnum
                    .fromMessage(manageClientService.updateAvantage(input));
            return ResponseEntity
                    .status(response.httpStatus())
                    .body(response.toString());
        }

        return ResponseEntity.ok("");
    }

}
