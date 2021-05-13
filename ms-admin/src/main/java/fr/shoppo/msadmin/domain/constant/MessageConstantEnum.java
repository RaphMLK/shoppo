package fr.shoppo.msadmin.domain.constant;

import java.util.Arrays;
import java.util.function.Predicate;

import static java.lang.String.format;

public enum MessageConstantEnum {
    OK("OK"),
    ERREUR("Une erreur est survenue"),
    ADMIN_NOT_FOUND("L'admin n'existe pas"),
    ADMIN_ERROR_LOGIN("Email et/ou mot de passe erroné");

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
