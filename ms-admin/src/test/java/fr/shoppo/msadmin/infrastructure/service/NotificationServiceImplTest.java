package fr.shoppo.msadmin.infrastructure.service;

import fr.shoppo.msadmin.domain.bo.UserNewPassword;
import fr.shoppo.msadmin.infrastructure.entity.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private RabbitTemplate template;

    @Mock
    private DirectExchange exchangeNotification;

    private final String routingKeyResetPassword = "routingKeyResetPassword";

    private final String exchangeNotificationName = "exchangeNotification";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationService.setRoutingKeyResetPassword(routingKeyResetPassword);
        when(exchangeNotification.getName()).thenReturn(exchangeNotificationName);
    }

    @Test
    void resetPassword() {
        var email = "toto@gmail.com";
        var password = "toto";
        var admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(password);

        notificationService.resetPassword(admin);

        verify(template).convertAndSend(
                exchangeNotificationName,
                routingKeyResetPassword,
                new UserNewPassword(admin.getEmail(), admin.getPassword())
        );
    }

}