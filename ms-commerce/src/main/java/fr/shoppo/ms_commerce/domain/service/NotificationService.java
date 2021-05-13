package fr.shoppo.ms_commerce.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.shoppo.ms_commerce.domain.bo.Information;
import fr.shoppo.ms_commerce.infrastructure.entity.Commande;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;

import java.util.List;

public interface NotificationService {
    void resetPassword(Commercant commercant);
    void createCommerce(Information information);
    void createCommande(List<Commande> commandeList) throws JsonProcessingException;
    void addCommercant(Information information);
}
