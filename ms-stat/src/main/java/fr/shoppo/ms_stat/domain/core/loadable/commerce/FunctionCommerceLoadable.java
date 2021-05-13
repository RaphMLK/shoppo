package fr.shoppo.ms_stat.domain.core.loadable.commerce;

import fr.shoppo.ms_stat.domain.StatService;
import fr.shoppo.ms_stat.domain.bo.Stat;
import fr.shoppo.ms_stat.domain.bo.stat_content.BaseDatedContent;
import fr.shoppo.ms_stat.infrastructure.entity.Commande;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;

import static fr.shoppo.ms_stat.domain.bo.StatLabel.*;
import static fr.shoppo.ms_stat.domain.bo.UserType.COMMERCANT;
import static fr.shoppo.ms_stat.domain.bo.commande.TypeCommandeEnum.ONLINE;


public class FunctionCommerceLoadable {
    private static final Logger logger = LoggerFactory.getLogger(FunctionCommerceLoadable.class);
    static final String LOG_ADD = "Added {} to {}";

    public static void addCAConsumer(Object cmd, StatService service){
        var commande = (Commande)cmd;
        var siret = commande.getCommerceSiretCode();
        var siretName = CA_ON_SIRET.format(siret);
        var vente = commande.getPrix();

        var stat = COMMERCANT.from(BaseDatedContent.of(siretName,vente));
        var typeCommande = commande.getTypeCommande();

        addBigDecimalToSaved(stat,vente,service);
        if(typeCommande != null && ONLINE.equals(typeCommande.getLibelle())) {
            addBigDecimalToSaved(stat.contentName(CLICK_AND_COLLECT_ATTR.format(siretName)), vente, service);
        }

        logger.info(LOG_ADD,vente,siretName);
    }

    public static void addNBClientConsummer(Object cmd, StatService service){
        var commande = (Commande)cmd;
        var siret = commande.getCommerceSiretCode();
        var clientLabel = NB_CLIENT.format(siret);
        var idClient = commande.getClientId();

        var statNb = COMMERCANT.from(BaseDatedContent.of(clientLabel,new Integer[]{idClient}));
        var typeCommande = commande.getTypeCommande();

        increaseNBToSaved(statNb,idClient,service);
        if(typeCommande != null && ONLINE.equals(typeCommande.getLibelle()))
            increaseNBToSaved(statNb.contentName(CLICK_AND_COLLECT_ATTR.format(clientLabel)), idClient, service);

        logger.info(LOG_ADD,idClient,siret);
    }

    static void addBigDecimalToSaved(
            Stat stat,
            BigDecimal amount,
            StatService service){
        service.findByCustomId(stat.getCustomId())
                .ifPresentOrElse(s ->
            service.saveStat(
                stat.contentValue(
                        s.contentValue(BigDecimal.class).add(amount))),
        () -> service.saveStat(stat));
    }

    static void increaseNBToSaved(
            Stat stat,
            int idClient,
            StatService service){
        service.findByCustomId(stat.getCustomId())
                .ifPresentOrElse(s ->{
            var listClients = Arrays.asList(s.contentValue(Integer[].class));
            listClients.add(idClient);
            service.saveStat(
                        stat.contentValue(listClients.toArray()));
        },
        () -> service.saveStat(stat));
    }

}
