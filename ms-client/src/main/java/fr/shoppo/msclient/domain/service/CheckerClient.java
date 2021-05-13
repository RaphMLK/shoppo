package fr.shoppo.msclient.domain.service;

import fr.shoppo.msclient.infrastructure.entity.Client;
import fr.shoppo.msclient.infrastructure.exception.ClientException;
import fr.shoppo.msclient.infrastructure.service.PasswordManager;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.util.stream.Stream;

import static fr.shoppo.msclient.domain.constant.MessageConstantEnum.ERREUR_INVALID_INPUT;

@Component
public class CheckerClient {

    PasswordManager passwordManager;

    public boolean checkClient(Client client) throws ClientException {
        if(client == null
            || client.getPassword() == null
            || client.getEmail() == null)
            throw new ClientException(ERREUR_INVALID_INPUT,client!=null?client.toString():"client is null");

        var parsedPassword = passwordInputParser(client.getPassword());
        client.setPassword(parsedPassword);
        return mailValidator(client.getEmail());
    }

    public boolean mailValidator(String mail){
        return EmailValidator
                .getInstance()
                .isValid(mail);
    }

    public String passwordInputParser(String password) throws ClientException {
        return Stream
                .of(defaultInputParser(password))
                .map(passwordManager::hash)
                .findFirst()
                .orElseThrow(() -> new ClientException(ERREUR_INVALID_INPUT,"Password is malformed"));
    }

    public String defaultInputParser(String input) throws ClientException {
        return Stream.of(input)
                .map(HtmlUtils::htmlEscape)
                .map(StringEscapeUtils::escapeJava)
                .map(StringEscapeUtils::escapeJson)
                .map(StringEscapeUtils::escapeEcmaScript)
                .map(StringEscapeUtils::escapeXSI)
                .map(StringEscapeUtils::escapeHtml3)
                .map(StringEscapeUtils::escapeHtml4)
                .map(StringEscapeUtils::escapeXml11)
                .findFirst()
                .orElseThrow(() -> new ClientException(ERREUR_INVALID_INPUT,"Input is malformed"));
    }

    @Autowired
    public void setPasswordManager(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }
}
