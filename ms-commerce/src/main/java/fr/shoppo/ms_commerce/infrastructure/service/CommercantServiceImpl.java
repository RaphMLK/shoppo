package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.service.CommercantService;
import fr.shoppo.ms_commerce.infrastructure.dao.CommercantDao;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantErrorLogin;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantNotFoundException;
import fr.shoppo.ms_commerce.infrastructure.exception.CommerceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommercantServiceImpl implements CommercantService {

    private final CommercantDao commercantDao;
    PasswordManager passwordManager;

    public CommercantServiceImpl(CommercantDao commercantDao) {
        this.commercantDao = commercantDao;
    }

    @Override
    public Commercant resetPassword(String email) throws CommercantNotFoundException {
        var commercant = commercantDao.findByEmail(email);
        if (commercant != null) {
            var clearPassword = passwordManager.generateSecureRandomPassword();
            commercant.setPassword(passwordManager.hash(clearPassword));
            commercant.setChangePassword(true);
            commercantDao.save(commercant);
            commercant.setPassword(clearPassword);/*On veut que l'utilisateur puisse lire son mdp*/
            return commercant;
        }
        throw new CommercantNotFoundException();
    }

    @Override
    public Commercant login(String email, String password) throws CommerceException {
        var commercant = commercantDao.findByEmail(email);
        if (commercant != null) {
            var hashPass = passwordManager.hash(password);
            if (commercant.getPassword().equals(hashPass)) {
                return commercant;
            }
            throw new CommercantErrorLogin();
        }
        throw new CommercantNotFoundException();
    }

    @Override
    public Optional<Commercant> getCommercant(String email) {
        Commercant commercant = commercantDao.findByEmail(email);
        if (commercant != null) {
           return Optional.of(commercant);
        }
        return Optional.empty();
    }

    @Override
    public Commercant changePasswordNeed(String email, String password) throws CommerceException {
        var commercant = commercantDao.findByEmail(email);
        if(commercant == null){
            throw new CommercantNotFoundException();
        }
        commercant.setPassword(passwordManager.hash(password));
        commercant.setChangePassword(false);
        commercantDao.save(commercant);
        return commercant;
    }

    @Override
    public Optional<List<Commercant>> findByCommerce(Commerce commerce) {
        return Optional.of(commercantDao.findByCommerceSiretCode(commerce.getSiretCode()));
    }

    @Override
    public Optional<Commercant> save(Commercant commercant) {
        return Optional.of(commercantDao.save(commercant));
    }

    @Autowired
    public void setPassworGenerator(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }
}
