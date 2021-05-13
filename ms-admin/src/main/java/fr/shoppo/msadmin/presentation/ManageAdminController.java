package fr.shoppo.msadmin.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.msadmin.domain.bo.User;
import fr.shoppo.msadmin.domain.service.AdminService;
import fr.shoppo.msadmin.domain.service.NotificationService;
import fr.shoppo.msadmin.infrastructure.entity.Admin;
import fr.shoppo.msadmin.infrastructure.exception.AdminException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static fr.shoppo.msadmin.domain.constant.MessageConstantEnum.ERREUR;
import static fr.shoppo.msadmin.domain.constant.MessageConstantEnum.OK;

@Component
public class ManageAdminController {

    private final AdminService adminService;

    private final NotificationService notificationService;

    public ManageAdminController(AdminService clientService, NotificationService notificationService) {
        this.adminService = clientService;
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${mq.queue.reset-password}")
    public String resetPassword(String email){
        try {
            var client = adminService.resetPassword(email);
            if(client!=null){
                notificationService.resetPassword(client);
                return OK.toString();
            }
            return ERREUR.toString();
        } catch (AdminException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.login}")
    public String login(User user) {
        try {
            Admin admin = adminService.login(user.getEmail(), user.getPassword());
            if(admin == null){
                return ERREUR.toString();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return "OK#"+objectMapper.writeValueAsString(adminService.login(user.getEmail(), user.getPassword()));
        } catch (AdminException | JsonProcessingException exceptionThatOccurs) {
            return exceptionThatOccurs.getMessage();
        }
    }


}
