package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.ms_commerce.domain.bo.AddVfpByProductGroup;
import fr.shoppo.ms_commerce.domain.service.VfpService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManageVfpController {

    private VfpService vfpService;

    @RabbitListener(queues = "${mq.queue.add-vfp-by-product-and-commerce}")
    public String addVfpByProduct(AddVfpByProductGroup addVfpByProductGroup){
        return vfpService.addVfpByProduct(addVfpByProductGroup);
    }

    @RabbitListener(queues = "${mq.queue.delete-vfp-by-commerce}")
    public String deleteVfp(int idVfp){
        return vfpService.deleteVfp(idVfp);
    }

    @RabbitListener(queues = "${mq.queue.get-product-commerce-vfp}")
    public String getVfp(String email) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(vfpService.getVfp(email));
    }

    @Autowired
    public void setVfpService(VfpService vfpService) {
        this.vfpService = vfpService;
    }
}
