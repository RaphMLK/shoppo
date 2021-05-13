package fr.shoppo.mainmsinterface.infrastructure.config.mq;

import org.springframework.beans.factory.annotation.Value;

public class TimeToLiveConfiguration {
    int timeToLive;

    @Value("${mq.x-message-ttl}")
    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
}
