package fr.shoppo.mainmsinterface.domain.service;

import fr.shoppo.mainmsinterface.infrastructure.entity.Connexion;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface ManageConnexionService {

    Optional<Connexion> createConnexion(Connexion connexion);
    String generateToken();
    String removeFromToken(String token);

    Optional<User> findByToken(String token);
}
