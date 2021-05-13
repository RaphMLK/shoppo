package fr.shoppo.ms_commerce.infrastructure.config.mq;

import fr.shoppo.ms_commerce.infrastructure.config.mq.myexchange.*;
import fr.shoppo.ms_commerce.infrastructure.config.mq.stat.ExchangeStatRabbitMq;
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
        AddCommercantMqConfig.class,
        CreateCommerceMqConfig.class,
        DeleteCommercantMqConfig.class,
        AddProductMqConfig.class,
        ChangePasswordNeedMqConfig.class,
        DeleteProductMqConfig.class,
        EditProductMqConfig.class,
        FindCommercantMqConfig.class,
        UpdateQuantityProduitMqConfig.class,
        GetProductMqConfig.class,
        GetProductsCommerceMqConfig.class,
        UpdateCommerceMqConfig.class,
        CreateCommandeMqConfig.class,
        GetAllCommercesMqConfig.class,
        GetCommandesCommerceMqConfig.class,
        GetPanierMqConfig.class,
        UpdatePanierMqConfig.class,
        CreateCommandeByCommerceMqConfig.class,
        GetPanierByCommerceMqConfig.class,
        DeleteVfpByCommerceMqConfig.class,
        AddVfpByProductAndCommerceMqConfig.class,
        GetCommandeMqConfig.class,
        GetCommandesClientMqConfig.class,
        GetProductsNotVfpCommerceMqConfig.class,
        GetProductsVfpCommerceMqConfig.class,
        AddDeleteVfpPanierMqConfig.class,
        UpdateCommandeTraiteeMqConfig.class,
        GetCommercantMqConfig.class,
        ExchangeStatRabbitMq.class

})
@TestPropertySource(locations = "classpath:application.yml")
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