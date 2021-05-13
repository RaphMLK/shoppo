package fr.shoppo.mainmsinterface.infrastructure.service;

import fr.shoppo.mainmsinterface.domain.service.ManageConnexionService;
import fr.shoppo.mainmsinterface.infrastructure.dao.ConnexionDao;
import fr.shoppo.mainmsinterface.infrastructure.entity.Connexion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum.CONNEXION_NOT_FOUND;
import static fr.shoppo.mainmsinterface.domain.constant.MessageConstantEnum.OK;
import static fr.shoppo.mainmsinterface.infrastructure.config.securite.Role.*;


@Service
public class ManageConnexionServiceServiceImpl implements ManageConnexionService {
    private static final Logger logger = LoggerFactory.getLogger(ManageConnexionServiceServiceImpl.class);

    private final ConnexionDao connexionDao;

    public ManageConnexionServiceServiceImpl(ConnexionDao connexionDao) {
        this.connexionDao = connexionDao;
    }

    @Override
    public Optional<Connexion> createConnexion(Connexion connexion) {
        try {
            return Optional.of(connexionDao.save(connexion));
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String removeFromToken(String token) {
        Connexion connexion = connexionDao.findByToken(token);
        if(connexion != null) {
            connexionDao.delete(connexion);
            return OK.toString();
        }
        else {
            return CONNEXION_NOT_FOUND.toString();
        }
    }

    @Override
    public Optional<User> findByToken(String token) {
        Connexion utilisateur = connexionDao.findByToken(token);
        if(utilisateur!= null){
            User user = null;
            String role = utilisateur.getRole();
            if(role.equals(CLIENT.libelle()) || role.equals(COMMERCANT.libelle()) || role.equals(ADMIN.libelle()))
                user = new User(utilisateur.getEmail(), utilisateur.getToken(), true, true, true, true, AuthorityUtils.createAuthorityList(PREFIXE_ROLE.libelle()+ role));
            return Optional.ofNullable(user);
        }
        return  Optional.empty();

    }

}
