package fr.shoppo.msadmin.infrastructure.config.mq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes ={
        TimeToLiveConfiguration.class
})
@TestPropertySource(properties = "mq.x-message-ttl=12000")
@SpringBootTest
class TimeToLiveConfigurationTest {

    TimeToLiveConfiguration timeToLiveConfiguration;

    @Test
    void should_retrieve_properties(){
        assertEquals(12000, timeToLiveConfiguration.timeToLive);
    }

    @Autowired
    void setTimeToLiveConf(TimeToLiveConfiguration timeToLiveConfiguration) {
        this.timeToLiveConfiguration = timeToLiveConfiguration;
    }
}