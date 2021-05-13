package fr.shoppo.ms_stat.infrastructure.implementation;


import fr.shoppo.ms_stat.domain.CommandeService;
import fr.shoppo.ms_stat.infrastructure.dao.CommandeDao;
import fr.shoppo.ms_stat.infrastructure.entity.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class CommandeServiceImpl implements CommandeService {
    CommandeDao commandeDao;
    final static DateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public Map<String,Commande> findAllCommande() {
        var cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, -5);
        Date fiveYearsAgo = cal.getTime();

        return findCommandeBetween(fiveYearsAgo,today);
    }

    @Override
    public Map<String, Commande> findAllCommandeSinceLastSave(Date lastSave){
        return findCommandeBetween(lastSave,new Date());
    }

    Map<String, Commande> findCommandeBetween(Date start, Date end){
        var commandList = commandeDao.findByDateCreationBetween(start,end);
        return commandList.stream()
                .collect(Collectors.toMap(commande ->
                    format("%s_%s_%s",commande.getCommerceSiretCode(),commande.getClientId(),commande.getId()),
                commande -> {
                    commande.setCommerceSiretCode(null);
                    return commande;
                }));

    }

    @Autowired
    public void setCommandeDao(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }
}
