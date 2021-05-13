package fr.shoppo.ms_commerce.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.shoppo.ms_commerce.infrastructure.entity.Commande;

import java.util.List;

public interface StatService {
    void createCommande(List<Commande> commandeList) throws JsonProcessingException;
}
