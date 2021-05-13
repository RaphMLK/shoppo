package fr.shoppo.msnotification.infrastructure.config.mq;


import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertSame;

@ContextConfiguration(classes ={
        TimeToLiveConfiguration.class,
        ResetPasswordMqConfig.class,
        AddCommercantMqConfig.class,
        CreateCommerceMqConfig.class,
        CreateClientMqConfig.class
})
@TestPropertySource(locations = "classpath:application.yml")
@SpringBootTest
abstract class MqConfigTest {

    @Test
    void should_have_any_number_of_queues(){
        var conf = getInstance();

        var nbQueue = Arrays.stream(conf.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("queue") )
                .count();


        if(nbQueue > 0) {
            Arrays.stream(conf.getClass().getDeclaredMethods())
                    .filter(method -> method.getName().startsWith("queue"))
                    .forEach(queue -> {
                        try {
                            assertSame(Queue.class, queue.invoke(conf).getClass());
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    abstract TimeToLiveConfiguration getInstance();
}