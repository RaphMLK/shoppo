package fr.shoppo.ms_commerce.domain.constant;

import java.util.Arrays;
import java.util.function.Predicate;

import static java.lang.String.format;

public enum MessageConstantEnum {
    OK("OK"),
    ERREUR_TYPE("ERROR"),
    ERREUR("Une erreur est survenue"),
    ERREUR_INVALID_INPUT("Une erreur est survenue parametres invalident"),
    ERREUR_COMMERCE_NOT_CREATED("Le commerce n'a pas ete cree"),
    ERREUR_COMMERCE_NOT_UPDATED("Le commerce n'a pas ete mis a jour"),
    ERREUR_COMMERCANT_NOT_ADDED ("Le commerçant n'a pas ete ajoute"),
    ERREUR_COMMERCE_NOT_FOUND("Le commerce est introuvable."),
    ERREUR_COMMERCANT_OWNER_NOT_DELETED("Le commercant est le proprietaire et n'a pas ete supprime."),
    COMMERCANT_NOT_FOUND ("Le commerçant n'existe pas"),
    COMMERCANT_ERROR_LOGIN("Email et/ou mot de passe erroné"),
    ERREUR_PRODUCT_NOT_CREATED("Le produit n'a pas pu être créé"),
    ERREUR_PRODUCT_INFO_MISSING("Une information concernant le produit est manquante"),
    ERREUR_EDIT_PRODUCT("Le produit n'a pas été modifié"),
    ERREUR_DELETE_PRODUCT("Le produit n'a pas été supprimé"),
    ERREUR_PRODUCT_NOT_FOUND("Le produit n'a pas été trouvé"),
    ERREUR_WRONG_COMMERCANT("Le produit n'appartient pas au commerce du commercant"),
    ERREUR_PRODUCT_NOT_UPDATE("Le produit n'a pas été mis à jour"),
    ERREUR_INVALID_PANIER("Le panier est invalide"),
    ERREUR_ADD_PANIER_QUANTITE_NULL("Ajout impossible la quantité est 0"),
    ERREUR_ADD_PANIER_STOCK_TROP_PETIT("Ajout impossible stock insufissant"),
    COMMANDE_NOT_FOUND("Commande non trouvée")
    ;

    String message;

    MessageConstantEnum(String message) {
        this.message = message;
    }

    public static MessageConstantEnum from(
            Predicate<MessageConstantEnum> filter,
            String key
    ){
        return Arrays
                .stream(values())
                .filter(filter)
                .findFirst()
                .orElseThrow( () ->
                        new IllegalArgumentException(
                                format("MessageConstantEnum [%s] not found",key)
                        )
                );
    }

    public static MessageConstantEnum fromName(
            String name
    ){
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
