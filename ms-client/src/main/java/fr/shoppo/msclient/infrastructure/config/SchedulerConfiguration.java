package fr.shoppo.msclient.infrastructure.config;


import fr.shoppo.msclient.domain.bo.AvantageVFP;
import fr.shoppo.msclient.domain.service.VFPStateManager;
import fr.shoppo.msclient.infrastructure.dao.ClientDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableScheduling
@EnableAsync
public class SchedulerConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfiguration.class);
    final static DateFormat DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    VFPStateManager stateManager;
    ClientDao dao;


    @Async
    @Scheduled(cron = "${shoppo.vfp.cron}", zone = "${shoppo.vfp.zone}")
    public void vfpScheduledActions() {
        logger.info("VFP SCHEDULED ACTION BEGIN AT {}",DATE.format(new Date()));

        var clients = dao.findAll();
        AtomicInteger vfpMemberCounter = new AtomicInteger(0);
        dao.findAll().forEach( client -> {
            stateManager.reset(client);
            if(!AvantageVFP.NONE.equals(client.getAvantage()))
                client.setAvantage(AvantageVFP.LOSE);
            if(client.isStatusVFP())
                vfpMemberCounter.incrementAndGet();
        });

        dao.saveAll(clients);
        logger.info("There is {} VFP Member",vfpMemberCounter.get());

        logger.info("VFP SCHEDULED ACTION END AT {}",DATE.format(new Date()));
    }


    @Autowired
    public void setDao(ClientDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setStateManager(VFPStateManager stateManager) {
        this.stateManager = stateManager;
    }
}
