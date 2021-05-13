package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.ms_commerce.domain.bo.FindCommercantInput;
import fr.shoppo.ms_commerce.domain.bo.Information;
import fr.shoppo.ms_commerce.domain.bo.User;
import fr.shoppo.ms_commerce.domain.bo.UserNewPassword;
import fr.shoppo.ms_commerce.domain.service.*;
import fr.shoppo.ms_commerce.infrastructure.entity.CodeNaf;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantErrorLogin;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantNotFoundException;
import fr.shoppo.ms_commerce.infrastructure.exception.CommerceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManageCommercantControllerTest {

    CommercantService commercantService;
    NotificationService notificationService;
    CommerceService commerceService;
    ProductService productService;
    ManageCommercantController manageCommercantController;
    VfpService vfpService;

    @BeforeEach
    void setup(){
        commercantService = mock(CommercantService.class);
        notificationService = mock(NotificationService.class);
        productService = mock(ProductService.class);
        commerceService = mock(CommerceService.class);
        vfpService = mock(VfpService.class);
        manageCommercantController = new ManageCommercantController(commercantService,commerceService, productService, notificationService, vfpService);

        doNothing().when(notificationService).resetPassword(any());
    }


    @Test
    void should_reset_password_with_success() throws CommercantNotFoundException {
        var commercant = new Commercant();
        when(commercantService.resetPassword(any())).thenReturn(commercant);

        assertEquals(OK.toString(),manageCommercantController.resetPassword(""));
    }

    @Test
    void should_reset_password_with_erreur_if_commercant_is_null() throws CommercantNotFoundException {
        when(commercantService.resetPassword(any())).thenReturn(null);

        assertEquals(ERREUR.toString(),manageCommercantController.resetPassword(""));
    }

    @Test
    void should_reset_password_with_not_found_if_exception_thrown() throws CommercantNotFoundException {
        when(commercantService.resetPassword(any())).thenThrow(new CommercantNotFoundException());

        assertEquals(COMMERCANT_NOT_FOUND.toString(),manageCommercantController.resetPassword(""));
    }

    @Test
    void should_login_with_success() throws CommerceException, JsonProcessingException {
        var commercant = new Commercant();
        var commerce = new Commerce();
        var codeNaf = new CodeNaf();
        commerce.setCodeNaf(codeNaf);
        commercant.setCommerce(commerce);
        when(commercantService.login(any(),any())).thenReturn(commercant);

        ObjectMapper objectMapper = new ObjectMapper();
        assertEquals("OK#"+objectMapper.writeValueAsString(commercant),manageCommercantController.login(new User()));
    }

    @Test
    void should_login_with_erreur_if_commercant_is_null() throws CommerceException {
        when(commercantService.login(any(),any())).thenReturn(null);

        assertEquals(ERREUR.toString(),manageCommercantController.login(new User()));
    }

    @Test
    void should_login_with_not_found_login_if_thrown_exception() throws CommerceException {
        when(commercantService.login(any(), any())).thenThrow(new CommercantNotFoundException());
        assertEquals(COMMERCANT_NOT_FOUND.toString(), manageCommercantController.login(new User()));
    }

    @Test
    void should_login_with_error_login_if_thrown_exception() throws CommerceException {
        when(commercantService.login(any(),any())).thenThrow(new CommercantErrorLogin());
        assertEquals(COMMERCANT_ERROR_LOGIN.toString(),manageCommercantController.login(new User()));
    }

    @Test
    void changePasswordNeedTest() throws CommerceException {
        UserNewPassword userNewPassword = new UserNewPassword();
        userNewPassword.setEmail("test");
        userNewPassword.setPassword("test");

        when(commercantService.changePasswordNeed("test", "test")).thenReturn(null);
        assertEquals(ERREUR.toString(),manageCommercantController.changePasswordNeed(userNewPassword));

        Commercant commercant = new Commercant();

        when(commercantService.changePasswordNeed("test", "test")).thenReturn(commercant);
        assertEquals(OK.toString(),manageCommercantController.changePasswordNeed(userNewPassword));

        when(commercantService.changePasswordNeed("test", "test")).thenThrow(new CommerceException(ERREUR));
        assertEquals(ERREUR.toString(),manageCommercantController.changePasswordNeed(userNewPassword));
    }

    @Test
    void findCommercantTest() throws JsonProcessingException {
        var commerce = new Commerce();
        var commercant = new Commercant();
        commercant.setOwner(true);
        commerce.setCodeNaf(new CodeNaf());
        commercant.setCommerce(commerce);
        commercant.setEmail("aa");

        var products = new ArrayList<Product>();
        var productSet = Set.copyOf(products);

        when(commerceService.findBySiret(anyString())).thenReturn(Optional.of(commerce));
        when(commercantService.getCommercant(anyString())).thenReturn(Optional.of(commercant));
        when(productService.getProductsCommerce(anyString())).thenReturn(products);

        var info = new Information();
        info.setCommercant(commercant);
        info.setCommerce(commerce);
        info.setProductList(productSet);

        ObjectMapper objectMapper = new ObjectMapper();
        var shouldGenerateProducts = manageCommercantController.findCommercant(FindCommercantInput.of("123@123",true));
        //prUnit.setCommerce(null);
        assertEquals(objectMapper.writeValueAsString(info),shouldGenerateProducts);
        assertEquals(objectMapper.writeValueAsString(info), manageCommercantController.findCommercant(FindCommercantInput.of("123@123",false)));
        // Impossible a tester correctement Mockito aime pas la liaison entre commerce et commercants...
        //assertEquals(info, manageCommercantController.findCommercant(Pair.of("12345678912345",false)));
    }
}