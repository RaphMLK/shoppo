package fr.shoppo.msclient.domain.constant;

import java.util.Arrays;
import java.util.function.Predicate;

import static java.lang.String.format;

public enum MessageConstantEnum {
    OK("OK"),
    ERREUR("Une erreur est survenue"),
    ERREUR_INVALID_INPUT("Une erreur est survenue parametres invalident"),
    CLIENT_NOT_FOUND("Le client n'existe pas"),
    CLIENT_ERROR_LOGIN("Email et/ou mot de passe erroné"),
    ERREUR_SOLDE_A_ZERO("Votre solde ne permet pas de réaliser le paiement"),
    ERREUR_AVANTAGE("Cet avantage est deja actif OU vous ne possedez pas le status VFP.");

    String message;

    MessageConstantEnum(String message) {
        this.message = message;
    }

    public static MessageConstantEnum from(
            Predicate<MessageConstantEnum> filter,
            String key
    ) {
        return Arrays
                .stream(values())
                .filter(filter)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                format("MessageConstantEnum [%s] not found", key)
                        )
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
