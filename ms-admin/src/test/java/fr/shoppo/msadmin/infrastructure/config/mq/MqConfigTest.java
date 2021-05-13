package fr.shoppo.msadmin.infrastructure.config.mq;

import fr.shoppo.msadmin.infrastructure.config.mq.myexchange.LoginMqConfig;
import fr.shoppo.msadmin.infrastructure.config.mq.myexchange.ResetPasswordMqConfig;
import fr.shoppo.msadmin.infrastructure.config.mq.notification.ExchangeNotificationRabbitMq;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ContextConfiguration(classes ={
        TimeToLiveConfiguration.class,
        LoginMqConfig.class,
        ResetPasswordMqConfig.class,
        ExchangeNotificationRabbitMq.class
})
@TestPropertySource(locations = "classpath:/application-test.yml")
@SpringBootTest
abstract class MqConfigTest {


    @Test
    void should_have_an_exchange_by_conf(){
        var conf = getInstance();

        var nbExchanger = Arrays.stream(conf.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("exchange") )
                .count();

        assertEquals(1,nbExchanger);
        if(nbExchanger == 1) {
            Arrays.stream(conf.getClass().getDeclaredMethods())
                    .filter(method -> method.getName().startsWith("exchange"))
                    .findFirst()
                    .ifPresent(method -> {
                        try {
                            assertSame(AbstractExchange.class, method.invoke(conf).getClass().getAnnotatedSuperclass().getType());
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

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