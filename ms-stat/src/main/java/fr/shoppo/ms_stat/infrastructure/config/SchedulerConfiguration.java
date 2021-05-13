package fr.shoppo.ms_stat.infrastructure.config;

import fr.shoppo.ms_stat.domain.core.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
@EnableAsync
public class SchedulerConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);
    final static DateFormat DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Calculator calculator;

    @Async
    @Scheduled(cron = "${shoppo.stat.cron}", zone = "${shoppo.stat.zone}")
    public void scheduleFixedDelayTask() {

        logger.info("STATISTICS CALCULATOR WOKE UP AT {}",DATE.format(new Date()));

        logger.info("=> CLIENT PROCESS");
        calculator.addingClientStatistics();

        logger.info("=> COMMERCANT PROCESS");
        calculator.addingCommercantStatistics();

        logger.info("=> ADMIN PROCESS");
        calculator.addingAdminStatistics();

        logger.info("STATISTICS CALCULATOR ASLEEP AT {}",DATE.format(new Date()));
    }

    @Autowired
    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    String cron;

    @Value("${shoppo.stat.cron}")
    public void setCron(String cron) {
        logger.info("CRON TAB SET WITH {}",cron);
        this.cron = cron;
    }
}
