package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.ms_commerce.domain.bo.FindCommercantInput;
import fr.shoppo.ms_commerce.domain.bo.Information;
import fr.shoppo.ms_commerce.domain.bo.User;
import fr.shoppo.ms_commerce.domain.bo.UserNewPassword;
import fr.shoppo.ms_commerce.domain.service.*;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantNotFoundException;
import fr.shoppo.ms_commerce.infrastructure.exception.CommerceException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.*;

@Component
public class ManageCommercantController {

    private final CommercantService commercantService;
    private final CommerceService commerceService;
    private final ProductService productService;
    private final NotificationService notificationService;
    private final VfpService vfpService;

    public ManageCommercantController(CommercantService commercantService, CommerceService commerceService, ProductService productService, NotificationService notificationService, VfpService vfpService) {
        this.commercantService = commercantService;
        this.commerceService = commerceService;
        this.productService = productService;
        this.notificationService = notificationService;
        this.vfpService = vfpService;
    }

    @RabbitListener(queues = "${mq.queue.find-commercant}")
    public String findCommercant(FindCommercantInput pair) throws JsonProcessingException {
        var mailOrSiret = pair.getMailOrSiret();
        var info = new Information();
        if(mailOrSiret.contains("@") || ! mailOrSiret.matches("[0-9]{14}")) {
            commercantService.getCommercant(mailOrSiret)
            .ifPresent(c -> {
                var commerce = c.getCommerce();
                info.setCommercant(c);
                info.setCommerce(commerce);
            });
        }else{
            commerceService.findBySiret(mailOrSiret)
            .ifPresent(c ->
                    commercantService.findByCommerce(c)
                            .flatMap(commercants ->
                                    commercants
                                            .stream()
                                            .findFirst())
                            .ifPresent(c2 -> {
                                    info.setCommerce(c);
                                    info.setCommercant(c2);
                            })
            );
        }

        Optional
                .of(pair.isWithProduct())
                .ifPresent( bool -> {
                    if(bool) {
                        var commercant = info.getCommercant();
                        var product =
                                Optional.of(productService.getProductsCommerce(commercant.getEmail()));
                        product.ifPresent(pl -> info.setProductList(Set.copyOf(pl)));
                        var productVfp = Optional.of(vfpService.getVfp(commercant.getEmail()));
                        productVfp.ifPresent(pl -> info.setAdvantages(Set.copyOf(pl)));
                    }
                });
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(info);
    }

    @RabbitListener(queues = "${mq.queue.reset-password}")
    public String resetPassword(String email) {
        try {
            var commercant = commercantService.resetPassword(email);
            if (commercant != null) {
                notificationService.resetPassword(commercant);
                return OK.toString();
            }
            return ERREUR.toString();
        } catch (CommercantNotFoundException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.login}")
    public String login(User user) {
        try {
            Commercant commercant = commercantService.login(user.getEmail(), user.getPassword());
            if(commercant == null)
                return ERREUR.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            return "OK#"+objectMapper.writeValueAsString(commercant);
        } catch (CommerceException | JsonProcessingException exceptionThatOccus) {
            return exceptionThatOccus.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.change-password-need}")
    public String changePasswordNeed(UserNewPassword userNewPassword){
        try{
            var commercant = commercantService.changePasswordNeed(userNewPassword.getEmail(), userNewPassword.getPassword());
            return commercant != null ? OK.toString() : ERREUR.toString();
        } catch (CommerceException e){
            return e.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.get-commercant}")
    public String getCommercant(String email) {
        var objectMapper = new ObjectMapper();
        var containerString = new AtomicReference<>(COMMANDE_NOT_FOUND.toString());
        commercantService.getCommercant(email).ifPresent( commercant -> {
            try {
                containerString.set(objectMapper.writeValueAsString(commercant));
            } catch (JsonProcessingException e) {
                containerString.set(ERREUR.toString());
            }
        });

        return containerString.get();
    }
}
