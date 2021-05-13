package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.shoppo.ms_commerce.domain.bo.AddVfpByProductGroup;
import fr.shoppo.ms_commerce.domain.service.VfpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ManageVfpControllerTest {

    @InjectMocks
    private ManageVfpController manageVfpController;

    @Mock
    private VfpService vfpService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addVfpByProduct() {
        var addVfpByProductGroup = new AddVfpByProductGroup();
        addVfpByProductGroup.setEmailCommerce("email@gmail.com");
        addVfpByProductGroup.setAddVfpByProducts(new HashSet<>());
        when(vfpService.addVfpByProduct(addVfpByProductGroup)).thenReturn("ok");
        assertEquals("ok", manageVfpController.addVfpByProduct(addVfpByProductGroup));
    }

    @Test
    void deleteVfp() {
        when(vfpService.deleteVfp(1)).thenReturn("ok");
        assertEquals("ok", manageVfpController.deleteVfp(1));
    }

    @Test
    void getVfp() throws JsonProcessingException {
        var email = "email@gmail.com";
        when(vfpService.getVfp(email)).thenReturn(List.of());
        assertEquals("[]", manageVfpController.getVfp(email));
    }
}