package fr.shoppo.mainmsinterface.domain.constant;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.function.Predicate;

public enum MessageConstantEnum {
    OK("OK"),
    ERREUR_TYPE("ERROR",HttpStatus.INTERNAL_SERVER_ERROR),
    ERREUR_INVALID_PANIER("Le panier est invalide"),
    ERREUR("Une erreur est survenue",HttpStatus.INTERNAL_SERVER_ERROR),
    COMMERCANT_NOT_FOUND("Le commerçant n'existe pas",HttpStatus.NOT_FOUND),
    ADMIN_NOT_FOUND("L'admin n'existe pas",HttpStatus.NOT_FOUND),
    CLIENT_NOT_FOUND("Le client n'existe pas",HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("L'utilisateur n'existe pas",HttpStatus.NOT_FOUND),
    ERROR_LOGIN("Email et/ou mot de passe erroné",HttpStatus.BAD_REQUEST),
    CONNEXION_NOT_FOUND("Le token n'existe pas",HttpStatus.NOT_FOUND),
    WRONG_CLIENT_COMMANDE("Cette commande ne vous appartient pas"),
    WRONG_COMMERCE_COMMANDE("Cette commande n'appartient pas à votre commerce"),
    UNKNOWN("Oups", HttpStatus.BAD_REQUEST),
    ERREUR_SOLDE_A_ZERO("Votre solde ne permet pas de réaliser le paiement",HttpStatus.BAD_REQUEST),
    COMMANDE_UPDATED("Commande mise à jour"),
    ERREUR_AVANTAGE("Cet avantage est deja actif OU vous ne possedez pas le status VFP.",HttpStatus.BAD_REQUEST)
    ;

    String message;
    HttpStatus statusToReturn;

    MessageConstantEnum(String message, HttpStatus httpStatus) {
        this.statusToReturn = httpStatus;
        this.message = message;
    }
    MessageConstantEnum(String message) {
        this(message,HttpStatus.OK);
    }

    public HttpStatus httpStatus(){
        return this.statusToReturn;
    }

    public static MessageConstantEnum from(
            Predicate<MessageConstantEnum> filter,
            String key
    ) {
        return Arrays
                .stream(values())
                .filter(filter)
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static MessageConstantEnum fromMessage(
            String message
    ){
        return from(
            messageConstantEnum -> message != null
            && message.equals(messageConstantEnum.message),
            message
        );
    }

    public static MessageConstantEnum fromName(
            String name
    ) {
        return from(
                messageConstantEnum -> name != null
                        && name.equals(messageConstantEnum.name()),
                name
        );
    }

    @Override
    public String toString() {
        return this.message;
    }
}
