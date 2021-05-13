package fr.shoppo.ms_stat.domain.core;

import fr.shoppo.ms_stat.domain.ClientService;
import fr.shoppo.ms_stat.domain.CommandeService;
import fr.shoppo.ms_stat.domain.StatService;
import fr.shoppo.ms_stat.domain.bo.stat_content.BaseContent;
import fr.shoppo.ms_stat.domain.bo.stat_content.BaseDatedContent;
import fr.shoppo.ms_stat.domain.bo.stat_id.BaseId;
import fr.shoppo.ms_stat.domain.bo.stat_id.StringId;
import fr.shoppo.ms_stat.infrastructure.entity.Client;
import fr.shoppo.ms_stat.infrastructure.entity.Commande;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static fr.shoppo.ms_stat.domain.bo.StatLabel.*;
import static fr.shoppo.ms_stat.domain.bo.UserType.ADMINISTRATEUR;
import static fr.shoppo.ms_stat.domain.bo.UserType.COMMERCANT;
import static fr.shoppo.ms_stat.domain.bo.commande.TypeCommandeEnum.ONLINE;

@Component
@SuppressWarnings(value = "unchecked")
public class Calculator {
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);
    final static DateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");

    StatService service;
    CommandeService commandeService;
    ClientService clientService;

    public void addingClientStatistics(){
        logger.info("All Done.");

    }

    public void addingCommercantStatistics(){
        var lastSave = service.findByCustomId(StringId.of(LAST_SAVE.format()));

        var commandes = new AtomicReference<Map<String,Commande>>(new HashMap<>());
        var now = new Date();

        service.findByCustomId(BaseId.of(COMMERCANT,CMD_H.format()))
                .ifPresent( c -> commandes.set( c.contentValue(Map.class)));

        lastSave.ifPresentOrElse(stat -> {
            var existingsCmd = commandes.get();

            var dateSinceLastSave = stat.contentValue(Date.class);

            stat.content(BaseContent.of("_", now));

            service.saveStat(stat);
            existingsCmd.putAll(commandeService.findAllCommandeSinceLastSave(dateSinceLastSave));
            commandes.set(existingsCmd);

        },() ->{
            service.saveStat(
                    COMMERCANT.from(BaseContent.of(now)).id(StringId.of(LAST_SAVE.format()))
            );
            commandes.set(commandeService.findAllCommande());
        });

        logger.info("ADDING {} COMMANDES", commandes.get().size());

        service.saveStat(
                COMMERCANT.from(
                        BaseDatedContent.of(CMD_H.format(), commandes.get())
                )
        );
        var client_commerce = new HashMap<String, List<Pair<Client,Boolean>>>();
        var ca_commerces = new HashMap<String, Pair<BigDecimal,BigDecimal>>();
        var nb_cmd_commerces = new HashMap<String, Pair<Integer,Integer>>();
        commandes.get().forEach((s, commande) -> {
            var siret = s.split("_")[0];
            var isCnc = commande.getTypeCommande()!=null && ONLINE.equals(commande.getTypeCommande().getLibelle());

            var ca_from_siret = ca_commerces.getOrDefault(siret,Pair.of(BigDecimal.ZERO,BigDecimal.ZERO));

            var newVal = Pair.of(ca_from_siret.getFirst().add(commande.getPrix()),
                    isCnc?
                            ca_from_siret.getSecond().add(commande.getPrix()):
                            ca_from_siret.getSecond());

            ca_commerces.put(siret,newVal);

            var clientList = client_commerce.getOrDefault(siret,new ArrayList<>());

            clientService.findById(commande.getClientId()).ifPresent(c -> clientList.add(Pair.of(c,isCnc)));
            client_commerce.put(siret,clientList);

            var nb_cmd_from_siret = nb_cmd_commerces.getOrDefault(siret,Pair.of(0,0));
            nb_cmd_commerces.put(siret,
                    Pair.of(nb_cmd_from_siret.getFirst()+1,nb_cmd_from_siret.getSecond()+(isCnc?1:0)));

        });

        nb_cmd_commerces.forEach((s, cmds) ->{
            var nb_cmd=cmds.getFirst();
            var nb_cmd_cnc = cmds.getSecond();
            var siretName = NB_CMD.format(s);
            var siretNameCnc = CLICK_AND_COLLECT_ATTR.format(siretName);
            logger.info("COMMERCE {} GET {} Commande",s,nb_cmd);
            service.saveStat(COMMERCANT.from(BaseDatedContent.of(siretName,nb_cmd)));

            logger.info("COMMERCE {} GET {} CnC Commande",s,nb_cmd_cnc);
            service.saveStat(COMMERCANT.from(BaseDatedContent.of(siretNameCnc,nb_cmd_cnc)));
        });

        client_commerce.forEach((s, clients) ->{
            logger.info("COMMERCE {} GET {} CLIENTS", s,clients.size());
            service.saveStat(
                    COMMERCANT.from(BaseDatedContent.of(CLIENT_H.format(s),clients
                            .stream()
                            .map(Pair::getFirst)
                            .collect(Collectors.toList())))
            );
            service.saveStat(
                    COMMERCANT.from(BaseDatedContent.of(NB_CLIENT.format(s),clients.size()))
            );
            var nbCnCClient = (int) clients
                    .stream()
                    .filter(Pair::getSecond).count();
            logger.info("COMMERCE {} GET {} CnC CLIENTS", s,nbCnCClient);


            service.saveStat(
                    COMMERCANT.from(BaseDatedContent.of(CLICK_AND_COLLECT_ATTR.format(NB_CLIENT.format(s)),
                            nbCnCClient))
            );

        });

        ca_commerces.forEach((s, bigDecimal) -> {
            var siretName = CA_ON_SIRET.format(s);
            var ca  = bigDecimal.getFirst();
            var cnc_ca = bigDecimal.getSecond();

            logger.info("CALCULATE {} with {}", siretName,ca);
            service.saveStat(COMMERCANT.from(BaseDatedContent.of(siretName,ca)));
            var siretNameCnc = CLICK_AND_COLLECT_ATTR.format(siretName);

            logger.info("CALCULATE {} with {}", siretNameCnc,cnc_ca);
            service.saveStat(COMMERCANT.from(BaseDatedContent.of(siretNameCnc,cnc_ca)));
        });
    }

    public void addingAdminStatistics(){

        var countVFP = clientService.countVFPClient();

        logger.info("NB CLIENT WITH VFP STATUS : {} ", countVFP);
        service.saveStat(
                ADMINISTRATEUR.from(
                        BaseDatedContent.of(NB_VFP.format(), countVFP)
                )
        );


    }

    @Autowired
    public void setService(StatService service) {
        this.service = service;
    }

    @Autowired
    public void setCommandeService(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
