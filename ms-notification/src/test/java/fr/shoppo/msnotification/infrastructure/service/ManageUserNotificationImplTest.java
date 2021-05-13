package fr.shoppo.msnotification.infrastructure.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManageUserNotificationImplTest {
    JavaMailSender javaMailSender;
    ManageUserNotificationImpl manageUserNotification;
    @BeforeEach
    void setup(){
        javaMailSender = mock(JavaMailSender.class);

        manageUserNotification = new ManageUserNotificationImpl(javaMailSender);
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
    }

    @Test
    void sendNewPassword(){
        manageUserNotification.sendNewPassword("test","test");
        var message = new SimpleMailMessage();
        message.setFrom("\"null\"<null>");
        message.setTo("test");
        message.setSubject("Réinitialisation mot de passe Shoppo");
        message.setText("Voici votre nouveau mot de passe : test");

        verify(javaMailSender).send(message);
    }
}