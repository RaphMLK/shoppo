package fr.shoppo.ms_commerce.domain.service;

import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantNotFoundException;
import fr.shoppo.ms_commerce.infrastructure.exception.CommerceException;

import java.util.List;
import java.util.Optional;

public interface CommercantService {
    Commercant resetPassword(String email) throws CommercantNotFoundException;
    Commercant login(String email, String password) throws CommerceException;
    Optional<Commercant> getCommercant(String email);
    Commercant changePasswordNeed(String email, String password) throws CommerceException;
    Optional<List<Commercant>> findByCommerce(Commerce commerce);
    Optional<Commercant> save(Commercant commercant);
}
