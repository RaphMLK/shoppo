package fr.shoppo.msnotification.infrastructure.service;

import com.github.mustachejava.MustacheFactory;
import fr.shoppo.msnotification.domain.bo.Template;
import fr.shoppo.msnotification.domain.bo.client.Client;
import fr.shoppo.msnotification.domain.bo.commerce.Commercant;
import fr.shoppo.msnotification.domain.bo.commerce.Commerce;
import fr.shoppo.msnotification.domain.bo.commerce.Information;
import fr.shoppo.msnotification.domain.service.ManageUserNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;

import static java.lang.String.format;

@Service
public class ManageUserNotificationImpl implements ManageUserNotification {
    private static final Logger logger = LoggerFactory.getLogger(ManageUserNotificationImpl.class);

    static final String PATTERN_RENAME = "\"%s\"<%s>";

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.properties.rename}")
    String renameSender;

    MustacheFactory mustacheFactory;

    public ManageUserNotificationImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendNewPassword(String email, String password) {
        var message = new SimpleMailMessage();
        message.setFrom(format(PATTERN_RENAME,renameSender,sender));
        message.setTo(email);
        message.setSubject("Réinitialisation mot de passe Shoppo");
        message.setText("Voici votre nouveau mot de passe : " + password);
        javaMailSender.send(message);
    }

    @Override
    public void sendInformationAboutNewCommerce(Information information) {
        var commerce = information.getCommerce();
        var owner = information.getCommercant();
        commerce.setOwner(owner);
        javaMailSender.send(mimeMessage -> buildMessage(mimeMessage,
                commerce.getEnseigne(),
                owner.getEmail(),
                commerce,
                "templates/create_commerce.html"));
    }

    @Override
    public void sendInformationAboutNewClient(Client client) {
        javaMailSender.send(mimeMessage -> buildMessage(mimeMessage,
                client.getName(),
                client.getEmail(),
                client,
                "templates/create_client.html"));
    }

    @Override
    public void sendInformationAboutAddCommercant(Commercant commercant) {
        javaMailSender.send(mimeMessage -> buildMessage(mimeMessage,
                commercant.getName(),
                commercant.getEmail(),
                commercant,
                "templates/add_commercant.html"));
    }


    String compileTemplate(Template object, String templateString){
        var writer = new StringWriter();
        var template = mustacheFactory.compile(templateString);
        template.execute(writer,object);

        return writer.toString();
    }

    void buildMessage(MimeMessage mimeMessage,
                      String fromName,
                      String fromEmail,
                      Template template,
                      String targetTemplate) throws MessagingException {
        var message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setFrom(format(PATTERN_RENAME,renameSender,sender));
        message.setTo(format(PATTERN_RENAME,fromName,fromEmail));
        message.setSubject(format("Bienvenue %s",fromName));
        message.setText(compileTemplate(template,targetTemplate),true);
        logger.info("Mail has been sent to {} !",fromEmail);
    }

    @Autowired
    public void setMustacheFactory(MustacheFactory mustacheFactory) {
        this.mustacheFactory = mustacheFactory;
    }
}
