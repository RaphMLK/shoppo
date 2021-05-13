package fr.shoppo.msclient.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.msclient.domain.bo.AvantageInput;
import fr.shoppo.msclient.domain.bo.AvantageVFP;
import fr.shoppo.msclient.domain.bo.SoldeInput;
import fr.shoppo.msclient.domain.bo.UserNewPassword;
import fr.shoppo.msclient.domain.service.ManageClientService;
import fr.shoppo.msclient.domain.service.NotificationService;
import fr.shoppo.msclient.domain.service.VfpUseService;
import fr.shoppo.msclient.infrastructure.entity.Client;
import fr.shoppo.msclient.infrastructure.exception.ClientException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static fr.shoppo.msclient.domain.constant.MessageConstantEnum.*;

@Component
public class ManageClientController {

    private final ManageClientService clientService;

    private final NotificationService notificationService;

    private final VfpUseService vfpUseService;

    public ManageClientController(ManageClientService clientService, NotificationService notificationService, VfpUseService vfpUseService) {
        this.clientService = clientService;
        this.notificationService = notificationService;
        this.vfpUseService = vfpUseService;
    }

    @RabbitListener(queues = "${mq.queue.create-client}")
    public String createClient(Client client){
        try {
            clientService.createClient(client);
            notificationService.createClient(client);
            return OK.toString();
        } catch (ClientException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.reset-password}")
    public String resetPassword(String email){
        try {
            var client = clientService.resetPassword(email);
            if(client!=null){
                notificationService.resetPassword(client);
                return OK.toString();
            }
            return ERREUR.toString();
        } catch (ClientException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.login}")
    public String login(Client client) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return "OK#"+objectMapper.writeValueAsString(clientService.login(client.getEmail(), client.getPassword()));
        } catch (ClientException | JsonProcessingException ex) {
            return ex.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.change-password-need}")
    public String changePasswordNeed(UserNewPassword userNewPassword){
        try {
            clientService.changePasswordNeed(userNewPassword.getEmail(), userNewPassword.getPassword());
            return OK.toString();
        } catch (ClientException ex) {
            return ex.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.update-solde}")
    public String updateSolde(SoldeInput input){
        return clientService.updateSolde(input.getEmail(),input.getAmount(), input.isEffective()) ?
                OK.toString():
                ERREUR_SOLDE_A_ZERO.toString();
    }

    @RabbitListener(queues = "${mq.queue.get-client}")
    public String getClient(String email) {
        try {
            Client client = clientService.getClientByEmail(email);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(client);
        } catch (ClientException | JsonProcessingException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.get-client-qrcode}")
    public String getClientByQrCode(String qrCode) {
        try {
            Client client = clientService.getClientByQrCode(qrCode);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(client);
        } catch (ClientException | JsonProcessingException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.update-avantage}")
    public String updateAvantage(AvantageInput input){
        try{
            return clientService.updateAvantage(input.getEmail(),input.getAvantage()) ?
                    OK.toString()
                    :ERREUR_AVANTAGE.toString();
        }catch (ClientException exception){
            return exception.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.get-all-avantage}")
    public String getAllAvantage() {
         try{
             var mapper = new ObjectMapper();
             return mapper.writeValueAsString(AvantageVFP.listAllAvantages());
         } catch ( JsonProcessingException e) {
             return e.getMessage();
         }
    }

    @RabbitListener(queues = "${mq.queue.is-transport}")
    public boolean isTransportVfp(String qrCode){
        return vfpUseService.clientIsTransport(UUID.fromString(qrCode));
    }

    @RabbitListener(queues = "${mq.queue.is-park}")
    public boolean isParkVfp(String plaque){
        return vfpUseService.clientIsPark(plaque);
    }


}
