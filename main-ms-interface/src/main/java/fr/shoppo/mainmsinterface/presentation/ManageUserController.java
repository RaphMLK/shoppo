package fr.shoppo.mainmsinterface.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.mainmsinterface.domain.bo.ConnectedUser;
import fr.shoppo.mainmsinterface.domain.bo.Email;
import fr.shoppo.mainmsinterface.domain.bo.Token;
import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum;
import fr.shoppo.mainmsinterface.domain.service.ManageAdminService;
import fr.shoppo.mainmsinterface.domain.service.ManageClientService;
import fr.shoppo.mainmsinterface.domain.service.ManageCommercantService;
import fr.shoppo.mainmsinterface.domain.service.ManageConnexionService;
import fr.shoppo.mainmsinterface.infrastructure.config.securite.Role;
import fr.shoppo.mainmsinterface.infrastructure.entity.Connexion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.function.Function;

import static fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum.*;
import static fr.shoppo.mainmsinterface.infrastructure.config.securite.Role.*;
import static java.lang.String.format;
/*(1)Par mesure de securite on n'indique pas quel service s'est lance
 * Ni les services qui n'ont pas abouti
 * */
/*(2)Nous n'avons rien trouve donc on retourne une erreur du client qui parle : NOT FOUND
* https://developer.mozilla.org/fr/docs/Web/HTTP/Status/409
* */
/*(3)Une erreur s'est produite... on tente de la recuperer si pas dans le referentiel un handler d'exception s'en occupe :D*/
/*(4)On peut creer une enumeration dans ce cas ca permet d'avoir de l'uniformite*/
@Controller
@RequestMapping("/manageUser")
public class ManageUserController {
    /*Ici le logger est vraiment propre a la classe c'est inutile de le tester donc on peut le laisser private*/
    private static final Logger logger = LoggerFactory.getLogger(ManageUserController.class);
    /*Si on ne precise pas le scope on est en private-package par defaut*/
    /*Si on veut pouvoir mock facilement sans getter/setter c'est mieux*/
    final ManageClientService manageClientService;

    final ManageCommercantService manageCommercantService;

    final ManageAdminService manageAdminService;

    final ManageConnexionService manageConnexionService;

    public ManageUserController(ManageClientService manageClientService, ManageCommercantService manageCommercantService, ManageAdminService manageAdminService, ManageConnexionService manageConnexionService) {
        this.manageClientService = manageClientService;
        this.manageCommercantService = manageCommercantService;
        this.manageAdminService = manageAdminService;
        this.manageConnexionService = manageConnexionService;
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPasswordUser(@RequestBody Email email) {
        var count_not_found = 0;
        var thereIsError = false;
        var errorContainer = ERREUR;
        var methodsToTry = List.of(
                (Function<String,String>) manageClientService::resetPassword,
                manageCommercantService::resetPassword,
                manageAdminService::resetPassword
        );
        var response = "";

        for (Function<String,String> method : methodsToTry ){
            response = method.apply(email.getValue());
            if(response.equals(OK.toString())){
                logger.info(format("Reset mot de passe de %s => OK",email.getValue())); /*(1)*/
                return new ResponseEntity<>("",OK.httpStatus());
            }else if(List.of(CLIENT_NOT_FOUND.toString(),COMMERCANT_NOT_FOUND.toString(),ADMIN_NOT_FOUND.toString())
                    .stream().anyMatch(response::equals)) count_not_found++;
            else if(response.equals(ERREUR.toString())) thereIsError = true;
            else errorContainer = MessageConstantEnum.fromMessage(response);
        }

        return notFoundOrAnyError(count_not_found,email.getValue(),response,thereIsError, errorContainer);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        var count_not_found = 0;
        var thereIsError = false;
        var errorContainer = ERREUR;
        var methodsToTryLinked = List.of(
                Pair.of((Function<User,String>) manageClientService::login,CLIENT),/*(4)*/
                Pair.of((Function<User,String>) manageCommercantService::login,COMMERCANT),
                Pair.of((Function<User,String>) manageAdminService::login,ADMIN)
        );

        var response = "";
        for (Pair<Function<User,String>, Role> pair : methodsToTryLinked ){
            var method = pair.getFirst();
            var role = pair.getSecond();
            response = method.apply(user);
            try{
                if(response.startsWith("OK#")){
                    var token =  manageConnexionService.generateToken();
                    Connexion connexion = new Connexion(user.getEmail(),token, role.libelle());
                    manageConnexionService.createConnexion(connexion);
                    ObjectMapper objectMapper = new ObjectMapper();
                    ConnectedUser connectedUser = new ConnectedUser();
                    connectedUser.setToken(token);
                    connectedUser.setRole(role.libelle());

                    connectedUser.setConnectedUser(objectMapper.readValue(response.substring(response.indexOf('#')+1), Object.class));
                    return new ResponseEntity<>(connectedUser,OK.httpStatus());
                }
                else if(List.of(CLIENT_NOT_FOUND.toString(),COMMERCANT_NOT_FOUND.toString(),ADMIN_NOT_FOUND.toString())
                        .stream().anyMatch(response::equals)) count_not_found++;
                else if(response.equals(ERREUR.toString())) thereIsError = true;
                else errorContainer = MessageConstantEnum.fromMessage(response);
            } catch (JsonProcessingException e) {
                errorContainer = MessageConstantEnum.fromMessage(ERREUR.toString());
                logger.warn("An error has occurs into login cause: {} - {} ",e.getCause(),e.getMessage());
            }


        }

        return notFoundOrAnyError(count_not_found,user.getEmail(),response, thereIsError, errorContainer);
    }

    ResponseEntity<String> notFoundOrAnyError(int count_not_found, String email,String response, boolean thereIsError, MessageConstantEnum errorContainer){
        if(!errorContainer.equals(ERREUR)) return new ResponseEntity<>(errorContainer.toString(), errorContainer.httpStatus());
        if(count_not_found == 3) { /*(2)*/
            logger.error(format("utilisateur introuvable : %s", email));
            return new ResponseEntity<>(USER_NOT_FOUND.toString(), USER_NOT_FOUND.httpStatus());
        }

        if(thereIsError) return new ResponseEntity<>(ERREUR.toString(),ERREUR.httpStatus());

        var message = MessageConstantEnum.fromMessage(response);/*(3)*/
        return new ResponseEntity<>(message.toString(), message.httpStatus());
    }

    @PostMapping("/disconnect")
    public ResponseEntity<String> disconnect(@RequestBody Token token) {
        String resp = this.manageConnexionService.removeFromToken(token.getValue());

        if(resp.equals(OK.toString())) {
            return new ResponseEntity<>(OK.toString(), OK.httpStatus());
        }

        if(resp.equals(CONNEXION_NOT_FOUND.toString())){
            return new ResponseEntity<>(CONNEXION_NOT_FOUND.toString(), CONNEXION_NOT_FOUND.httpStatus());
        }

        return new ResponseEntity<>(ERREUR.toString(), ERREUR.httpStatus());

    }
}
