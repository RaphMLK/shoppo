package fr.shoppo.ms_stat.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;

public class TimeToLiveConfiguration {
    protected int timeToLive;

    @Value("${mq.x-message-ttl}")
    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
}
