package fr.shoppo.mainmsinterface.infrastructure.config.securite;

import fr.shoppo.mainmsinterface.domain.service.ManageConnexionService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Classe permettant de trouver l'utilisateur selon le token
 */
@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final ManageConnexionService manageConnexionService;

    public AuthenticationProvider(ManageConnexionService manageConnexionService) {
        this.manageConnexionService = manageConnexionService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        Object token = usernamePasswordAuthenticationToken.getCredentials();
        try {
            return Optional
                    .ofNullable(token)
                    .map(String::valueOf)
                    .flatMap(manageConnexionService::findByToken)
                    .orElseThrow(() -> new IllegalAccessError("An error has occurs"));
        } catch (Throwable throwable) {
            logger.info("context",throwable);
            throw new IllegalAccessError("An error has occurs");
        }
    }
}

